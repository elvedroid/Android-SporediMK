package com.example.elvedin.sporedimk.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.elvedin.sporedimk.Filter;
import com.example.elvedin.sporedimk.R;
import com.example.elvedin.sporedimk.adapter.SimpleAdapter;
import com.example.elvedin.sporedimk.model.MoreOptionsTag;
import com.example.elvedin.sporedimk.model.SimpleItem;
import com.example.elvedin.sporedimk.utils.UiHelper;

import java.util.ArrayList;
import java.util.List;

public class MoreFragment extends BaseFragment implements SimpleAdapter.SimpleAdapterInterface {

    RecyclerView recyclerView;
    SimpleAdapter adapter;
    List<SimpleItem> options;

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
        ((TextView)contentView.findViewById(R.id.tv_version)).setText(String.format(getString(R.string.version), Filter.version));
        initData();
        recyclerView = contentView.findViewById(R.id.rv_more_options);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SimpleAdapter(getContext(), options);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        options = new ArrayList<>();
        options.add(new SimpleItem(getString(R.string.language), MoreOptionsTag.LANGUAGE_TAG));
        options.add(new SimpleItem(getString(R.string.rate_the_app), MoreOptionsTag.RATE_THE_APP));
    }

    public static MoreFragment newInstance() {
        return new MoreFragment();
    }

    @Override
    public void onItemClicked(int tag) {
        switch (tag){
            case MoreOptionsTag.LANGUAGE_TAG:
                UiHelper.replaceFragment(getActivity().getSupportFragmentManager(), R.id.containerLayoutMain, new LanguageFragment(), true, 0, 0);
                break;
            case MoreOptionsTag.RATE_THE_APP:
                break;
        }
    }

    @Override
    protected boolean showBackButton() {
        return true;
    }

}
