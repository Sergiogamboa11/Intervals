package com.example.intervals;

import java.util.ArrayList;

public class Presets {

    String name;
    ArrayList<Interval> list;

    public Presets(String name, ArrayList<Interval> list) {
        this.name = name;
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public ArrayList<Interval> getList() {
//        return list;
//    }
//
//    public void setList(ArrayList<Interval> list) {
//        this.list = list;
//    }

    public boolean addInterval(String time, String action){

        return false;
    }

    public boolean removeInterval (String time, String action){

        return false;
    }

}
