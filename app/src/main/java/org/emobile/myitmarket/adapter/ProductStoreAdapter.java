package org.emobile.myitmarket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import org.emobile.myitmarket.R;
import org.emobile.myitmarket.base.BaseAdapter;
import org.emobile.myitmarket.model.ProductOffers;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by elvedin on 10/30/17.
 */

public class ProductStoreAdapter extends BaseAdapter<ProductOffers, ProductStoreAdapter.ViewHolder> {
    private Context context;
    public ProductStoreAdapter(Context context) {
        super(new ArrayList<>());
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        return new ProductStoreAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductOffers item = getItem(position);
        String title = item.getPrice().getValue()
                + item.getPrice().getUnit();
        String iconStr = item.getSellerLogo();

        holder.tvTitle.setText(title);

        Glide.with(context)

                .load(iconStr)
                .into(holder.icon);

        holder.mView.setOnClickListener(view -> {
            if (clickListener != null) {
                clickListener.OnItemClicked(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.product_store_item;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        @BindView(R.id.iv_logo) ImageView icon;
        @BindView(R.id.tv_title) TextView tvTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;
        }
    }
}
