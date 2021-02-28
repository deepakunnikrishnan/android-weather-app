package com.androidnerds.weatherview.domain.mapper;

import com.androidnerds.common.mapping.Mapper;
import com.androidnerds.common.util.DateUtil;
import com.androidnerds.weatherview.data.remote.dto.DayForecastDto;
import com.androidnerds.weatherview.domain.model.DayForecast;

import java.util.Calendar;

import javax.inject.Inject;

public class DayForecastDtoToDomainMapper implements Mapper<DayForecastDto, DayForecast> {

    @Inject
    public DayForecastDtoToDomainMapper() {

    }

    @Override
    public DayForecast map(DayForecastDto input) {
        DayForecast dayForecast = new DayForecast();
        dayForecast.setId(input.getId());
        dayForecast.setWeatherStateName(input.getWeatherStateName());
        dayForecast.setWeatherStateAbbreviation(input.getWeatherStateAbbreviation());
        dayForecast.setWindDirection(input.getWindDirection());
        dayForecast.setWindSpeed(input.getWindSpeed());
        dayForecast.setWindDirectionCompass(input.getWindDirectionCompass());
        dayForecast.setCreatedTime(DateUtil.stringToDate(input.getCreatedTime()));

        dayForecast.setApplicableTime(DateUtil.stringToDate(input.getApplicableTime(), Calendar.getInstance().getTimeZone().getID()));
        dayForecast.setMinTemperature(input.getMinTemperature());
        dayForecast.setMaxTemperature(input.getMaxTemperature());
        dayForecast.setCurrentTemperature(input.getCurrentTemperature());
        dayForecast.setAirPressure(input.getAirPressure());
        dayForecast.setHumidity(input.getHumidity());
        dayForecast.setVisibility(input.getVisibility());
        dayForecast.setPredictability(input.getPredictability());
        return dayForecast;
    }
}
