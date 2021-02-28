package com.androidnerds.weatherview.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module(includes = {
        DataModule.class,
        DomainModule.class,
        SchedulerProviderModule.class
})
public class WeatherModule {

    private final Context context;

    public WeatherModule(Context context) {
        this.context = context;
    }

    @Provides
    Context provideContext() {
        return context;
    }
}
