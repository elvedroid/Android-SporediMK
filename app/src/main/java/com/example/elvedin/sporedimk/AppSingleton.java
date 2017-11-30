package com.example.elvedin.sporedimk;

import com.example.elvedin.sporedimk.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elvedin on 10/26/17.
 * Singleton class for keeping some data while the app is running
 */

public class AppSingleton {
    private static AppSingleton instance = null;
    private List<Category> categories = null;
    private List<Category> defaultSuggestedCategories;

    public static AppSingleton getInstance() {
        if(instance == null) {
            instance = new AppSingleton();
        }
        return instance;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getDefaultSuggestedCategories() {
        if(defaultSuggestedCategories == null) {
            defaultSuggestedCategories = new ArrayList<>();
            Category category, c2, c3, c4;
            category = new Category("Преносни компјутери");
            category.setIcon("http://tehnomarket.com.mk/img/products/full/1491385400n_1.jpg");
            defaultSuggestedCategories.add(category);
            c2 = new Category("Мобилни телефони");
            c2.setIcon("http://setec.mk/image/cache/data/Sliki/10067-180x135.png");
            defaultSuggestedCategories.add(c2);
            c3 = new Category("Фенови");
            c3.setIcon("http://setec.mk/image/cache/data/Sliki/10146-180x135.png");
            defaultSuggestedCategories.add(c3);
            c4 = new Category("Ultrabooks");
            c4.setIcon("https://www3.lenovo.com/medias/lenovo-laptop-yoga-520-14-back.png?context=bWFzdGVyfHJvb3R8MTgwMTN8aW1hZ2UvcG5nfGg5YS9oODkvOTQxNDM5NDgzOTA3MC5wbmd8MzUyMDIwZDdiOGZlODg3NzkyYjNlNWJhZDYyOWYxNTI2ZmY1NjQ3YjJkY2NlYjE2NzYyNjNjMDAxOTVmNzA3OA");
            defaultSuggestedCategories.add(c4);
        }
        return defaultSuggestedCategories;
    }

    public void setDefaultSuggestedCategories(List<Category> defaultSuggestedCategories) {
        this.defaultSuggestedCategories = defaultSuggestedCategories;
    }
}
