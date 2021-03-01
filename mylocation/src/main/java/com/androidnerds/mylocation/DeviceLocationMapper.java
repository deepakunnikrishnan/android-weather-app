package com.androidnerds.mylocation;

import android.location.Location;

public class DeviceLocationMapper {

    public DeviceLocation map(Location location) {
        return new DeviceLocation(location.getLatitude(), location.getLongitude());
    }
}
