package org.emobile.myitmarket.base.products.productdetails;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.emobile.myitmarket.model.Favorites;
import org.emobile.myitmarket.model.Product;
import org.emobile.myitmarket.base.scheduler.BaseSchedulerProvider;
import org.emobile.myitmarket.base.scheduler.SchedulerProvider;
import org.emobile.myitmarket.manager.network_manager.RemoteRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

/*
 * Created by elvedin on 11/29/17.
 */

public class ProductDetailsPresenter implements ProductDetailsContract.Presenter {

    private final RemoteRepository repository;

    @Nullable
    private ProductDetailsContract.View view;

    private BaseSchedulerProvider schedulerProvider;

    @Inject
    public ProductDetailsPresenter(@NonNull RemoteRepository repository) {
        this.repository = checkNotNull(repository, "repository cannot be null");
        this.schedulerProvider = new SchedulerProvider();
    }

    @Override
    public void loadOffersForSameProduct(@NonNull Product product) {

        if (view != null)
            view.setProgressIndicator(true);

        repository.getOffersWithSameProduct(product)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(productOffers -> {
                    if (view != null) {
                        view.loadOffers(productOffers);
                        view.setProgressIndicator(false);
                    }
                }, error -> {
                    if (view != null) {
                        view.showError(error.getMessage());
                        view.setProgressIndicator(false);
                    }
                });
    }

    @Override
    public void checkIfProductIsInFavorites(@NonNull Favorites favorites) {
        repository.checkIfProductIsFavorite(favorites)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(isInFavorites -> {
                    if (view != null) {
                        view.toggleFavorite(isInFavorites);
                    }
                }, error -> {
                });
    }

    @Override
    public void setIsInFavorites(@NonNull final Favorites favorites) {
        repository.addProductsToFavorites(favorites)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isSuccessful -> {
                    if (view != null)
                        view.toggleFavorite(favorites.isFavorite());
                }, error -> {
                });
    }

    @Override
    public <T> void takeView(T view) {
        this.view = (ProductDetailsContract.View) view;
    }

    @Override
    public void dropView() {
        this.view = null;
    }
}
