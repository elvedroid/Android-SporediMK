package org.emobile.myitmarket.model;

/**
 * Created by elvedin on 10/24/17.
 */

public class Price extends BaseModel{
    private Double value;
    private String unit;

    public Price() {
    }

    public Price(Double value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
