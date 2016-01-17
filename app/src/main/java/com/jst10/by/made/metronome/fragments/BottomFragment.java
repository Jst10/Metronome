package com.jst10.by.made.metronome.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.jst10.by.made.metronome.Constants;
import com.jst10.by.made.metronome.R;
import com.jst10.by.made.metronome.custom.views.CustomEditText;

/**
 * Created by jst10 on 17.1.2016.
 */
public class BottomFragment extends Fragment {
    public interface TempoChangedListener {
        void onTempoChanged();
    }

    public static final int DEFAULT_TEMPO = 60;
    public static final int MAX_TEMPO_LIMIT = 300;
    public static final int MINIMAL_TEMPO_LIMIT = 30;

    private static final int REP_DELAY = 50;

    private View layoutView;
    private CustomEditText tempoField;
    private View reduceTempoButton;
    private View increaseTempoButton;
    private TempoChangedListener tempoChangedListener;


    private int currentTempo;
    private boolean autoIncrement;
    private boolean autoReduce;

    private SharedPreferences preferences;

    private Handler repeatUpdateHandler = new Handler();



    private CustomEditText.EditingListener editingListener=new CustomEditText.EditingListener() {
        @Override
        public void onDoneEditing() {
            onUserDoneTyping();
        }
    };

    TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||event.getAction() == KeyEvent.ACTION_DOWN &&event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (event==null||!event.isShiftPressed()) {
                        onUserDoneTyping();
                        return false;
                    }
                }

            return false;
        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == reduceTempoButton.getId()) {
                decrement();
            } else if (v.getId() == increaseTempoButton.getId()) {
                increment();
            } else {
                throw new IllegalAccessError();
            }
        }
    };

    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (v.getId() == reduceTempoButton.getId()) {
                autoReduce = true;
            } else if (v.getId() == increaseTempoButton.getId()) {
                autoIncrement = true;
            } else {
                throw new IllegalAccessError();
            }
            repeatUpdateHandler.post(new TempoUpdater());

            return false;
        }
    };

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (v.getId() == reduceTempoButton.getId() || v.getId() == increaseTempoButton.getId()) {
                if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)) {
                    autoIncrement = false;
                    autoReduce = false;
                }
            } else {
                throw new IllegalAccessError();
            }
            return false;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.bottom_fragment, container, false);
        bindViews();
        init();
        return layoutView;
    }

    private void bindViews() {
        tempoField = (CustomEditText) layoutView.findViewById(R.id.field_tempo);
        reduceTempoButton = layoutView.findViewById(R.id.button_reduce_tempo);
        increaseTempoButton = layoutView.findViewById(R.id.button_increase_tempo);
    }

    private void init() {
        preferences = getActivity().getSharedPreferences(Constants.ALL_PREFERENCES_KEY, Context.MODE_PRIVATE);
        currentTempo = preferences.getInt(Constants.PREFERENCE_KEY_TEMPO, DEFAULT_TEMPO);
        tempoField.setText(Integer.toString(currentTempo));
        autoIncrement = false;
        autoReduce = false;

        tempoField.setEditingListener(editingListener);
        tempoField.setOnEditorActionListener(onEditorActionListener);
        reduceTempoButton.setOnClickListener(onClickListener);
        increaseTempoButton.setOnClickListener(onClickListener);

        reduceTempoButton.setOnLongClickListener(onLongClickListener);
        increaseTempoButton.setOnLongClickListener(onLongClickListener);

        reduceTempoButton.setOnTouchListener(onTouchListener);
        increaseTempoButton.setOnTouchListener(onTouchListener);
    }

    public void setTempoChangedListener(TempoChangedListener tempoChangedListener) {
        this.tempoChangedListener = tempoChangedListener;
    }

    private void increment() {
        if (currentTempo >= MAX_TEMPO_LIMIT) {
            autoIncrement = false;
        } else {
            currentTempo++;
            tempoField.setText(Integer.toString(currentTempo));
            onUserDoneTyping();
        }
    }

    private void decrement() {
        if (currentTempo <= MINIMAL_TEMPO_LIMIT) {
            autoReduce = false;
        } else {
            currentTempo--;
            tempoField.setText(Integer.toString(currentTempo));
            onUserDoneTyping();
        }
    }


    private void onUserDoneTyping(){
        String value = tempoField.getText().toString();
        try {
            currentTempo = Integer.parseInt(value);
            if (currentTempo < MINIMAL_TEMPO_LIMIT) {
                currentTempo = MINIMAL_TEMPO_LIMIT;
                tempoField.setText(Integer.toString(currentTempo));
            } else if (currentTempo > MAX_TEMPO_LIMIT) {
                currentTempo = MAX_TEMPO_LIMIT;
                tempoField.setText(Integer.toString(currentTempo));
            }
        } catch (Exception e) {
            e.printStackTrace();
            currentTempo = DEFAULT_TEMPO;
            tempoField.setText(Integer.toString(currentTempo));
        }
        Log.d("tempoch", "tempochanged:" + currentTempo);
        tempoChanged();
    }

    private void tempoChanged() {

        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putInt(Constants.PREFERENCE_KEY_TEMPO, currentTempo);
        prefsEditor.apply();

        if (tempoChangedListener != null) {
            tempoChangedListener.onTempoChanged();
        }
    }

    class TempoUpdater implements Runnable {
        public void run() {
            if (autoIncrement) {
                increment();
                repeatUpdateHandler.postDelayed(new TempoUpdater(), REP_DELAY);
            } else if (autoReduce) {
                decrement();
                repeatUpdateHandler.postDelayed(new TempoUpdater(), REP_DELAY);
            }
        }
    }
}
