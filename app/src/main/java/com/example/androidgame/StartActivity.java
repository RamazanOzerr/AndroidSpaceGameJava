package com.example.androidgame;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class StartActivity extends AppCompatActivity {

    private CircleImageView image1, image2;
    private Intent intent;
    private int ourCharacter, enemy;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        intent = new Intent(this, MainActivity.class);

        image1.setOnClickListener(view -> {
//            ourCharacter = R.drawable.rte_3;
//            enemy = R.drawable.kilicdar;
            ourCharacter = R.drawable.rocket6;
            enemy = R.drawable.ufo1;
            startGame();
        });

        image2.setOnClickListener(view -> {
//            ourCharacter =  R.drawable.kilicdar;
//            enemy = R.drawable.rte_3;
            ourCharacter =  R.drawable.ufo1;
            enemy = R.drawable.rocket6;
            startGame();
        });
    }

    private void startGame(){
        intent.putExtra("our_char", ourCharacter);
        intent.putExtra("enemy", enemy);
        startActivity(intent);
    }

}
