package com.androidnerds.weatherview.presentation.mapper;

import com.androidnerds.common.mapping.Mapper;
import com.androidnerds.common.util.DateUtil;
import com.androidnerds.weatherview.domain.model.DayForecast;
import com.androidnerds.weatherview.presentation.util.PresentationUtils;
import com.androidnerds.weatherview.presentation.model.ForecastViewData;

import javax.inject.Inject;

public class ForecastViewDataMapper implements Mapper<DayForecast, ForecastViewData>  {

    @Inject
    public ForecastViewDataMapper() {

    }

    @Override
    public ForecastViewData map(DayForecast input) {
        ForecastViewData forecastViewData = new ForecastViewData();
        forecastViewData.setDay(DateUtil.getDayOfWeek(input.getApplicableTime()));
        forecastViewData.setWeatherStatusIcon(PresentationUtils.getDrawableForState(input.getWeatherStateAbbreviation()));
        forecastViewData.setMinTemperature(Math.round(input.getMinTemperature()) + "\u00B0");
        forecastViewData.setMaxTemperature(Math.round(input.getMaxTemperature()) + "\u00B0");
        return forecastViewData;
    }
}
