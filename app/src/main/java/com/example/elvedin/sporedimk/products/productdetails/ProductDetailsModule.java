package com.example.elvedin.sporedimk.products.productdetails;

/**
 * Created by elvedin on 12/4/17.
 */

import com.example.elvedin.sporedimk.di.ActivityScoped;
import com.example.elvedin.sporedimk.di.FragmentScoped;
import com.example.elvedin.sporedimk.model.Offer;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

import static com.example.elvedin.sporedimk.products.productdetails.ProductDetailsFragment.EXTRA_OFFER;

@Module
public abstract class ProductDetailsModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract ProductDetailsFragment productDetailFragment();

    @ActivityScoped
    @Binds
    abstract ProductDetailsContract.Presenter productDetailsPresenter(ProductDetailsPresenter presenter);

    @Provides
    @ActivityScoped
    static Offer provideOffer(ProductDetailsFragment fragment) {
        return fragment.getArguments().getParcelable(EXTRA_OFFER);
    }
}
