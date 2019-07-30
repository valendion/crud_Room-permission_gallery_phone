package com.example.kontak_app;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen_activity extends AppCompatActivity {

    private static int LamaTampilSplash = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MULAI KODING

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // KODING

        setContentView(R.layout.activity_splash_screen);


        //KODING LAGI

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // to do auto generated stub
                Intent intent = new Intent(SplashScreen_activity.this, MainActivity.class);
                startActivity(intent);

                // jeda setelah splashscren

                this.selesai();
            }

            private void selesai() {
                //auto
            }
        }, LamaTampilSplash);
    }
}
