package com.techipinfotech.onlinestudy1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;

import androidx.appcompat.app.AppCompatActivity;

import com.techipinfotech.onlinestudy1.intro.WelcomeActivity;


public class SplashActivity extends AppCompatActivity {


    private Animation animation;
    Runnable runnable;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.removeCallbacks(runnable);
                startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
                finish();
            }
        }, 3000);
    }


}
