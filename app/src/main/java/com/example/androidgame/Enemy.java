package com.example.androidgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Enemy {
    Context context;
    Bitmap enemySpaceship;
    int ex, ey;
    int enemyVelocity;
    Random random;

    public Enemy(Context context, int enemy) {
        this.context = context;
        enemySpaceship = BitmapFactory.decodeResource(context.getResources(), enemy);
        random = new Random();
        ex = 200 + random.nextInt(400);
        ey = 0;
        enemyVelocity = 14 + random.nextInt(10);
    }

    public Bitmap getEnemySpaceship(){
        return enemySpaceship;
    }

    int getEnemySpaceshipWidth(){
        return enemySpaceship.getWidth();
    }

    int getEnemySpaceshipHeight(){
        return enemySpaceship.getHeight();
    }
}