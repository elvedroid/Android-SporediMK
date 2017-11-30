package com.example.elvedin.sporedimk.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.elvedin.sporedimk.R;
import com.example.elvedin.sporedimk.model.ProductOffers;
import com.example.elvedin.sporedimk.ui.view.ProductStoreItem;

import java.util.List;

/**
 * Created by elvedin on 10/30/17.
 */

public class ProductStoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface ProductStoreItemInterface {
        void onProductStoreItemClicked(View view, int adapterPosition);
    }

    private Activity activity;
    List<ProductOffers> productStoreItems;
    private ProductStoreItemInterface mClickListener;

    public ProductStoreAdapter(Activity activity, List<ProductOffers> productStoreItems) {
        this.productStoreItems = productStoreItems;
        this.activity = activity;
    }

    public void setmClickListener(ProductStoreItemInterface mClickListener) {
        this.mClickListener = mClickListener;
    }

    public List<ProductOffers> getProductStoreItems() {
        return productStoreItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        return new ProductStoreAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        String title = productStoreItems.get(position).getPrice().getValue()
                + productStoreItems.get(position).getPrice().getUnit();
        String iconStr = productStoreItems.get(position).getSellerLogo();

        ((ProductStoreAdapter.ViewHolder) holder).tvTitle.setText(title);

        Glide.with(activity)
                .asBitmap()
                .load(iconStr)
                .into(((ViewHolder) holder).icon);

        ((ProductStoreAdapter.ViewHolder) holder).mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null) {
                    mClickListener.onProductStoreItemClicked(view, position);
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.product_store_item;
    }

    @Override
    public int getItemCount() {
        return productStoreItems.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final ImageView icon;
        final TextView tvTitle;
        ProductStoreItem mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            tvTitle = view.findViewById(R.id.tv_title);
            icon = view.findViewById(R.id.iv_logo);
            mItem = null;
        }
    }
}
