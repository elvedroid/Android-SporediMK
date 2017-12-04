package com.example.elvedin.sporedimk.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.example.elvedin.sporedimk.R;
import com.example.elvedin.sporedimk.adapter.CategoryAdapter;
import com.example.elvedin.sporedimk.adapter.OfferAdapter;
import com.example.elvedin.sporedimk.managers.persistence.Persistence;
import com.example.elvedin.sporedimk.model.CatLang;
import com.example.elvedin.sporedimk.model.Offer;
import com.example.elvedin.sporedimk.products.productdetails.ProductDetailsFragment;
import com.example.elvedin.sporedimk.ui.manager.AppHolder;
import com.example.elvedin.sporedimk.ui.manager.log.LogLevel;
import com.example.elvedin.sporedimk.utils.EndlessRecyclerViewScrollListener;
import com.example.elvedin.sporedimk.utils.UiHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.elvedin.sporedimk.ui.fragment.CategoryFragment.FROM_CATEGORY;

/**
 * Created by elvedin on 10/27/17.
 * Show list of products
 */

public class ProductListFragment extends BaseFragment implements OfferAdapter.OfferItemInterface {

    public static final String IS_LINEAR_LAYOUT_PRODUCTS = "IS_LINEAR_LAYOUT_PRODUCTS";
    public static final String TAG = "ProductListFragment";
    RecyclerView recyclerView;
    List<Offer> offers;
    OfferAdapter adapter;
    GridLayoutManager mGridLayoutManager;
    LinearLayoutManager mLinearLayoutManager;
    Integer page = 0, perPage= 32;
    private EndlessRecyclerViewScrollListener scrollListener;
    String fromCategory = "";
    private boolean isLinearLayout = false;

    public static ProductListFragment newInstance(String fromCategory) {
        Bundle args = new Bundle();
        args.putString(FROM_CATEGORY, fromCategory);
        ProductListFragment fragment = new ProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_category;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.title_products);
    }

    @Override
    protected void initViews(View contentView) {
        if (getArguments() != null) {
            fromCategory = getArguments().getString(FROM_CATEGORY);
        }
        offers = new ArrayList<>();
        bindView(contentView);
        getProducts(fromCategory);
    }

    private void loadNextPage(int page) {
        this.page = page;
        getProducts(fromCategory);
    }

    private void getProducts(String fromCategory) {
        Call<List<Offer>> call = AppHolder.getInstance().getClientInterface().getOffers(new CatLang(fromCategory, "en"), page, perPage);
        call.enqueue(new Callback<List<Offer>>() {
            @Override
            public void onResponse(@NonNull Call<List<Offer>> call, @NonNull Response<List<Offer>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    offers.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    AppHolder.logWithToast(getActivity(), LogLevel.ERROR, TAG, getString(R.string.connection_error));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Offer>> call, @NonNull Throwable t) {
                AppHolder.logWithToast(getActivity(), LogLevel.ERROR, TAG, t.getMessage());
            }
        });
    }

    private void bindView(View contentView) {
        recyclerView = contentView.findViewById(R.id.rv_categories);

        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(isLinearLayout ? mLinearLayoutManager : mGridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new OfferAdapter(getActivity(), offers, isLinearLayout ? CategoryAdapter.ITEM_TYPE_LIST : CategoryAdapter.ITEM_TYPE_GRID);
        adapter.setmClickListener(this);
        scrollListener = new EndlessRecyclerViewScrollListener(isLinearLayout ? mLinearLayoutManager : mGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                loadNextPage(page);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onOfferItemClicked(View view, int adapterPosition) {
        Offer offer = adapter.getOffers().get(adapterPosition);
        ProductDetailsFragment fragment = ProductDetailsFragment.newInstance(offer);
        UiHelper.addFragment(getActivity().getSupportFragmentManager(),
                R.id.containerLayoutMain,
                fragment,
                ProductDetailsFragment.TAG,
                true, 0, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cards_view:
                adapter.setItemType(CategoryAdapter.ITEM_TYPE_GRID);
                recyclerView.setLayoutManager(mGridLayoutManager);
                scrollListener.setmLayoutManager(mGridLayoutManager);
                adapter.notifyDataSetChanged();
                isLinearLayout = false;
                Persistence.setBoolean(IS_LINEAR_LAYOUT_PRODUCTS, isLinearLayout);
                break;
            case R.id.menu_list_view:
                adapter.setItemType(CategoryAdapter.ITEM_TYPE_LIST);
                recyclerView.setLayoutManager(mLinearLayoutManager);
                scrollListener.setmLayoutManager(mLinearLayoutManager);
                adapter.notifyDataSetChanged();
                isLinearLayout = true;
                Persistence.setBoolean(IS_LINEAR_LAYOUT_PRODUCTS, isLinearLayout);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean showBackButton() {
        return true;
    }

    @Override
    protected boolean showSearchView() {
        return true;
    }

    @Override
    protected boolean showMenuItems() {
        return true;
    }
}
