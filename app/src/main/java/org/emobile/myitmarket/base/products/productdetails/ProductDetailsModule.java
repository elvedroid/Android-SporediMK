package org.emobile.myitmarket.base.products.productdetails;

/**
 * Created by elvedin on 12/4/17.
 */

import org.emobile.myitmarket.base.di.scope.ActivityScoped;
import org.emobile.myitmarket.base.di.scope.FragmentScoped;
import org.emobile.myitmarket.model.Offer;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

import static org.emobile.myitmarket.base.products.productdetails.ProductDetailsFragment.EXTRA_OFFER;

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
