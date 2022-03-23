package com.marathon.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.marathon.bmicalculator.databinding.ActivityLoginBinding;
import com.marathon.bmicalculator.databinding.ActivityMainBinding;
import com.marathon.bmicalculator.fragments.HistoryFragment;
import com.marathon.bmicalculator.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        callFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_nav_calculator:  callFragment(new HomeFragment());
                break;
                case R.id.bottom_nav_history:  callFragment(new HistoryFragment());
                break;
            }
            return true;
        });
        checkPreferences();


    }

    private void checkPreferences() {
        SharedPreferences preferences = getSharedPreferences("LoginDetails", MODE_PRIVATE);
        String name  = preferences.getString("Name", null);
        if (name == null) {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        } else {
            
        }
    }

    private void callFragment(Fragment fragment) {
        FragmentManager fm  = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.commit();
    }
}