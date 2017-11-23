package com.example.elvedin.sporedimk.ui.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.example.elvedin.sporedimk.R;

/**
 * Created by elvedin on 11/1/17.
 */

public class MoreFragment extends BaseFragment {
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_more;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.title_more);
    }

    @Override
    protected void initViews(View contentView) {

    }

    public static MoreFragment newInstance() {
        return new MoreFragment();
    }
}
