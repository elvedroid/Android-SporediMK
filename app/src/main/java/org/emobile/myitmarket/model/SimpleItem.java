package org.emobile.myitmarket.model;

public class SimpleItem {
    private String title;
    private int tag;
    private boolean checked = false;

    public SimpleItem() {
    }

    public SimpleItem(String title, int tag) {
        this.title = title;
        this.tag = tag;
    }

    public SimpleItem(String title, int tag, boolean checked) {
        this.title = title;
        this.tag = tag;
        this.checked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
