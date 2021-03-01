package com.androidnerds.weatherview.data.local;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

/**
 * DataSource for locations.
 */
public class LocationLocalDataSource {

    @Inject
    public LocationLocalDataSource() {

    }

    /**
     * Implementation of getting the cities for the app.
     * @return a Single with a list of cities.
     */
    public Single<List<String>> getCities() {
        List<String> cities = new ArrayList<>();
        cities.add("San Francisco");
        cities.add("San Jose");
        cities.add("New York");
        cities.add("London");
        cities.add("Dubai");

        return Single.just(cities);
    }
}


