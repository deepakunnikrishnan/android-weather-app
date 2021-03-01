package com.androidnerds.weatherview.di;

import com.androidnerds.common.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class SchedulerProviderModule {

    @Provides
    public static SchedulerProvider providerSchedulerProvider() {
        return new DefaultScheduler();
    }

}
