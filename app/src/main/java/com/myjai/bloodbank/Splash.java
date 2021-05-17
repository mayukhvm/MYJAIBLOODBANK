package com.myjai.bloodbank;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread th = new Thread()
        {
            @Override
            public void run() {
                try
                {
                    sleep(4000);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    Intent var = new Intent(Splash.this, MainActivity.class);
                    startActivity(var);
                }
            }
        };
        th.start();
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        finish();
    }
}
