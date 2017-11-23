package com.example.elvedin.sporedimk;

import com.example.elvedin.sporedimk.model.Category;
import com.example.elvedin.sporedimk.ui.manager.network_manager.ClientInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elvedin on 10/26/17.
 */

public class AppSingleton {
    private static AppSingleton instance = null;
    private List<Category> categories = null;
    private ClientInterface clientInterface = null;
    private List<Category> defaultSuggestedCategories;

    public static AppSingleton getInstance() {
        if(instance == null) {
            instance = new AppSingleton();
        }
        return instance;
    }

    public ClientInterface getClientInterface() {
        return clientInterface;
    }

    public void setClientInterface(ClientInterface clientInterface) {
        this.clientInterface = clientInterface;
    }

    public static void setInstance(AppSingleton instance) {
        AppSingleton.instance = instance;
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
            category.setIcon("http://setec.mk/image/cache/data/Sliki/10019-180x135.png");
            defaultSuggestedCategories.add(category);
            c2 = new Category("Мобилни телефони");
            c2.setIcon("http://setec.mk/image/cache/data/Sliki/10017-180x135.png");
            defaultSuggestedCategories.add(c2);
            c3 = new Category("Блендери");
            c3.setIcon("http://setec.mk/image/cache/data/Sliki/10049-180x135.png");
            defaultSuggestedCategories.add(c3);
            c4 = new Category("Мобилни телефони");
            c4.setIcon("http://setec.mk/image/cache/data/Sliki/10014-180x135.png");
            defaultSuggestedCategories.add(c4);
        }
        return defaultSuggestedCategories;
    }

    public void setDefaultSuggestedCategories(List<Category> defaultSuggestedCategories) {
        this.defaultSuggestedCategories = defaultSuggestedCategories;
    }
}
