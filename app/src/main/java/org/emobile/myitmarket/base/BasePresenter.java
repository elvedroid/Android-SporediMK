package org.emobile.myitmarket.base;

import org.emobile.myitmarket.base.scheduler.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by elvedin on 12/5/17.
 */

public class BasePresenter<V extends BaseView> {

    @Inject
    protected V mView;
    @Inject
    protected SchedulerProvider schedulerProvider;

    protected V getView() {
        return mView;
    }

    protected <T> void subscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(observer);
    }
}