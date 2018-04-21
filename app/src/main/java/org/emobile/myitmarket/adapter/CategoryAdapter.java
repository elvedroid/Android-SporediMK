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

import java.util.List;

/**
 * Created by Elvedin Selimoski on 9/23/17. mail: elveselimoski@gmail.com
 */

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface CategoryItemInterface{
        void onCategoryItemClicked(View view, int adapterPosition);
    }

    public final static int ITEM_TYPE_GRID = 0;
    public final static int ITEM_TYPE_LIST = 1;
    private List<Category> categories;
    private Activity activity;
    private CategoryItemInterface mClickListener;
    private int itemType;

    public CategoryAdapter(Activity activity, List<Category> categories, int itemType) {
        this.categories = categories;
        this.activity = activity;
        this.itemType = itemType;
    }

    public void setmClickListener(CategoryItemInterface mClickListener) {
        this.mClickListener = mClickListener;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public List<Category> getCategories() {
        return categories;
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
        String title = categories.get(position).getName();
        String iconStr = categories.get(position).getIcon();

        if (holder.getItemViewType() == R.layout.category_grid_item) {
            ((ViewHolderGrid)holder).tvTitle.setText(title);
            Glide.with(activity)

                    .load(iconStr)
                    .into(((CategoryAdapter.ViewHolderGrid)holder).icon);
            ((ViewHolderGrid)holder).mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mClickListener!=null){
                        mClickListener.onCategoryItemClicked(view, position);
                    }
                }
            });
        } else if(holder.getItemViewType() == R.layout.category_list_item) {
            ((ViewHolderList)holder).tvTitle.setText(title);
            ((ViewHolderList)holder).tvTitle
                    .setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            Glide.with(activity)

                    .load(iconStr)
                    .into(((CategoryAdapter.ViewHolderList)holder).icon);
            ((ViewHolderList)holder).mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mClickListener!=null){
                        mClickListener.onCategoryItemClicked(view,position);
                        notifyDataSetChanged();
                    }
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
        return categories.size();
    }

    private class ViewHolderGrid extends RecyclerView.ViewHolder {
        final View mView;
        final ImageView icon;
        final TextView tvTitle;
        Category mItem;

        ViewHolderGrid(View view) {
            super(view);
            mView = view;
            tvTitle = view.findViewById(R.id.tv_title);
            icon = view.findViewById(R.id.thumbnail);
            mItem = null;
        }
    }

    private class ViewHolderList extends RecyclerView.ViewHolder {
        final View mView;
        final ImageView icon;
        final TextView tvTitle;
        Category mItem;

        ViewHolderList(View view) {
            super(view);
            mView = view;
            tvTitle = view.findViewById(R.id.tv_title);
            icon = view.findViewById(R.id.image);
            mItem = null;
        }
    }
}
