package org.emobile.myitmarket.ui.view;

/**
 * Created by elvedin on 11/13/17.
 */

public class HorizontalAdapterItem {
    private String icon;
    private String title;

    public HorizontalAdapterItem() {
    }

    public HorizontalAdapterItem(String icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
