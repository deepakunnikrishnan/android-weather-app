package com.androidnerds.weatherview.domain.mapper;

import com.androidnerds.common.mapping.ListMapper;
import com.androidnerds.common.mapping.Mapper;
import com.androidnerds.common.util.DateUtil;
import com.androidnerds.weatherview.data.remote.dto.DayForecastDto;
import com.androidnerds.weatherview.data.remote.dto.WeatherInfoDto;
import com.androidnerds.weatherview.domain.model.DayForecast;
import com.androidnerds.weatherview.domain.model.WeatherInfo;

import javax.inject.Inject;

public class WeatherDtoToDomainMapper implements Mapper<WeatherInfoDto, WeatherInfo> {

    private final ListMapper<DayForecastDto, DayForecast> dtoDayForecastListMapper;

    @Inject
    public WeatherDtoToDomainMapper(ListMapper<DayForecastDto, DayForecast> dtoDayForecastListMapper) {
        this.dtoDayForecastListMapper = dtoDayForecastListMapper;
    }

    @Override
    public WeatherInfo map(WeatherInfoDto input) {
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setLocationId(input.getWoeid());
        weatherInfo.setTitle(input.getTitle());
        weatherInfo.setLocationType(input.getLocationType());

        weatherInfo.setTimeZone(input.getTimeZone());
        weatherInfo.setTimeZoneName(input.getTimeZoneName());

        weatherInfo.setSunRise(DateUtil.stringToDate(input.getSunRise()));
        weatherInfo.setSunSet(DateUtil.stringToDate(input.getSunSet()));
        weatherInfo.setTime(DateUtil.stringToDate(input.getTime()));

        weatherInfo.setConsolidatedWeather(dtoDayForecastListMapper.map(input.getConsolidatedWeather()));

        return weatherInfo;
    }
}
