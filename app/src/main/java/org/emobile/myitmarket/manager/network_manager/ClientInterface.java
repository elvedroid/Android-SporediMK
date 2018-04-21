package org.emobile.myitmarket.manager.network_manager;

import org.emobile.myitmarket.model.CatLang;
import org.emobile.myitmarket.model.Category;
import org.emobile.myitmarket.model.Favorites;
import org.emobile.myitmarket.model.Offer;
import org.emobile.myitmarket.model.Product;
import org.emobile.myitmarket.model.ProductOffers;

import org.emobile.myitmarket.utils.Constants;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ClientInterface {
    @GET(Constants.GET_ROOT_CATEGORIES)
    Call<List<Category>> getRootCategories(@Query("lang") String lang);

    @GET(Constants.GET_LEAF_CATEGORIES + "/{lang}")
    Call<List<Category>> getLeafCategories(@Path("lang") String lang);

    @POST(Constants.GET_SUBCATEGORIES)
    Call<List<Category>> getCategoriesFromCategory(@Body CatLang fromCategory);

    @POST(Constants.GET_OFFERS_PER_CATEGORY
            + "/{" + Constants.PAGE + "}"
            + "/{" + Constants.PER_PAGE + "}")
    Call<List<Offer>> getOffers(@Body CatLang fromCategory,
                                @Path(Constants.PAGE) Integer page,
                                @Path(Constants.PER_PAGE) Integer perPage);

    @POST(Constants.GET_OFFERS_FOR_PRODUCT)
    Observable<List<ProductOffers>> getOffersWithSameProduct(@Body Product product);

    @POST(Constants.ADD_PRODUCT_TO_FAVORITES)
    Observable<Boolean> addProductsToFavorites(@Body Favorites product);

    @POST(Constants.CHECK_IF_PRODUCT_IS_FAVORITE)
    Observable<Boolean> checkIfProductIsFavorite(@Body Favorites product);

    @POST(Constants.GET_MY_FAVORITE_PRODUCTS)
    Call<List<Offer>> getMyFavoriteProducts(@Body String appId);

    @GET(Constants.GET_MOST_FAVORITE_PRODUCTS)
    Call<List<Offer>> getMostFavoriteProducts();

    @GET(Constants.GET_FILTERED_PRODUCTS + "/{" + Constants.SEARCH_PHRASE + "}"
            + "/{" + Constants.LANG + "}"
            + "/{" + Constants.PAGE + "}"
            + "/{" + Constants.PER_PAGE + "}")
    Call<List<Offer>> getFilteredProducts(
            @Path(Constants.SEARCH_PHRASE) String search_phrase,
            @Path(Constants.LANG) String lang,
            @Path(Constants.PAGE) Integer page,
            @Path(Constants.PER_PAGE) Integer perPage);
}
