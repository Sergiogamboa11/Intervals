package com.example.intervals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

public class PresetListViewAdapter extends ArrayAdapter<Interval> {


    private Context mContext;
    int mResource;

    public PresetListViewAdapter(Context context, int resource, ArrayList<Interval> objects) {
        super(context, resource, objects);
        this.mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String time = getItem(position).getTime();
        String action = getItem(position).getAction();
//
//        Interval interval = new Interval(time, action);

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        NumberPicker numberPickerMin = (NumberPicker) convertView.findViewById(R.id.minutesPicker);
        NumberPicker numberPickerSec = (NumberPicker) convertView.findViewById(R.id.secondsPicker);
        TextView actionIn = (TextView) convertView.findViewById(R.id.actionInput);



//        TextView tvTime = (TextView) convertView.findViewById(R.id.timeTextView);
//        TextView tvAction = (TextView) convertView.findViewById(R.id.actionTextView);
//
//        tvTime.setText(time);
//        tvAction.setText(action);

        return convertView;
    }
}
