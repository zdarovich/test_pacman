package com.white.android.pacman.world;

import com.white.android.pacman.R;
import com.white.android.pacman.engine.GameEngine;

import java.util.Random;

/**
 * Created by San4o on 12.01.2017.
 */

public class Maze{

    private final int BLOCK = 1;
    private final int FREE_SPACE = 0;
    private GameEngine gameEngine;
    private Random random = new Random();


    private int[][] mazeMap = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,1,1,1,1,0,1,1,1,1,0,1,1,1,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,0,1,0,1,1,1,0,1,1,1,0,1,1,0,1,0,1},
            {1,0,1,0,1,0,0,0,1,0,0,0,1,0,0,1,0,0,0,1},
            {1,0,1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,1,0,1},
            {1,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,1,0,1},
            {1,0,0,0,1,1,1,0,1,0,0,0,0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,0,0,0,0,0,0,1,1,0,1,0,1,0,1},
            {1,0,0,0,1,0,1,1,1,1,1,0,0,0,0,1,1,1,0,1},
            {1,0,1,1,1,0,1,0,1,1,1,0,1,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,1,0,0,0,1,0,1,0,0,1,1,1,0,1},
            {1,0,1,0,1,1,1,1,0,0,1,0,1,1,0,1,0,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,1,0,1},
            {1,0,1,1,1,0,0,1,1,0,1,0,1,1,1,1,0,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };

    public Maze(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public void init() {
        for (int i = 0; i < mazeMap.length; i++) {
            for (int k = 0; k < mazeMap[i].length; k++) {
                if (mazeMap[i][k] == BLOCK) {
                    new Block(gameEngine, R.drawable.block_1, k, i).addToGameEngine(gameEngine,3);
                } else if (mazeMap[i][k] == FREE_SPACE) {
                    new Candy(gameEngine, R.drawable.lsd_1, k, i).addToGameEngine(gameEngine,3);

                }
            }
        }
    }

    public int getRandomY() {
        int max = 18;
        int min = 1;
        return random.nextInt(max - min + 1) + min;
    }

    public double getRandomX(int row) {
        int max = 18;
        int min = 1;
        int rand = random.nextInt(max - min + 1) + min;
        while (mazeMap[row][rand] != FREE_SPACE) {
            rand = random.nextInt(max - min + 1) + min;
        }
        return rand;
    }


}
