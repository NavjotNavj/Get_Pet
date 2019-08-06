package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.myapplication.Moldels.FullScreen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser  = firebaseAuth.getCurrentUser();
        FullScreen fullScreen = new FullScreen(SplashActivity.this);
        fullScreen.changeStatusBarColor();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                nextpage();

            }
        },SPLASH_TIME_OUT);


    }

   private void nextpage(){
    if(firebaseUser != null ){
        Intent myIntent = new Intent(SplashActivity.this, listing_animals.class);
        startActivity(myIntent);
        finish();
    }
    else if(firebaseUser == null ){
        Intent myIntent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(myIntent);
        finish();
    }

}

}
