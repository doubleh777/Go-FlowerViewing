package com.HWHH.henry.goflower_viewing;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler hd = new Handler();
        hd.postDelayed(new Runnable(){
            public void run() {
                finish();
            }
        },2500);
        overridePendingTransition(0, 0);
    }

}
