package com.example.ideafoundation.meetaapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ideafoundation on 31/01/17.
 */
//Splash screen
public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    SharedPreferences sharedpreferences;
    String username,useremail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = getSharedPreferences("logindata", Context.MODE_PRIVATE);
        username=sharedpreferences.getString("username",null);
        useremail=sharedpreferences.getString("useremail",null);
        setContentView(R.layout.splash_activity);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                // if username not null or empty then move to CreateNeedActivity
                if(username != null && !username.isEmpty())
                {
                    Intent intent=new Intent(SplashActivity.this,CreateNeedActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                // else move to CompleteProfileActivity
                else
                {
                    Intent mainIntent = new Intent(SplashActivity.this,CompleteProfileActivity.class);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    //finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
