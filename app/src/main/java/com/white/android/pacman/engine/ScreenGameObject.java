package com.white.android.pacman.engine;

import android.graphics.Rect;

/**
 * Created by San4o on 11.01.2017.
 */

public abstract class ScreenGameObject extends GameObject {

    protected double x;

    protected double y;

    protected int height;

    protected int width;

    public Rect boundingRect = new Rect(-1, -1, -1, -1);


    public boolean checkCollision(ScreenGameObject otherObject) {
        return checkRectangularCollision(otherObject);
    }

    private boolean checkRectangularCollision(ScreenGameObject object) {
        return Rect.intersects(boundingRect, object.boundingRect);
    }

    public void onPostUpdate(GameEngine gameEngine) {
        boundingRect.set(
                (int) x,
                (int) y,
                (int) x + width,
                (int) y + height);
    }


    public void onCollision(GameEngine gameEngine,
                            ScreenGameObject sgo) {

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
