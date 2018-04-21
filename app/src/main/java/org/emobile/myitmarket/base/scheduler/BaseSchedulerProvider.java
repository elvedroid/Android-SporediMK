package org.emobile.myitmarket.base.scheduler;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

/*
 * Created by elvedin on 11/29/17.
 */

public interface BaseSchedulerProvider {
    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
