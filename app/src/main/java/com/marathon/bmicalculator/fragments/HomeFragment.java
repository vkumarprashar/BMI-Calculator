package com.marathon.bmicalculator.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.marathon.bmicalculator.R;
import com.marathon.bmicalculator.database.DBHelper;
import com.marathon.bmicalculator.databinding.FragmentHomeBinding;
import com.marathon.bmicalculator.models.BodyMassIndex;
import com.marathon.bmicalculator.models.Gender;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {

    Button button, saveBtn;
    EditText heightEditText, weightEditText;
    TextView resultTextView;
    Spinner spinner;

    DBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        button = fragmentView.findViewById(R.id.calculateBtn);
        heightEditText = fragmentView.findViewById(R.id.heightEditText);
        weightEditText = fragmentView.findViewById(R.id.weightEditText);
        resultTextView = fragmentView.findViewById(R.id.resultTextView);
        spinner = fragmentView.findViewById(R.id.genderSpinner);
        saveBtn = fragmentView.findViewById(R.id.saveHistoryBtn);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, Arrays.asList(Gender.values()));
        spinner.setAdapter(arrayAdapter);

        BodyMassIndex bmi = new BodyMassIndex();
        button.setOnClickListener(v -> {
            String str1 = heightEditText.getText().toString();
            String str2 = weightEditText.getText().toString();

            if (TextUtils.isEmpty(str2)) {
                weightEditText.setError("Please enter your weight");
                weightEditText.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(str1)) {
                heightEditText.setError("Please enter your Height");
                heightEditText.requestFocus();
                return;
            }

            float height = Float.parseFloat(str1) / 100;
            bmi.setHeight(str1);
            float weight = Float.parseFloat(str2);
            bmi.setWeight(str2);
            //Calculate BMI value
            float bmiValue = calculateBMI(weight, height);

            //Define the meaning of the bmi value
            String bmiInterpretation = interpretBMI(bmiValue);

            String result = bmiValue + " - " + bmiInterpretation;
            bmi.setResult(result);
            resultTextView.setText(result);


            String gender = spinner.getSelectedItem().toString();
            bmi.setGender(gender);
            saveBtn.setVisibility(View.VISIBLE);
            try{
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                Log.e("Keyboard", "While hiding keyboard: "+ e.getMessage());
            }
        });

        saveBtn.setOnClickListener(v -> {
            Log.d("Save Btn", "onCreateView: Gender: " + bmi.getGender());
            boolean check = dbHelper.saveHistory(bmi.getGender(), bmi.getHeight(), bmi.getWeight(), bmi.getResult());
            if (check) {
                Toast.makeText(getContext(), "History Saved!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Error!, Contact Support!", Toast.LENGTH_SHORT).show();
            }
        });
        return fragmentView;
    }

    private float calculateBMI ( float weight, float height){
        return (float) (weight / (height * height));
    }

    // Interpret what BMI means
    private String interpretBMI ( float bmiValue){

        if (bmiValue < 16) {
            return "Severely underweight";
        } else if (bmiValue < 18.5) {
            return "Underweight";
        } else if (bmiValue < 25) {
            return "Normal";
        } else if (bmiValue < 30) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

}