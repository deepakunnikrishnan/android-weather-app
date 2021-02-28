package com.androidnerds.weatherview.domain;

import androidx.lifecycle.MutableLiveData;

import com.androidnerds.common.mapping.Mapper;
import com.androidnerds.weatherview.data.LocationRepository;
import com.androidnerds.weatherview.domain.model.WeatherInfo;
import com.androidnerds.weatherview.presentation.model.WeatherViewData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;

public class GetMajorCitiesWeatherUseCase {

    protected MutableLiveData<List<WeatherViewData>> liveData = new MutableLiveData<>();

    private final IWeatherRepository weatherRepository;
    private final LocationRepository locationRepository;
    private final Mapper<WeatherInfo, WeatherViewData> weatherDataMapper;
    private final CompositeDisposable compositeDisposable;

    @Inject
    public GetMajorCitiesWeatherUseCase(LocationRepository locationRepository,
                                        IWeatherRepository weatherRepository,
                                        Mapper<WeatherInfo, WeatherViewData> weatherDataMapper) {
        this.locationRepository = locationRepository;
        this.weatherRepository = weatherRepository;
        this.weatherDataMapper = weatherDataMapper;
        this.compositeDisposable = new CompositeDisposable();
    }

    public void execute() {

        Disposable disposable = this.locationRepository.getCities()
                .flatMapObservable((Function<List<String>, ObservableSource<WeatherInfo>>) cities -> Observable.fromIterable(cities)
                        .concatMapSingle((Function<String, SingleSource<WeatherInfo>>) locationId -> weatherRepository.getLocationInfo(locationId)
                                .flatMap(locationInfo -> weatherRepository.getWeatherInfo(locationInfo.getLocationId()))
                        ))
                .map(weatherDataMapper::map)
                .toList()
                .subscribe(this::onSuccess, this::onFailure);
        this.compositeDisposable.add(disposable);
    }

    public MutableLiveData<List<WeatherViewData>> getLiveData() {
        return liveData;
    }

    private void onSuccess(List<WeatherViewData> weatherInfoList) {
        liveData.postValue(weatherInfoList);
    }

    private void onFailure(Throwable throwable) {
        System.out.println("onFailure: " + throwable.getMessage());
    }
}
