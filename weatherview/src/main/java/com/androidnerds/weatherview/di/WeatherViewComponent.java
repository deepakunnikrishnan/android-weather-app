package com.androidnerds.weatherview.di;

import android.content.Context;

import com.androidnerds.weatherview.presentation.WeatherViewFragment;

import dagger.Component;

@Component(modules = {WeatherModule.class})
public interface WeatherViewComponent {
    Context context();
    void inject(WeatherViewFragment weatherViewFragment);
}
