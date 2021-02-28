package com.androidnerds.weatherview.data.remote;

import javax.inject.Inject;

public class ApiConfig {

    private String baseUrl;

    @Inject
    public ApiConfig(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
