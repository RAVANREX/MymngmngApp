package com.example.mnymng.fragments;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mnymng.R;
import com.example.mnymng.adapter.GridAdapter;
import com.example.mnymng.model.MenuItem;


import java.util.ArrayList;
import java.util.List;

public class ExpenseFragment extends Fragment {


    private RecyclerView recyclerView;
    private GridAdapter adapter;
    private List<MenuItem> menuItems;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense, container, false);  // Create layout

        // Find the RecyclerView in the inflated layout
        recyclerView = view.findViewById(R.id.recycler_view);

        // Set up GridLayoutManager (e.g., 4 columns; adjust as needed)
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));

        // Prepare sample data (replace with your actual data source)
        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(R.drawable.ic_icon, "Home"));
        menuItems.add(new MenuItem(R.drawable.ic_icon, "Gallery"));
        menuItems.add(new MenuItem(R.drawable.ic_icon, "Settings"));
        menuItems.add(new MenuItem(R.drawable.ic_icon, "Logout"));
        // Add more items as needed

        // Create and set the adapter
        adapter = new GridAdapter(getContext(), menuItems);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
