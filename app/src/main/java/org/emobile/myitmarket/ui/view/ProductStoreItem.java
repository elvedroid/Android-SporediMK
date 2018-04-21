package org.emobile.myitmarket.ui.view;

/**
 * Created by elvedin on 10/30/17.
 */

public class ProductStoreItem {
    public String icon;
    public String price;
    public String url;

    public ProductStoreItem() {
    }

    public ProductStoreItem(String icon, String price, String url) {
        this.icon = icon;
        this.price = price;
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}