package org.emobile.myitmarket.base.di.component;

import org.emobile.myitmarket.base.di.module.ActivityModule;
import org.emobile.myitmarket.base.di.module.ProductModule;
import org.emobile.myitmarket.base.di.scope.ActivityScoped;
import org.emobile.myitmarket.base.products.productdetails.ProductDetailsFragment;
import org.emobile.myitmarket.ui.fragment.ProductListFragment;

import dagger.Component;

/**
 * Created by elvedin on 12/5/17.
 */

@ActivityScoped
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, ProductModule.class})
public interface ProductComponent extends ActivityComponent {
    void inject(ProductListFragment productListFragment);
    void inject(ProductDetailsFragment productDetailsFragment);
}
