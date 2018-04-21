package org.emobile.myitmarket.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.emobile.myitmarket.utils.Filter;
import org.emobile.myitmarket.base.di.HasComponent;
import org.emobile.myitmarket.base.BaseActivity;
import org.emobile.myitmarket.manager.AppHolder;
import org.emobile.myitmarket.manager.log.LogLevel;

/**
 * Created by elvedin on 9/23/17.
 */

public abstract class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";

    public BaseFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(getLayoutResId(), container, false);

        try {
            initViews(contentView);
            ((BaseActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(showBackButton());
            ((BaseActivity)getActivity()).setShowSearchMenuItem(showSearchView());
            ((BaseActivity)getActivity()).setShowMenuItemsChooseView(showMenuItems());
            ((BaseActivity)getActivity()).setTitle(getTitle());
        } catch (Throwable e) {
            getActivity().finish();
            if(Filter.isDebuggingMode) {
                AppHolder.log(LogLevel.ERROR, TAG, e.getMessage());
            }
        }

        return contentView;
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    protected abstract int getLayoutResId();
    protected abstract String getTitle();
    protected boolean showBackButton(){ return false; };
    protected boolean showSearchView(){return false; }
    protected boolean showMenuItems(){return false; }
    protected abstract void initViews(View contentView);
}
