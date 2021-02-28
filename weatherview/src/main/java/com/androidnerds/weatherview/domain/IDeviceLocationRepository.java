package com.androidnerds.weatherview.domain;

import com.androidnerds.mylocation.DeviceLocation;

import io.reactivex.rxjava3.core.Flowable;

public interface IDeviceLocationRepository {

    Flowable<DeviceLocation> getCurrentLocation();
}
