package com.white.android.pacman.engine;

/**
 * Created by San4o on 11.01.2017.
 */

public class DrawThread extends Thread {

    private final GameEngine gameEngine;

    private boolean gameIsRunning = true;

    private boolean pauseGame = false;

    private Object lock = new Object();

    public DrawThread(GameEngine mGameEngine) {
        gameEngine = mGameEngine;
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

    @Override
    public void run() {
        long elapsedMillis;
        long currentTimeMillis;
        long previousTimeMillis = System.currentTimeMillis();

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
                        //stay on the loop
                    }
                }
                currentTimeMillis = System.currentTimeMillis();
            }
            if (elapsedMillis < 20) { // 50 fps
                try {
                    Thread.sleep(20 - elapsedMillis);
                } catch (InterruptedException e) {
                    //continue
                }
            }
            gameEngine.onDraw();
            previousTimeMillis = currentTimeMillis;
        }
    }

    public void pauseGame() {
        pauseGame = true;
    }

    public void resumeGame(){
        if (pauseGame == true) {
            pauseGame = false;
            synchronized (lock) {
                lock.notify();
            }
        }
    }
}
