package com.androidnerds.weatherview.data;

import com.androidnerds.mylocation.DeviceLocation;
import com.androidnerds.mylocation.DeviceLocationDataSource;
import com.androidnerds.weatherview.domain.IDeviceLocationRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;

/**
 * Repository implementation for the DeviceLocation.
 * This class uses the {@link DeviceLocationDataSource} for retrieving the location.
 */
public class DeviceLocationRepository implements IDeviceLocationRepository {

    private final DeviceLocationDataSource deviceLocationDataSource;

    @Inject
    public DeviceLocationRepository(DeviceLocationDataSource deviceLocationDataSource) {
        this.deviceLocationDataSource = deviceLocationDataSource;
    }

    /**
     * Fetches the DeviceLocation via the {@link DeviceLocationDataSource}
     * @return a Flowable that returns the DeviceLocation.
     */
    @Override
    public Flowable<DeviceLocation> getCurrentLocation() {
        return deviceLocationDataSource.getDeviceLocation();
    }
}
