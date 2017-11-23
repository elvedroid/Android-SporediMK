package com.example.elvedin.sporedimk.model;

/**
 * Created by elvedin on 10/24/17.
 */

import java.util.ArrayList;
import java.util.List;

public class Seller extends BaseModel {
    private String name;
    private List<Offer> offers;

    public Seller() {
    }

    public Seller(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Offer> getOffers() {
        if (offers == null) {
            offers = new ArrayList<Offer>();
        }
        return offers;
    }
    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}
