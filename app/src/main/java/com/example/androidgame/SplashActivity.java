package com.example.androidgame;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ConstraintLayout constraintLayout = findViewById(R.id.constraint_splash);

        constraintLayout.setOnClickListener(view -> {
            startActivity(new Intent(this, StartActivity.class));
        });


    }
}