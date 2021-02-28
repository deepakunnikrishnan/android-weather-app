package com.androidnerds.weatherview.domain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidnerds.common.mapping.Mapper;
import com.androidnerds.common.rx.SchedulerProvider;
import com.androidnerds.mylocation.DeviceLocation;
import com.androidnerds.weatherview.domain.model.WeatherInfo;
import com.androidnerds.weatherview.presentation.model.WeatherViewData;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;

public class GetCurrentLocationWeatherUseCase {

    private final IDeviceLocationRepository deviceLocationRepository;
    private final IWeatherRepository weatherRepository;
    private final SchedulerProvider schedulerProvider;
    private final Mapper<WeatherInfo, WeatherViewData> weatherDataMapper;
    private final MutableLiveData<WeatherViewData> weatherInfoMutableLiveData = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable;

    @Inject
    public GetCurrentLocationWeatherUseCase(IDeviceLocationRepository deviceLocationRepository,
                                            IWeatherRepository weatherRepository,
                                            Mapper<WeatherInfo, WeatherViewData> weatherDataMapper,
                                            SchedulerProvider schedulerProvider) {
        this.deviceLocationRepository = deviceLocationRepository;
        this.weatherRepository = weatherRepository;
        this.weatherDataMapper = weatherDataMapper;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = new CompositeDisposable();
    }

    public LiveData<WeatherViewData> getLiveData() {
        return weatherInfoMutableLiveData;
    }

    public void execute() {
        Disposable disposable = deviceLocationRepository.getCurrentLocation()
                .flatMapSingle((Function<DeviceLocation, SingleSource<WeatherInfo>>) deviceLocation -> weatherRepository.getLocationInfo(deviceLocation.getLatitude(), deviceLocation.getLongitude())
                        .flatMap(locationInfo -> weatherRepository.getWeatherInfo(locationInfo.getLocationId())))
                .map(weatherDataMapper::map)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe(weatherInfoMutableLiveData::postValue, throwable -> {
                    System.out.println("error: " + throwable.getMessage());
                });
        compositeDisposable.add(disposable);
    }
}
