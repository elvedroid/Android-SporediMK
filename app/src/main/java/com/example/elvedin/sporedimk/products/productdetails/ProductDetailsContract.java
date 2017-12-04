package com.example.elvedin.sporedimk.products.productdetails;

import com.example.elvedin.sporedimk.BasePresenter;
import com.example.elvedin.sporedimk.model.Favorites;
import com.example.elvedin.sporedimk.model.Product;
import com.example.elvedin.sporedimk.model.ProductOffers;

import java.util.List;

/*
 * Created by elvedin on 11/29/17.
 */

public interface ProductDetailsContract {

    interface View {
        void setProgressIndicator(boolean active);

        void toggleFavorite(Boolean isInFavorites);

        void loadOffers(List<ProductOffers> productOffer);

        void showError(String message);
    }

    interface Presenter extends BasePresenter {

        void loadOffersForSameProduct(Product product);

        void checkIfProductIsInFavorites(Favorites favorites);

        void setIsInFavorites(Favorites favorites);
    }
}
