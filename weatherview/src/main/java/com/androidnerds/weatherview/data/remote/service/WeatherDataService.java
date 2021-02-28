package com.androidnerds.weatherview.data.remote.service;

import com.androidnerds.weatherview.data.remote.dto.LocationInfoDto;
import com.androidnerds.weatherview.data.remote.dto.WeatherInfoDto;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Interface for the API services of the app.
 */
public interface WeatherDataService {

    @GET("location/search/")
    Single<List<LocationInfoDto>> getLocation(@QueryMap Map<String, String> map);

    @GET("location/{woeid}/")
    Single<WeatherInfoDto> getWeather(@Path("woeid") int woeid);
}
