package com.white.android.pacman.engine;

import android.graphics.Canvas;

import com.white.android.pacman.sound.GameEvent;

/**
 * Created by San4o on 11.01.2017.
 */

public abstract class GameObject {

    public int layer;

    public abstract void startGame(GameEngine gameEngine);

    public abstract void onUpdate(long elapsedMillis, GameEngine gameEngine);


    public abstract void onDraw(Canvas canvas);

    public final Runnable onAddedRunnable = new Runnable() {
        @Override
        public void run() {
            onAddedToGameUiThread();
        }
    };

    public final Runnable onRemovedRunnable = new Runnable() {
        @Override
        public void run() {
            onRemovedFromGameUiThread();
        }
    };

    public void onRemovedFromGameUiThread() {}

    public void onAddedToGameUiThread() {}

    public void onPostUpdate(GameEngine gameEngine) {};

    public void onGameEvent(GameEvent gameEvent) {};

    public void addToGameEngine (GameEngine gameEngine, int layer) {
        gameEngine.addGameObject(this, layer);
    }

    public void removeFromGameEngine (GameEngine gameEngine) {
        gameEngine.removeGameObject(this);
    }

    public void onAddedToGameEngine() {
    }

    public void onRemovedFromGameEngine() {
    }
}
