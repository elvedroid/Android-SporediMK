package org.emobile.myitmarket.base.main;

import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.emobile.myitmarket.R;
import org.emobile.myitmarket.model.Offer;
import org.emobile.myitmarket.model.Product;

/**
 * Created by Elvedin Selimoski on 4/21/18. mail: elveselimoski@gmail.com
 */
public class SuggestionsAdapter extends CursorAdapter {
    private static final int VIEW_TYPE_ITEM_SUGGESTION = R.layout.item_search;
    public static final String PRODUCT_CURSOR = "PRODUCT_CURSOR";

    private final Gson gson;
    private LayoutInflater layoutInflater;

    public SuggestionsAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        this.layoutInflater = LayoutInflater.from(context);
        gson = new Gson();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return layoutInflater.inflate(VIEW_TYPE_ITEM_SUGGESTION, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        LinearLayout llNoResults = view.findViewById(R.id.ll_no_results);
        View llSearchParent = view.findViewById(R.id.cv_search_view);
        if (cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID)) == -1) {
            llNoResults.setVisibility(View.VISIBLE);
            llSearchParent.setVisibility(View.GONE);
            return;
        }

        llNoResults.setVisibility(View.GONE);
        llSearchParent.setVisibility(View.VISIBLE);

        Offer offer = gson.fromJson(cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_CURSOR)), Offer.class);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvPrice = view.findViewById(R.id.tv_price);
        ImageView ivProduct = view.findViewById(R.id.image);

        tvTitle.setText(offer.getProduct().getName());

        Glide.with(context)
                .load(offer.getProduct().getImage())
                .into(ivProduct);

        String price = offer.getPrice().getValue() + " " + offer.getPrice().getUnit();
        tvPrice.setText(price);
    }
}
