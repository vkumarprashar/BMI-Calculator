package com.marathon.bmicalculator.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.marathon.bmicalculator.R;
import com.marathon.bmicalculator.models.BodyMassIndex;

import java.util.List;

public class CustomeListViewAdapter extends BaseAdapter {

    Activity activity;
    List<BodyMassIndex> list;

    public CustomeListViewAdapter(Activity activity, List<BodyMassIndex> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public BodyMassIndex getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("INSIDE", "getView: getting called" );
        View view = activity.getLayoutInflater().inflate(R.layout.list_view_history, null, false);
        BodyMassIndex bmi = getItem(position);

        TextView genderTextView = view.findViewById(R.id.genderTextView);
        genderTextView.setText("Gender: " + bmi.getGender());
        TextView heightTextView = view.findViewById(R.id.heightTextView);
        heightTextView.setText("Height: " + bmi.getHeight());
        TextView weightTextView = view.findViewById(R.id.weightTextView);
        weightTextView.setText("Weight: " + bmi.getWeight());
        TextView resultTextView = view.findViewById(R.id.resultTextView);
        resultTextView.setText("Results: " +bmi.getResult());
        TextView dateTimeTextView = view.findViewById(R.id.dateTimeTextView);
        dateTimeTextView.setText(bmi.getDateTime());

        return view;
    }
}
