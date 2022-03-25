package com.nevesti.www.bairroseguro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.nevesti.www.bairroseguro.Config;
import com.nevesti.www.bairroseguro.R;

public class ActivitySplash extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        new CountDownTimer(Config.SPLASH_TIME, 500) {

            @Override
            public void onFinish() {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
                finish();

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onTick(long millisUntilFinished) {

            }
        }.start();

    }
}
