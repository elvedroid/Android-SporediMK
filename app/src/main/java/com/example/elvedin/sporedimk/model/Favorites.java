package com.example.elvedin.sporedimk.model;

/**
 * Created by elvedin on 11/10/17.
 */

public class Favorites extends BaseModel {
    private String appID;
    private String productName;
    private boolean favorite;

    public Favorites() {
    }

    public Favorites(String appID, String productName) {
        this.appID = appID;
        this.productName = productName;
    }

    public Favorites(String appID, String productName, boolean favorite) {
        this.appID = appID;
        this.productName = productName;
        this.favorite = favorite;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

}
