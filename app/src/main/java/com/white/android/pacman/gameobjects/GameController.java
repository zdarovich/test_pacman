package com.white.android.pacman.gameobjects;

import android.graphics.Canvas;

import com.white.android.pacman.R;
import com.white.android.pacman.engine.GameEngine;
import com.white.android.pacman.engine.GameObject;
import com.white.android.pacman.sound.GameEvent;
import com.white.android.pacman.world.Maze;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by San4o on 12.01.2017.
 */

public class GameController extends GameObject {

    private static final int TIME_BETWEEN_ENEMIES = 1500;
    private static final int GHOST_POOL_SIZE = 4;
    private static final int INITIAL_LIFES = 3;

    private Player newPacMan;
    private Maze maze;

    private int[] ghost_3 = {R.drawable.ghost_3_forward, R.drawable.ghost_3_back,
            R.drawable.ghost_3_left, R.drawable.ghost_3_right};

    private int[] pacman = {R.drawable.pacman_animated_forward, R.drawable.pacman_animated_back,
    R.drawable.pacman_animated_left, R.drawable.pacman_animated_right};

    private long currentMillis;
    private List<Ghost> ghostPool = new ArrayList<Ghost>();

    private int enemiesSpawned;

    private GameControllerState state;
    private int waitingTime;

    private int numLives;

    public GameController(GameEngine gameEngine) {
        //double pixelFactor = gameEngine.pixelFactor;
        // We initialize the pool of items now
        maze = new Maze(gameEngine);
        maze.init();
        for (int i = 0; i < GHOST_POOL_SIZE; i++) {
            ghostPool.add(new Ghost(this, gameEngine, R.drawable.ghost_3_right));
        }
    }

    @Override
    public void startGame(GameEngine gameEngine) {
        currentMillis = 0;
        //enemiesSpawned = 0;
        waitingTime = 0;
        for (int i=0; i<INITIAL_LIFES; i++) {
            gameEngine.onGameEvent(GameEvent.LifeAdded);
        }
        state = GameControllerState.PlacingPacMan;
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent == GameEvent.PacManHit) {
            state = GameControllerState.PlacingPacMan;
            waitingTime = 0;
        }
        else if (gameEvent == GameEvent.GameOver) {
            state = GameControllerState.GameOver;
            //showGameOverDialog();
        }
        else if (gameEvent == GameEvent.LifeAdded) {
            numLives++;
        }
        else if (gameEvent == GameEvent.CandyEaten) {
            numLives++;
        }
    }

    /*private void showGameOverDialog() {
        parent.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GameOverDialog quitDialog = new GameOverDialog(parent);
                quitDialog.setListener(parent);
                parent.showDialog(quitDialog);
            }
        });
    }*/

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        if (state == GameControllerState.SpawningEnemies && !ghostPool.isEmpty()) {

            currentMillis += elapsedMillis;
            //long waveTimestamp = enemiesSpawned * TIME_BETWEEN_ENEMIES; // - mWaveStartingTimestamp[i];
            if (currentMillis > TIME_BETWEEN_ENEMIES) {
                int randY = maze.getRandomY();
                // Spawn a new enemy
                Ghost a = ghostPool.remove(0);
                a.init(gameEngine, maze.getRandomX(randY), randY);
                a.addToGameEngine(gameEngine, 3);
                //enemiesSpawned++;
                currentMillis = 0;
                return;
            }
        }

        else if (state == GameControllerState.PlacingPacMan) {
            if (numLives == 0){
                gameEngine.onGameEvent(GameEvent.GameOver);
            }
            else {
                numLives--;
                gameEngine.onGameEvent(GameEvent.LifeLost);
                int randY = maze.getRandomY();
                newPacMan = new Player(gameEngine, R.drawable.pacman_animated_forward);
                newPacMan.init(gameEngine, maze.getRandomX(randY), randY);
                newPacMan.addToGameEngine(gameEngine, 3);
                newPacMan.startGame(gameEngine);
                //state = GameControllerState.Waiting;
                state = GameControllerState.SpawningEnemies;
                waitingTime = 0;
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        // This game object does not draw anything
    }

    public void returnToPool(Ghost ghost) {
        ghostPool.add(ghost);
        //enemiesSpawned--;
    }

    public boolean isPacManToTheLeft(Ghost ghost) {
        return newPacMan.getCurrentX() < ghost.getCurrentX();
    }

    public boolean isPacManAbove(Ghost ghost) {
        return newPacMan.getCurrentY() < ghost.getCurrentY();
    }

}
