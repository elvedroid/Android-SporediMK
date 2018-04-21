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
import org.emobile.myitmarket.ui.view.HorizontalAdapterItem;

import java.util.List;

/**
 * Created by elvedin on 11/13/17.
 */

public class HorizontalRecyclerAdapter extends RecyclerView.Adapter<HorizontalRecyclerAdapter.ViewHolder> {
    public interface OnItemClickInterface {
        void onItemClicked(View view, int adapterPosition);
    }

    private Context context;
    private List<HorizontalAdapterItem> items;
    private OnItemClickInterface listener;

    public HorizontalRecyclerAdapter(Context context, List<HorizontalAdapterItem> items) {
        this.context = context;
        this.items = items;
    }

    public void setListener(OnItemClickInterface listener) {
        this.listener = listener;
    }

    @Override
    public HorizontalRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizontal_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HorizontalRecyclerAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(items.get(position).getTitle());
        Glide.with(context)

                .load(items.get(position).getIcon())
                .into(holder.ivIcon);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClicked(view, holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View view;
        final ImageView ivIcon;
        final TextView tvTitle;

        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ivIcon = itemView.findViewById(R.id.image);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
