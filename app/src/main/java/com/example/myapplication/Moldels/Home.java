package com.example.myapplication.Moldels;

import android.app.Application;
import android.content.Intent;

import com.example.myapplication.SplashActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseUser firebaseUser  = firebaseAuth.getCurrentUser();
//
//        if(firebaseUser != null ){
//            Intent myIntent = new Intent(Home.this, SplashActivity.class);
//            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(myIntent);
//        }




    }
}
