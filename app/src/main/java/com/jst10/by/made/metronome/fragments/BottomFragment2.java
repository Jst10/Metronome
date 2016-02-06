package com.jst10.by.made.metronome.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jst10.by.made.metronome.Constants;
import com.jst10.by.made.metronome.MainActivity;
import com.jst10.by.made.metronome.R;
import com.jst10.by.made.metronome.custom.views.CustomEditText;
import com.jst10.by.made.metronome.interfaces.TempoChangedListener;

/**
 * Created by jst10 on 17.1.2016.
 */
public class BottomFragment2 extends Fragment {


    private View layoutView;
    private SeekBar seekBar;
    private TempoChangedListener tempoChangedListener;


    private int currentTempo;
    private SharedPreferences preferences;


    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener =new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            currentTempo=progress;
            tempoChanged();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.bottom_fragment2, container, false);
        bindViews();
        init();
        return layoutView;
    }

    private void bindViews() {
        seekBar = (SeekBar) layoutView.findViewById(R.id.seekBar);
    }

    private void init() {
        preferences = getActivity().getSharedPreferences(Constants.ALL_PREFERENCES_KEY, Context.MODE_PRIVATE);
        currentTempo = preferences.getInt(Constants.PREFERENCE_KEY_TEMPO, MainActivity.DEFAULT_TEMPO);
        seekBar.setMax(MainActivity.MAX_TEMPO_LIMIT);
        seekBar.setProgress(currentTempo);
        seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }

    public void setTempoChangedListener(TempoChangedListener tempoChangedListener) {
        this.tempoChangedListener = tempoChangedListener;
    }


    private void tempoChanged() {

        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putInt(Constants.PREFERENCE_KEY_TEMPO, currentTempo);
        prefsEditor.apply();

        if (tempoChangedListener != null) {
            tempoChangedListener.onTempoChanged();
        }
    }

}
