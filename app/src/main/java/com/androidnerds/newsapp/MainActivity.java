package com.androidnerds.newsapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnerds.weatherview.presentation.WeatherViewFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, WeatherViewFragment.newInstance())
        .commitNow();

    }
}