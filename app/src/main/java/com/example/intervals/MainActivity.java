package com.example.intervals;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    Button startButton, stopButton, pauseButton, resetButton;
    long startTime, currenTime;
    long pausedTime = 0;
    boolean isRunning = false;
    TextView timeTextView, actionTextView;
    Handler handler = new Handler();
    Handler soundHandler = new Handler();
    ListView timeListView;

    ArrayList<Interval> intervalList;

    SoundPool soundPool;
    MediaPlayer mediaPlayer;
    int alert1, alert2;
    IntervalListViewAdapter adapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        timeTextView = (TextView) findViewById(R.id.tvTime);
        actionTextView = (TextView) findViewById(R.id.tvAction);

        timeListView = (ListView) findViewById(R.id.timeListView);


        Interval a = new Interval("00:05","Run1");
        Interval a2 = new Interval("00:10","Walk");
        Interval a3 = new Interval("00:17","Run");
        Interval a4 = new Interval("00:20","Walk");
        Interval a5 = new Interval("00:25","Skip");
        Interval a6 = new Interval("11:00","Run");
        Interval a7 = new Interval("10:00","Run");
        Interval a8 = new Interval("10:30","Walk");
        Interval a9 = new Interval("11:00","Run");
        Interval a10 = new Interval("10:00","Run");
        Interval a11 = new Interval("10:30","Walk");
        Interval a12 = new Interval("11:00","Run12");

        intervalList = new ArrayList<>();
        intervalList.add(a);
        intervalList.add(a2);
        intervalList.add(a3);
        intervalList.add(a4);
        intervalList.add(a5);
        intervalList.add(a6);
        intervalList.add(a7);
        intervalList.add(a8);
        intervalList.add(a9);
        intervalList.add(a10);
        intervalList.add(a11);
        intervalList.add(a12);

        adapter = new IntervalListViewAdapter(this, R.layout.interval_adapter_layout, intervalList);
        timeListView.setAdapter(adapter);
    }

    public boolean checkTime(ArrayList<Interval> list, long currenTime){

        long minutes = (currenTime / 1000) / 60;
        long seconds = (currenTime / 1000) % 60;
        long milliseconds = (currenTime % 1000) ;
        String time = String.format("%02d:%02d",
                minutes, seconds);

        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getTime().equals(time) && milliseconds < 100) { // only play if time matches and its in the earlier part of the second (avoids multiple alarms)
                String temp = intervalList.get(i).getAction();
                actionTextView.setText( temp+ "");
                intervalList.remove(i);
                adapter.notifyDataSetChanged();
                play();
            }

        }

        return false;
    }

    /**
     * Takes a long variable and displays it in the TextView as MM:SS:NN
     * @param millis The time to be converted and displayed
     */
    public void displayTime(long millis){
        long minutes = (millis / 1000) / 60;
        long seconds = (millis / 1000) % 60;
        long milliseconds = (millis % 1000) / 10 ;
        String time = String.format("%02d:%02d.%02d",
                minutes, seconds, milliseconds);
        timeTextView.setText(time);
    }


    /**
     * Starts timer and displays it on the screen
     * @param view The button that triggers the method
     */
    public void startTimer(View view){
        if(!isRunning) {
            isRunning = true;
            startTime = System.currentTimeMillis();
            currenTime = System.currentTimeMillis() + pausedTime - startTime;

            final Runnable r = new Runnable() {
                public void run() {
                    if(isRunning) {
                        currenTime = System.currentTimeMillis() + pausedTime - startTime;
                        displayTime(currenTime);
                        checkTime(intervalList, currenTime);
                        handler.postDelayed(this, 60);
                    }
                }
            };
            handler.postDelayed(r, 60);
            
        }
    }

    public void pauseTimer(View view){
        if(isRunning){
            pausedTime = currenTime;
            isRunning = false;
        }
    }

    public void resetTimer(View view){
        displayTime(0);
        pausedTime = 0;
        isRunning = false;
    }


    public void play() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.alerthighintensity);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }
        mediaPlayer.start();
    }

    private void stopPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


}
