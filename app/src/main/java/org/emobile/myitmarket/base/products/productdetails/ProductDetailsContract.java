package org.emobile.myitmarket.base.products.productdetails;

import org.emobile.myitmarket.base.IBasePresenter;
import org.emobile.myitmarket.model.Favorites;
import org.emobile.myitmarket.model.Product;
import org.emobile.myitmarket.model.ProductOffers;

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

    interface Presenter extends IBasePresenter {

        void loadOffersForSameProduct(Product product);

        void checkIfProductIsInFavorites(Favorites favorites);

        void setIsInFavorites(Favorites favorites);
    }
}
