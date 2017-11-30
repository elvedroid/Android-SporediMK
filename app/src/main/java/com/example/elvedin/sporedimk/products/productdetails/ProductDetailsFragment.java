package com.example.elvedin.sporedimk.products.productdetails;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.elvedin.sporedimk.R;
import com.example.elvedin.sporedimk.adapter.ProductStoreAdapter;
import com.example.elvedin.sporedimk.model.Favorites;
import com.example.elvedin.sporedimk.model.Offer;
import com.example.elvedin.sporedimk.model.ProductOffers;
import com.example.elvedin.sporedimk.scheduler.SchedulerProvider;
import com.example.elvedin.sporedimk.ui.activity.BaseActivity;
import com.example.elvedin.sporedimk.ui.fragment.BaseFragment;
import com.example.elvedin.sporedimk.ui.manager.AppHolder;
import com.example.elvedin.sporedimk.ui.manager.log.LogLevel;
import com.example.elvedin.sporedimk.ui.manager.network_manager.RemoteRepository;
import com.google.android.gms.iid.InstanceID;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * Created by elvedin on 10/27/17.
 */

public class ProductDetailsFragment extends BaseFragment implements ProductStoreAdapter.ProductStoreItemInterface, ProductDetailsContract.View {

    public final static String TAG = "ProductDetailsFragment";
    public final static String EXTRA_OFFER = "EXTRA_OFFER";

    @BindView(R.id.fab_favorites) FloatingActionButton floatingActionButton;
    @BindView(R.id.product_image) ImageView mainImageView;
    @BindView(R.id.rv_stores) RecyclerView rvStores;
//    @BindView(R.id.rv_similar_prodcuts) RecyclerView rvSimilarProducts;
    @BindView(R.id.tv_description) TextView tvDesc;

    ProgressBar progressBar;

    RemoteRepository repository;
    ProductDetailsPresenter presenter;
    Offer offer;
    Boolean isFavorite = false;
    ProductStoreAdapter productStoreAdapter;
    List<ProductOffers> productOffers;

    public static ProductDetailsFragment newInstance(Offer offer) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_OFFER, offer);
        ProductDetailsFragment fragment = new ProductDetailsFragment();
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
        ButterKnife.bind(this, contentView);

        offer = getArguments().getParcelable(EXTRA_OFFER);
        productOffers = new ArrayList<>();
        productStoreAdapter = new ProductStoreAdapter(getActivity(), productOffers);
        Glide.with(getActivity())
                .asBitmap()
                .load(offer.getProduct().getImage())
                .into(mainImageView);
        tvDesc.setText(offer.getProduct().getDescription());
        initRecyclerViews();

        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).setHideSearchMenuItem(true);
        }

        presenter = new ProductDetailsPresenter(AppHolder.getInstance().getRemoteRepository(), this, new SchedulerProvider());
        presenter.checkIfProductIsInFavorites(new Favorites(InstanceID.getInstance(getContext()).getId(), offer.getProduct().getName()));
        presenter.loadOffersForSameProduct(offer.getProduct());
    }

    private void initRecyclerViews() {
        RecyclerView.LayoutManager mLayoutManagerStores = new LinearLayoutManager(getActivity());
        rvStores.setLayoutManager(mLayoutManagerStores);
        rvStores.setHasFixedSize(true);
        rvStores.setAdapter(productStoreAdapter);
        productStoreAdapter.setmClickListener(this);

//        TODO:Implement similar products
//        RecyclerView.LayoutManager mLayoutManagerSimilarProducts = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        rvSimilarProducts.setLayoutManager(mLayoutManagerSimilarProducts);
//        rvSimilarProducts.setHasFixedSize(true);
    }

    @Override
    public void onProductStoreItemClicked(View view, int adapterPosition) {
        ProductOffers item = productStoreAdapter.getProductStoreItems().get(adapterPosition);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(item.getUrl()));
        startActivity(i);
    }

    @OnClick(R.id.fab_favorites)
    public void onFabFavoriteClicked() {
        presenter.setIsInFavorites(new Favorites(InstanceID.getInstance(getContext()).getId(), offer.getProduct().getName(), !isFavorite));
    }

    @Override
    public void setProgressIndicator(boolean active) {

    }

    @Override
    public void toggleFavorite(Boolean isInFavorites) {
        isFavorite = isInFavorites;
        if (isInFavorites) {
            int color = ContextCompat.getColor(getActivity(), android.R.color.holo_red_light);
            floatingActionButton.getDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        } else {
            int color = ContextCompat.getColor(getActivity(), R.color.primaryTintColor);
            floatingActionButton.getDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }

    @Override
    public void loadOffers(List<ProductOffers> productOffer) {
        productOffers.clear();
        productOffers.addAll(productOffer);
        productStoreAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        AppHolder.logWithToast(getContext(), LogLevel.ERROR, TAG, message);
    }
}
