package com.white.android.pacman.sprite;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;

import com.white.android.pacman.engine.GameEngine;
import com.white.android.pacman.engine.ScreenGameObject;

/**
 * Created by San4o on 16.01.2017.
 */

public abstract class Animation extends ScreenGameObject{

    private AnimationDrawable animationDrawable1;
    private AnimationDrawable animationDrawable2;
    private AnimationDrawable animationDrawable3;
    private AnimationDrawable animationDrawable4;

    private AnimationDrawable currentAnim;
    private int totalTime;
    private long currentTime;

    protected double pixelFactor;

    protected Bitmap bitmap;

    private final Matrix matrix = new Matrix();




    public Animation (GameEngine gameEngine, int drawableResUp, int drawableResDown, int drawableResRight,
                      int drawableResLeft) {
        Resources r = gameEngine.getContext().getResources();
        animationDrawable1 = (AnimationDrawable) r.getDrawable(drawableResUp);
        animationDrawable2 = (AnimationDrawable) r.getDrawable(drawableResDown);
        animationDrawable3 = (AnimationDrawable) r.getDrawable(drawableResRight);
        animationDrawable4 = (AnimationDrawable) r.getDrawable(drawableResLeft);
        pixelFactor = gameEngine.pixelFactor;


        initFirstDrawable(animationDrawable1);

    }

    public void initFirstDrawable(AnimationDrawable anim) {

        height = (int) (anim.getIntrinsicHeight() * pixelFactor);
        width = (int) (anim.getIntrinsicWidth() * pixelFactor);
        bitmap = ((BitmapDrawable) anim.getFrame(0)).getBitmap();
    }


    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

        currentTime += elapsedMillis;
        if (currentTime > totalTime) {
            if (currentAnim.isOneShot()) {
                return;
            } else {
                currentTime = currentTime % totalTime;
            }
        }
        long animationElapsedTime = 0;
        for (int i = 0; i < currentAnim.getNumberOfFrames(); i++) {
            animationElapsedTime += currentAnim.getDuration(i);
            if (animationElapsedTime > currentTime) {
                bitmap = ((BitmapDrawable) currentAnim.getFrame(i)).getBitmap();
                break;
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {

        if (x > canvas.getWidth()
                || y > canvas.getHeight()
                || x < -width
                || y < -height) {
            return;
        }
        //paint.setColor(Color.BLUE);
        //canvas.drawRect(boundingRect, paint);
        matrix.reset();
        matrix.postScale((float) pixelFactor, (float) pixelFactor);
        matrix.postTranslate((float) x, (float) y);

        canvas.drawBitmap(bitmap, matrix, null);
    }

    public void setAnimDirection(int direction) {
        if (direction == 1) currentAnim = animationDrawable1;
        if (direction == 2) currentAnim = animationDrawable2;
        if (direction == 3) currentAnim = animationDrawable3;
        if (direction == 4) currentAnim = animationDrawable4;
    }
}
