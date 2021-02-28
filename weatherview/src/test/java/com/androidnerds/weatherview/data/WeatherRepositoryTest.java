package com.androidnerds.weatherview.data;

import com.androidnerds.common.mapping.Mapper;
import com.androidnerds.weatherview.data.remote.WeatherRemoteDataSource;
import com.androidnerds.weatherview.data.remote.dto.LocationInfoDto;
import com.androidnerds.weatherview.data.remote.dto.WeatherInfoDto;
import com.androidnerds.weatherview.domain.model.LocationInfo;
import com.androidnerds.weatherview.domain.model.WeatherInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.rxjava3.core.Single;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class WeatherRepositoryTest {

    WeatherRepository weatherRepository;
    @Mock
    WeatherRemoteDataSource weatherRemoteDataSource;
    @Mock
    Mapper<LocationInfoDto, LocationInfo> dtoLocationInfoMapper;
    @Mock
    Mapper<WeatherInfoDto, WeatherInfo> dtoWeatherInfoMapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        weatherRepository = new WeatherRepository(weatherRemoteDataSource,
                dtoLocationInfoMapper,
                dtoWeatherInfoMapper);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testGetLocationInfoForQuerySuccess() {
        Mockito.when(weatherRemoteDataSource.getLocation("London"))
                .thenReturn(Single.just(new LocationInfoDto()));
        Mockito.when(dtoLocationInfoMapper.map(any()))
                .thenReturn(new LocationInfo());

        LocationInfo locationInfo = weatherRepository.getLocationInfo("London")
                .blockingGet();
        assertNotNull(locationInfo);
    }

    @Test
    public void testGetLocationInfoForLatLngSuccess() {
        Mockito.when(weatherRemoteDataSource.getLocation(10.0,10.0))
                .thenReturn(Single.just(new LocationInfoDto()));
        Mockito.when(dtoLocationInfoMapper.map(any()))
                .thenReturn(new LocationInfo());

        LocationInfo locationInfo = weatherRepository.getLocationInfo(10.0,10.0)
                .blockingGet();
        assertNotNull(locationInfo);
    }

    @Test
    public void testGetWeatherInfoForSuccess() {
        Mockito.when(weatherRemoteDataSource.getWeather(10))
                .thenReturn(Single.just(new WeatherInfoDto()));
        Mockito.when(dtoWeatherInfoMapper.map(any()))
                .thenReturn(new WeatherInfo());
        WeatherInfo weatherInfo = weatherRepository.getWeatherInfo(10)
                .blockingGet();
        assertNotNull(weatherInfo);

    }
}