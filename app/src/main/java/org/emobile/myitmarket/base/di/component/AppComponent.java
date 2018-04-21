package org.emobile.myitmarket.base.di.component;

import org.emobile.myitmarket.base.di.module.AppModule;
import org.emobile.myitmarket.base.BaseActivity;
import org.emobile.myitmarket.manager.network_manager.RemoteRepository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by elvedin on 12/4/17.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(BaseActivity activity);

    RemoteRepository repository();
}
