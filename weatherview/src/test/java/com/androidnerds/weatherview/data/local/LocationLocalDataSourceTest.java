package com.androidnerds.weatherview.data.local;

import org.junit.Test;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.TestObserver;

import static org.junit.Assert.*;

public class LocationLocalDataSourceTest {

    @Test
    public void testGetCities() {
        LocationLocalDataSource locationLocalDataSource = new LocationLocalDataSource();
        locationLocalDataSource
                .getCities()
                .test()
                .assertValueCount(1)
                .assertNoErrors()
                .assertComplete();
    }
}