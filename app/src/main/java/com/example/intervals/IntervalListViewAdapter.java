package com.example.intervals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class IntervalListViewAdapter extends ArrayAdapter<Interval> {


    private Context mContext;
    int mResource;

    public IntervalListViewAdapter(Context context, int resource, ArrayList<Interval> objects) {
        super(context, resource, objects);
        this.mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String time = getItem(position).getTime();
        String action = getItem(position).getAction();

        Interval interval = new Interval(time, action);

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        TextView tvTime = (TextView) convertView.findViewById(R.id.timeTextView);
        TextView tvAction = (TextView) convertView.findViewById(R.id.actionTextView);

        tvTime.setText(time);
        tvAction.setText(action);

        return convertView;
    }
}
