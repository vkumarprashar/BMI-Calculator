package com.marathon.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.marathon.bmicalculator.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginBtn.setOnClickListener(v -> {
            savePreferences(binding.nameEditText.getText().toString());
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        });
    }

    private void savePreferences(String name) {
        SharedPreferences preferences = getSharedPreferences("LoginDetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Name", name);
        editor.commit();
    }
}