package com.jst10.by.made.metronome.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.jst10.by.made.metronome.Constants;
import com.jst10.by.made.metronome.MainActivity;
import com.jst10.by.made.metronome.R;
import com.jst10.by.made.metronome.custom.views.CustomMetronome;
import com.jst10.by.made.metronome.custom.views.CustomProgressBar;
import com.jst10.by.made.metronome.interfaces.TempoChangedListener;

/**
 * Created by jst10 on 17.1.2016.
 */
public class MiddleFragment1 extends BaseMiddleFragment {

    private View layoutView;
    private CustomMetronome progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.middle_fragment1, container, false);
        bindViews();
        init();
        return layoutView;
    }

    private void bindViews() {
        progressBar= (CustomMetronome) layoutView.findViewById(R.id.progressBar);
    }

    private void init() {
        progressBar.setMaxProgress((int)MainActivity.NUMBER_OF_TICKS_PER_BIT);
        progressBar.setProgress(0);
    }


    private boolean reverse=false;


    @Override
    public void onTick(int tick) {
        if(progressBar!=null&&isAdded()) {
            if(reverse){
                progressBar.setProgress((int)MainActivity.NUMBER_OF_TICKS_PER_BIT-tick);
            }else {
                progressBar.setProgress(tick);
            }
            if(tick==MainActivity.NUMBER_OF_TICKS_PER_BIT){
                reverse=!reverse;
            }
        }
    }
}
