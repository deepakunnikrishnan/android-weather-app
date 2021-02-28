package com.androidnerds.weatherview.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidnerds.weatherview.domain.GetCurrentLocationWeatherUseCase;
import com.androidnerds.weatherview.domain.GetLocationUseCase;
import com.androidnerds.weatherview.domain.GetMajorCitiesWeatherUseCase;

import javax.inject.Inject;

public class WeatherViewModelFactory implements ViewModelProvider.Factory {

    private final GetLocationUseCase locationUseCase;
    private final GetCurrentLocationWeatherUseCase locationWeatherUseCase;
    private final GetMajorCitiesWeatherUseCase majorCitiesWeatherUseCase;


    @Inject
    public WeatherViewModelFactory(GetLocationUseCase locationUseCase,
                                   GetCurrentLocationWeatherUseCase locationWeatherUseCase,
                                   GetMajorCitiesWeatherUseCase majorCitiesWeatherUseCase) {
        this.locationUseCase = locationUseCase;
        this.locationWeatherUseCase = locationWeatherUseCase;
        this.majorCitiesWeatherUseCase = majorCitiesWeatherUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(WeatherViewViewModel.class)) {
            return (T) new WeatherViewViewModel(locationUseCase, locationWeatherUseCase, majorCitiesWeatherUseCase);
        }
        return null;
    }
}
