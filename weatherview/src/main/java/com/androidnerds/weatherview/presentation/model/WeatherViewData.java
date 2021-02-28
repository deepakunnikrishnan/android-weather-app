package com.androidnerds.weatherview.presentation.model;

import java.util.List;

public class WeatherViewData {

    private WeatherCard weatherCard;

    private List<ForecastViewData> forecastViewDataList;

    public WeatherCard getWeatherCard() {
        return weatherCard;
    }

    public void setWeatherCard(WeatherCard weatherCard) {
        this.weatherCard = weatherCard;
    }

    public List<ForecastViewData> getForecastViewDataList() {
        return forecastViewDataList;
    }

    public void setForecastViewDataList(List<ForecastViewData> forecastViewDataList) {
        this.forecastViewDataList = forecastViewDataList;
    }
}
