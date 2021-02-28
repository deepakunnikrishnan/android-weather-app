package com.androidnerds.weatherview.domain;

import com.androidnerds.weatherview.domain.model.LocationInfo;
import com.androidnerds.weatherview.domain.model.WeatherInfo;

import io.reactivex.rxjava3.core.Single;

public interface IWeatherRepository {

    public Single<LocationInfo> getLocationInfo(String query);

    public Single<LocationInfo> getLocationInfo(double latitude, double longitude);

    public Single<WeatherInfo> getWeatherInfo(int locationId);

}