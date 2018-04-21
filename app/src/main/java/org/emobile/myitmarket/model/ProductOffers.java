package org.emobile.myitmarket.model;

/**
 * Created by elvedin on 11/1/17.
 */

public class ProductOffers extends BaseModel {
    private String sellerLogo;
    private String url;
    private Price price;

    public ProductOffers() {
    }

    public ProductOffers(String sellerLogo, String url, Price price) {
        this.sellerLogo = sellerLogo;
        this.url = url;
        this.price = price;
    }

    public String getSellerLogo() {
        return sellerLogo;
    }

    public void setSellerLogo(String sellerLogo) {
        this.sellerLogo = sellerLogo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
}

