package com.androidnerds.weatherview.data.remote;

import com.androidnerds.common.rx.SchedulerProvider;
import com.androidnerds.weatherview.data.remote.dto.LocationInfoDto;
import com.androidnerds.weatherview.data.remote.dto.WeatherInfoDto;
import com.androidnerds.weatherview.data.remote.service.WeatherDataService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class WeatherRemoteDataSource {

    private WeatherDataService weatherDataService;
    private SchedulerProvider schedulerProvider;

    @Inject
    public WeatherRemoteDataSource(WeatherDataService weatherDataService,
                                   SchedulerProvider schedulerProvider) {
        this.weatherDataService = weatherDataService;
        this.schedulerProvider = schedulerProvider;
    }

    public Single<LocationInfoDto> getLocation(String query) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("query", query);
        return weatherDataService.getLocation(queryMap)
                .map(locationInfoDtos -> locationInfoDtos.get(0))
                .subscribeOn(schedulerProvider.io());
    }

    public Single<LocationInfoDto> getLocation(double lat, double lng) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("lattlong", lat + "," + lng);
        return weatherDataService.getLocation(queryMap)
                .map(locationInfoDtos -> locationInfoDtos.get(0))
                .subscribeOn(schedulerProvider.io());
    }

    public Single<WeatherInfoDto> getWeather(int woeid) {
        return weatherDataService.getWeather(woeid)
                .subscribeOn(schedulerProvider.io());
    }


}
