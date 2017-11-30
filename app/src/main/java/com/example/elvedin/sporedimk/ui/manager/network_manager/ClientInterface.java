package com.example.elvedin.sporedimk.ui.manager.network_manager;

import com.example.elvedin.sporedimk.model.CatLang;
import com.example.elvedin.sporedimk.model.Category;
import com.example.elvedin.sporedimk.model.Favorites;
import com.example.elvedin.sporedimk.model.Offer;
import com.example.elvedin.sporedimk.model.Product;
import com.example.elvedin.sporedimk.model.ProductOffers;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static com.example.elvedin.sporedimk.utils.Constants.*;

public interface ClientInterface {
    @GET(GET_ROOT_CATEGORIES + "/{lang}")
    Call<List<Category>> getRootCategories(@Path("lang") String lang);

    @GET(GET_LEAF_CATEGORIES + "/{lang}")
    Call<List<Category>> getLeafCategories(@Path("lang") String lang);

    @POST(GET_SUBCATEGORIES)
    Call<List<Category>> getCategoriesFromCategory(@Body CatLang fromCategory);

    @POST(GET_OFFERS_PER_CATEGORY
            + "/{" + PAGE + "}"
            + "/{" + PER_PAGE + "}")
    Call<List<Offer>> getOffers(@Body CatLang fromCategory,
                                @Path(PAGE) Integer page,
                                @Path(PER_PAGE) Integer perPage);

    @POST(GET_OFFERS_FOR_PRODUCT)
    Observable<List<ProductOffers>> getOffersWithSameProduct(@Body Product product);

    @POST(ADD_PRODUCT_TO_FAVORITES)
    Observable<Boolean> addProductsToFavorites(@Body Favorites product);

    @POST(CHECK_IF_PRODUCT_IS_FAVORITE)
    Observable<Boolean> checkIfProductIsFavorite(@Body Favorites product);

    @POST(GET_MY_FAVORITE_PRODUCTS)
    Call<List<Offer>> getMyFavoriteProducts(@Body String appId);

    @GET(GET_MOST_FAVORITE_PRODUCTS)
    Call<List<Offer>> getMostFavoriteProducts();

    @GET(GET_FILTERED_PRODUCTS + "/{" + SEARCH_PHRASE + "}"
            + "/{" + LANG + "}"
            + "/{" + PAGE + "}"
            + "/{" + PER_PAGE + "}")
    Call<List<Offer>> getFilteredProducts(
            @Path(SEARCH_PHRASE) String search_phrase,
            @Path(LANG) String lang,
            @Path(PAGE) Integer page,
            @Path(PER_PAGE) Integer perPage);
}
