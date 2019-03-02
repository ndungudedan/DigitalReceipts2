package com.example.dedan.digitalreceipts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splash extends Activity {
    int splashTime=4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent main=new Intent(splash.this,MainActivity.class);
                startActivity(main);
                finish();
            }
        },splashTime);
    }
}
