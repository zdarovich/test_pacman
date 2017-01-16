package com.white.android.pacman.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.white.android.pacman.MainActivity;
import com.white.android.pacman.R;
import com.white.android.pacman.fragments.MainBaseFragment;

/**
 * Created by San4o on 11.01.2017.
 */

public class MainMenuFragment extends MainBaseFragment implements View.OnClickListener {
    public MainMenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_start).setOnClickListener(this);
        view.findViewById(R.id.btn_score).setOnClickListener(this);
        view.findViewById(R.id.btn_quit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_start) {
            ((MainActivity)getActivity()).startGame();
        }
        if (v.getId() == R.id.btn_score) {
            //((MainActivity)getActivity()).openScore();
        }
        if (v.getId() == R.id.btn_quit) {
            ((MainActivity)getActivity()).navigateBack();
        }
    }
}
