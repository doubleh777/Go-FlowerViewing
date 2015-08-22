package com.HWHH.henry.goflower_viewing;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.Random;


public class YoutubeMusicPlayActivity extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener{

    private YouTubePlayerView ytpv;
    private YouTubePlayer ytp;
    private DAO dao;
    private MusicArrayList playList;
    private MusicArrayList tmpPlayList;
    private Random rand;
    private AdView adView;
    private int curPlayingMusicNum;
    private TextView musicTitle;

    protected  void onPause(){
        super.onPause();

        if(ytp != null) {
            int playTime = ytp.getCurrentTimeMillis();

            ytp.loadVideo(playList.get(curPlayingMusicNum).musicId, playTime);

            onResume();
            ytp.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                @Override
                public void onLoading() {

                }

                @Override
                public void onLoaded(String s) {

                }

                @Override
                public void onAdStarted() {

                }

                @Override
                public void onVideoStarted() {

                }

                @Override
                public void onVideoEnded() {
                    curPlayingMusicNum = rand.nextInt(tmpPlayList.size());
                    ytp.loadVideo(tmpPlayList.get(curPlayingMusicNum).musicId);
                    musicTitle.setText(tmpPlayList.get(curPlayingMusicNum).musicTitle);
                    tmpPlayList.remove(curPlayingMusicNum);
                    if (tmpPlayList.isEmpty()) {
                        for (Music music : playList) {
                            tmpPlayList.add(music);
                        }
                    }

                }

                @Override
                public void onError(YouTubePlayer.ErrorReason errorReason) {

                }
            });
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_music_player);

        musicTitle = (TextView)findViewById(R.id.music_title);


//        //애드몹 달기
//        adView = (AdView)findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//
//        adView.loadAd(adRequest);


        //데이터 가져오기
        dao = new DAO(this);
        String version = getIntent().getExtras().getString("version");
        tmpPlayList = new MusicArrayList();

        if(version.equals("v1")){
            //버젼 1인 경우
            playList = dao.getPlayListV1();
            for(Music music : playList){
                tmpPlayList.add(music);
            }

        }else{
            //버전 2인 경우
            playList = dao.getPlayListV2();
            for(Music music : playList){
                tmpPlayList.add(music);
            }

        }

        //유튜브 플레이어 api사용을 위해 구글에서 발급받은 사용자 인증키 입력
        ytpv = (YouTubePlayerView)findViewById(R.id.youtubeplayer);

        PropertyManager pm = new PropertyManager(this);


        ytpv.initialize(pm.getAPIKey("YouTubeAPIKey"), this);


    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        ytp = youTubePlayer;

        if (ytp != null) {
            //첫 음악 재생시

                rand = new Random(System.currentTimeMillis());
                curPlayingMusicNum = rand.nextInt(tmpPlayList.size());
                ytp.loadVideo(tmpPlayList.get(curPlayingMusicNum).musicId);
                musicTitle.setText(tmpPlayList.get(curPlayingMusicNum).musicTitle);
                tmpPlayList.remove(curPlayingMusicNum);


            //두번째 이후 음악 재생시
                ytp.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onLoaded(String s) {

                    }

                    @Override
                    public void onAdStarted() {

                    }

                    @Override
                    public void onVideoStarted() {

                    }

                    @Override
                    public void onVideoEnded() {
                        curPlayingMusicNum = rand.nextInt(tmpPlayList.size());
                        Log.i("test", "random Number : " + curPlayingMusicNum);

                        ytp.loadVideo(tmpPlayList.get(curPlayingMusicNum).musicId);
                        musicTitle.setText(tmpPlayList.get(curPlayingMusicNum).musicTitle);
                        tmpPlayList.remove(curPlayingMusicNum);
                        if(tmpPlayList.isEmpty()){
                            for(Music music : playList){
                                tmpPlayList.add(music);
                            }
                        }
                    }

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {

                    }
                });

        }
    }
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        this.DialogSimple();
    }



    private void DialogSimple(){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
       alt_bld.setMessage("YouTube플레이어 초기화에 실패하였습니다." +
                            "YouTube 앱이 설치되어 있지 않을 가능성이 있습니다. YouTube앱을 다운하시겠습니까?").setCancelable(
                false).setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Uri uri = Uri.parse("market://details?id=com.google.android.youtube");
                        Intent it = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(it);

                    }
                }).setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alt_bld.create();
        // Title for AlertDialog
        alert.setTitle("YouTube앱을 설치해주세요!");
        // Icon for AlertDialog
        alert.show();
    }
}
