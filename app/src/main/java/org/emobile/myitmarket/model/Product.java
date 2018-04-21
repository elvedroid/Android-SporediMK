package org.emobile.myitmarket.model;

/**
 * Created by elvedin on 10/24/17.
 */

public class Product extends BaseModel{
    private String name;
    private String brand;
    private String description;
    private String image;

    public Product() {
    }

    public Product(String name, String brand, String description) {
        this.name = name;
        this.brand = brand;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return getName();
    }
}
