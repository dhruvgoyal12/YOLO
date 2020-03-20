package com.example.yolo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashS extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashs);


        android.os.Handler handler1= new android.os.Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashS.this, MainActivity.class));
                finish();
            }
        },1000);



    }
    @Override
    public void onBackPressed(){
        System.exit(0);
    }
}
