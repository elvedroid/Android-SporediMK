package org.emobile.myitmarket.adapter;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.emobile.myitmarket.R;
import org.emobile.myitmarket.model.SimpleItem;

import java.util.List;

public class CheckableAdapter extends RecyclerView.Adapter<CheckableAdapter.ViewHolder> {
    public interface SimpleAdapterInterface {
        void onItemClicked(int adapterPosition);
    }

    private Context context;
    private List<SimpleItem> items;
    private SimpleAdapter.SimpleAdapterInterface listener;

    public CheckableAdapter(Context context, List<SimpleItem> options) {
        this.context = context;
        this.items = options;
    }

    public void setListener(SimpleAdapter.SimpleAdapterInterface listener) {
        this.listener = listener;
    }

    @Override
    public CheckableAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checkable_item, parent, false);
        return new CheckableAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CheckableAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(items.get(position).getTitle());
        if(items.get(position).isChecked()){
            holder.tvTitle.setCompoundDrawablesWithIntrinsicBounds(null,null, ResourcesCompat.getDrawable(context.getResources(), android.R.drawable.checkbox_on_background, null),null);
        } else {
            holder.tvTitle.setCompoundDrawablesWithIntrinsicBounds(null,null, ResourcesCompat.getDrawable(context.getResources(), android.R.drawable.checkbox_off_background, null),null);
        }
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
