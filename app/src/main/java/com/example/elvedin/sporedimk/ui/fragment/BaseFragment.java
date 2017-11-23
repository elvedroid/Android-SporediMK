package com.example.elvedin.sporedimk.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.elvedin.sporedimk.R;
import com.example.elvedin.sporedimk.ui.activity.BaseActivity;

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
            ((BaseActivity)getActivity()).setTitle(getTitle());
            ((BaseActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(showBackButton());
        } catch (Throwable e) {
            getActivity().finish();
//            if(Filter.isDebuggingMode) {
//                Log.e(TAG, "error initializing fragment", e);
//            }
        }

        return contentView;
    }

    protected abstract int getLayoutResId();
    protected abstract String getTitle();
    protected boolean showBackButton(){ return false; };
    protected abstract void initViews(View contentView);
}
