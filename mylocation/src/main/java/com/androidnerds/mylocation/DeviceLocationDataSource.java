package com.androidnerds.mylocation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class DeviceLocationDataSource {

    private Context context;
    private final FusedLocationProviderClient locationProviderClient;
    private LocationRequest locationRequest;
    private final DeviceLocationCallback locationCallback;

    private final PublishSubject<DeviceLocation> deviceLocationPublishSubject = PublishSubject.create();
    private final Flowable<DeviceLocation> deviceLocationFlowable = deviceLocationPublishSubject.toFlowable(BackpressureStrategy.LATEST);

    private final DeviceLocationMapper mapper;

    public DeviceLocationDataSource(Context context) {
        this.context = context;
        locationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        locationCallback = new DeviceLocationCallback();
        this.mapper = new DeviceLocationMapper();
    }


    public Flowable<DeviceLocation> getDeviceLocation() {
        return deviceLocationFlowable.doOnSubscribe(new Consumer<Subscription>() {
            @Override
            public void accept(Subscription subscription) throws Throwable {
                getCurrentLocation();
            }
        }).doOnCancel(new Action() {
            @Override
            public void run() throws Throwable {
                stopLocationUpdate();
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        locationProviderClient
                .getLastLocation()
                .addOnSuccessListener(location -> {
                    if (null != location) {
                        sendLocationResult(location);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d("MyLocation", "error: " + e.getMessage());
                });
        if (null == locationRequest) {
            locationRequest = LocationRequest.create();
            locationRequest.setInterval(1000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }
        requestLocationUpdates(locationRequest);
    }

    private void sendLocationResult(Location location) {
        deviceLocationPublishSubject.onNext(mapper.map(location));
    }


    @SuppressLint("MissingPermission")
    private void requestLocationUpdates(LocationRequest locationRequest) {
        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void stopLocationUpdate() {
        locationProviderClient.removeLocationUpdates(locationCallback);
    }

    class DeviceLocationCallback extends LocationCallback {

        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            if (null != locationResult) {
                List<Location> locationList = locationResult.getLocations();
                if (null != locationList && !locationList.isEmpty()) {
                    sendLocationResult(locationList.get(0));
                    stopLocationUpdate();
                }
            }
        }

        @Override
        public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {
            super.onLocationAvailability(locationAvailability);
        }
    }


}
