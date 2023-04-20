package com.example.androidgame;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameMain(this,
                getIntent().getIntExtra("our_char",R.drawable.rocket3),
                getIntent().getIntExtra("enemy",R.drawable.ufo)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}