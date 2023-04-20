package com.example.androidgame;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import de.hdodenhof.circleimageview.CircleImageView;

public class WonActivity extends AppCompatActivity {

    private CircleImageView imageRestart, imageExit;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won);

        mediaPlayer = MediaPlayer.create(this, R.raw.win);
        mediaPlayer.start();

        imageRestart = findViewById(R.id.image_restart);
        imageExit = findViewById(R.id.image_exit);

        setListeners();
    }

    private void setListeners() {

        imageRestart.setOnClickListener(view -> {
            startActivity(new Intent(this, StartActivity.class));
            mediaPlayer.stop();
            finish();
        });

        imageExit.setOnClickListener(view -> {
            mediaPlayer.stop();
            finish();
        });

    }
}