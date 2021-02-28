package com.androidnerds.weatherview.data.remote.service;

import com.androidnerds.weatherview.data.remote.dto.LocationInfoDto;
import com.androidnerds.weatherview.data.remote.dto.WeatherInfoDto;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface WeatherDataService {

    //https://www.metaweather.com/api/location/search/?query=london
    //https://www.metaweather.com/api/location/search/?lattlong=50.068,-5.316
    @GET("location/search/")
    Single<List<LocationInfoDto>> getLocation(@QueryMap Map<String, String> map);

    //https://www.metaweather.com/api/location/44418/
    @GET("location/{woeid}/")
    Single<WeatherInfoDto> getWeather(@Path("woeid") int woeid);
}
