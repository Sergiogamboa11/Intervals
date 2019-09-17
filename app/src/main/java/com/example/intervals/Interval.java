package com.example.intervals;

public class Interval {

    private String time;
    private String action;

    public Interval(String time, String action) {
        this.time = time;
        this.action = action;
    }

    public String getTime() {
        return time;
    }

    public String getAction() {
        return action;
    }

    public void setTime(String interval) {
        this.time = interval;
    }


    public void setAction(String action) {
        this.action = action;
    }
}
