package com.example.elvedin.sporedimk.ui.fragment;

import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.elvedin.sporedimk.AppSingleton;
import com.example.elvedin.sporedimk.R;
import com.example.elvedin.sporedimk.adapter.CategoryAdapter;
import com.example.elvedin.sporedimk.adapter.HorizontalRecyclerAdapter;
import com.example.elvedin.sporedimk.adapter.OfferAdapter;
import com.example.elvedin.sporedimk.model.CatLang;
import com.example.elvedin.sporedimk.model.Category;
import com.example.elvedin.sporedimk.model.Offer;
import com.example.elvedin.sporedimk.ui.manager.AppHolder;
import com.example.elvedin.sporedimk.ui.view.HorizontalAdapterItem;
import com.example.elvedin.sporedimk.utils.UiHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by elvedin on 11/1/17.
 */

public class HomeFragment extends BaseFragment implements HorizontalRecyclerAdapter.OnItemClickInterface, OfferAdapter.OfferItemInterface {

    RecyclerView recyclerView, mfoRecyclerView;
    RecyclerView.LayoutManager layoutManager, mGridLayoutManager;
    HorizontalRecyclerAdapter adapter;
    List<HorizontalAdapterItem> items;
    OfferAdapter mfoAdapter;
    List<Offer> mfoList;
    private AdView mAdView;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.title_home);
    }

    @Override
    protected void initViews(View contentView) {
        mAdView = contentView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("D4528B3D9CDE7BDEC56DFB086AAD06C4").build();
//        mAdView.loadAd(adRequest);

        items = new ArrayList<>();
        recyclerView = contentView.findViewById(R.id.rv_suggested_categories);
        recyclerView.setNestedScrollingEnabled(false);
        layoutManager = new GridLayoutManager(getContext(), 4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HorizontalRecyclerAdapter(getContext(), items);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        updateItemsFromCategories(AppSingleton.getInstance().getDefaultSuggestedCategories());
        adapter.notifyDataSetChanged();

        mfoList = new ArrayList<>();
        mfoRecyclerView = contentView.findViewById(R.id.rv_most_favorite_offers);
        mfoRecyclerView.setNestedScrollingEnabled(false);
        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mfoRecyclerView.setLayoutManager(mGridLayoutManager);
        mfoAdapter = new OfferAdapter(getActivity(), mfoList, R.layout.category_grid_item);
        mfoRecyclerView.setAdapter(mfoAdapter);
        mfoAdapter.setmClickListener(this);
        getOffers();
    }

    private void updateItemsFromCategories(List<Category> categories) {
        for (Category category : categories) {
            items.add(new HorizontalAdapterItem(category.getIcon(), category.getName()));
        }
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onOItemClicked(View view, int adapterPosition) {
        HorizontalAdapterItem item = items.get(adapterPosition);
        UiHelper.replaceFragment(getActivity().getSupportFragmentManager(),
                R.id.containerLayoutMain,
                ProductListFragment.newInstance(item.getTitle()),
                true, 0, 0);
    }

    private void getOffers() {
        Call<List<Offer>> call = AppHolder.getInstance().getClientInterface().getMostFavoriteProducts();
        call.enqueue(new Callback<List<Offer>>() {
            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mfoList.clear();
                    mfoList.addAll(response.body());
                    mfoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onOfferItemClicked(View view, int adapterPosition) {
        Offer offer = mfoList.get(adapterPosition);
        ProductFragment fragment = ProductFragment.newInstance(offer);
        UiHelper.replaceFragment(getActivity().getSupportFragmentManager(),
                R.id.containerLayoutMain,
                fragment,
                true, 0, 0);
    }

}
