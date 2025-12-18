package com.example.mnymng.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mnymng.R;
import com.example.mnymng.adapter.GridAdapter; // Assuming GridAdapter is in this package
import com.example.mnymng.model.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class IconListFragment extends Fragment {

    private RecyclerView recyclerView;
    private GridAdapter<MenuItem, MenuItemViewHolder> adapter; // Use the generic types
    private List<MenuItem> menuItems1;
    private List<MenuItem> menuItems2;

    public static String screenName;

    // ViewHolder specific to MenuItem and item_grid.xml
    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        TextView emojiTextView;
        TextView nameTextView;

        public MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ensure these IDs match your item_grid.xml or your item layout
            emojiTextView = itemView.findViewById(R.id.item_emoji);
            nameTextView = itemView.findViewById(R.id.item_name);
        }

        public void bind(MenuItem item) {
            if (item != null) {
                if (emojiTextView != null) {
                    emojiTextView.setText(item.getEmoji()); // Assuming MenuItem has getEmoji()
                }
                if (nameTextView != null) {
                    nameTextView.setText(item.getName()); // Assuming MenuItem has getName()
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_iconlist, container, false);
        recyclerView = view.findViewById(R.id.rv_icon_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5)); // Adjust span count as needed

        // Prepare sample data
        menuItems1 = new ArrayList<>();
        menuItems1.add(new MenuItem("🏠", "Expense"));
        menuItems1.add(new MenuItem("🖼️", "Gallery"));
        menuItems1.add(new MenuItem("⚙️", "Settings"));
        menuItems1.add(new MenuItem("➕", "Add"));
        // Add more items as needed
        // Prepare sample data
        menuItems2 = new ArrayList<>();
        menuItems2.add(new MenuItem("🏠", "Income"));
        menuItems2.add(new MenuItem("🖼️", "Gallery"));
        menuItems2.add(new MenuItem("⚙️", "Settings"));
        menuItems2.add(new MenuItem("➕", "Add"));

        // 1. Create the ViewHolderFactory
        GridAdapter.ViewHolderFactory<MenuItemViewHolder> factory = itemView -> new MenuItemViewHolder(itemView);

        // 2. Create the ViewHolderBinder
        GridAdapter.ViewHolderBinder<MenuItem, MenuItemViewHolder> binder = (holder, item) -> holder.bind(item);

        // 3. Instantiate the GridAdapter
        // Assuming R.layout.item_grid is your layout for each grid item
        adapter = new GridAdapter<>(
                getContext(),
                getMenuByName(screenName),
                R.layout.item_grid, // Replace with your actual item layout resource ID
                factory,
                binder
        );

        // 4. Set click listener (optional)
        adapter.setOnItemClickListener((menuItem, position) -> {
            Log.d("IconListFragment", "Clicked: " + menuItem.getName() + " at position " + position);
            // Example navigation:
            // if (menuItem.getName().equals("Add")) {
            // NavHostFragment.findNavController(IconListFragment.this)
            // .navigate(R.id.your_navigation_action); // Replace with your action
            // }
            // Handle other menu item clicks here if needed
        });

        recyclerView.setAdapter(adapter);
        return view;
    }
    private List<MenuItem> getMenuByName(String name) {
        if (name.equals("EXPENSE")) {
            return menuItems1;
        } else if (name.equals("INCOME")) {
            return menuItems2;
        } else {
            return menuItems1;
        }

    }
}
