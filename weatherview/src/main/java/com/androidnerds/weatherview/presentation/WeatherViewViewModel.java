package com.androidnerds.weatherview.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.androidnerds.common.Result;
import com.androidnerds.weatherview.domain.GetCurrentLocationWeatherUseCase;
import com.androidnerds.weatherview.domain.GetMajorCitiesWeatherUseCase;
import com.androidnerds.weatherview.domain.model.WeatherInfo;
import com.androidnerds.weatherview.presentation.model.WeatherCard;
import com.androidnerds.weatherview.presentation.model.WeatherViewData;
import com.hadilq.liveevent.LiveEvent;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WeatherViewViewModel extends ViewModel {

    private final GetCurrentLocationWeatherUseCase locationWeatherUseCase;
    private final GetMajorCitiesWeatherUseCase majorCitiesWeatherUseCase;
    private final MediatorLiveData<List<WeatherViewData>> weatherInfoMediatorLiveData = new MediatorLiveData<>();
    private final LiveEvent<String> errorMessageEvent = new LiveEvent<>();

    public WeatherViewViewModel(GetCurrentLocationWeatherUseCase locationWeatherUseCase,
                                GetMajorCitiesWeatherUseCase majorCitiesWeatherUseCase) {
        this.locationWeatherUseCase = locationWeatherUseCase;
        this.majorCitiesWeatherUseCase = majorCitiesWeatherUseCase;
        weatherInfoMediatorLiveData.addSource(majorCitiesWeatherUseCase.getLiveData(), this::handleMajorCitiesWeather);
        weatherInfoMediatorLiveData.addSource(locationWeatherUseCase.getLiveData(), this::handleCurrentLocationWeather);
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

    private void handleCurrentLocationWeather(Result<WeatherViewData, Throwable> result) {
        if(result.getData() != null) {
            List<WeatherViewData> currentList = weatherInfoMediatorLiveData.getValue();
            List<WeatherViewData> newList = new ArrayList<>();
            newList.add(result.getData());
            if(null != currentList) {
                newList.addAll(removeEmptyWeatherData(currentList));
            }
            weatherInfoMediatorLiveData.postValue(newList);
        } else {
            errorMessageEvent.setValue(result.getError().getMessage());
        }
    }

    private void handleMajorCitiesWeather(Result<List<WeatherViewData>, Throwable> result) {
        if(result.getData() != null) {
            List<WeatherViewData> weatherViewDataList = getWeatherViewData(result);
            weatherInfoMediatorLiveData.postValue(weatherViewDataList);
        } else {
            errorMessageEvent.setValue(result.getError().getMessage());
        }
    }

    @NotNull
    private List<WeatherViewData> getWeatherViewData(Result<List<WeatherViewData>, Throwable> result) {
        List<WeatherViewData> weatherViewDataList = result.getData();
        WeatherViewData emptyWeatherViewData = new WeatherViewData();
        emptyWeatherViewData.setEmpty(true);
        WeatherCard weatherCard = new WeatherCard();
        weatherCard.setEmpty(true);
        emptyWeatherViewData.setWeatherCard(weatherCard);
        emptyWeatherViewData.setForecastViewDataList(new ArrayList<>());
        weatherViewDataList.add(emptyWeatherViewData);
        return weatherViewDataList;
    }

    private List<WeatherViewData> removeEmptyWeatherData(List<WeatherViewData> currentList) {
        return currentList.stream().filter(viewData -> !viewData.isEmpty()).collect(Collectors.toList());
    }

    @Override
    protected void onCleared() {
        locationWeatherUseCase.clear();
        majorCitiesWeatherUseCase.clear();
        super.onCleared();
    }
}