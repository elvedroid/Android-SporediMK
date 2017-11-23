package com.example.elvedin.sporedimk.ui.fragment;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.elvedin.sporedimk.R;
import com.example.elvedin.sporedimk.adapter.CategoryAdapter;
import com.example.elvedin.sporedimk.adapter.ProductStoreAdapter;
import com.example.elvedin.sporedimk.model.Favorites;
import com.example.elvedin.sporedimk.model.Offer;
import com.example.elvedin.sporedimk.model.ProductOffers;
import com.example.elvedin.sporedimk.ui.activity.BaseActivity;
import com.example.elvedin.sporedimk.ui.activity.MainActivity;
import com.example.elvedin.sporedimk.ui.manager.AppHolder;
import com.google.android.gms.iid.InstanceID;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by elvedin on 10/27/17.
 */

public class ProductFragment extends BaseFragment implements ProductStoreAdapter.ProductStoreItemInterface, View.OnClickListener {
    public final static String TAG = "ProductFragment";
    public final static String EXTRA_OFFER = "EXTRA_OFFER";

    Offer offer;
    RecyclerView rvStores, rvSimilarProducts;
    TextView tvDesc;
    ImageView mainImageView;
    ProductStoreAdapter productStoreAdapter;
    FloatingActionButton floatingActionButton;
    List<ProductOffers> productOffers;
    Boolean isFavorite = false;

    public static ProductFragment newInstance(Offer offer) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_OFFER, offer);
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_product;
    }

    @Override
    protected String getTitle() {
        return offer.getProduct().getName();
    }

    @Override
    protected boolean showBackButton() {
        return true;
    }

    @Override
    protected void initViews(View contentView) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).setHideSearchMenuItem(true);
        }
        bindView(contentView);
        getOffers();
    }

    private void bindView(View contentView) {
        offer = getArguments().getParcelable(EXTRA_OFFER);
        productOffers = new ArrayList<>();
        productStoreAdapter = new ProductStoreAdapter(getActivity(), productOffers);

        mainImageView = contentView.findViewById(R.id.main_backdrop);
        Glide.with(getActivity())
                .asBitmap()
                .load(offer.getProduct().getImage())
                .into(mainImageView);

        rvStores = contentView.findViewById(R.id.rv_stores);
        RecyclerView.LayoutManager mLayoutManagerStores = new LinearLayoutManager(getActivity());
        rvStores.setLayoutManager(mLayoutManagerStores);
        rvStores.setHasFixedSize(true);
        rvStores.setAdapter(productStoreAdapter);
        productStoreAdapter.setmClickListener(this);

        rvSimilarProducts = contentView.findViewById(R.id.rv_similar_prodcuts);
        RecyclerView.LayoutManager mLayoutManagerSimilarProducts = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvSimilarProducts.setLayoutManager(mLayoutManagerSimilarProducts);
        rvSimilarProducts.setHasFixedSize(true);

        tvDesc = contentView.findViewById(R.id.tv_description);
        tvDesc.setText(offer.getProduct().getDescription());

        floatingActionButton = contentView.findViewById(R.id.fab_favorites);
        floatingActionButton.setOnClickListener(this);
        checkIfFavorite();
    }

    private void getOffers() {
        Call<List<ProductOffers>> call = AppHolder.getInstance().getClientInterface().getOffersWithSameProduct(offer.getProduct());
        call.enqueue(new Callback<List<ProductOffers>>() {
            @Override
            public void onResponse(Call<List<ProductOffers>> call, Response<List<ProductOffers>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productOffers.clear();
                    productOffers.addAll(response.body());
                    productStoreAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<ProductOffers>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onProductStoreItemClicked(View view, int adapterPosition) {
        ProductOffers item = productStoreAdapter.getProductStoreItems().get(adapterPosition);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(item.getUrl()));
        startActivity(i);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_favorites:
                isFavorite = !isFavorite;
                setFavorite(isFavorite);
                break;
        }
    }

    private void setFavorite(final Boolean isFavorite) {
        Favorites favorites = new Favorites(InstanceID.getInstance(getContext()).getId(), offer.getProduct().getName(), isFavorite);
        Call<Void> call = AppHolder.getInstance().getClientInterface().addProductsToFavorites(favorites);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    setFavoriteIcon(isFavorite);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void setFavoriteIcon(Boolean isFavorite) {
        if (isFavorite) {
            int color = ContextCompat.getColor(getActivity(), android.R.color.holo_red_light);
            floatingActionButton.getDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
//                    floatingActionButton.setBackgroundTintList(ResourcesCompat.getColorStateList(getResources(),android.R.color.holo_red_dark, null));
        } else {
            int color = ContextCompat.getColor(getActivity(), R.color.primaryTintColor);
            floatingActionButton.getDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
//                    floatingActionButton.setBackgroundTintList(ResourcesCompat.getColorStateList(getResources(), R.color.primaryTintColor, null));
        }
    }

    public void checkIfFavorite() {
        Favorites favorites = new Favorites(InstanceID.getInstance(getContext()).getId(), offer.getProduct().getName());
        Call<Boolean> call = AppHolder.getInstance().getClientInterface().checkIfProductIsFavorite(favorites);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    setFavoriteIcon(response.body());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }
}
