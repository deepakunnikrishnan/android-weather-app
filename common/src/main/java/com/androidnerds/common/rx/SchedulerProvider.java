package com.androidnerds.common.rx;

import io.reactivex.rxjava3.core.Scheduler;

public interface SchedulerProvider {

    Scheduler mainThread();
    Scheduler io();
    Scheduler newThread();

}