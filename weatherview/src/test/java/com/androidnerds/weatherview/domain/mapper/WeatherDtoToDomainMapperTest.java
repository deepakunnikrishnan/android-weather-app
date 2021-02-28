package com.androidnerds.weatherview.domain.mapper;

import com.androidnerds.common.mapping.ListMapper;
import com.androidnerds.common.mapping.ListMapperImpl;
import com.androidnerds.weatherview.data.remote.ApiConstants;
import com.androidnerds.weatherview.data.remote.dto.DayForecastDto;
import com.androidnerds.weatherview.data.remote.dto.WeatherInfoDto;
import com.androidnerds.weatherview.di.RemoteModule;
import com.androidnerds.weatherview.domain.model.DayForecast;
import com.androidnerds.weatherview.domain.model.WeatherInfo;
import com.androidnerds.weatherview.testutils.MockResponseFileReader;
import com.google.gson.Gson;

import org.junit.Test;

import static org.junit.Assert.*;

public class WeatherDtoToDomainMapperTest {

    @Test
    public void map() {
        MockResponseFileReader mockResponseFileReader = new MockResponseFileReader(ApiConstants.WEATHER_DATA);
        ListMapper<DayForecastDto, DayForecast> dtoDayForecastListMapper = new ListMapperImpl<>(
                new DayForecastDtoToDomainMapper()
        );
        WeatherDtoToDomainMapper dtoToDomainMapper = new WeatherDtoToDomainMapper(dtoDayForecastListMapper);
        Gson gson = RemoteModule.provideGson();
        WeatherInfoDto weatherInfoDto = gson.fromJson(mockResponseFileReader.getContent(), WeatherInfoDto.class);

        WeatherInfo weatherInfo = dtoToDomainMapper.map(weatherInfoDto);

        assertNotNull(weatherInfo);
        assertEquals(44418, weatherInfo.getLocationId());
        assertEquals("London", weatherInfo.getTitle());
        assertEquals("City", weatherInfo.getLocationType());
        assertEquals("Europe/London",weatherInfo.getTimeZone());
        assertEquals("LMT", weatherInfo.getTimeZoneName());
        assertNotNull(weatherInfo.getConsolidatedWeather());
    }
}