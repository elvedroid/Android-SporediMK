package com.example.elvedin.sporedimk.adapter;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.elvedin.sporedimk.R;
import com.example.elvedin.sporedimk.model.SimpleItem;

import java.util.List;

/**
 * Created by elvedin on 11/23/17.
 */

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {

    public interface SimpleAdapterInterface {
        void onItemClicked(int adapterPosition);
    }

    private Context context;
    private List<SimpleItem> items;
    private SimpleAdapterInterface listener;

    public SimpleAdapter(Context context, List<SimpleItem> options) {
        this.context = context;
        this.items = options;
    }

    public void setListener(SimpleAdapterInterface listener) {
        this.listener = listener;
    }

    @Override
    public SimpleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simple_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(items.get(position).getTitle());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClicked(items.get(holder.getAdapterPosition()).getTag());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle;
        final View view;

        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
