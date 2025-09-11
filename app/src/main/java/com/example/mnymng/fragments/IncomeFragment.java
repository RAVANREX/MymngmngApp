package com.example.mnymng.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.mnymng.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class IncomeFragment extends Fragment {

    public IncomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_common, container, false);

        FloatingActionButton buttonOpenDrawer = view.findViewById(R.id.fab_add);
        buttonOpenDrawer.setOnClickListener(v -> {
            BottomDrawer bottomDrawerFragment = new BottomDrawer();
            CataListFragment.screenName = "INCOME";
            bottomDrawerFragment.show(getParentFragmentManager(), bottomDrawerFragment.getTag());
        });
        return view;
    }
}