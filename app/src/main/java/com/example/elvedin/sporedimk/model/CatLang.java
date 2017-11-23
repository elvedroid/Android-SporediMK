package com.example.elvedin.sporedimk.model;

/**
 * Created by elvedin on 11/4/17.
 */

public class CatLang extends BaseModel {
    private String categoryName;
    private String lang;

    public CatLang() {
    }

    public CatLang(String categoryName, String lang) {
        this.categoryName = categoryName;
        this.lang = lang;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
