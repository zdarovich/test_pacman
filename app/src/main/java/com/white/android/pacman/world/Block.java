package com.white.android.pacman.world;

import com.white.android.pacman.engine.GameEngine;
import com.white.android.pacman.sprite.Sprite;

/**
 * Created by San4o on 14.01.2017.
 */

public class Block extends Sprite {

    private double unitX;
    private double unitY;


    public Block(GameEngine gameEngine, int drawableRes, int mazeX, int mazeY) {
        super(gameEngine, drawableRes);
        unitX = (gameEngine.width - width) / 30;
        unitY = (gameEngine.height - height) / 17;
        x = gameEngine.gameUnit  * mazeX;
        y = gameEngine.gameUnit  * mazeY;
    }


    @Override
    public void startGame(GameEngine gameEngine) {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

    }

    @Override
    public void onPostUpdate(GameEngine gameEngine) {
        boundingRect.set(
                (int) x ,
                (int) y ,
                (int) x + width * 2 / 3,
                (int) y + height * 2 / 3);
    }
}
