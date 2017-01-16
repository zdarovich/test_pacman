package com.white.android.pacman.sound;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by San4o on 15.01.2017.
 */

public final class SoundManager {

    private static final int MAX_STREAMS = 10;
    private static final float DEFAULT_MUSIC_VOLUME = 0.6f;

    private static final String SOUNDS_PREF_KEY = "com.white.android.pacman.sounds.boolean";

    private static SoundManager instance;


    private HashMap<GameEvent, Integer> soundsMap;

    private Context context;
    private SoundPool soundPool;

    private boolean soundEnabled;
    private boolean musicEnabled;

    private MediaPlayer bgPlayer;

    public SoundManager(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        soundEnabled = prefs.getBoolean(SOUNDS_PREF_KEY, true);
        // Use SoundPool.Builder on API 21 http://developer.android.com/reference/android/media/SoundPool.Builder.html
        this.context = context;
        loadIfNeeded();
    }

    private void loadEventSound(Context context, GameEvent event, String... filename) {
        try {
            AssetFileDescriptor descriptor = context.getAssets().openFd("sfx/" + filename[0]);
            int soundId = soundPool.load(descriptor, 1);
            soundsMap.put(event, soundId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playSoundForGameEvent(GameEvent event) {
        if (!soundEnabled) {
            return;
        }
//		SoundInfo sound = soundsMap.get(event);
//		if (sound != null) {
//			sound.play(soundPool);
//		}
        Integer soundId = soundsMap.get(event);
        if (soundId != null) {
            soundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    private void loadIfNeeded () {
        if (soundEnabled) {
            loadSounds();
        }
    }

    private void loadSounds() {
        createSoundPool();
        soundsMap = new HashMap<GameEvent, Integer>();
//		soundsMap = new HashMap<T, SoundInfo>();
        loadEventSound(context, GameEvent.CandyEaten, "tone_1.mid");
        loadEventSound(context, GameEvent.PacManHit, "tone.2.mid");
        loadEventSound(context, GameEvent.GameOver, "tone_3.mid");
    }

    private void createSoundPool() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }
        else {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(MAX_STREAMS)
                    .build();
        }
    }




    private void unloadSounds() {
        soundPool.release();
        soundPool = null;
        soundsMap.clear();
    }

    public void toggleSoundStatus() {
        soundEnabled = !soundEnabled;
        if (soundEnabled) {
            loadSounds();
        }
        else {
            unloadSounds();
        }
        // Save it to preferences
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putBoolean(SOUNDS_PREF_KEY, soundEnabled)
                .commit();
    }


    public boolean getMusicStatus() {
        return musicEnabled;
    }

    public boolean getSoundStatus() {
        return soundEnabled;
    }
}
