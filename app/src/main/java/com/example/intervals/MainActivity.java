package com.example.intervals;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    Button startButton, stopButton, pauseButton, resetButton;
    long startTime, currenTime;
    long pausedTime = 0;
    boolean isRunning = false;
    TextView timeTextView;
    Handler handler = new Handler();
    ListView timeListView;



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
        timeListView = (ListView) findViewById(R.id.timeListView);

        Interval a = new Interval("10:00","Run");
        Interval a2 = new Interval("10:30","Walk");
        Interval a3 = new Interval("11:00","Run");

        ArrayList<Interval> intervalList = new ArrayList<>();
        intervalList.add(a);
        intervalList.add(a2);
        intervalList.add(a3);

        IntervalListViewAdapter adapter = new IntervalListViewAdapter(this, R.layout.interval_adapter_layout, intervalList);
        timeListView.setAdapter(adapter);

    }


    /**
     * Takes a long variable and displays it in the TextView as MM:SS:NN
     * @param millis The time to be converted and displayed
     */
    public void displayTime(long millis){
        long minutes = (millis / 1000) / 60;
        long seconds = (millis / 1000) % 60;
        long milliseconds = (millis % 1000) / 10 ;
        String time = String.format("%02d:%02d:%02d",
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

}
