package com.marathon.bmicalculator.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.marathon.bmicalculator.R;
import com.marathon.bmicalculator.adapters.CustomeListViewAdapter;
import com.marathon.bmicalculator.database.DBHelper;

public class HistoryFragment extends Fragment {

    ListView listView;
    DBHelper dbHelper;
    CustomeListViewAdapter customeListViewAdapter;
    TextView textView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getContext());
        customeListViewAdapter = new CustomeListViewAdapter(getActivity(), dbHelper.fetchData());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        listView = view.findViewById(R.id.listViewHistory);
        listView.setAdapter(customeListViewAdapter);
        textView = view.findViewById(R.id.initializeNameTextView);
        getNameFromPreferences();
        return view;
    }

    private void getNameFromPreferences() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("Name", "Owner");
        textView.setText("Hello," + name + ". Check your history!");

    }
}