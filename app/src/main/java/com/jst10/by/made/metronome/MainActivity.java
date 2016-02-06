package com.jst10.by.made.metronome;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.jst10.by.made.metronome.fragments.BottomFragment2;
import com.jst10.by.made.metronome.fragments.MiddleFragment1;
import com.jst10.by.made.metronome.fragments.TopFragment;
import com.jst10.by.made.metronome.interfaces.TempoChangedListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_TOP_FRAGMENT = "top_fragment";
    private static final String TAG_BOTTOM_FRAGMENT = "bottom_fragment1";
    private static final String TAG_MIDDLE_FRAGMENT = "middle_fragment";

    private static final double NUMBER_OF_SECONDS_IN_MINUTE=60.0;
    private static final double NUMBER_OF_TICKS_PER_BIT=100.0;

    public static final int DEFAULT_TEMPO = 60;
    public static final int MAX_TEMPO_LIMIT = 300;
    public static final int MINIMAL_TEMPO_LIMIT = 30;



    private SharedPreferences preferences;
    private CountDownTimer timer;
    private int timerInterval;
    private int currentTick=0;

    private TempoChangedListener tempoChangedListener=new TempoChangedListener() {
        @Override
        public void onTempoChanged() {
            updateTempoFromSettings();
        }
    };

    private void updateTempoFromSettings(){
        int currentTempo = preferences.getInt(Constants.PREFERENCE_KEY_TEMPO, MainActivity.DEFAULT_TEMPO);
        timerInterval=(int)((NUMBER_OF_SECONDS_IN_MINUTE/currentTempo)*1000);
        initTimer();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferences = getSharedPreferences(Constants.ALL_PREFERENCES_KEY, Context.MODE_PRIVATE);
        updateTempoFromSettings();

    }


    @Override
    protected void onResume() {
        super.onResume();
        initFragments();
        initTimer();
    }

    private void initFragments() {
        initTopFragment();
        initBottomFragment();
        initMiddleFragment();
    }

    private void initTopFragment() {
        Fragment fragment = new TopFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.top_fragment_container, fragment, TAG_TOP_FRAGMENT);
        transaction.commit();
    }

    private void initBottomFragment() {
        Fragment fragment = new BottomFragment2();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.bottom_fragment_container, fragment, TAG_BOTTOM_FRAGMENT);
        transaction.commit();
    }

    private void initMiddleFragment() {
        Fragment fragment = new MiddleFragment1();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.middle_fragment_container, fragment, TAG_MIDDLE_FRAGMENT);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initTimer(){
        if(timer!=null){
            timer.cancel();
        }
        createNewTInfinitiveTimer();
        currentTick=0;
        timer.start();
    }

    private void createNewTInfinitiveTimer() {
        timer = new CountDownTimer(timerInterval, (int) (timerInterval / NUMBER_OF_TICKS_PER_BIT)) {

            public void onTick(long millisUntilFinished) {
                currentTick++;
                currentTick%=NUMBER_OF_TICKS_PER_BIT;

            }

            public void onFinish() {
                timer.start();
            }
        };
    }
}
