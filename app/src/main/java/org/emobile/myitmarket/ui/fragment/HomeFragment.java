package org.emobile.myitmarket.ui.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.emobile.myitmarket.base.BaseFragment;
import org.emobile.myitmarket.manager.AppSingleton;
import org.emobile.myitmarket.R;
import org.emobile.myitmarket.adapter.HorizontalRecyclerAdapter;
import org.emobile.myitmarket.adapter.OfferAdapter;
import org.emobile.myitmarket.model.Category;
import org.emobile.myitmarket.model.Offer;
import org.emobile.myitmarket.base.products.productdetails.ProductDetailsFragment;
import org.emobile.myitmarket.manager.AppHolder;
import org.emobile.myitmarket.ui.view.HorizontalAdapterItem;
import org.emobile.myitmarket.utils.UiHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends BaseFragment implements HorizontalRecyclerAdapter.OnItemClickInterface, OfferAdapter.OfferItemInterface {

    private static final String TAG = "HomeFragment";
    RecyclerView recyclerView, mfoRecyclerView;
    RecyclerView.LayoutManager layoutManager, mGridLayoutManager;
    HorizontalRecyclerAdapter adapter;
    List<HorizontalAdapterItem> items;
    OfferAdapter mfoAdapter;
    List<Offer> mfoList;

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
        AdView mAdView = contentView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("681B64652CFE0B26BFF3F3A331E82339")
                .addTestDevice("D4528B3D9CDE7BDEC56DFB086AAD06C4")
                .build();
        mAdView.loadAd(adRequest);

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
    public void onItemClicked(View view, int adapterPosition) {
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
            public void onResponse(@NonNull Call<List<Offer>> call, @NonNull Response<List<Offer>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mfoList.clear();
                    mfoList.addAll(response.body());
                    mfoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Offer>> call, @NonNull Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "getMostFavoriteProducts", t);
            }
        });
    }

    @Override
    public void onOfferItemClicked(View view, int adapterPosition) {
        Offer offer = mfoList.get(adapterPosition);
        ProductDetailsFragment fragment = ProductDetailsFragment.newInstance(offer);
        UiHelper.replaceFragment(getActivity().getSupportFragmentManager(),
                R.id.containerLayoutMain,
                fragment,
                true, 0, 0);
    }

    @Override
    protected boolean showSearchView() {
        return true;
    }

}
