package org.emobile.myitmarket.base;

/**
 * Created by elvedin on 12/4/17.
 */

public interface IBasePresenter {

    <T> void takeView(T view);
    void dropView();
}
