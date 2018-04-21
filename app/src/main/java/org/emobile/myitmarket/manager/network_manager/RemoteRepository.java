package org.emobile.myitmarket.manager.network_manager;

import org.emobile.myitmarket.model.CatLang;
import org.emobile.myitmarket.model.Category;
import org.emobile.myitmarket.model.Favorites;
import org.emobile.myitmarket.model.Offer;
import org.emobile.myitmarket.model.Product;
import org.emobile.myitmarket.model.ProductOffers;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by elvedin on 11/29/17.
 */

public class RemoteRepository implements ClientInterface {
    private ClientInterface api;

    @Inject
    public RemoteRepository(Retrofit retrofit) {
        this.api = retrofit.create(ClientInterface.class);
    }

    @Override
    public Call<List<Category>> getRootCategories(String lang) {
        return api.getRootCategories(lang);
    }

    @Override
    public Call<List<Category>> getLeafCategories(String lang) {
        return api.getLeafCategories(lang);
    }

    @Override
    public Call<List<Category>> getCategoriesFromCategory(CatLang fromCategory) {
        return api.getCategoriesFromCategory(fromCategory);
    }

    @Override
    public Call<List<Offer>> getOffers(CatLang fromCategory, Integer page, Integer perPage) {
        return api.getOffers(fromCategory, page, perPage);
    }

    @Override
    public Observable<List<ProductOffers>> getOffersWithSameProduct(Product product) {
        return api.getOffersWithSameProduct(product);
    }

    @Override
    public Observable<Boolean> addProductsToFavorites(Favorites product) {
        return api.addProductsToFavorites(product);
    }

    @Override
    public Observable<Boolean> checkIfProductIsFavorite(Favorites product) {
        return api.checkIfProductIsFavorite(product);
    }

    @Override
    public Call<List<Offer>> getMyFavoriteProducts(String appId) {
        return api.getMyFavoriteProducts(appId);
    }

    @Override
    public Call<List<Offer>> getMostFavoriteProducts() {
        return api.getMostFavoriteProducts();
    }

    @Override
    public Call<List<Offer>> getFilteredProducts(String search_phrase, String lang, Integer page, Integer perPage) {
        return api.getFilteredProducts(search_phrase, lang, page, perPage);
    }
}
