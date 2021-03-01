package com.androidnerds.weatherview.domain.mapper;

import com.androidnerds.weatherview.data.remote.ApiConstants;
import com.androidnerds.weatherview.data.remote.dto.LocationInfoDto;
import com.androidnerds.weatherview.di.RemoteModule;
import com.androidnerds.weatherview.domain.model.LocationInfo;
import com.androidnerds.weatherview.testutils.MockResponseFileReader;
import com.google.gson.Gson;

import org.junit.Test;

import static org.junit.Assert.*;

public class LocationDtoToDomainMapperTest {

    LocationDtoToDomainMapper dtoToDomainMapper;
    MockResponseFileReader mockResponseFileReader;
    @Test
    public void map() {
        mockResponseFileReader = new MockResponseFileReader(ApiConstants.LOCATION_DTO);
        dtoToDomainMapper = new LocationDtoToDomainMapper();
        Gson gson = RemoteModule.provideGson();
        LocationInfoDto dto = gson.fromJson(mockResponseFileReader.getContent(), LocationInfoDto.class);
        LocationInfo locationInfo = dtoToDomainMapper.map(dto);
        assertNotNull(locationInfo);
        assertEquals(44418, locationInfo.getLocationId());
        assertEquals("London", locationInfo.getTitle());
        assertEquals("City", locationInfo.getType());
        assertEquals(51.506321, locationInfo.getLatitude(), 0);
        assertEquals(-0.12714, locationInfo.getLongitude(), 0);
    }
}