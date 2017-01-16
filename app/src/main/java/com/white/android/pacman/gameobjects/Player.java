package com.white.android.pacman.gameobjects;

import com.white.android.pacman.engine.GameEngine;
import com.white.android.pacman.engine.ScreenGameObject;
import com.white.android.pacman.input.InputController;
import com.white.android.pacman.sound.GameEvent;
import com.white.android.pacman.sprite.AnimatedSprite;
import com.white.android.pacman.world.Block;
import com.white.android.pacman.world.Candy;

/**
 * Created by San4o on 12.01.2017.
 */

public class Player extends AnimatedSprite {

    private static final int LIVES = 3;
    private int maxX;
    private int maxY;
    private double speedFactor;
    private boolean hasCollidedWithBlock;

    private double changePositionX;
    private double changePositionY;



    private double currentX;
    private double currentY;


    private final int GAME_UNITS_PER_SECOND = 4;

    public Player(GameEngine gameEngine, int drawableRes) {
        super(gameEngine, drawableRes);
        speedFactor = gameEngine.gameUnit * GAME_UNITS_PER_SECOND / 1000d;


        maxX = gameEngine.width - width;
        maxY = gameEngine.height - height;


    }

    @Override
    public void startGame(GameEngine gameEngine) {
    }

    private void updatePosition(long elapsedMillis, InputController inputController){
        double horizontal = inputController.getHorizontalFactor();

        changePositionX = speedFactor * horizontal * elapsedMillis;
        x += changePositionX;

        double vertical = inputController.getVerticalFactor();

        changePositionY = speedFactor * vertical * elapsedMillis;
        y += changePositionY;

    }


    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        super.onUpdate(elapsedMillis, gameEngine);
        if (!hasCollidedWithBlock) {
            updatePosition(elapsedMillis, gameEngine.inputController);
            hasCollidedWithBlock = false;
        } else {
            x -= changePositionX;
            y -= changePositionY;
            hasCollidedWithBlock = false;
        }
        currentX = x;
        currentY = y;
    }
    @Override
    public void onCollision(GameEngine gameEngine,
                            ScreenGameObject otherObject) {
        if (otherObject instanceof Ghost) {
            removeFromGameEngine(gameEngine);
            //gameEngine.stopGame();
            Ghost a = (Ghost) otherObject;
            a.removeFromGameEngine(gameEngine);
            gameEngine.onGameEvent(GameEvent.PacManHit);
            //HighScoreActivity.score.add(ScoreGameObject.points);

        } else if (otherObject instanceof Candy) {
            //gameEngine.stopGame();
            Candy c = (Candy) otherObject;
            c.removeFromGameEngine(gameEngine);
            gameEngine.onGameEvent(GameEvent.CandyEaten);

        } else if (otherObject instanceof Block) {
            //gameEngine.stopGame();
            hasCollidedWithBlock = true;
        }
    }

    @Override
    public void onPostUpdate(GameEngine gameEngine) {
        boundingRect.set(
                (int) x,
                (int) y,
                (int) x + width * 2 / 3,
                (int) y + height * 2 / 3);
    }


    public double getCurrentX() {
        return currentX;
    }

    public double getCurrentY() {
        return currentY;
    }

    public void init(GameEngine gameEngine, double positionX, double positionY) {
        x = positionX * gameEngine.gameUnit;
        y = positionY * gameEngine.gameUnit;
    }
}
