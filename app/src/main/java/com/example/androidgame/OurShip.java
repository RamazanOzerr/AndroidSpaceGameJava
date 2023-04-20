package com.example.androidgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class OurShip {
    Context context;
    Bitmap ourSpaceship;
    int ox, oy;
    Random random;

    public OurShip(Context context, int our_char) {
        this.context = context;
        ourSpaceship = BitmapFactory.decodeResource(context.getResources(), our_char);
        random = new Random();
        ox = random.nextInt(GameMain.screenWidth);
        oy = GameMain.screenHeight - ourSpaceship.getHeight();
    }

    public Bitmap getOurSpaceship(){
        return ourSpaceship;
    }

    int getOurSpaceshipWidth(){
        return ourSpaceship.getWidth();
    }
}
