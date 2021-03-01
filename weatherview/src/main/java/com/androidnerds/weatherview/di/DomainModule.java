package com.androidnerds.weatherview.di;

import com.androidnerds.common.mapping.ListMapper;
import com.androidnerds.common.mapping.ListMapperImpl;
import com.androidnerds.common.mapping.Mapper;
import com.androidnerds.weatherview.data.remote.dto.DayForecastDto;
import com.androidnerds.weatherview.data.remote.dto.LocationInfoDto;
import com.androidnerds.weatherview.data.remote.dto.WeatherInfoDto;
import com.androidnerds.weatherview.domain.mapper.DayForecastDtoToDomainMapper;
import com.androidnerds.weatherview.domain.mapper.LocationDtoToDomainMapper;
import com.androidnerds.weatherview.domain.mapper.WeatherDtoToDomainMapper;
import com.androidnerds.weatherview.domain.model.DayForecast;
import com.androidnerds.weatherview.domain.model.LocationInfo;
import com.androidnerds.weatherview.domain.model.WeatherInfo;
import com.androidnerds.weatherview.presentation.mapper.ForecastViewDataMapper;
import com.androidnerds.weatherview.presentation.mapper.WeatherDomainToWeatherViewDataMapper;
import com.androidnerds.weatherview.presentation.mapper.WeatherInfoToWeatherCardMapper;
import com.androidnerds.weatherview.presentation.model.ForecastViewData;
import com.androidnerds.weatherview.presentation.model.WeatherCard;
import com.androidnerds.weatherview.presentation.model.WeatherViewData;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * Class forms the module for providing dependencies for Domain layer.
 */
@Module
public abstract class DomainModule {

    @Binds
    public abstract Mapper<LocationInfoDto, LocationInfo> provideLocationDtoToDomainMapper(LocationDtoToDomainMapper locationDtoToDomainMapper);

    @Binds
    public abstract Mapper<WeatherInfoDto, WeatherInfo> provideWeatherDtoToDomainMapper(WeatherDtoToDomainMapper weatherDtoToDomainMapper);

    @Provides
    public static ListMapper<DayForecastDto, DayForecast> provideDayForecastDtoListToDomainMapper(Mapper<DayForecastDto, DayForecast> mapper) {
        return new ListMapperImpl<>(mapper);
    }

    @Binds
    public abstract Mapper<DayForecastDto, DayForecast> provideDayForecastDtoToDomainMapper(DayForecastDtoToDomainMapper dayForecastDtoToDomainMapper);

    @Binds
    public abstract Mapper<WeatherInfo, WeatherViewData> provideWeatherDataMapper(WeatherDomainToWeatherViewDataMapper mapper);

    @Binds
    public abstract Mapper<WeatherInfo, WeatherCard> provideWeatherCardMapper(WeatherInfoToWeatherCardMapper mapper);

    @Binds
    public abstract Mapper<DayForecast, ForecastViewData> provideForecastMapper(ForecastViewDataMapper mapper);

}
