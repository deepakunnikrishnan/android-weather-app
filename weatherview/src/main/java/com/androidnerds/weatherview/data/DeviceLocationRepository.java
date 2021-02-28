package com.androidnerds.weatherview.data;

import com.androidnerds.mylocation.DeviceLocation;
import com.androidnerds.mylocation.DeviceLocationDataSource;
import com.androidnerds.weatherview.domain.IDeviceLocationRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;

public class DeviceLocationRepository implements IDeviceLocationRepository {

    private DeviceLocationDataSource deviceLocationDataSource;

    @Inject
    public DeviceLocationRepository(DeviceLocationDataSource deviceLocationDataSource) {
        this.deviceLocationDataSource = deviceLocationDataSource;
    }

    @Override
    public Flowable<DeviceLocation> getCurrentLocation() {
        return deviceLocationDataSource.getDeviceLocation();
    }
}
