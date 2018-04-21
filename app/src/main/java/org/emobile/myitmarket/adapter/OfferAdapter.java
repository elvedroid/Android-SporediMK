package org.emobile.myitmarket.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import org.emobile.myitmarket.R;
import org.emobile.myitmarket.model.Category;
import org.emobile.myitmarket.model.Offer;

import java.util.List;

/**
 * Created by elvedin on 9/23/17.
 */

public class OfferAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OfferItemInterface{
        void onOfferItemClicked(View view, int adapterPosition);
    }

    public final static int ITEM_TYPE_GRID = 0;
    public final static int ITEM_TYPE_LIST = 1;
    private List<Offer> offers;
    private Activity activity;
    private OfferItemInterface mClickListener;
    private int itemType;

    public OfferAdapter(Activity activity, List<Offer> categories, int itemType) {
        this.offers = categories;
        this.activity = activity;
        this.itemType = itemType;
    }

    public void setmClickListener(OfferItemInterface mClickListener) {
        this.mClickListener = mClickListener;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        if(viewType == R.layout.category_grid_item) {
            return new ViewHolderGrid(view);
        }
        else {
            return new ViewHolderList(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        String title = offers.get(position).getProduct().getName();
        String price = offers.get(position).getPrice().getValue() + " " + offers.get(position).getPrice().getUnit();
        String iconStr = offers.get(position).getProduct().getImage();

        if (holder.getItemViewType() == R.layout.category_grid_item) {
            ((ViewHolderGrid)holder).tvTitle.setText(title);
            ((ViewHolderGrid)holder).tvPrice.setText(price);
            ((ViewHolderGrid)holder).tvPrice.setVisibility(View.VISIBLE);
            Glide.with(activity)

                    .load(iconStr)
                    .into(((ViewHolderGrid)holder).icon);

            ((ViewHolderGrid)holder).mView.setOnClickListener(view -> {
                if(mClickListener!=null){
                    mClickListener.onOfferItemClicked(view, position);
                }
            });
        } else if(holder.getItemViewType() == R.layout.category_list_item) {
            ((ViewHolderList)holder).tvTitle.setText(title);
            ((ViewHolderList)holder).tvTitle
                    .setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

            Glide.with(activity)

                    .load(iconStr)
                    .into(((ViewHolderList)holder).icon);
            ((ViewHolderList)holder).tvPrice.setText(price);
            ((ViewHolderList)holder).tvPrice.setVisibility(View.VISIBLE);

            ((ViewHolderList)holder).mView.setOnClickListener(view -> {
                if(mClickListener!=null){
                    mClickListener.onOfferItemClicked(view,position);
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (itemType) {
            case ITEM_TYPE_GRID:
                return R.layout.category_grid_item;
            case ITEM_TYPE_LIST:
                return R.layout.category_list_item;
            default:
                return R.layout.category_grid_item;
        }
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    private class ViewHolderGrid extends RecyclerView.ViewHolder {
        final View mView;
        final ImageView icon;
        final TextView tvTitle, tvPrice;
        Category mItem;

        ViewHolderGrid(View view) {
            super(view);
            mView = view;
            tvTitle = view.findViewById(R.id.tv_title);
            tvPrice = view.findViewById(R.id.price);
            icon = view.findViewById(R.id.thumbnail);
            mItem = null;
        }
    }

    private class ViewHolderList extends RecyclerView.ViewHolder {
        final View mView;
        final ImageView icon;
        final TextView tvTitle, tvPrice;
        Category mItem;

        ViewHolderList(View view) {
            super(view);
            mView = view;
            tvTitle = view.findViewById(R.id.tv_title);
            tvPrice = view.findViewById(R.id.tv_price);
            icon = view.findViewById(R.id.image);
            mItem = null;
        }
    }
}
