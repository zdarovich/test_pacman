package com.white.android.pacman.counter;

import android.graphics.Canvas;
import android.view.View;
import android.widget.TextView;

import com.white.android.pacman.engine.GameEngine;
import com.white.android.pacman.engine.GameObject;
import com.white.android.pacman.sound.GameEvent;

/**
 * Created by San4o on 14.01.2017.
 */

public class LivesCounter extends GameObject {

    private final TextView text;
    private final int STARTING_LIFES = 3;
    private final int ONE_LIFE = 1;
    private int lifes;
    private boolean pointsHaveChanged;

    public LivesCounter(View view, int viewResId) {
        this.text = (TextView) view.findViewById(viewResId);
    }

    @Override
    public void startGame(GameEngine gameEngine) {
        lifes = 3;
        text.post(updateTextRunnable);
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

    }
    private Runnable updateTextRunnable = new Runnable() {
        @Override
        public void run() {
            String text = String.format("%01d", lifes);
            LivesCounter.this.text.setText(text);
        }
    };

    @Override
    public void onDraw(Canvas canvas) {
        if (pointsHaveChanged) {
            text.post(updateTextRunnable);
            pointsHaveChanged = false;
        }
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent == GameEvent.LifeLost) {
            lifes -= ONE_LIFE;
            pointsHaveChanged = true;
        }

    }



}
