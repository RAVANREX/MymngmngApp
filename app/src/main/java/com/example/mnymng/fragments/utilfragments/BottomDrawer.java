package com.example.mnymng.fragments.utilfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mnymng.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomDrawer extends BottomSheetDialogFragment {

    private int currentIndex = 0;
    private Fragment[] fragments = {new CataListFragment(), new CataListFragment(), new CataListFragment()};

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_drawer, container, false);

//        Button btnNext = view.findViewById(R.id.btnNext);
//        Button btnCancel = view.findViewById(R.id.btnCancel);

        // Load first fragment
        loadFragment(fragments[currentIndex]);

//        btnNext.setOnClickListener(v -> {
//            if (currentIndex < fragments.length - 1) {
//                currentIndex++;
//                loadFragment(fragments[currentIndex]);
//            } else {
//                // Optionally, you could loop back to the first fragment
//                // currentIndex = 0;
//                // loadFragment(fragments[currentIndex]);
//                dismiss(); // Or close drawer when done with all fragments
//            }
//        });
//
//        btnCancel.setOnClickListener(v -> {
//            dismiss(); // Close the bottom sheet
//        });

        return view;
    }

    private void loadFragment(Fragment fragment) {
        if (fragment != null && isAdded()) {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.bottom_fragment_container, fragment)
                    .commit();
        }
    }
}
