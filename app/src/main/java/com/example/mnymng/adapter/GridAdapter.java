package com.example.mnymng.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mnymng.R; // Make sure this R is your project's R
import com.example.mnymng.model.MenuItem; // Make sure this path is correct

import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    private List<MenuItem> menuItems;
    private Context context;
    private OnItemClickListener listener; // Listener variable

    /**
     * Interface definition for a callback to be invoked when an item in this
     * RecyclerView has been clicked.
     */
    public interface OnItemClickListener {
        /**
         * Callback method to be invoked when an item in this RecyclerView has been clicked.
         *
         * @param item The MenuItem that was clicked.
         * @param position The position of the item in the adapter.
         */
        void onItemClick(MenuItem item, int position);
    }

    /**
     * Register a callback to be invoked when an item in this adapter has been clicked.
     *
     * @param listener The callback that will be invoked.
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public GridAdapter(Context context, List<MenuItem> menuItems) {
        this.context = context; // Context can be useful for inflating views or other context-specific operations
        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // It's generally better to get the LayoutInflater from the parent's context
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItem currentItem = menuItems.get(position);
        holder.icon.setImageResource(currentItem.getIconResId());
        holder.name.setText(currentItem.getName());

        // Set the click listener on the entire item view
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                // It's crucial to use holder.getAdapterPosition() if your list can change
                // or if there are headers/footers. If the list is static and simple,
                // the 'position' parameter of onBindViewHolder is often okay too.
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) { // Check for valid position
                    listener.onItemClick(menuItems.get(currentPosition), currentPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.item_icon); // Ensure these IDs exist in item_grid.xml
            name = itemView.findViewById(R.id.item_name); // Ensure these IDs exist in item_grid.xml
        }
    }
}
