package com.example.dimazatolokin.myapplication.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.dimazatolokin.myapplication.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();
    }
}
