package com.example.mnymng.adapter;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mnymng.DB.models.Category; // Import Category
import com.example.mnymng.fragments.PageOneFragment;
import com.example.mnymng.fragments.PageTwoFragment;
import com.example.mnymng.fragments.PopupViewFragment;

import java.io.Serializable; // Import Serializable if Category is Serializable

public class MyViewPagerAdapter extends FragmentStateAdapter {

    private static final int NUM_PAGES_ADD_MODE = 2;
    private final Category categoryForEditing; // Category object for edit mode

    public MyViewPagerAdapter(@NonNull PopupViewFragment fragmentActivity, @Nullable Category categoryForEditing) {
        super(fragmentActivity);
        this.categoryForEditing = categoryForEditing;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (categoryForEditing != null) {
            // Edit mode: Only PageTwoFragment is shown
            PageTwoFragment pageTwoFragment = new PageTwoFragment();
            Bundle args = new Bundle();
            // Make sure Category is Serializable or Parcelable
            args.putSerializable("categoryDataToEdit", categoryForEditing); 
            pageTwoFragment.setArguments(args);
            return pageTwoFragment;
        } else {
            // Add mode: Standard two-page setup
            switch (position) {
                case 0:
                    return new PageOneFragment();
                case 1:
                    return new PageTwoFragment(); // This will be a blank PageTwoFragment
                default:
                    return new PageOneFragment(); // Should not happen
            }
        }
    }

    @Override
    public int getItemCount() {
        if (categoryForEditing != null) {
            return 1; // Edit mode: only one page (PageTwoFragment)
        } else {
            return NUM_PAGES_ADD_MODE; // Add mode: two pages
        }
    }
}
