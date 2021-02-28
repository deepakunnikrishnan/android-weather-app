package com.androidnerds.weatherview.di;

import android.content.Context;

import com.androidnerds.common.mapping.Mapper;
import com.androidnerds.mylocation.DeviceLocationDataSource;
import com.androidnerds.weatherview.data.DeviceLocationRepository;
import com.androidnerds.weatherview.data.LocationRepository;
import com.androidnerds.weatherview.data.WeatherRepository;
import com.androidnerds.weatherview.data.local.LocationLocalDataSource;
import com.androidnerds.weatherview.data.remote.WeatherRemoteDataSource;
import com.androidnerds.weatherview.data.remote.dto.LocationInfoDto;
import com.androidnerds.weatherview.data.remote.dto.WeatherInfoDto;
import com.androidnerds.weatherview.domain.IDeviceLocationRepository;
import com.androidnerds.weatherview.domain.ILocationRepository;
import com.androidnerds.weatherview.domain.IWeatherRepository;
import com.androidnerds.weatherview.domain.model.LocationInfo;
import com.androidnerds.weatherview.domain.model.WeatherInfo;

import dagger.Module;
import dagger.Provides;

/**
 * Class forms the module for providing dependencies for Data layer.
 */
@Module(includes = {RemoteModule.class})
public class DataModule {

    @Provides
    public static ILocationRepository provideLocationRepository(LocationLocalDataSource locationLocalDataSource) {
        return new LocationRepository(locationLocalDataSource);
    }

    @Provides
    public static IDeviceLocationRepository provideDeviceLocationRepository(DeviceLocationDataSource deviceLocationDataSource) {
        return new DeviceLocationRepository(deviceLocationDataSource);
    }

    @Provides
    public static IWeatherRepository provideWeatherRepository(WeatherRemoteDataSource weatherRemoteDataSource,
                                                              Mapper<LocationInfoDto, LocationInfo> dtoLocationInfoMapper,
                                                              Mapper<WeatherInfoDto, WeatherInfo> dtoWeatherInfoMapper) {
        return new WeatherRepository(weatherRemoteDataSource, dtoLocationInfoMapper, dtoWeatherInfoMapper);
    }

    @Provides
    public static DeviceLocationDataSource provideDeviceLocationDataSource(Context context) {
        return new DeviceLocationDataSource(context);
    }


}
