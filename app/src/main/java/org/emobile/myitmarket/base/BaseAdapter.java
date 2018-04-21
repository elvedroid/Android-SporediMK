package org.emobile.myitmarket.base;

import android.support.v7.widget.RecyclerView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by elvedin on 12/5/17.
 */

public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<T> items;

    public interface OnItemClickListener {
        void OnItemClicked(int adapterPosition);
    }

    protected OnItemClickListener clickListener;

    public BaseAdapter(List<T> items) {
        this.items = items;
    }

    public void addOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**
     * Adds the specified object at the end of the array.
     *
     * @param object The object to add at the end of the array.
     */
    public void add(final T object) {
        items.add(object);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAll(List<T> list) {
        items.addAll(list);
        notifyItemRangeInserted(items.size() - list.size(), items.size());
    }

    public void load(List<T> list) {
        items.clear();
        items.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * Remove all elements from the list.
     */
    public void clear() {
        final int size = getItemCount();
        items.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public T getItem(final int position) {
        return items.get(position);
    }

    public long getItemId(final int position) {
        return position;
    }

    /**
     * Returns the position of the specified item in the array.
     *
     * @param item The item to retrieve the position of.
     * @return The position of the specified item.
     */
    public int getPosition(final T item) {
        return items.indexOf(item);
    }

    /**
     * Inserts the specified object at the specified index in the array.
     *
     * @param object The object to insert into the array.
     * @param index  The index at which the object must be inserted.
     */
    public void insert(final T object, int index) {
        items.add(index, object);
        notifyItemInserted(index);

    }

    /**
     * Removes the specified object from the array.
     *
     * @param object The object to remove.
     */
    public void remove(T object) {
        final int position = getPosition(object);
        items.remove(object);
        notifyItemRemoved(position);
    }

    /**
     * Sorts the content of this adapter using the specified comparator.
     *
     * @param comparator The comparator used to sort the objects contained in this adapter.
     */
    public void sort(Comparator<? super T> comparator) {
        Collections.sort(items, comparator);
        notifyItemRangeChanged(0, getItemCount());
    }
}
