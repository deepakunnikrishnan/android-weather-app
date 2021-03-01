package com.androidnerds.weatherview.data;

import com.androidnerds.weatherview.data.local.LocationLocalDataSource;

import org.junit.Before;
import org.junit.Test;

public class LocationRepositoryTest {

    LocationRepository locationRepository;
    @Before
    public void setup() {
        LocationLocalDataSource locationLocalDataSource = new LocationLocalDataSource();
        locationRepository = new LocationRepository(locationLocalDataSource);
    }

    @Test
    public void testGetCitiesSuccess() {
        locationRepository.getCities()
                .test()
                .assertValueCount(1)
                .assertNoErrors()
                .assertComplete();
    }
}