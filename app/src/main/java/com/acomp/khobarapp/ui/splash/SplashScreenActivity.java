package com.acomp.khobarapp.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.acomp.khobarapp.R;
import com.acomp.khobarapp.ui.home.HomeActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread splashScreenThread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    Log.e(this.getClass().getSimpleName(), "showSplashScreen: " + e.getMessage());
                } finally {
                    Intent moveToHomeActivityIntent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                    startActivity(moveToHomeActivityIntent);
                    finish();
                }
            }
        };
        splashScreenThread.start();
    }
}
