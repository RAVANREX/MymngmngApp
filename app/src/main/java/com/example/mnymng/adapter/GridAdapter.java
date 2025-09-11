package com.example.mnymng.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mnymng.R;
import com.example.mnymng.model.MenuItem;

import java.util.List;

public class GridAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private List<T> items;
    private Context context;
    private int layoutResId;
    private OnItemClickListener<T> onItemClickListener;
    private ViewHolderFactory<VH> viewHolderFactory;
    private ViewHolderBinder<T, VH> viewHolderBinder;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public interface OnItemClickListener<T> {
        void onItemClick(T item, int position);
    }

    public interface ViewHolderFactory<VH extends RecyclerView.ViewHolder> {
        VH createViewHolder(View itemView);
    }

    public interface ViewHolderBinder<T, VH extends RecyclerView.ViewHolder> {
        void bindViewHolder(VH holder, T item);
    }

    public GridAdapter(Context context, List<T> items, int layoutResId,
                       ViewHolderFactory<VH> viewHolderFactory,
                       ViewHolderBinder<T, VH> viewHolderBinder) {
        this.context = context;
        this.items = items;
        this.layoutResId = layoutResId;
        this.viewHolderFactory = viewHolderFactory;
        this.viewHolderBinder = viewHolderBinder;
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(this.layoutResId, parent, false);
        return viewHolderFactory.createViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        T currentItem = items.get(position);
        viewHolderBinder.bindViewHolder(holder, currentItem);

        if (currentItem instanceof MenuItem) {
            MenuItem menuItem = (MenuItem) currentItem;
            if (menuItem.isSelected()) {
                holder.itemView.setBackgroundColor(Color.LTGRAY); // Or use a color resource
            } else {
                holder.itemView.setBackgroundColor(Color.TRANSPARENT); // Default background
            }
        }

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    // Update selection state
                    if (selectedPosition != RecyclerView.NO_POSITION && items.get(selectedPosition) instanceof MenuItem) {
                        ((MenuItem) items.get(selectedPosition)).setSelected(false);
                        notifyItemChanged(selectedPosition);
                    }
                    if (items.get(currentPosition) instanceof MenuItem) {
                        ((MenuItem) items.get(currentPosition)).setSelected(true);
                        selectedPosition = currentPosition;
                        notifyItemChanged(selectedPosition);
                    }
                    onItemClickListener.onItemClick(items.get(currentPosition), currentPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void updateData(List<T> newItems) {
        this.items.clear();
        this.items.addAll(newItems);
        selectedPosition = RecyclerView.NO_POSITION; // Reset selection on new data
        notifyDataSetChanged();
    }
    
    // Method to get the currently selected item
    public T getSelectedItem() {
        if (selectedPosition != RecyclerView.NO_POSITION) {
            return items.get(selectedPosition);
        }
        return null;
    }
}
