package com.androidnerds.weatherview.di;

import com.androidnerds.weatherview.data.remote.WeatherRemoteDataSourceTest;

import dagger.Component;

@Component(modules = {WeatherModule.class})
public interface TestWeatherViewComponent extends WeatherViewComponent {

    void inject(WeatherRemoteDataSourceTest remoteDataSourceTest);
}
