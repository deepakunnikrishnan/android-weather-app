package com.androidnerds.weatherview.presentation.mapper;

import com.androidnerds.common.mapping.Mapper;
import com.androidnerds.weatherview.domain.model.DayForecast;
import com.androidnerds.weatherview.domain.model.WeatherInfo;
import com.androidnerds.weatherview.presentation.model.ForecastViewData;
import com.androidnerds.weatherview.presentation.model.WeatherCard;
import com.androidnerds.weatherview.presentation.model.WeatherViewData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class WeatherDomainToWeatherViewDataMapper implements Mapper<WeatherInfo, WeatherViewData> {

    private Mapper<WeatherInfo, WeatherCard> weatherCardMapper;
    private Mapper<DayForecast, ForecastViewData> forecastViewDataMapper;

    @Inject
    public WeatherDomainToWeatherViewDataMapper(Mapper<WeatherInfo, WeatherCard> weatherCardMapper,
                                                Mapper<DayForecast, ForecastViewData> forecastViewDataMapper) {
        this.weatherCardMapper = weatherCardMapper;
        this.forecastViewDataMapper = forecastViewDataMapper;
    }

    @Override
    public WeatherViewData map(WeatherInfo input) {
        WeatherViewData viewData = new WeatherViewData();
        viewData.setWeatherCard(weatherCardMapper.map(input));
        List<ForecastViewData> forecastViewDataList = new ArrayList<>();
        List<DayForecast> dayForecastList = input.getConsolidatedWeather();
        if (null != dayForecastList && dayForecastList.size() > 1) {
            for (int i = 1; i < dayForecastList.size(); i++) {
                forecastViewDataList.add(forecastViewDataMapper.map(dayForecastList.get(i)));
            }
        }
        viewData.setForecastViewDataList(forecastViewDataList);
        return viewData;
    }
}
