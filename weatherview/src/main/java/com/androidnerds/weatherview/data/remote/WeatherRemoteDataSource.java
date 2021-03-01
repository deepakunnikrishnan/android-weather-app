package com.androidnerds.weatherview.data.remote;

import com.androidnerds.common.rx.SchedulerProvider;
import com.androidnerds.weatherview.data.remote.dto.LocationInfoDto;
import com.androidnerds.weatherview.data.remote.dto.WeatherInfoDto;
import com.androidnerds.weatherview.data.remote.service.WeatherDataService;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

/**
 * Remote DataSource implementation for the WeatherData.
 * Integrates the API services for the WeatherData.
 */
public class WeatherRemoteDataSource {

    private final WeatherDataService weatherDataService;
    private final SchedulerProvider schedulerProvider;

    @Inject
    public WeatherRemoteDataSource(WeatherDataService weatherDataService,
                                   SchedulerProvider schedulerProvider) {
        this.weatherDataService = weatherDataService;
        this.schedulerProvider = schedulerProvider;
    }

    /**
     * Requests the Location-Search API with the a name.
     * @param query - city name
     * @return a Single that returns Location information for the query.
     */
    public Single<LocationInfoDto> getLocation(String query) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("query", query);
        return weatherDataService.getLocation(queryMap)
                .map(locationInfoDtos -> locationInfoDtos.get(0))
                .subscribeOn(schedulerProvider.io());
    }

    /**
     * Requests the Location-Search API with the Lat-Lng info provided.
     * @return a Single that returns Location information for the Lat-Lng passed.
     */
    public Single<LocationInfoDto> getLocation(double lat, double lng) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("lattlong", lat + "," + lng);
        return weatherDataService.getLocation(queryMap)
                .map(locationInfoDtos -> locationInfoDtos.get(0))
                .subscribeOn(schedulerProvider.io());
    }

    /**
     * Request the Weather Data API with the woeid.
     * @param woeid - Where On EarthId
     * @return a Single that returns Weather information for the id.
     */
    public Single<WeatherInfoDto> getWeather(int woeid) {
        return weatherDataService.getWeather(woeid)
                .subscribeOn(schedulerProvider.io());
    }


}
