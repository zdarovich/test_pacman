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

public class ScoreGameObject extends GameObject {

    private static final int POINTS_GAINED_PER_CANDY_EATEN = 20;

    private final TextView text;
    private int points;
    private boolean pointsHaveChanged;

    public ScoreGameObject(View view, int viewResId) {
        text = (TextView) view.findViewById(viewResId);
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
    }

    @Override
    public void startGame(GameEngine gameEngine) {
        points = 0;
        text.post(updateTextRunnable);
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent == GameEvent.CandyEaten) {
            points += POINTS_GAINED_PER_CANDY_EATEN;
            pointsHaveChanged = true;
        }
    }

    private Runnable updateTextRunnable = new Runnable() {
        @Override
        public void run() {
            String text = String.format("%06d", points);
            ScoreGameObject.this.text.setText(text);
        }
    };

    @Override
    public void onDraw(Canvas canvas) {
        if (pointsHaveChanged) {
            text.post(updateTextRunnable);
            pointsHaveChanged = false;
        }
    }
}
