package com.example.mnymng.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log; // Added import
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mnymng.DB.AppDatabase;
import com.example.mnymng.DB.dao.CategoryDao;
import com.example.mnymng.R;
import com.example.mnymng.adapter.MyViewPagerAdapter;
import com.example.mnymng.DB.models.Category; // Added import for Category

import java.io.Serializable; // Added import for Serializable
import java.util.concurrent.Executors;

public class PopupViewFragment extends DialogFragment {

    private Category categoryToEdit; // Field to store category for editing

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup, container, false);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);

        // Check for category to edit from arguments (moved here to be available for adapter)
        if (getArguments() != null && getArguments().containsKey("categoryToEdit")) {
            Serializable serializableCategory = getArguments().getSerializable("categoryToEdit");
            if (serializableCategory instanceof Category) {
                categoryToEdit = (Category) serializableCategory;
            }
        }

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(this, categoryToEdit); // Pass categoryToEdit
        viewPager.setAdapter(adapter);

        viewPager.post(() -> {
            Log.d("PopupViewFragment", "ViewPager2 height: " + viewPager.getMeasuredHeight());
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button nextButton = view.findViewById(R.id.nextButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);

        // categoryToEdit is already populated in onCreateView if arguments are present
        if (categoryToEdit != null) {
            Log.d("PopupViewFragment", "Editing category: " + categoryToEdit.getCata_name());
        }

        nextButton.setOnClickListener(v -> {
            ViewPager2 viewPager = requireView().findViewById(R.id.viewPager);
            if (viewPager.getAdapter() == null) {
                return; // Adapter not set, do nothing
            }
            int currentItem = viewPager.getCurrentItem();
            int itemCount = viewPager.getAdapter().getItemCount();

            PageTwoFragment pageTwoFragment = null;
            // ... (code to find PageOneFragment if in add mode) ...

            // Loop through all fragments managed by PopupViewFragment's ChildFragmentManager
            for (Fragment fragment : getChildFragmentManager().getFragments()) {
                if (fragment instanceof PageTwoFragment) {
                    pageTwoFragment = (PageTwoFragment) fragment;
                    break; // Found it
                }
            }


            if (currentItem < itemCount - 1) { // If not the last page
                viewPager.setCurrentItem(currentItem + 1);
                // Check if the new current page is the last page
                if (viewPager.getCurrentItem() == itemCount - 1) {
                    nextButton.setText(categoryToEdit != null ? "Save" : "Submit"); // Change text based on mode
                } else {
                    nextButton.setText("Next"); // Ensure it says "Next" on intermediate pages
                }
            } else { // currentItem is the last page
                // Action for "Submit" or "Save" button click
                if (categoryToEdit != null) {
                    // Handle SAVE action for editing
                    Log.d("PopupViewFragment", "Saving edited category: " + categoryToEdit.getCata_name());
                    // TODO: Implement actual save logic (e.g., update database)
                    if (pageTwoFragment != null) {
                        // Now we have the CURRENT INSTANCE of PageTwoFragment
                        String emoji = pageTwoFragment.getSelectedEmoji(); // Calling instance method
                        String name = pageTwoFragment.getCategoryName(); // Calling instance method
                        String desc = pageTwoFragment.getdescription();      // Calling instance method
                        // ... and so on
                        categoryToEdit.setCata_icon(emoji);
                        categoryToEdit.setCata_name(name);
                        categoryToEdit.setCata_desc(desc);
                        // ... and so on
                        CategoryDao categoryDao = AppDatabase.getDatabase(getContext()).categoryDao();
                        Executors.newSingleThreadExecutor().execute(() -> {
                                    categoryDao.update(categoryToEdit);
                                });
                        // Proceed with database operations using this retrieved data
                    } else {
                        // Handle case where PageTwoFragment instance wasn't found (should not happen if ViewPager is working)
                        Log.e("PopupViewFragment", "PageTwoFragment instance not found!");
                        Toast.makeText(getContext(), "Error: Could not retrieve form data.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // You'll need to collect data from PageOneFragment and PageTwoFragment
                } else {
                    // Handle SUBMIT action for adding
                    Log.d("PopupViewFragment", "Submitting new category");
                    // TODO: Implement actual submit logic (e.g., insert into database)
                    // You'll need to collect data from PageOneFragment and PageTwoFragment
                }
                dismiss();
            }
        });

        cancelButton.setOnClickListener(v -> {
            // Handle Cancel button click
            dismiss();
        });

        // Set initial button text for the last page if already on it
        ViewPager2 viewPager = requireView().findViewById(R.id.viewPager);
        if (viewPager.getAdapter() != null && viewPager.getCurrentItem() == viewPager.getAdapter().getItemCount() - 1) {
            nextButton.setText(categoryToEdit != null ? "Save" : "Submit");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            // Make the dialog 80% of screen height and match parent width
            int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.80);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, height);
        }
    }
}