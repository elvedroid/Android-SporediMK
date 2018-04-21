package org.emobile.myitmarket.model;

/**
 * Created by elvedin on 10/24/17.
 */

public class Category extends BaseModel{
    private String uri;
    private String name;
    private String subCategoryOf;
    private String image;
    private String sameAs;

    public Category() {
    }

    public Category(String uri, String name, String subCategoryOf, String icon) {
        this.uri = uri;
        this.name = name;
        this.subCategoryOf = subCategoryOf;
        this.image = icon;
    }

    public Category(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubCategoryOf() {
        return subCategoryOf;
    }

    public void setSubCategoryOf(String subCategoryOf) {
        this.subCategoryOf = subCategoryOf;
    }

    public String getIcon() {
        return image;
    }

    public void setIcon(String icon) {
        this.image = icon;
    }

    public String getSameAs() {
        return sameAs;
    }

    public void setSameAs(String sameAs) {
        this.sameAs = sameAs;
    }
}
