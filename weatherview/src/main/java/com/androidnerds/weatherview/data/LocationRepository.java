package com.androidnerds.weatherview.data;

import com.androidnerds.weatherview.data.local.LocationLocalDataSource;
import com.androidnerds.weatherview.domain.ILocationRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class LocationRepository implements ILocationRepository {

    private LocationLocalDataSource locationLocalDataSource;

    @Inject
    public LocationRepository(LocationLocalDataSource locationLocalDataSource) {
        this.locationLocalDataSource = locationLocalDataSource;
    }

    @Override
    public Single<List<String>> getCities() {
        return locationLocalDataSource.getCities();
    }
}
