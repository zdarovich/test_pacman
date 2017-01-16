package com.white.android.pacman.fragments;

import android.app.Fragment;

import com.white.android.pacman.MainActivity;

/**
 * Created by San4o on 11.01.2017.
 */

public class MainBaseFragment extends Fragment {
    public boolean onBackPressed() {return false;}

    protected MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }
}
