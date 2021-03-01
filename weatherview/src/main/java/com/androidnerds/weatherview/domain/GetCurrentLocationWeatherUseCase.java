package com.androidnerds.weatherview.domain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidnerds.common.Result;
import com.androidnerds.common.mapping.Mapper;
import com.androidnerds.common.rx.SchedulerProvider;
import com.androidnerds.mylocation.DeviceLocation;
import com.androidnerds.weatherview.data.DeviceLocationRepository;
import com.androidnerds.weatherview.domain.model.WeatherInfo;
import com.androidnerds.weatherview.presentation.model.WeatherViewData;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;

/**
 * UseCase for fetching the WeatherData for the current location.
 * The UseCase class communicates with the data layer.
 * The result for the UseCase is delivered to the presentation layer via LiveData.
 */
public class GetCurrentLocationWeatherUseCase {

    private final IDeviceLocationRepository deviceLocationRepository;
    private final IWeatherRepository weatherRepository;
    private final SchedulerProvider schedulerProvider;
    private final Mapper<WeatherInfo, WeatherViewData> weatherDataMapper;
    private final MutableLiveData<Result<WeatherViewData, Throwable>> weatherInfoMutableLiveData = new MutableLiveData<>();
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

    public LiveData<Result<WeatherViewData, Throwable>> getLiveData() {
        return weatherInfoMutableLiveData;
    }

    /**
     * On calling the execute, the UseCase class performs the below:
     * 1. Fetches the current location of the user using the {@link DeviceLocationRepository#getCurrentLocation()}
     * 2. Uses the Lat-Lng to retrieve the locationInfo via the {@link IWeatherRepository#getLocationInfo(double, double)}
     * 3. Fetches the Weather info for the location via the {@link IWeatherRepository#getWeatherInfo(int)}
     */
    public void execute() {
        Disposable disposable = deviceLocationRepository.getCurrentLocation()
                .flatMapSingle((Function<DeviceLocation, SingleSource<WeatherInfo>>) deviceLocation -> weatherRepository.getLocationInfo(deviceLocation.getLatitude(), deviceLocation.getLongitude())
                        .flatMap(locationInfo -> weatherRepository.getWeatherInfo(locationInfo.getLocationId())))
                .map(weatherDataMapper::map)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe(this::onSuccess, this::onFailure);
        compositeDisposable.add(disposable);
    }

    private void onSuccess(WeatherViewData viewData) {
        weatherInfoMutableLiveData.postValue(new Result<>(viewData, null));
    }

    private void onFailure(Throwable throwable) {
        weatherInfoMutableLiveData.postValue(new Result<>(null, throwable));
    }

    public void clear() {
        compositeDisposable.clear();
        compositeDisposable.dispose();
    }
}
