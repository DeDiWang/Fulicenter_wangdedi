package cn.ucai.fulicenter.activity;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.utils.MFGT;

public class SplashActivity extends AppCompatActivity {
    private long splashTime=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                //create dbs
                long castTime = System.currentTimeMillis() - currentTime;
                if(castTime<splashTime){
                    try {
                        Thread.sleep(splashTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //startActivity(new Intent(SplashActivity.this,MainActivity.class));
                MFGT.gotoMainActivity(SplashActivity.this);
                MFGT.finish(SplashActivity.this);
            }
        }).start();

    }
}
