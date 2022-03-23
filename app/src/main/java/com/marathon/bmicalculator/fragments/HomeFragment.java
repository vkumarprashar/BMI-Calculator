package com.marathon.bmicalculator.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.marathon.bmicalculator.R;
import com.marathon.bmicalculator.database.DBHelper;
import com.marathon.bmicalculator.databinding.FragmentHomeBinding;
import com.marathon.bmicalculator.models.Gender;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    Button button;
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

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.support_simple_spinner_dropdown_item, Arrays.asList(Gender.values()));
        spinner.setAdapter(arrayAdapter);

        button.setOnClickListener(v -> {
            String str1 = heightEditText.getText().toString();
            String str2 = weightEditText.getText().toString();
            if (TextUtils.isEmpty(str1)) {
                heightEditText.setError("Please enter your weight");
                heightEditText.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(str2)) {
                weightEditText.setError("Please enter your weight");
                weightEditText.requestFocus();
                return;
            }

            float weight = Float.parseFloat(str2);
            float height = Float.parseFloat(str1) / 100;

            //Calculate BMI value
            float bmiValue = calculateBMI(weight, height);

            //Define the meaning of the bmi value
            String bmiInterpretation = interpretBMI(bmiValue);

            String result = bmiValue + " - " + bmiInterpretation;
            resultTextView.setText(result);

            String gender = spinner.getSelectedItem().toString();

            boolean check = dbHelper.saveHistory(gender, str1, str2, result);
            if (check) {
                Toast.makeText(getContext(), "VALUE IS TRUE, HENCE SAVED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "VALUE IS FALSE, HENCE SIYAPA", Toast.LENGTH_SHORT).show();
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