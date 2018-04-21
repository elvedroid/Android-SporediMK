package org.emobile.myitmarket.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import org.emobile.myitmarket.base.BaseFragment;
import org.emobile.myitmarket.utils.Filter;
import org.emobile.myitmarket.R;
import org.emobile.myitmarket.adapter.CategoryAdapter;
import org.emobile.myitmarket.adapter.OfferAdapter;
import org.emobile.myitmarket.manager.persistence.Persistence;
import org.emobile.myitmarket.model.Offer;
import org.emobile.myitmarket.base.products.productdetails.ProductDetailsFragment;
import org.emobile.myitmarket.manager.AppHolder;
import org.emobile.myitmarket.manager.log.LogLevel;
import org.emobile.myitmarket.utils.EndlessRecyclerViewScrollListener;
import org.emobile.myitmarket.utils.UiHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by elvedin on 11/26/17.
 * Fragment that shows result offers from the search
 */

public class SearchResultFragment extends BaseFragment implements OfferAdapter.OfferItemInterface {
    private static final String QUERY = "QUERY";
    private static final String TAG = "SearchResultFragment";
    RecyclerView recyclerView;
    List<Offer> offers;
    OfferAdapter adapter;
    GridLayoutManager mGridLayoutManager;
    LinearLayoutManager mLinearLayoutManager;
    private EndlessRecyclerViewScrollListener scrollListener;
    private boolean isLinearLayout = false;
    Integer page = 0, perPage = 32;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_search_result;
    }

    @Override
    protected String getTitle() {
        return getArguments().getString(QUERY);
    }

    @Override
    protected void initViews(View contentView) {
        initRecyclerView(contentView);
        getOffers();
    }

    private void initRecyclerView(View contentView) {
        offers = new ArrayList<>();
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
                loadMore(page);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
        recyclerView.setAdapter(adapter);
    }

    private void loadMore(int page) {
        this.page = page;
        getOffers();
    }

    private void getOffers() {
        String query = getArguments().getString(QUERY);
        Call<List<Offer>> call = AppHolder.getInstance().getClientInterface().getFilteredProducts(query != null ? query : "", Filter.language, page, perPage);
        call.enqueue(new Callback<List<Offer>>() {
            @Override
            public void onResponse(@NonNull Call<List<Offer>> call, @NonNull Response<List<Offer>> response) {
                if (response.body() != null) {
                    AppHolder.log(LogLevel.INFO, TAG, "OnResponseNOTNullBody");
                    offers.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    AppHolder.logWithToast(getActivity(), LogLevel.ERROR, TAG, "OnResponseNullBody");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Offer>> call, @NonNull Throwable t) {
                AppHolder.logWithToast(getActivity(), LogLevel.ERROR, TAG, t.getMessage());

            }
        });
    }

    public static SearchResultFragment newInstance(String query) {
        Bundle args = new Bundle();
        args.putString(QUERY, query);
        SearchResultFragment fragment = new SearchResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onOfferItemClicked(View view, int adapterPosition) {
        Offer offer = adapter.getOffers().get(adapterPosition);
        ProductDetailsFragment fragment = ProductDetailsFragment.newInstance(offer);
        UiHelper.replaceFragment(getActivity().getSupportFragmentManager(),
                R.id.containerLayoutMain,
                fragment,
                true, 0, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cards_view:
                adapter.setItemType(CategoryAdapter.ITEM_TYPE_GRID);
                recyclerView.setLayoutManager(mGridLayoutManager);
                adapter.notifyDataSetChanged();
                isLinearLayout = false;
                Persistence.setBoolean(ProductListFragment.IS_LINEAR_LAYOUT_PRODUCTS, isLinearLayout);
                break;
            case R.id.menu_list_view:
                adapter.setItemType(CategoryAdapter.ITEM_TYPE_LIST);
                recyclerView.setLayoutManager(mLinearLayoutManager);
                adapter.notifyDataSetChanged();
                isLinearLayout = true;
                Persistence.setBoolean(ProductListFragment.IS_LINEAR_LAYOUT_PRODUCTS, isLinearLayout);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem search = menu.findItem(R.id.menu_search);
        if (search != null && search.getActionView() != null) {
            search.expandActionView();
            ((SearchView) search.getActionView()).setQuery(getArguments().getString(QUERY), false);
            search.getActionView().clearFocus();
            search.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem menuItem) {
                    return false;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                    getActivity().onBackPressed();
                    return true;
                }
            });
        }
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
