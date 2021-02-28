package com.androidnerds.weatherview.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.androidnerds.weatherview.domain.GetCurrentLocationWeatherUseCase;
import com.androidnerds.weatherview.domain.GetMajorCitiesWeatherUseCase;
import com.androidnerds.weatherview.presentation.model.WeatherViewData;
import com.hadilq.liveevent.LiveEvent;

import java.util.ArrayList;
import java.util.List;

public class WeatherViewViewModel extends ViewModel {

    private final GetCurrentLocationWeatherUseCase locationWeatherUseCase;
    private final GetMajorCitiesWeatherUseCase majorCitiesWeatherUseCase;
    private final MediatorLiveData<List<WeatherViewData>> weatherInfoMediatorLiveData = new MediatorLiveData<>();
    private final LiveEvent<String> errorMessageEvent = new LiveEvent<>();

    public WeatherViewViewModel(GetCurrentLocationWeatherUseCase locationWeatherUseCase,
                                GetMajorCitiesWeatherUseCase majorCitiesWeatherUseCase) {
        this.locationWeatherUseCase = locationWeatherUseCase;
        this.majorCitiesWeatherUseCase = majorCitiesWeatherUseCase;
        weatherInfoMediatorLiveData.addSource(majorCitiesWeatherUseCase.getLiveData(), result -> {
            if(result.getData() != null) {
                weatherInfoMediatorLiveData.postValue(result.getData());
            } else {
                errorMessageEvent.setValue(result.getError().getMessage());
            }
        });
        weatherInfoMediatorLiveData.addSource(locationWeatherUseCase.getLiveData(), result -> {
            if(result.getData() != null) {
                List<WeatherViewData> currentList = weatherInfoMediatorLiveData.getValue();
                List<WeatherViewData> newList = new ArrayList<>();
                newList.add(result.getData());
                if(null != currentList) {
                    newList.addAll(currentList);
                }
                weatherInfoMediatorLiveData.postValue(newList);
            } else {
                errorMessageEvent.setValue(result.getError().getMessage());
            }
        });
        fetchMajorCitiesWeather();
    }

    public LiveData<List<WeatherViewData>> getWeatherInfoMediatorLiveData() {
        return weatherInfoMediatorLiveData;
    }

    public LiveEvent<String> getErrorMessageEvent() {
        return errorMessageEvent;
    }

    public void fetchUserLocation() {
        locationWeatherUseCase.execute();
    }

    private void fetchMajorCitiesWeather() {
        majorCitiesWeatherUseCase.execute();
    }
}