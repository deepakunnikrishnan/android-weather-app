package com.androidnerds.weatherview.data;

import com.androidnerds.weatherview.data.local.LocationLocalDataSource;
import com.androidnerds.weatherview.domain.ILocationRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

/**
 * Repository implementation of the Location data.
 * Uses the {@link LocationLocalDataSource} for fetching the actual data.
 */
public class LocationRepository implements ILocationRepository {

    private final LocationLocalDataSource locationLocalDataSource;

    @Inject
    public LocationRepository(LocationLocalDataSource locationLocalDataSource) {
        this.locationLocalDataSource = locationLocalDataSource;
    }

    /**
     * @return a Single of the list with names of the cities.
     */
    @Override
    public Single<List<String>> getCities() {
        return locationLocalDataSource.getCities();
    }
}
