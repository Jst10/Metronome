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
    private CustomProgressBar progressBar;
    private SeekBar seekBar;
    private int progresEdge;

    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            progresEdge=progress;
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
        layoutView = inflater.inflate(R.layout.middle_fragment1, container, false);
        bindViews();
        init();
        return layoutView;
    }

    private void bindViews() {
        progressBar= (CustomProgressBar) layoutView.findViewById(R.id.progressBar);
        seekBar=(SeekBar)layoutView.findViewById(R.id.seekBar);
    }

    private void init() {
        progressBar.setMaxProgress((int) MainActivity.NUMBER_OF_TICKS_PER_BIT);
        progressBar.setProgress(0);

        progresEdge=0;
        seekBar.setMax((int) MainActivity.NUMBER_OF_TICKS_PER_BIT);
        seekBar.setProgress(progresEdge);


        seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }


    private boolean reverse=false;


    @Override
    public void onTick(int tick) {
        if(progressBar!=null&&isAdded()) {
            if(reverse){
                updateProgress((int) MainActivity.NUMBER_OF_TICKS_PER_BIT - tick);
            }else {
                updateProgress(tick);
            }
            if(tick==MainActivity.NUMBER_OF_TICKS_PER_BIT){
                reverse=!reverse;
            }
        }
    }
    private void updateProgress(int progress){
        if(progresEdge>progress){
            progressBar.setProgress(0);
        }else{
            progressBar.setProgress(progress);
        }
    }
}
