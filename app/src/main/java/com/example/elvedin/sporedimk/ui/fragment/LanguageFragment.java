package com.example.elvedin.sporedimk.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.elvedin.sporedimk.Filter;
import com.example.elvedin.sporedimk.R;
import com.example.elvedin.sporedimk.adapter.CheckableAdapter;
import com.example.elvedin.sporedimk.adapter.SimpleAdapter;
import com.example.elvedin.sporedimk.model.SimpleItem;
import com.example.elvedin.sporedimk.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class LanguageFragment extends BaseFragment implements SimpleAdapter.SimpleAdapterInterface {
    private static final int EN = 0;
    private static final int MK = 1;

    RecyclerView recyclerView;
    CheckableAdapter adapter;
    List<SimpleItem> languages;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_language;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.language);
    }

    @Override
    protected void initViews(View contentView) {
        initData();
        recyclerView = contentView.findViewById(R.id.rv_langs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CheckableAdapter(getContext(), languages);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        languages = new ArrayList<>();
        languages.add(new SimpleItem(getString(R.string.lang_en), EN, Filter.language.equals(Constants.LANGUAGE_EN)));
        languages.add(new SimpleItem(getString(R.string.lang_mk), MK, Filter.language.equals(Constants.LANGUAGE_MK)));
    }

    @Override
    public void onItemClicked(int tag) {
        switch (tag){
            case EN:
                Filter.language = Constants.LANGUAGE_EN;
                getActivity().recreate();
                break;
            case MK:
                Filter.language = Constants.LANGUAGE_MK;
                getActivity().recreate();
                break;
        }
    }

    @Override
    protected boolean showBackButton() {
        return true;
    }
}
