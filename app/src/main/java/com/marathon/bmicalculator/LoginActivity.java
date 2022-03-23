package com.marathon.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;

import com.marathon.bmicalculator.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginBtn.setOnClickListener(v -> {
            String name = binding.nameEditText.getText().toString();
            //Through error in EditText if empty
            if (TextUtils.isEmpty(name)) {
                binding.nameEditText.setError("Enter your name to continue");
                binding.nameEditText.requestFocus();
                return;
            }
            savePreferences(name);
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        });
    }

    /**
     * Method to store the name in the share preferences
     * @param name
     */
    private void savePreferences(String name) {
        SharedPreferences preferences = getSharedPreferences("LoginDetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Name", name);
        editor.commit();
    }
}