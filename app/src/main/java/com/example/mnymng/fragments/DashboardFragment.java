package com.example.mnymng.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.mnymng.R;
//import com.example.mnymng.db.DBHelper;
// Add charts or views for analysis

public class DashboardFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);  // Create layout with charts
        //DBHelper dbHelper = new DBHelper(getContext());
        // Query data for dashboards, e.g., expenses by month
        // Use libraries like MPAndroidChart for visualizations (add dep if needed)
        return view;
    }
}
