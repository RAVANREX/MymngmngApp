package com.example.mnymng.fragments.utilfragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast; // Added import

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mnymng.DB.dao.CategoryDao;
import com.example.mnymng.DB.enums.CategoryType;
import com.example.mnymng.DB.AppDatabase;
import com.example.mnymng.DB.models.Category;
import com.example.mnymng.R;
import com.example.mnymng.adapter.GridAdapter;
// Import PopupTransactionFragment if it's in a different package, assuming it's in the same for now
// import com.example.mnymng.fragments.utilfragments.PopupTransactionFragment; 
import com.example.mnymng.model.MenuItem;

import java.io.Serializable; // Added for passing Category in Bundle
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CataListFragment extends Fragment {

    private RecyclerView recyclerView;
    private GridAdapter<MenuItem, MenuItemViewHolder> adapter;
    private CategoryDao categoryDao;
    public static CategoryType screenName;

    private boolean isEditMode = false; // Flag for edit mode
    private List<Category> currentCategories = new ArrayList<>(); // To store loaded categories

    // ViewHolder specific to MenuItem and item_grid.xml
    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        TextView emojiTextView;
        TextView nameTextView;

        public MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            emojiTextView = itemView.findViewById(R.id.item_emoji);
            nameTextView = itemView.findViewById(R.id.item_name);
        }

        public void bind(MenuItem item) {
            if (item != null) {
                if (emojiTextView != null) {
                    emojiTextView.setText(item.getEmoji());
                }
                if (nameTextView != null) {
                    nameTextView.setText(item.getName());
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AtomicReference<View> view = new AtomicReference<>(inflater.inflate(R.layout.fragment_catalist, container, false));
        recyclerView = view.get().findViewById(R.id.rv_icon_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));

        categoryDao = AppDatabase.getDatabase(getContext()).categoryDao();
        CategoryType categoryType =   screenName != null ? screenName : null;

        GridAdapter.ViewHolderFactory<MenuItemViewHolder> factory = MenuItemViewHolder::new;
        GridAdapter.ViewHolderBinder<MenuItem, MenuItemViewHolder> binder = (holder, item) -> holder.bind(item);

        adapter = new GridAdapter<>(
                getContext(),
                new ArrayList<>(),
                R.layout.item_grid,
                factory,
                binder
        );

        adapter.setOnItemClickListener((menuItem, position) -> {
            Log.d("CataListFragment", "Clicked: " + menuItem.getName() + " at position " + position);

            if (menuItem.getName().equals("Edit")) {
                isEditMode = !isEditMode; // Toggle edit mode
                if (isEditMode) {
                    Toast.makeText(getContext(), "Edit mode activated. Select a category to edit.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Edit mode deactivated.", Toast.LENGTH_SHORT).show();
                }
            } else if (menuItem.getName().equals("Add")) {
                isEditMode = false; // Ensure exit from edit mode
                PopupViewFragment popupViewFragment = new PopupViewFragment();
                // For "Add", no arguments are needed for the popup
                popupViewFragment.show(getParentFragmentManager(), "PopupViewFragmentTag");
            } else { // This is a category item click (not "Edit" or "Add")
                if (isEditMode) {
                    // Ensure position is valid for currentCategories (which excludes Edit/Add buttons)
                    if (position < currentCategories.size()) {
                        Category selectedCategory = currentCategories.get(position);

                        PopupViewFragment popupViewFragment = new PopupViewFragment();
                        Bundle bundle = new Bundle();
                        // Make sure your Category class implements Serializable or Parcelable
                        bundle.putSerializable("categoryToEdit", (Serializable) selectedCategory);
                        popupViewFragment.setArguments(bundle);
                        popupViewFragment.show(getParentFragmentManager(), "PopupViewFragmentTag"); // Using the same tag

                        isEditMode = false; // Exit edit mode after selection
                        Toast.makeText(getContext(), "Exited edit mode.", Toast.LENGTH_SHORT).show(); 
                    } else {
                        Log.e("CataListFragment", "Invalid position for category in edit mode: " + position);
                        isEditMode = false; // Also exit edit mode if position was invalid
                    }
                } else {
                    // Handle normal click on category item if needed
                    Log.d("CataListFragment", "Normal click on: " + menuItem.getName());
                    if (position < currentCategories.size()) {
                        Category selectedCategory = currentCategories.get(position);
                        // Changed to PopupTransactionFragment
                        PopupTransactionFragment popupTransactionFragment = new PopupTransactionFragment();
                        Bundle bundle = new Bundle();
                        // Make sure your Category class implements Serializable or Parcelable
                        // Ensure PopupTransactionFragment expects "transactionAddCall" or adjust as needed
                        bundle.putSerializable("transactionAddCall", (Serializable) selectedCategory);
                        popupTransactionFragment.setArguments(bundle);
                        popupTransactionFragment.show(getParentFragmentManager(), "PopupTransactionFragmentTag"); // Using a new tag
                        // Close the bottom drawer
                    }

                }
            }
        });

        recyclerView.setAdapter(adapter);
        loadCategoriesByType(categoryType);
        return view.get();
    }

    private void loadCategoriesByType(CategoryType categoryType) {
        Log.d("CataListFragmentData", "Loading categories for type: " + categoryType);
        categoryDao.getCategoriesByType(categoryType).observe(getViewLifecycleOwner(), categories -> {
            Log.d("CataListFragmentData", "Categories received: " + (categories == null ? "null" : categories.size()));
            List<MenuItem> menuItems = new ArrayList<>();
            currentCategories.clear(); // Clear previous categories

            if (categories != null) {
                currentCategories.addAll(categories); // Store fetched categories
                for (Category category : categories) {
                    Log.d("CataListFragmentData", "Adding category to menu: " + category.getCata_name());
                    menuItems.add(new MenuItem(category.getCata_icon(), category.getCata_name()));
                }
            } else {
                Log.d("CataListFragmentData", "Categories list is null");
            }
            // Add Edit and Add buttons AFTER categories
            menuItems.add(new MenuItem("⚙️", "Edit"));
            menuItems.add(new MenuItem("➕", "Add"));
            adapter.updateData(menuItems);

        });
    }
}