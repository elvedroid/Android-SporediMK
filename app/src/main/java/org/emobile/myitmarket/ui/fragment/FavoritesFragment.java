package org.emobile.myitmarket.ui.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.emobile.myitmarket.R;
import org.emobile.myitmarket.adapter.CategoryAdapter;
import org.emobile.myitmarket.adapter.OfferAdapter;
import org.emobile.myitmarket.base.BaseFragment;
import org.emobile.myitmarket.model.Offer;
import org.emobile.myitmarket.base.products.productdetails.ProductDetailsFragment;
import org.emobile.myitmarket.manager.AppHolder;
import org.emobile.myitmarket.utils.UiHelper;
import com.google.android.gms.iid.InstanceID;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Elvedin Selimoski on 11/1/17. mail: elveselimoski@gmail.com
 */

public class FavoritesFragment extends BaseFragment implements OfferAdapter.OfferItemInterface {

    RecyclerView recyclerView;
    List<Offer> offers;
    OfferAdapter adapter;
    RecyclerView.LayoutManager mGridLayoutManager, mLinearLayoutManager;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_category;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.title_favorites);
    }

    @Override
    protected void initViews(View contentView) {
        bindView(contentView);
        getProducts();
    }

    private void bindView(View contentView) {
        recyclerView = contentView.findViewById(R.id.rv_categories);

        mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        offers = new ArrayList<>();
        adapter = new OfferAdapter(getActivity(), offers, CategoryAdapter.ITEM_TYPE_GRID);
        adapter.setmClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void getProducts() {
        Call<List<Offer>> call = AppHolder.getInstance().getClientInterface().getMyFavoriteProducts(InstanceID.getInstance(getContext()).getId());
        call.enqueue(new Callback<List<Offer>>() {
            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                if(response.isSuccessful()){
                    offers.clear();
                    offers.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {

            }
        });
    }

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
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
