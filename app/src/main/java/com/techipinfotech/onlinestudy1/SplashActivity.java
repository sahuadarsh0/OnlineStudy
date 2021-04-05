package com.techipinfotech.onlinestudy1;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;

import androidx.appcompat.app.AppCompatActivity;
import com.rezwan.knetworklib.KNetwork;
import com.techipinfotech.onlinestudy1.intro.WelcomeActivity;
import org.jetbrains.annotations.Nullable;
import technited.minds.androidutils.MD;



public class SplashActivity extends AppCompatActivity implements KNetwork.OnNetWorkConnectivityListener {


    private Animation animation;
    Runnable runnable;
    Handler handler;
    KNetwork.Request knRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        knRequest = KNetwork.INSTANCE.bind(this, getLifecycle())
                .showKNDialog(false)
                .setConnectivityListener(this);

    }

    public void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onNetConnected() {
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

    @Override
    public void onNetDisConnected() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                MD.alert(SplashActivity.this, "No Internet", "Please check your internet connection and try again!!!", "yes");
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    @Override
    public void onNetError(@Nullable String s) {

    }
}
