package com.white.android.pacman.sprite;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.white.android.pacman.engine.GameEngine;
import com.white.android.pacman.engine.ScreenGameObject;

/**
 * Created by San4o on 12.01.2017.
 */

public abstract class Sprite extends ScreenGameObject {

    protected Drawable spriteDrawable;

    protected double pixelFactor;

    protected Bitmap bitmap;

    private final Matrix matrix = new Matrix();

    private final Paint paint = new Paint();

    private int drawableResource;

    public Sprite() {
    }

    public Sprite (GameEngine gameEngine, int drawableRes) {
        this.drawableResource = drawableRes;
        pixelFactor = gameEngine.pixelFactor;
        initDrawable(gameEngine, drawableRes);

    }

    public void initDrawable(GameEngine gameEngine, int drawableRes) {
        Resources r = gameEngine.getContext().getResources();
        spriteDrawable = r.getDrawable(drawableRes);


        height = (int) (spriteDrawable.getIntrinsicHeight() * pixelFactor);
        width = (int) (spriteDrawable.getIntrinsicWidth() * pixelFactor);
        bitmap = obtainDefaultBitmap();
    }

    public Bitmap obtainDefaultBitmap() {
        return ((BitmapDrawable) spriteDrawable).getBitmap();
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


}
