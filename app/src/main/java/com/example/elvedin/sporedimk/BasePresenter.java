package com.example.elvedin.sporedimk;

import android.view.View;

/**
 * Created by elvedin on 12/4/17.
 */

public interface BasePresenter {
//    TODO Change View to T
    <T> void takeView(T view);
    void dropView();
}
