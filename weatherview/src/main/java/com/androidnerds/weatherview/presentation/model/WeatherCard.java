package com.androidnerds.weatherview.presentation.model;

import androidx.annotation.DrawableRes;

import com.androidnerds.weatherview.presentation.customviews.WeatherInfoLabelView;

public class WeatherCard {
    private int locationId;
    private String cityName;
    private String weatherStatus;
    @DrawableRes
    private int statusIcon;
    private String currentTemperature;

    private WeatherInfoLabelView.LabelInfo minTemperature;
    private WeatherInfoLabelView.LabelInfo maxTemperature;

    private WeatherInfoLabelView.LabelInfo wind;
    private WeatherInfoLabelView.LabelInfo humidity;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWeatherStatus() {
        return weatherStatus;
    }

    public void setWeatherStatus(String weatherStatus) {
        this.weatherStatus = weatherStatus;
    }

    public int getStatusIcon() {
        return statusIcon;
    }

    public void setStatusIcon(int statusIcon) {
        this.statusIcon = statusIcon;
    }

    public String getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(String currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public WeatherInfoLabelView.LabelInfo getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(WeatherInfoLabelView.LabelInfo minTemperature) {
        this.minTemperature = minTemperature;
    }

    public WeatherInfoLabelView.LabelInfo getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(WeatherInfoLabelView.LabelInfo maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public WeatherInfoLabelView.LabelInfo getWind() {
        return wind;
    }

    public void setWind(WeatherInfoLabelView.LabelInfo wind) {
        this.wind = wind;
    }

    public WeatherInfoLabelView.LabelInfo getHumidity() {
        return humidity;
    }

    public void setHumidity(WeatherInfoLabelView.LabelInfo humidity) {
        this.humidity = humidity;
    }
}