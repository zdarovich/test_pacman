package com.white.android.pacman.engine;

import android.content.Context;

import java.util.List;

/**
 * Created by San4o on 11.01.2017.
 */

public interface GameView {
    void draw();
    void setLayers(List<List<GameObject>> gameObjects);
    int getWidth();
    int getHeight();
    int getPaddingLeft();
    int getPaddingRight();
    int getPaddingTop();
    int getPaddingBottom();
    Context getContext();
}
