package com.jst10.by.made.metronome.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.jst10.by.made.metronome.Constants;
import com.jst10.by.made.metronome.MainActivity;
import com.jst10.by.made.metronome.R;
import com.jst10.by.made.metronome.interfaces.TempoChangedListener;

/**
 * Created by jst10 on 17.1.2016.
 */
public class BaseBottomFragment extends Fragment {


    private TempoChangedListener tempoChangedListener;


    protected int currentTempo;
    protected SharedPreferences preferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getActivity().getSharedPreferences(Constants.ALL_PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    public void setTempoChangedListener(TempoChangedListener tempoChangedListener) {
        this.tempoChangedListener = tempoChangedListener;
    }


    protected void tempoChanged() {

        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putInt(Constants.PREFERENCE_KEY_TEMPO, currentTempo);
        prefsEditor.apply();

        if (tempoChangedListener != null) {
            tempoChangedListener.onTempoChanged();
        }
    }

}
