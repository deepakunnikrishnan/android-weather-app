package com.androidnerds.weatherview.domain.mapper;

import com.androidnerds.weatherview.data.remote.ApiConstants;
import com.androidnerds.weatherview.data.remote.dto.DayForecastDto;
import com.androidnerds.weatherview.di.RemoteModule;
import com.androidnerds.weatherview.domain.model.DayForecast;
import com.androidnerds.weatherview.testutils.MockResponseFileReader;
import com.google.gson.Gson;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DayForecastDtoToDomainMapperTest {

    DayForecastDtoToDomainMapper dtoToDomainMapper;
    MockResponseFileReader mockResponseFileReader;

    @Test
    public void testMap() {
        mockResponseFileReader = new MockResponseFileReader(ApiConstants.FORECAST_DTO);
        Gson gson = RemoteModule.provideGson();
        DayForecastDto dto = gson.fromJson(mockResponseFileReader.getContent(), DayForecastDto.class);
        dtoToDomainMapper = new DayForecastDtoToDomainMapper();
        DayForecast actual = dtoToDomainMapper.map(dto);
        assertNotNull(actual);
        assertEquals(5978353975689216L, actual.getId());
        assertEquals("Light Cloud", actual.getWeatherStateName());
        assertEquals("lc", actual.getWeatherStateAbbreviation());
        assertEquals("ENE", actual.getWindDirectionCompass());
        assertEquals(2.95, actual.getMinTemperature(), 0);
        assertEquals(11.315, actual.getMaxTemperature(), 0);
        assertEquals(10.205, actual.getCurrentTemperature(), 0);
        assertEquals(4.99291958120841, actual.getWindSpeed(), 0);
        assertEquals(65.16662387203694, actual.getWindDirection(), 0);
        assertEquals(1037.5, actual.getAirPressure(), 0);
        assertEquals(65, actual.getHumidity(), 0);
        assertEquals(8.761644496142527, actual.getVisibility(), 0);
        assertEquals(70, actual.getPredictability(), 0);
    }
}