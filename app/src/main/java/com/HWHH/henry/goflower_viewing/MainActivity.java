package com.HWHH.henry.goflower_viewing;


import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    ImageButton playButtonV1;
    ImageButton playButtonV2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, SplashActivity.class));
        setContentView(R.layout.activity_main);

        //CPU WakeLock 해제
        //CpuWakeLocker.cpuWakeRelease();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        playButtonV1 = (ImageButton)findViewById(R.id.playButtonV1);
        playButtonV1.setOnClickListener(this);

        playButtonV2 = (ImageButton)findViewById(R.id.playButtonV2);
        playButtonV2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.playButtonV1:
                Intent playV1Intent = new Intent(this, YoutubeMusicPlayActivity.class);
                playV1Intent.putExtra("version","v1");
                startActivity(playV1Intent);
                break;

            case R.id.playButtonV2:
                Intent playV2Intent = new Intent(this, YoutubeMusicPlayActivity.class);
                playV2Intent.putExtra("version","v2");
                startActivity(playV2Intent);
                break;
        }
    }
}
