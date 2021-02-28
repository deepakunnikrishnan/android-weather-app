package com.androidnerds.weatherview.data.local;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class LocationLocalDataSource {

    @Inject
    public LocationLocalDataSource() {

    }

    public Single<List<String>> getCities() {
        List<String> cities = new ArrayList<>();
        cities.add("San Francisco");
        cities.add("San Jose");
        cities.add("New York");
        //cities.add("New Jersey");

        return Single.just(cities);
    }
}


