package com.white.android.pacman.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Created by San4o on 11.01.2017.
 */

public class StandardGameView extends View implements GameView {

    private List<List<GameObject>> layers;

    public StandardGameView(Context context) {
        super(context);
    }
    public StandardGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public StandardGameView(Context context, AttributeSet attrs,
                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        synchronized (layers) {
            int numLayers = layers.size();
            for (int i = 0; i < numLayers; i++) {
                List<GameObject> currentLayer = layers.get(i);
                int numObjects = currentLayer.size();
                for (int j=0; j<numObjects; j++) {
                    currentLayer.get(j).onDraw(canvas);
                }
            }
        }
    }

    @Override
    public void draw() {
        postInvalidate();
    }



    @Override
    public void setLayers(List<List<GameObject>> mGameObjects) {
        layers = mGameObjects;
    }
}

