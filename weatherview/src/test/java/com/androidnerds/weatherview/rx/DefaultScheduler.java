package com.androidnerds.weatherview.rx;

import com.androidnerds.common.rx.SchedulerProvider;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DefaultScheduler implements SchedulerProvider {
    @Override
    public Scheduler mainThread() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler newThread() {
        return Schedulers.trampoline();
    }
}
