package com.androidnerds.weatherview.domain.mapper;

import android.text.TextUtils;

import com.androidnerds.common.mapping.Mapper;
import com.androidnerds.weatherview.data.remote.dto.LocationInfoDto;
import com.androidnerds.weatherview.domain.model.LocationInfo;

import javax.inject.Inject;

public class LocationDtoToDomainMapper implements Mapper<LocationInfoDto, LocationInfo> {

    @Inject
    public LocationDtoToDomainMapper() {

    }

    @Override
    public LocationInfo map(LocationInfoDto input) {
        LocationInfo locationInfo = new LocationInfo();
        locationInfo.setLocationId(input.getWoeid());
        locationInfo.setTitle(input.getTitle());
        locationInfo.setType(input.getLocationType());
        String latLng = input.getLatLng();
        if(!TextUtils.isEmpty(latLng)) {
            String[] splits = latLng.split(",");
            if(null != splits && splits.length == 2) {
                locationInfo.setLatitude(Double.parseDouble(splits[0]));
                locationInfo.setLongitude(Double.parseDouble(splits[1]));
            }
        }
        return locationInfo;
    }
}
