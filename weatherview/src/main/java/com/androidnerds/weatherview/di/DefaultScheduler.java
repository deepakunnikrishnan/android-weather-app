package com.androidnerds.weatherview.di;

import com.androidnerds.common.rx.SchedulerProvider;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Implementation of the {@link SchedulerProvider}.
 * Provides the threads to be used.
 */
public class DefaultScheduler implements SchedulerProvider {

    @Override
    public Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    public Scheduler newThread() {
        return Schedulers.newThread();
    }
}
