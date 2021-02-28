package com.androidnerds.weatherview.domain;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidnerds.common.rx.SchedulerProvider;
import com.androidnerds.mylocation.DeviceLocation;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class GetLocationUseCase {

    protected MutableLiveData<DeviceLocation> liveData = new MutableLiveData<>();

    private final IDeviceLocationRepository deviceLocationRepository;
    private final SchedulerProvider schedulerProvider;
    private final CompositeDisposable compositeDisposable;

    @Inject
    public GetLocationUseCase(Context context, IDeviceLocationRepository deviceLocationRepository, SchedulerProvider schedulerProvider) {
        this.deviceLocationRepository = deviceLocationRepository;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = new CompositeDisposable();
    }

    public void execute() {
        Disposable disposable = this.deviceLocationRepository.getCurrentLocation()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.mainThread())
                .subscribe(this::onNext, this::onError);
        compositeDisposable.add(disposable);
    }

    public LiveData<DeviceLocation> getLiveData() {
        return liveData;
    }

    private void onNext(DeviceLocation deviceLocation) {
        liveData.postValue(deviceLocation);
        compositeDisposable.dispose();

    }

    private void onError(Throwable throwable) {

    }

}
