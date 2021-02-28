package com.androidnerds.weatherview.domain;

import com.androidnerds.mylocation.DeviceLocation;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface ILocationRepository {

    Single<List<String>> getCities();
}
