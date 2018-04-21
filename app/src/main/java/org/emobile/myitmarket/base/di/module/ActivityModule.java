package org.emobile.myitmarket.base.di.module;

import android.app.Activity;

import org.emobile.myitmarket.base.di.scope.ActivityScoped;

import dagger.Module;
import dagger.Provides;

/**
 * Created by elvedin on 12/5/17.
 */

@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @ActivityScoped Activity activity() {
        return this.activity;
    }
}