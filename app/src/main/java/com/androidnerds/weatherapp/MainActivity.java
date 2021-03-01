package com.androidnerds.weatherapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnerds.weatherview.presentation.WeatherViewFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, WeatherViewFragment.newInstance())
                    .commitNow();
        }
    }
}