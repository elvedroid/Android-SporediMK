package com.example.elvedin.sporedimk.ui.manager.network_manager;

import com.example.elvedin.sporedimk.model.CatLang;
import com.example.elvedin.sporedimk.model.Category;
import com.example.elvedin.sporedimk.model.Favorites;
import com.example.elvedin.sporedimk.model.Offer;
import com.example.elvedin.sporedimk.model.Product;
import com.example.elvedin.sporedimk.model.ProductOffers;
import com.example.elvedin.sporedimk.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ClientInterface {
    @GET(Constants.GET_ROOT_CATEGORIES + "/{lang}")
    Call<List<Category>> getRootCategories(@Path("lang") String lang);

    @GET(Constants.GET_LEAF_CATEGORIES + "/{lang}")
    Call<List<Category>> getLeafCategories(@Path("lang") String lang);

    @POST(Constants.GET_SUBCATEGORIES)
    Call<List<Category>> getCategoriesFromCategory(@Body CatLang fromCategory);

    @POST(Constants.GET_OFFERS_PER_CATEGORY)
    Call<List<Offer>> getOffers(@Body CatLang fromCategory);

    @POST(Constants.GET_OFFERS_FOR_PRODUCT)
    Call<List<ProductOffers>> getOffersWithSameProduct(@Body Product product);

    @POST(Constants.ADD_PRODUCT_TO_FAVORITES)
    Call<Void> addProductsToFavorites(@Body Favorites product);

    @POST(Constants.CHECK_IF_PRODUCT_IS_FAVORITE)
    Call<Boolean> checkIfProductIsFavorite(@Body Favorites product);

    @POST(Constants.GET_MY_FAVORITE_PRODUCTS)
    Call<List<Offer>> getMyFavoriteProducts(@Body String appId);

    @GET(Constants.GET_MOST_FAVORITE_PRODUCTS)
    Call<List<Offer>> getMostFavoriteProducts();
}
