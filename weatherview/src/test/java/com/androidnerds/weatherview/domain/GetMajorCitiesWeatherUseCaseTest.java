package com.androidnerds.weatherview.domain;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.androidnerds.common.mapping.Mapper;
import com.androidnerds.common.rx.SchedulerProvider;
import com.androidnerds.weatherview.data.LocationRepository;
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

import io.reactivex.rxjava3.core.Single;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class GetMajorCitiesWeatherUseCaseTest {

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    LocationRepository locationRepository;
    @Mock
    IWeatherRepository weatherRepository;
    @Mock
    Mapper<WeatherInfo, WeatherViewData> weatherDataMapper;

    SchedulerProvider schedulerProvider;

    @Mock
    private Observer<List<WeatherViewData>> observer;
    @Captor
    private ArgumentCaptor<List<WeatherViewData>> argumentCaptor;

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
        GetMajorCitiesWeatherUseCase useCase = new GetMajorCitiesWeatherUseCase(
                locationRepository, weatherRepository, weatherDataMapper);

        List<String> cities = new ArrayList<>();
        cities.add("San Francisco");
        cities.add("San Jose");
        cities.add("New York");
        cities.add("London");
        cities.add("Dubai");

        Mockito.when(locationRepository.getCities())
                .thenReturn(Single.just(cities));

        MockResponseFileReader mockResponseFileReader = new MockResponseFileReader(ApiConstants.LOCATION_INFO);
        Gson gson = RemoteModule.provideGson();
        Mockito.when(weatherRepository.getLocationInfo(anyString()))
                .thenReturn(Single.just(gson.fromJson(mockResponseFileReader.getContent(), LocationInfo.class)));

        mockResponseFileReader = new MockResponseFileReader(ApiConstants.WEATHER_INFO);
        Mockito.when(weatherRepository.getWeatherInfo(anyInt()))
                .thenReturn(Single.just(gson.fromJson(mockResponseFileReader.getContent(), WeatherInfo.class)));

        WeatherViewData weatherViewData = mockWeatherViewData();

        Mockito.when(weatherDataMapper.map(any(WeatherInfo.class)))
                .thenReturn(weatherViewData);

        //operation
        useCase.getLiveData().observeForever(observer);
        useCase.execute();

        //verify
        Mockito.verify(observer).onChanged(argumentCaptor.capture());
        Assert.assertNotNull(argumentCaptor.getValue());
        Assert.assertEquals(5, argumentCaptor.getValue().size());

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