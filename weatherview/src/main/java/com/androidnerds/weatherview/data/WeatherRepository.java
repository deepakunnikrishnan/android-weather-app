package com.androidnerds.weatherview.data;

import com.androidnerds.common.mapping.Mapper;
import com.androidnerds.weatherview.data.remote.WeatherRemoteDataSource;
import com.androidnerds.weatherview.data.remote.dto.LocationInfoDto;
import com.androidnerds.weatherview.data.remote.dto.WeatherInfoDto;
import com.androidnerds.weatherview.domain.IWeatherRepository;
import com.androidnerds.weatherview.domain.model.LocationInfo;
import com.androidnerds.weatherview.domain.model.WeatherInfo;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class WeatherRepository implements IWeatherRepository {

    private WeatherRemoteDataSource weatherRemoteDataSource;
    private Mapper<LocationInfoDto, LocationInfo> dtoLocationInfoMapper;
    private Mapper<WeatherInfoDto, WeatherInfo> dtoWeatherInfoMapper;

    @Inject
    public WeatherRepository(WeatherRemoteDataSource weatherRemoteDataSource,
                             Mapper<LocationInfoDto, LocationInfo> dtoLocationInfoMapper,
                             Mapper<WeatherInfoDto, WeatherInfo> dtoWeatherInfoMapper) {
        this.weatherRemoteDataSource = weatherRemoteDataSource;
        this.dtoLocationInfoMapper = dtoLocationInfoMapper;
        this.dtoWeatherInfoMapper = dtoWeatherInfoMapper;
    }

    @Override
    public Single<LocationInfo> getLocationInfo(String query) {
        return weatherRemoteDataSource.getLocation(query)
                .map(locationInfoDto -> dtoLocationInfoMapper.map(locationInfoDto));
    }

    @Override
    public Single<LocationInfo> getLocationInfo(double latitude, double longitude) {
        return weatherRemoteDataSource.getLocation(latitude, longitude)
                .map(locationInfoDto -> dtoLocationInfoMapper.map(locationInfoDto));
    }

    @Override
    public Single<WeatherInfo> getWeatherInfo(int locationId) {
        return weatherRemoteDataSource.getWeather(locationId)
                .map(weatherInfoDto -> dtoWeatherInfoMapper.map(weatherInfoDto));
    }
}
