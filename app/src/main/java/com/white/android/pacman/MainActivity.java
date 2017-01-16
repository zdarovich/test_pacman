package com.white.android.pacman;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.white.android.pacman.fragments.MainActivityFragment;
import com.white.android.pacman.fragments.MainMenuFragment;
import com.white.android.pacman.sound.SoundManager;

/**
 * Created by San4o on 11.01.2017.
 */

public class MainActivity extends Activity {
    private static final String TAG_FRAGMENT = "content";
    private SoundManager soundManager;
    //private static HighScoreActivity score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainMenuFragment(), TAG_FRAGMENT)
                    .commit();
        }
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundManager = new SoundManager(getApplicationContext());
        //score = new HighScoreActivity();
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public void startGame() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new MainActivityFragment(), TAG_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }
    /*public void openScore() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, score, TAG_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }*/
    @Override
    public void onBackPressed() {
        final MainActivityFragment fragment = (MainActivityFragment) getFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if (fragment == null || !fragment.onBackPressed()) {
            super.onBackPressed();
        }
    }
    public void navigateBack() {
        super.onBackPressed();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE);
            }
            else {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }
    }

}
