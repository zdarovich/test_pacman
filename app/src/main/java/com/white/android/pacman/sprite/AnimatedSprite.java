package com.white.android.pacman.sprite;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;

import com.white.android.pacman.engine.GameEngine;

/**
 * Created by San4o on 12.01.2017.
 */

public abstract class AnimatedSprite extends Sprite {

    private AnimationDrawable animationDrawable;
    private int totalTime;
    private long currentTime;

    public AnimatedSprite(GameEngine gameEngine, int drawableRes) {
        super(gameEngine, drawableRes);

        animationDrawable = (AnimationDrawable) spriteDrawable;
        // Calculate the total time of the animation
        totalTime = 0;
        for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
            totalTime += animationDrawable.getDuration(i);
        }

    }

    @Override
    public Bitmap obtainDefaultBitmap() {
        AnimationDrawable ad = (AnimationDrawable) spriteDrawable;
        return ((BitmapDrawable) ad.getFrame(0)).getBitmap();
    }


    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

        currentTime += elapsedMillis;
        if (currentTime > totalTime) {
            if (animationDrawable.isOneShot()) {
                return;
            } else {
                currentTime = currentTime % totalTime;
            }
        }
        long animationElapsedTime = 0;
        for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
            animationElapsedTime += animationDrawable.getDuration(i);
            if (animationElapsedTime > currentTime) {
                bitmap = ((BitmapDrawable) animationDrawable.getFrame(i)).getBitmap();
                break;
            }
        }
    }
}


