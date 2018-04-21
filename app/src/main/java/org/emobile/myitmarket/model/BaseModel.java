package org.emobile.myitmarket.model;

/**
 * Created by elvedin on 9/23/17.
 */

public class BaseModel {
    private Long id;
    protected long creationDate;
    protected long lastModificationDate;

    public BaseModel() {
    }

    public BaseModel(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public long getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(long lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj != null && obj instanceof BaseModel){
            return this.id == ((BaseModel) obj).id;
        }

        return false;
    }
}
