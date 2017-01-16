package com.white.android.pacman.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.white.android.pacman.MainActivity;
import com.white.android.pacman.R;
import com.white.android.pacman.counter.LivesCounter;
import com.white.android.pacman.counter.ScoreGameObject;
import com.white.android.pacman.engine.GameEngine;
import com.white.android.pacman.engine.GameView;
import com.white.android.pacman.gameobjects.GameController;
import com.white.android.pacman.input.InputController;

/**
 * Created by San4o on 11.01.2017.
 */

public class MainActivityFragment extends MainBaseFragment implements View.OnClickListener{
    private GameEngine gameEngine;
    private GameView gameView;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_play_pause).setOnClickListener(this);
        final ViewTreeObserver obs = view.getViewTreeObserver();
        obs.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    obs.removeGlobalOnLayoutListener(this);
                }
                else {
                    obs.removeOnGlobalLayoutListener(this);
                }
                GameView gameView = (GameView) getView().findViewById(R.id.gameView);
                gameEngine = new GameEngine(getActivity(), gameView, 4);
                gameEngine.setInputController(new InputController(getView()));
                gameEngine.setSoundManager(getMainActivity().getSoundManager());
                gameEngine.addGameObject(new GameController(gameEngine), 2);
                gameEngine.addGameObject(new ScoreGameObject(getView(), R.id.txt_score), 0);
                gameEngine.addGameObject(new LivesCounter(getView(), R.id.txt_lives), 0);
                //gameEngine.addGameObject(new Player(gameEngine), 3);
                gameEngine.startGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_play_pause) {
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (gameEngine.isRunning()){
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gameEngine.stopGame();
    }

    @Override
    public boolean onBackPressed() {
        if (gameEngine.isRunning()) {
            pauseGameAndShowPauseDialog();
            return true;
        }
        return false;
    }

    private void pauseGameAndShowPauseDialog() {
        gameEngine.pauseGame();
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.pause_dialog_title)
                .setMessage(R.string.pause_dialog_message)
                .setPositiveButton(R.string.resume, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        gameEngine.resumeGame();
                    }
                })
                .setNegativeButton(R.string.stop, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        gameEngine.stopGame();
                        ((MainActivity)getActivity()).navigateBack();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        gameEngine.resumeGame();
                    }
                })
                .create()
                .show();

    }

    private void playOrPause() {
        Button button = (Button) getView().findViewById(R.id.btn_play_pause);
        if (gameEngine.isPaused()) {
            gameEngine.resumeGame();
            button.setText(R.string.pause);
        }
        else {
            gameEngine.pauseGame();
            button.setText(R.string.resume);
        }
    }
}
