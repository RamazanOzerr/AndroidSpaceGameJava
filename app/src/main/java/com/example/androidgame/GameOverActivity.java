package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {

    TextView tvPoints;
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        mediaPlayer = MediaPlayer.create(this, R.raw.fail);
        mediaPlayer.start();
        int points = getIntent().getExtras().getInt("points");
        tvPoints = findViewById(R.id.tvPoints);
        tvPoints.setText("" + points);
    }

    public void restart(View view) {
        Intent intent = new Intent(GameOverActivity.this, StartActivity.class);
        startActivity(intent);
        mediaPlayer.stop();
        finish();
    }

    public void exit(View view) {
        mediaPlayer.stop();
        finish();
    }
}
