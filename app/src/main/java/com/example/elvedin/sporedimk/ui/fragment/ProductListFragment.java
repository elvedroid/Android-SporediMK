package com.example.elvedin.sporedimk.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.elvedin.sporedimk.R;
import com.example.elvedin.sporedimk.adapter.CategoryAdapter;
import com.example.elvedin.sporedimk.adapter.OfferAdapter;
import com.example.elvedin.sporedimk.managers.persistence.Persistence;
import com.example.elvedin.sporedimk.model.CatLang;
import com.example.elvedin.sporedimk.model.Category;
import com.example.elvedin.sporedimk.model.Offer;
import com.example.elvedin.sporedimk.model.Product;
import com.example.elvedin.sporedimk.ui.manager.AppHolder;
import com.example.elvedin.sporedimk.utils.UiHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.elvedin.sporedimk.ui.fragment.CategoryFragment.FROM_CATEGORY;

/**
 * Created by elvedin on 10/27/17.
 */

public class ProductListFragment extends BaseFragment implements OfferAdapter.OfferItemInterface {

    private static final String IS_LINEAR_LAYOUT_PRODUCTS = "IS_LINEAR_LAYOUT_PRODUCTS";
    RecyclerView recyclerView;
    List<Offer> offers;
    OfferAdapter adapter;
    RecyclerView.LayoutManager mGridLayoutManager, mLinearLayoutManager;
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
        return "Products";
    }

    @Override
    protected void initViews(View contentView) {
        if (getArguments() != null) {
            fromCategory = getArguments().getString(FROM_CATEGORY);
        }
        offers = new ArrayList<>();
        getProducts(contentView, fromCategory);
    }

    private void getProducts(final View contentView, String fromCategory) {
        Call<List<Offer>> call = AppHolder.getInstance().getClientInterface().getOffers(new CatLang(fromCategory, "en"));
        call.enqueue(new Callback<List<Offer>>() {
            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    offers.addAll(response.body());
                }
                bindView(contentView);
            }

            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
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
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onOfferItemClicked(View view, int adapterPosition) {
        Offer offer = adapter.getOffers().get(adapterPosition);
        ProductFragment fragment = ProductFragment.newInstance(offer);
        UiHelper.replaceFragment(getActivity().getSupportFragmentManager(),
                R.id.containerLayoutMain,
                fragment,
                true, 0, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_cards_view:
                adapter.setItemType(CategoryAdapter.ITEM_TYPE_GRID);
                recyclerView.setLayoutManager(mGridLayoutManager);
                adapter.notifyDataSetChanged();
                isLinearLayout = false;
                Persistence.setBoolean(IS_LINEAR_LAYOUT_PRODUCTS, isLinearLayout);
                break;
            case R.id.menu_list_view:
                adapter.setItemType(CategoryAdapter.ITEM_TYPE_LIST);
                recyclerView.setLayoutManager(mLinearLayoutManager);
                adapter.notifyDataSetChanged();
                isLinearLayout = true;
                Persistence.setBoolean(IS_LINEAR_LAYOUT_PRODUCTS, isLinearLayout);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
