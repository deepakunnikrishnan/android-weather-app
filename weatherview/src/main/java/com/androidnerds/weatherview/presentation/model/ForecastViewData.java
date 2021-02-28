package com.androidnerds.weatherview.presentation.model;

import androidx.annotation.DrawableRes;

public class ForecastViewData {

    private String day;
    @DrawableRes
    private int weatherStatusIcon;
    private String minTemperature;
    private String maxTemperature;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMinTemperature() {
        return minTemperature;
    }

    public int getWeatherStatusIcon() {
        return weatherStatusIcon;
    }

    public void setWeatherStatusIcon(int weatherStatusIcon) {
        this.weatherStatusIcon = weatherStatusIcon;
    }

    public void setMinTemperature(String minTemperature) {
        this.minTemperature = minTemperature;
    }

    public String getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(String maxTemperature) {
        this.maxTemperature = maxTemperature;
    }
}
