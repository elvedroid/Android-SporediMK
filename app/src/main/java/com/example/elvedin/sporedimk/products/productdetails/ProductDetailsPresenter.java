package com.example.elvedin.sporedimk.products.productdetails;

import android.support.annotation.NonNull;

import com.example.elvedin.sporedimk.model.Favorites;
import com.example.elvedin.sporedimk.model.Product;
import com.example.elvedin.sporedimk.model.ProductOffers;
import com.example.elvedin.sporedimk.scheduler.BaseSchedulerProvider;
import com.example.elvedin.sporedimk.ui.manager.network_manager.ClientInterface;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

/*
 * Created by elvedin on 11/29/17.
 */

public class ProductDetailsPresenter implements ProductDetailsContract.UserActionsListener {

    private final ClientInterface repository;

    private final ProductDetailsContract.View view;

    private final BaseSchedulerProvider schedulerProvider;

    public ProductDetailsPresenter(@NonNull ClientInterface repository,
                                   @NonNull ProductDetailsContract.View view,
                                   @NonNull BaseSchedulerProvider schedulerProvider) {
        this.repository = checkNotNull(repository, "repository cannot be null");
        this.view = checkNotNull(view, "view cannot be null");
        this.schedulerProvider = checkNotNull(schedulerProvider, "schedulerProvider cannot be null");
    }

    @Override
    public void loadOffersForSameProduct(@NonNull Product product) {
        view.setProgressIndicator(true);

        repository.getOffersWithSameProduct(product)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new DisposableObserver<List<ProductOffers>>() {
                    @Override
                    public void onNext(List<ProductOffers> productOffers) {
                        view.loadOffers(productOffers);
                        view.setProgressIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError(e.getMessage());
                        view.setProgressIndicator(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void checkIfProductIsInFavorites(@NonNull Favorites favorites) {
        repository.checkIfProductIsFavorite(favorites)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        view.toggleFavorite(aBoolean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void setIsInFavorites(@NonNull final Favorites favorites) {
        repository.addProductsToFavorites(favorites)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean isSuccessfull) {
                        view.toggleFavorite(favorites.isFavorite());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
