package com.androidnerds.weatherview.data.remote;

import javax.inject.Inject;

/**
 * Configuration class for the API.
 */
public class ApiConfig {

    private final String baseUrl;

    @Inject
    public ApiConfig(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
