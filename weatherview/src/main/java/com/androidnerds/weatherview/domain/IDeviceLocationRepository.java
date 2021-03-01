package com.androidnerds.weatherview.domain;

import com.androidnerds.mylocation.DeviceLocation;

import io.reactivex.rxjava3.core.Flowable;

/**
 * Interface defining the DeviceLocation repository.
 */
public interface IDeviceLocationRepository {

    Flowable<DeviceLocation> getCurrentLocation();
}
