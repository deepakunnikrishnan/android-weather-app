package com.androidnerds.weatherview.presentation.mapper;

import com.androidnerds.common.mapping.Mapper;
import com.androidnerds.weatherview.domain.model.DayForecast;
import com.androidnerds.weatherview.domain.model.WeatherInfo;
import com.androidnerds.weatherview.presentation.model.WeatherViewData;
import com.androidnerds.weatherview.presentation.util.PresentationUtils;
import com.androidnerds.weatherview.presentation.customviews.WeatherInfoLabelView;
import com.androidnerds.weatherview.presentation.model.WeatherCard;

import java.util.List;

import javax.inject.Inject;

/**
 * Mapper implementation for mapping {@link WeatherInfo} to {@link WeatherCard}
 */
public class WeatherInfoToWeatherCardMapper implements Mapper<WeatherInfo, WeatherCard> {

    @Inject
    public WeatherInfoToWeatherCardMapper() {
    }

    @Override
    public WeatherCard map(WeatherInfo input) {
        WeatherCard weatherCard = new WeatherCard();
        weatherCard.setLocationId(input.getLocationId());
        weatherCard.setCityName(input.getTitle());

        List<DayForecast> dayForecastList = input.getConsolidatedWeather();
        if (null != dayForecastList && !dayForecastList.isEmpty()) {
            DayForecast dayForecast = dayForecastList.get(0);
            weatherCard.setWeatherStatus(dayForecast.getWeatherStateName());
            weatherCard.setStatusIcon(PresentationUtils.getDrawableForState(dayForecast.getWeatherStateAbbreviation()));
            weatherCard.setCurrentTemperature(Math.round(dayForecast.getCurrentTemperature()) + "\u00B0");
            //minTemp
            WeatherInfoLabelView.LabelInfo labelInfoMin = new WeatherInfoLabelView.LabelInfo();
            labelInfoMin.label = "MIN";
            labelInfoMin.value = Math.round(dayForecast.getMinTemperature()) + "\u00B0";
            weatherCard.setMinTemperature(labelInfoMin);
            //maxTemp
            WeatherInfoLabelView.LabelInfo labelInfoMax = new WeatherInfoLabelView.LabelInfo();
            labelInfoMax.label = "MAX";
            labelInfoMax.value = Math.round(dayForecast.getMaxTemperature()) + "\u00B0";
            weatherCard.setMaxTemperature(labelInfoMax);
            //Wind
            WeatherInfoLabelView.LabelInfo labelInfoWind = new WeatherInfoLabelView.LabelInfo();
            labelInfoWind.label = "WIND(" + dayForecast.getWindDirectionCompass() + ")";
            labelInfoWind.value = Math.round(dayForecast.getWindSpeed()) + "\u00B0";
            weatherCard.setWind(labelInfoWind);
            //Humidity
            WeatherInfoLabelView.LabelInfo labelInfoHumidity = new WeatherInfoLabelView.LabelInfo();
            labelInfoHumidity.label = "HUMIDITY";
            labelInfoHumidity.value = Math.round(dayForecast.getHumidity()) + "%";
            weatherCard.setHumidity(labelInfoHumidity);
        }
        return weatherCard;
    }
}
