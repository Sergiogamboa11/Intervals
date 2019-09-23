package com.example.intervals;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class StopwatchFragment extends Fragment {

    Button startButton, pauseButton, resetButton;
    ListView timeListView;
    TextView timeTextView, actionTextView;
    ArrayList<Interval> intervalList, rawIntervalList;
    IntervalListViewAdapter adapter;
    long startTime, currenTime;
    long pausedTime = 0;
    boolean isRunning = false;
    Handler handler = new Handler();
    MediaPlayer mediaPlayer;

    public interface  FragmentStopwatchListener{
        void onInputStopwatchSent();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);

        timeTextView = view.findViewById(R.id.tvTime);
        actionTextView = view.findViewById(R.id.tvAction);
        timeListView = view.findViewById(R.id.timeListView);

        startButton = view.findViewById(R.id.btnStart);
        startButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startTimer();
            }
        });


        pauseButton = view.findViewById(R.id.btnPause);
        pauseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                pauseTimer();
            }
        });

        resetButton = view.findViewById(R.id.btnReset);
        resetButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                resetTimer();
            }
        });

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
        rawIntervalList = (ArrayList<Interval>)intervalList.clone();

        adapter = new IntervalListViewAdapter(getContext(), R.layout.interval_adapter_layout, intervalList);

        timeListView.setAdapter(adapter);

        return view;
    }

    public boolean checkTime(ArrayList<Interval> list, long currentTime){

        long minutes = (currentTime / 1000) / 60;
        long seconds = (currentTime / 1000) % 60;
        long milliseconds = (currentTime % 1000) ;
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
    public void startTimer(){
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

    public void pauseTimer(){
        if(isRunning){
            pausedTime = currenTime;
            isRunning = false;
        }
    }

    public void resetTimer(){
        displayTime(0);
        pausedTime = 0;
        isRunning = false;
        intervalList = (ArrayList<Interval>)rawIntervalList.clone();
        adapter = new IntervalListViewAdapter(getContext(), R.layout.interval_adapter_layout, intervalList);
        timeListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void play() {
        if (mediaPlayer == null && getContext()!=null) {
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.alerthighintensity);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }
        if(mediaPlayer!=null)
            mediaPlayer.start();
        }

    private void stopPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
