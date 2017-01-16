package com.white.android.pacman.gameobjects;

import com.white.android.pacman.engine.GameEngine;
import com.white.android.pacman.engine.ScreenGameObject;
import com.white.android.pacman.sprite.AnimatedSprite;
import com.white.android.pacman.world.Block;

/**
 * Created by San4o on 12.01.2017.
 */

public class Ghost extends AnimatedSprite {

    private GameController controller;

    private final int BACK = -1;
    private final int FORWARD = 1;
    private final int GAME_UNITS_PER_SECOND = 4;
    private double speed;

    private double changePositionX;
    private double changePositionY;

    private double currentX;
    private double currentY;

    private boolean hasCollidedWithBlock;



    public Ghost(GameController controller, GameEngine gameEngine, int drawableRes) {
        super(gameEngine, drawableRes);
        this.controller = controller;
        this.speed = gameEngine.gameUnit * GAME_UNITS_PER_SECOND / 1000d / 2;

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

        super.onUpdate(elapsedMillis, gameEngine);
        //x += speed * elapsedMillis;
        //y += speed * elapsedMillis;


        if (!hasCollidedWithBlock) {
            findPathToPacMan(elapsedMillis);
            hasCollidedWithBlock = false;
        } else {
            x -= changePositionX;

            y -= changePositionY;

            hasCollidedWithBlock = false;
        }
        findPathToPacMan(elapsedMillis);
        currentX = x;
        currentY = y;


    }

    public void init(GameEngine gameEngine, double positionX, double positionY) {
        x = positionX * gameEngine.gameUnit;
        y = positionY * gameEngine.gameUnit;
    }

    public void findPathToPacMan(long elapsedMillis) {
        findPathToPacManByX(elapsedMillis);
        findPathToPacManByY(elapsedMillis);
    }

    public void findPathToPacManByX(long elapsedMillis) {
        if (controller.isPacManToTheLeft(this)) {
            changePositionX = moveX(BACK, elapsedMillis);
        } else {
            changePositionX = moveX(FORWARD, elapsedMillis);
        }
    }
    public void findPathToPacManByY(long elapsedMillis) {
        if (controller.isPacManAbove(this)) {
            changePositionY = moveY(BACK, elapsedMillis);
        } else {
            changePositionY = moveY(FORWARD, elapsedMillis);
        }
    }

    private double moveX(int dest, long elapsedMillis) {
        double changeX = speed * dest * elapsedMillis;
        x += changeX;
        return changeX;

    }

    private double moveY(int dest, long elapsedMillis) {
        double changeY = speed * dest * elapsedMillis;
        y += changeY;
        return changeY;
    }

    public double getCurrentX() {
        return currentX;
    }

    public double getCurrentY() {
        return currentY;
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Block) {
            hasCollidedWithBlock = true;
        }
    }

    @Override
    public void startGame(GameEngine gameEngine) {
    }

    @Override
    public void removeFromGameEngine(GameEngine gameEngine) {
        super.removeFromGameEngine(gameEngine);
    }

    @Override
    public void addToGameEngine (GameEngine gameEngine, int layer) {
        super.addToGameEngine(gameEngine, layer);
    }

    @Override
    public void onRemovedFromGameEngine() {
        controller.returnToPool(this);
    }

    @Override
    public void onPostUpdate(GameEngine gameEngine) {
        boundingRect.set(
                (int) x + width / 2,
                (int) y + height / 2,
                (int) x + width / 2,
                (int) y + height / 2);
    }


}

