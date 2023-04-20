package com.example.androidgame;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class GameMain extends View {

    MediaPlayer musicShoted;
    Context context;
    Bitmap background, lifeImage;
    Handler handler;
    long UPDATE_MILLIS = 30;
    static int screenWidth, screenHeight;
    int points = 100;
    int life = 5;
    Paint scorePaint;
    int TEXT_SIZE = 80;
    boolean paused = false;
    OurShip ourShip;
    Enemy enemySpaceship;
    Random random;
    ArrayList<Shot> enemyShots, ourShots;
    Explosion explosion;
    ArrayList<Explosion> explosions;
    boolean enemyShotAction = false;
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };


    public GameMain(Context context, int our_char, int enemy) {
        super(context);
        this.context = context;
        musicShoted = MediaPlayer.create(context, R.raw.nuclear);
//        musicMain.start();
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        random = new Random();
        enemyShots = new ArrayList<>();
        ourShots = new ArrayList<>();
        explosions = new ArrayList<>();
        ourShip = new OurShip(context, our_char);
        enemySpaceship = new Enemy(context, enemy);
        handler = new Handler();
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.space);
        lifeImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.life);
        scorePaint = new Paint();
        scorePaint.setColor(Color.RED);
        scorePaint.setTextSize(TEXT_SIZE);
        scorePaint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Draw background, Points and life on Canvas
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawText("enemy: " + points, 0, TEXT_SIZE, scorePaint);
        for(int i=life; i>=1; i--){
            canvas.drawBitmap(lifeImage, screenWidth - lifeImage.getWidth() * i, 0, null);
        }

        if(points == 0){
            paused = true;
            handler = null;
//            musicMain.stop();
            musicShoted.stop();
            Intent intent = new Intent(context, WonActivity.class);
//            intent.putExtra("points", points);
            context.startActivity(intent);
            ((Activity) context).finish();
        }

        // When life becomes 0, stop game and launch GameOver Activity with points
        if(life == 0){
            paused = true;
            handler = null;
//            musicMain.stop();
            musicShoted.stop();
            Intent intent = new Intent(context, GameOverActivity.class);
            intent.putExtra("points", points);
            context.startActivity(intent);
            ((Activity) context).finish();
        }
        // Move enemySpaceship
        enemySpaceship.ex += enemySpaceship.enemyVelocity;
        // If enemySpaceship collides with right wall, reverse enemyVelocity
        if(enemySpaceship.ex + enemySpaceship.getEnemySpaceshipWidth() >= screenWidth){
            enemySpaceship.enemyVelocity *= -1;
        }
        // If enemySpaceship collides with left wall, again reverse enemyVelocity
        if(enemySpaceship.ex <=0){
            enemySpaceship.enemyVelocity *= -1;
        }
        // Till enemyShotAction is false, enemy should fire shots from random travelled distance
        if(enemyShotAction == false){
            if(enemySpaceship.ex >= 200 + random.nextInt(400)){
                Shot enemyShot = new Shot(context, enemySpaceship.ex + enemySpaceship.getEnemySpaceshipWidth() / 2, enemySpaceship.ey );
                enemyShots.add(enemyShot);
                // We're making enemyShotAction to true so that enemy can take a shot at a time
                enemyShotAction = true;
            }
            if(enemySpaceship.ex >= 400 + random.nextInt(800)){
                Shot enemyShot = new Shot(context, enemySpaceship.ex + enemySpaceship.getEnemySpaceshipWidth() / 2, enemySpaceship.ey );
                enemyShots.add(enemyShot);
                // We're making enemyShotAction to true so that enemy can take a shot at a time
                enemyShotAction = true;
            }
            else{
                Shot enemyShot = new Shot(context, enemySpaceship.ex + enemySpaceship.getEnemySpaceshipWidth() / 2, enemySpaceship.ey );
                enemyShots.add(enemyShot);
                // We're making enemyShotAction to true so that enemy can take a shot at a time
                enemyShotAction = true;
            }
        }
        // Draw the enemy Spaceship
        canvas.drawBitmap(enemySpaceship.getEnemySpaceship(), enemySpaceship.ex, enemySpaceship.ey, null);
        // Draw our spaceship between the left and right edge of the screen
        if(ourShip.ox > screenWidth - ourShip.getOurSpaceshipWidth()){
            ourShip.ox = screenWidth - ourShip.getOurSpaceshipWidth();
        }else if(ourShip.ox < 0){
            ourShip.ox = 0;
        }
        // Draw our Spaceship
        canvas.drawBitmap(ourShip.getOurSpaceship(), ourShip.ox, ourShip.oy, null);
        // Draw the enemy shot downwards our spaceship and if it's being hit, decrement life, remove
        // the shot object from enemyShots ArrayList and show an explosion.
        // Else if, it goes away through the bottom edge of the screen also remove
        // the shot object from enemyShots.
        // When there is no enemyShots no the screen, change enemyShotAction to false, so that enemy
        // can shot.
        for(int i=0; i < enemyShots.size(); i++){
            enemyShots.get(i).shy += 15;
            canvas.drawBitmap(enemyShots.get(i).getShot(), enemyShots.get(i).shx, enemyShots.get(i).shy, null);
            if((enemyShots.get(i).shx >= ourShip.ox)
                    && enemyShots.get(i).shx <= ourShip.ox + ourShip.getOurSpaceshipWidth()
                    && enemyShots.get(i).shy >= ourShip.oy
                    && enemyShots.get(i).shy <= screenHeight){
                musicShoted.start();
                life--;
                enemyShots.remove(i);
                explosion = new Explosion(context, ourShip.ox, ourShip.oy);
                explosions.add(explosion);

            }else if(enemyShots.get(i).shy >= screenHeight){
                enemyShots.remove(i);
            }
            if(enemyShots.size() < 1){
                enemyShotAction = false;
            }
        }
        // Draw our spaceship shots towards the enemy. If there is a collision between our shot and enemy
        // spaceship, increment points, remove the shot from ourShots and create a new Explosion object.
        // Else if, our shot goes away through the top edge of the screen also remove
        // the shot object from enemyShots ArrayList.
        for(int i=0; i < ourShots.size(); i++){
            ourShots.get(i).shy -= 15;
            canvas.drawBitmap(ourShots.get(i).getShot(), ourShots.get(i).shx, ourShots.get(i).shy, null);
            if((ourShots.get(i).shx >= enemySpaceship.ex)
                    && ourShots.get(i).shx <= enemySpaceship.ex + enemySpaceship.getEnemySpaceshipWidth()
                    && ourShots.get(i).shy <= enemySpaceship.getEnemySpaceshipWidth()
                    && ourShots.get(i).shy >= enemySpaceship.ey){
                musicShoted.start();
                points-=10;
                ourShots.remove(i);
                explosion = new Explosion(context, enemySpaceship.ex, enemySpaceship.ey);
                explosions.add(explosion);
            }else if(ourShots.get(i).shy <=0){
                ourShots.remove(i);
            }
        }
        // Do the explosion
        for(int i=0; i < explosions.size(); i++){
            canvas.drawBitmap(explosions.get(i).getExplosion(explosions.get(i).explosionFrame), explosions.get(i).eX, explosions.get(i).eY, null);
            explosions.get(i).explosionFrame++;
            if(explosions.get(i).explosionFrame > 8){
                explosions.remove(i);
            }
        }
        // If not paused, we’ll call the postDelayed() method on handler object which will cause the
        // run method inside Runnable to be executed after 30 milliseconds, that is the value inside
        // UPDATE_MILLIS.
        if(!paused)
            handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int)event.getX();
        // When event.getAction() is MotionEvent.ACTION_UP, if ourShots arraylist size < 1,
        // create a new Shot.
        // This way we restrict ourselves of making just one shot at a time, on the screen.
        if(event.getAction() == MotionEvent.ACTION_UP){
            if(ourShots.size() < 1){ // we can shot only once
                Shot ourShot = new Shot(context, ourShip.ox + ourShip.getOurSpaceshipWidth() / 2, ourShip.oy);
                ourShots.add(ourShot);
            }
        }
        // When event.getAction() is MotionEvent.ACTION_DOWN, control ourShip
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            ourShip.ox = touchX;
        }
        // When event.getAction() is MotionEvent.ACTION_MOVE, control ourShip
        // along with the touch.
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            ourShip.ox = touchX;
        }
        // Returning true in an onTouchEvent() tells Android system that you already handled
        // the touch event and no further handling is required.
        return true;
    }
}
