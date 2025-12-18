package com.example.mnymng.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView; // Assuming TextView is used in list_item.xml

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager; // Or LinearLayoutManager if you want a list
import androidx.recyclerview.widget.RecyclerView;

import com.example.mnymng.DB.enums.Currency;
import com.example.mnymng.R;
import com.example.mnymng.adapter.GridAdapter; // Or your renamed GenericRecyclerAdapter
import com.example.mnymng.model.ListItem; // Using ListItem model
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ExpenseFragment extends Fragment {

    private RecyclerView recyclerView;
    private GridAdapter<ListItem, MyListItemViewHolder> adapter; // Use ListItem and its ViewHolder
    private List<ListItem> dataList; // Changed from menuItems to dataList

    // ViewHolder specific to list_item.xml
    // TODO: Adjust this ViewHolder based on your list_item.xml views and ListItem model
    public static class MyListItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemValue;
        TextView currency;

        public MyListItemViewHolder(@NonNull View itemView) {
            super(itemView);
            // TODO: Find your views from list_item.xml
            // Example: itemTitle = itemView.findViewById(R.id.list_item_title);
            // If R.id.list_item_title doesn't exist, create it in list_item.xml or use an existing ID
            itemName = itemView.findViewById(R.id.item_name); // Placeholder, replace with actual ID from list_item.xml
            itemValue = itemView.findViewById(R.id.item_value);
            currency = itemView.findViewById(R.id.item_sign);
        }

        @SuppressLint("SetTextI18n")
        public void bind(ListItem item) {
            if (item != null) {
                // TODO: Bind data from ListItem to your views
                // Example: itemTitle.setText(item.getTitle()); // Assuming ListItem has getTitle()
                // itemTitle.setText(item.getName()); // Placeholder, replace with actual method from ListItem
                itemName.setText(item.getItemName());
                itemValue.setText(item.getItemValue().toString());
                currency.setText(item.getcurrency());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common, container, false);

        FloatingActionButton buttonOpenDrawer = view.findViewById(R.id.fab_add);
        buttonOpenDrawer.setOnClickListener(v -> {
            BottomDrawer bottomDrawerFragment = new BottomDrawer();
            CataListFragment.screenName = "EXPENSE";
            bottomDrawerFragment.show(getParentFragmentManager(), bottomDrawerFragment.getTag());
        });

        recyclerView = view.findViewById(R.id.recycler_view);

        // TODO: Choose your LayoutManager: GridLayoutManager or LinearLayoutManager
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1)); // Example: 1 column for a list-like appearance

        // Prepare sample data (replace with your actual data source)
        dataList = new ArrayList<>();
        // TODO: Populate dataList with ListItem objects
        // Example:
        // dataList.add(new ListItem("Item 1 Title", "Some detail for item 1"));
        // dataList.add(new ListItem("Add New Item", "Tap to add something new")); // Example for navigation
        dataList.add(new ListItem("Add New Item", 123, Currency.INR.getSymbol(),"Wifi","12/12/2023"));
        dataList.add(new ListItem("Add New Item", 123,Currency.INR.getSymbol(),"Wifi","12/12/2023"));
        dataList.add(new ListItem("Add New Item", 123,Currency.INR.getSymbol(),"Wifi","12/12/2023"));
        dataList.add(new ListItem("Add New Item", 123,Currency.INR.getSymbol(),"Wifi","12/12/2023"));
        dataList.add(new ListItem("Add New Item", 123,Currency.INR.getSymbol(),"Wifi","12/12/2023"));
        dataList.add(new ListItem("Add New Item", 123,Currency.INR.getSymbol(),"Wifi","12/12/2023"));
        dataList.add(new ListItem("Add New Item", 123,Currency.INR.getSymbol(),"Wifi","12/12/2023"));


        // Create the ViewHolderFactory
        GridAdapter.ViewHolderFactory<MyListItemViewHolder> factory = itemView -> new MyListItemViewHolder(itemView);

        // Create the ViewHolderBinder
        GridAdapter.ViewHolderBinder<ListItem, MyListItemViewHolder> binder = (holder, item) -> holder.bind(item);

        // Create the adapter
        adapter = new GridAdapter<>(
                getContext(),
                dataList,
                R.layout.list_item,      // TODO: Ensure this is your correct layout file for list items
                factory,
                binder
        );

        // Set the click listener
        adapter.setOnItemClickListener((item, position) -> {
            // TODO: Update click logic based on ListItem properties and your navigation needs
            // Example:
            // if ("Add New Item".equals(item.getTitle())) { // Assuming ListItem has getTitle()
            //    NavHostFragment.findNavController(ExpenseFragment.this)
            //            .navigate(R.id.fragment_mtm); // Or your new destination
            //}
            Log.d("ExpenseFragment", "Clicked: " + item.toString() + " at position " + position);
            // Handle other item clicks here if needed
        });

        recyclerView.setAdapter(adapter);
        return view;
    }
}
