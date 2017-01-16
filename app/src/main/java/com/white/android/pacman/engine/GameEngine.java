package com.white.android.pacman.engine;

import android.app.Activity;
import android.content.Context;

import com.white.android.pacman.input.InputController;
import com.white.android.pacman.sound.GameEvent;
import com.white.android.pacman.sound.SoundManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by San4o on 11.01.2017.
 */

public class GameEngine {

    private List<List<GameObject>> layers = new ArrayList<List<GameObject>>();
    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    private List<ScreenGameObject> objectsCollision = new ArrayList<ScreenGameObject>();
    private List<GameObject> objectsToAdd = new ArrayList<GameObject>();
    private List<GameObject> objectsToRemove = new ArrayList<GameObject>();
    private List<GameObject> collisionBlocks = new ArrayList<GameObject>();

    private final GameView gameView;
    private UpdateThread updateThread;
    private DrawThread drawThread;
    public InputController inputController;
    private Activity activity;

    public int width;
    public int height;
    public double pixelFactor;
    public double gameUnit;

    private SoundManager soundManager;

    public GameEngine(Activity a, GameView mGameView, int numLayers) {
        activity = a;
        gameView = mGameView;
        gameView.setLayers(layers);

        width = gameView.getWidth() - gameView.getPaddingRight() -
                gameView.getPaddingLeft();
        height = gameView.getHeight() - gameView.getPaddingTop() -
                gameView.getPaddingBottom();
        pixelFactor = height / 900d;
        gameUnit = pixelFactor * 45d;

        for (int i=0; i < numLayers; i++) {
            layers.add(new ArrayList<GameObject>());
        }
    }

    public void setSoundManager(SoundManager soundManager) {
        this.soundManager = soundManager;
    }

    public void startGame() {
        //Stop game if running
        stopGame();

        //Setup the game objects
        int numGameObjects = gameObjects.size();
        for (int i = 0; i < numGameObjects; i++) {
            gameObjects.get(i).startGame(this);
        }
        /*if (inputController != null) {
            inputController.onStart();
        }*/

        //Start the update thread
        updateThread = new UpdateThread(this);
        updateThread.start();

        //Start the drawing thread
        drawThread = new DrawThread(this);
        drawThread.start();
    }

    public void stopGame() {
        if (updateThread != null) {
            updateThread.stopGame();
        }
        if (drawThread != null) {
            drawThread.stopGame();
        }
        if (inputController != null) {
            inputController.onStop();
        }
    }

    public void pauseGame() {
        if (updateThread != null) {
            updateThread.pauseGame();
        }
        if (drawThread != null) {
            drawThread.pauseGame();
        }
        if (inputController != null) {
            inputController.onPause();
        }
    }

    public void resumeGame() {
        if (updateThread != null) {
            updateThread.resumeGame();
        }
        if (drawThread != null) {
            drawThread.resumeGame();
        }
        if (inputController != null) {
            inputController.onResume();
        }
    }

    public void addGameObject(final GameObject gameObject, int mLayer) {
        gameObject.layer = mLayer;
        if (isRunning()) {
            objectsToAdd.add(gameObject);
        }
        else {
            addToLayerNow(gameObject);
        }
        activity.runOnUiThread(gameObject.onAddedRunnable);
    }

    public void removeGameObject(final GameObject gameObject) {
        objectsToRemove.add(gameObject);
        activity.runOnUiThread(gameObject.onRemovedRunnable);
    }

    public void setInputController(InputController controller) {
        inputController = controller;
    }

    public void onUpdate(long elapsedMillis) {

        int numGameObjects = gameObjects.size();
        for (int i = 0; i < numGameObjects; i++) {
            gameObjects.get(i).onUpdate(elapsedMillis, this);
            gameObjects.get(i).onPostUpdate(this);
        }
        checkCollisions();
        synchronized (layers) {
            while (!objectsToRemove.isEmpty()) {
                GameObject objectToRemove = objectsToRemove.remove(0);
                gameObjects.remove(objectToRemove);
                layers.get(objectToRemove.layer).remove(objectToRemove);
                if (objectToRemove instanceof  ScreenGameObject) {
                    objectsCollision.remove((ScreenGameObject) objectToRemove);
                }
                objectToRemove.onRemovedFromGameEngine();
            }
            while (!objectsToAdd.isEmpty()) {
                GameObject gameObject = objectsToAdd.remove(0);
                addToLayerNow(gameObject);
            }
        }
    }

    private void addToLayerNow (GameObject object) {
        int layer = object.layer;
        while (layers.size() <= layer) {
            layers.add(new ArrayList<GameObject>());
        }
        layers.get(layer).add(object);
        gameObjects.add(object);
        if (object instanceof ScreenGameObject) {
            ScreenGameObject sgo = (ScreenGameObject) object;
            objectsCollision.add(sgo);
        }
        object.onAddedToGameEngine();
    }

    public void onGameEvent (GameEvent gameEvent) {
        // We notify all the GameObjects
        int numObjects = gameObjects.size();
        for (int i=0; i<numObjects; i++) {
            gameObjects.get(i).onGameEvent(gameEvent);
        }

        soundManager.playSoundForGameEvent(gameEvent);
    }

    private void checkCollisions() {
        int numObjects = objectsCollision.size();
        for (int i = 0; i < numObjects; i++) {
            ScreenGameObject objectA = objectsCollision.get(i);
            for (int j = i + 1; j < numObjects; j++) {
                ScreenGameObject objectB = objectsCollision.get(j);
                if (objectA.checkCollision(objectB)) {
                    objectA.onCollision(this, objectB);
                    objectB.onCollision(this, objectA);
                }
            }
        }
    }


    public void onDraw() {
        gameView.draw();
    }

    public boolean isPaused() {
        return updateThread != null && updateThread.isGamePaused();
    }

    public boolean isRunning() {
        return updateThread != null && updateThread.isGameRunning();
    }

    public Context getContext() {
        return gameView.getContext();
    }
}
