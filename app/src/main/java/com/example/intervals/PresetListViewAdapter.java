package com.example.intervals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PresetListViewAdapter extends ArrayAdapter<Interval> implements NumberPicker.OnValueChangeListener {


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
        numberPickerMin.setMinValue(0);
        numberPickerMin.setMaxValue(59);
        numberPickerSec.setMinValue(0);
        numberPickerSec.setMaxValue(59);
        numberPickerMin.setOnValueChangedListener(this);
        numberPickerSec.setOnValueChangedListener(this);
        TextView actionIn = (TextView) convertView.findViewById(R.id.actionInput);
        
//        TextView tvTime = (TextView) convertView.findViewById(R.id.timeTextView);
//        TextView tvAction = (TextView) convertView.findViewById(R.id.actionTextView);
//
//        tvTime.setText(time);
//        tvAction.setText(action);

        return convertView;
    }


    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) { //i is current val, i1 is prev
        Toast.makeText(mContext, "Prev: " + i1 + " New: " + i1, Toast.LENGTH_SHORT).show();

    }
}
