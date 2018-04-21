package org.emobile.myitmarket.base.products.productdetails;

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
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.emobile.myitmarket.adapter.ProductStoreAdapter;
import org.emobile.myitmarket.base.BaseActivity;
import org.emobile.myitmarket.base.BaseAdapter;
import org.emobile.myitmarket.base.di.component.ProductComponent;
import org.emobile.myitmarket.model.Favorites;
import org.emobile.myitmarket.model.Offer;
import org.emobile.myitmarket.model.ProductOffers;
import org.emobile.myitmarket.R;
import org.emobile.myitmarket.base.BaseFragment;
import org.emobile.myitmarket.manager.AppHolder;
import org.emobile.myitmarket.manager.log.LogLevel;

import com.google.android.gms.iid.InstanceID;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * Created by elvedin on 10/27/17.
 */

public class ProductDetailsFragment extends BaseFragment implements ProductDetailsContract.View {

    public final static String TAG = "ProductDetailsFragment";
    public final static String EXTRA_OFFER = "EXTRA_OFFER";

    @BindView(R.id.fab_favorites)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.product_image)
    ImageView mainImageView;
    @BindView(R.id.rv_stores)
    RecyclerView rvStores;
    //    @BindView(R.id.rv_similar_prodcuts) RecyclerView rvSimilarProducts;
    @BindView(R.id.tv_description)
    TextView tvDesc;

    @Inject
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
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
    }

    @Override
    public void onPause() {
        presenter.dropView();
        super.onPause();
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
        getComponent(ProductComponent.class).inject(this);

        offer = getArguments().getParcelable(EXTRA_OFFER);
        Glide.with(getActivity())

                .load(offer.getProduct().getImage())
                .into(mainImageView);
        tvDesc.setText(offer.getProduct().getDescription());

        initRecyclerViews();

        presenter.checkIfProductIsInFavorites(new Favorites(InstanceID.getInstance(getContext()).getId(), offer.getProduct().getName()));
        presenter.loadOffersForSameProduct(offer.getProduct());
    }

    private void initRecyclerViews() {
        productStoreAdapter = new ProductStoreAdapter(getActivity());
        productStoreAdapter.addOnItemClickListener(onProductStoreItemClicked);
        rvStores.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvStores.setHasFixedSize(true);
        rvStores.setAdapter(productStoreAdapter);

//        TODO:Implement similar products
//        RecyclerView.LayoutManager mLayoutManagerSimilarProducts = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        rvSimilarProducts.setLayoutManager(mLayoutManagerSimilarProducts);
//        rvSimilarProducts.setHasFixedSize(true);
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
        productStoreAdapter.load(productOffer);
    }

    @Override
    public void showError(String message) {
        AppHolder.logWithToast(getContext(), LogLevel.ERROR, TAG, message);
    }

    BaseAdapter.OnItemClickListener onProductStoreItemClicked = adapterPosition -> {
        ProductOffers item = productStoreAdapter.getItem(adapterPosition);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(item.getUrl()));
        startActivity(i);
    };

}
