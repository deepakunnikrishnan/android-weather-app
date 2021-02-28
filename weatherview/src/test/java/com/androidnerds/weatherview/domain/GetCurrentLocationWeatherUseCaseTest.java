package com.androidnerds.weatherview.domain;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.androidnerds.common.Result;
import com.androidnerds.common.mapping.Mapper;
import com.androidnerds.common.rx.SchedulerProvider;
import com.androidnerds.mylocation.DeviceLocation;
import com.androidnerds.weatherview.data.remote.ApiConstants;
import com.androidnerds.weatherview.di.RemoteModule;
import com.androidnerds.weatherview.domain.model.LocationInfo;
import com.androidnerds.weatherview.domain.model.WeatherInfo;
import com.androidnerds.weatherview.presentation.model.ForecastViewData;
import com.androidnerds.weatherview.presentation.model.WeatherCard;
import com.androidnerds.weatherview.presentation.model.WeatherViewData;
import com.androidnerds.weatherview.rx.DefaultScheduler;
import com.androidnerds.weatherview.testutils.MockResponseFileReader;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class GetCurrentLocationWeatherUseCaseTest {


    @Mock
    IDeviceLocationRepository deviceLocationRepository;
    @Mock
    IWeatherRepository weatherRepository;
    @Mock
    Mapper<WeatherInfo, WeatherViewData> weatherDataMapper;
    SchedulerProvider schedulerProvider;
    @Mock
    private Observer<Result<WeatherViewData,Throwable>> observer;
    @Captor
    private ArgumentCaptor<Result<WeatherViewData,Throwable>> argumentCaptor;

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        schedulerProvider = new DefaultScheduler();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testExecute() {

        GetCurrentLocationWeatherUseCase useCase = new GetCurrentLocationWeatherUseCase(deviceLocationRepository,
                weatherRepository, weatherDataMapper, schedulerProvider);
        Gson gson = RemoteModule.provideGson();
        MockResponseFileReader mockResponseFileReader = new MockResponseFileReader(ApiConstants.LOCATION_INFO);

        //Mocks
        Mockito.when(deviceLocationRepository.getCurrentLocation())
                .thenReturn(Flowable.just(new DeviceLocation(10.0,10.0)));
        Mockito.when(weatherRepository.getLocationInfo(10.0,10.0))
                .thenReturn(Single.just(gson.fromJson(mockResponseFileReader.getContent(), LocationInfo.class)));
        mockResponseFileReader = new MockResponseFileReader(ApiConstants.WEATHER_INFO);
        Mockito.when(weatherRepository.getWeatherInfo(44418))
                .thenReturn(Single.just(gson.fromJson(mockResponseFileReader.getContent(), WeatherInfo.class)));

        WeatherViewData weatherViewData = mockWeatherViewData();

        Mockito.when(weatherDataMapper.map(any()))
                .thenReturn(weatherViewData);

        //operation
        useCase.getLiveData().observeForever(observer);
        useCase.execute();

        //verify
        Mockito.verify(observer).onChanged(argumentCaptor.capture());
        Assert.assertNotNull(argumentCaptor.getValue());
        Assert.assertNotNull(argumentCaptor.getValue().getData());
    }

    @NotNull
    private WeatherViewData mockWeatherViewData() {
        WeatherViewData weatherViewData = new WeatherViewData();
        WeatherCard weatherCard = new WeatherCard();
        weatherViewData.setWeatherCard(weatherCard);
        List<ForecastViewData> forecastViewDataList = new ArrayList<>();
        forecastViewDataList.add(new ForecastViewData());
        forecastViewDataList.add(new ForecastViewData());
        forecastViewDataList.add(new ForecastViewData());
        forecastViewDataList.add(new ForecastViewData());
        forecastViewDataList.add(new ForecastViewData());
        weatherViewData.setForecastViewDataList(forecastViewDataList);
        return weatherViewData;
    }
}