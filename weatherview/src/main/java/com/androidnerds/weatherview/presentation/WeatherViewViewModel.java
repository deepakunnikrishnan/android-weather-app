package com.androidnerds.weatherview.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.androidnerds.weatherview.domain.GetCurrentLocationWeatherUseCase;
import com.androidnerds.weatherview.domain.GetMajorCitiesWeatherUseCase;
import com.androidnerds.weatherview.presentation.model.WeatherViewData;

import java.util.ArrayList;
import java.util.List;

public class WeatherViewViewModel extends ViewModel {

    private final GetCurrentLocationWeatherUseCase locationWeatherUseCase;
    private final GetMajorCitiesWeatherUseCase majorCitiesWeatherUseCase;
    private final MediatorLiveData<List<WeatherViewData>> weatherInfoMediatorLiveData = new MediatorLiveData<>();

    public WeatherViewViewModel(GetCurrentLocationWeatherUseCase locationWeatherUseCase,
                                GetMajorCitiesWeatherUseCase majorCitiesWeatherUseCase) {
        this.locationWeatherUseCase = locationWeatherUseCase;
        this.majorCitiesWeatherUseCase = majorCitiesWeatherUseCase;
        weatherInfoMediatorLiveData.addSource(majorCitiesWeatherUseCase.getLiveData(), weatherInfoMediatorLiveData::postValue);
        weatherInfoMediatorLiveData.addSource(locationWeatherUseCase.getLiveData(), weatherViewData -> {
            List<WeatherViewData> currentList = weatherInfoMediatorLiveData.getValue();
            List<WeatherViewData> newList = new ArrayList<>();
            newList.add(weatherViewData);
            if(null != currentList) {
                newList.addAll(currentList);
            }
            weatherInfoMediatorLiveData.postValue(newList);
        });
        fetchMajorCitiesWeather();
    }

    public LiveData<List<WeatherViewData>> getWeatherInfoMediatorLiveData() {
        return weatherInfoMediatorLiveData;
    }

    public void fetchUserLocation() {
        locationWeatherUseCase.execute();
    }

    private void fetchMajorCitiesWeather() {
        majorCitiesWeatherUseCase.execute();
    }
}