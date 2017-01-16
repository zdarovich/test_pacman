package com.white.android.pacman.input;

import android.view.MotionEvent;
import android.view.View;

import com.white.android.pacman.R;

/**
 * Created by San4o on 11.01.2017.
 */

public class InputController  implements  View.OnTouchListener {

    private double horizontalFactor;
    private double verticalFactor;

    public InputController(View view) {
        view.findViewById(R.id.keypad_up).setOnTouchListener(this);
        view.findViewById(R.id.keypad_down).setOnTouchListener(this);
        view.findViewById(R.id.keypad_left).setOnTouchListener(this);
        view.findViewById(R.id.keypad_right).setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getActionMasked();
        int id = v.getId();

        if (action == MotionEvent.ACTION_DOWN) {
            if (id == R.id.keypad_up) {
                verticalFactor -= 1;
            } else if (id == R.id.keypad_down) {
                verticalFactor += 1;
            } else if (id == R.id.keypad_left) {
                horizontalFactor -= 1;
            } else if (id == R.id.keypad_right) {
                horizontalFactor += 1;
            }
        } else if (action == MotionEvent.ACTION_UP) {
            if (id == R.id.keypad_up) {
                verticalFactor += 1;
            } else if (id == R.id.keypad_down) {
                verticalFactor -= 1;
            } else if (id == R.id.keypad_left) {
                horizontalFactor += 1;
            } else if (id == R.id.keypad_right) {
                horizontalFactor -= 1;
            }
        }
        return false;
    }

    public double getHorizontalFactor() {
        return horizontalFactor;
    }

    public double getVerticalFactor() {
        return verticalFactor;
    }

    public void onStart() {

    }
    public void onStop() {

    }
    public void onPause() {

    }
    public void onResume() {

    }
    public void onPreUpdate() {

    }

}
