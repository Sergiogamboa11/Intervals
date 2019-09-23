package com.example.intervals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class PresetFragment extends Fragment {

    PresetListViewAdapter adapter;
    ArrayList<Interval> intervalList;
    ListView presetListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_presets, container, false);


        Interval a = new Interval("00:05","Run1");
        Interval a2 = new Interval("00:10","Walk");
        Interval a3 = new Interval("00:17","Run");

        intervalList = new ArrayList<>();
        intervalList.add(a);
        intervalList.add(a2);
        intervalList.add(a3);

        presetListView = view.findViewById(R.id.presetListView);

        adapter = new PresetListViewAdapter(getContext(), R.layout.preset_adapter_layout, intervalList);
        presetListView.setAdapter(adapter);

        return view;
    }
}
