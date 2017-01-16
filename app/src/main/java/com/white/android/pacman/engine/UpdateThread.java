package com.white.android.pacman.engine;

/**
 * Created by San4o on 11.01.2017.
 */

public class UpdateThread extends Thread {

    private final GameEngine gameEngine;

    private boolean gameIsRunning = true;

    private boolean pauseGame = false;

    private Object lock = new Object();

    public UpdateThread(GameEngine mGameEngine) {
        gameEngine = mGameEngine;
    }

    @Override
    public void run() {
        long previousTimeMillis;
        long currentTimeMillis;
        long elapsedMillis;
        previousTimeMillis = System.currentTimeMillis();

        while (gameIsRunning) {
            currentTimeMillis = System.currentTimeMillis();
            elapsedMillis = currentTimeMillis - previousTimeMillis;
            if (pauseGame) {
                while (pauseGame) {
                    try {
                        synchronized (lock) {
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        // We stay on the loop
                    }
                }
                currentTimeMillis = System.currentTimeMillis();
            }
            gameEngine.onUpdate(elapsedMillis);
            previousTimeMillis = currentTimeMillis;
        }
    }

    public void resumeGame() {
        if (pauseGame == true) {
            pauseGame = false;
            synchronized (lock) {
                lock.notify();
            }
        }
    }

    public void pauseGame() {
        pauseGame = true;
    }

    public void start() {
        gameIsRunning = true;
        pauseGame = false;
        super.start();
    }

    public void stopGame() {
        gameIsRunning = false;
        resumeGame();
    }

    public boolean isGameRunning() {
        return pauseGame;
    }

    public boolean isGamePaused() {
        return gameIsRunning;
    }
}
