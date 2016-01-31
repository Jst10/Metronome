package com.jst10.by.made.metronome.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jst10.by.made.metronome.R;
import com.jst10.by.made.metronome.custom.views.Light;

/**
 * Created by jst10 on 17.1.2016.
 */
public class TopFragment extends Fragment {
        private View layoutView;

        private Light[] lights;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            layoutView = inflater.inflate(R.layout.top_fragment, container, false);
            bindViews();
            init();
            return layoutView;
        }

        private void bindViews() {
            lights=new Light[4];
            lights[0]= (Light) layoutView.findViewById(R.id.light1);
            lights[1]= (Light) layoutView.findViewById(R.id.light2);
            lights[2]= (Light) layoutView.findViewById(R.id.light3);
            lights[3]= (Light) layoutView.findViewById(R.id.light4);

        }

        private void init() {

            lights[1].setActivated(true);
            lights[3].setActivated(true);
        }

}
