package com.example.elvedin.sporedimk.di.component;

import com.example.elvedin.sporedimk.MainApplication;
import com.example.elvedin.sporedimk.di.module.AppModule;
import com.example.elvedin.sporedimk.di.module.NetModule;
import com.example.elvedin.sporedimk.main.MainActivity;
import com.example.elvedin.sporedimk.products.productdetails.ProductDetailsFragment;
import com.example.elvedin.sporedimk.products.productdetails.ProductDetailsModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by elvedin on 12/4/17.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class, ProductDetailsModule.class})
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(ProductDetailsFragment productDetailsFragment);
}
