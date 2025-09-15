package com.example.mnymng.fragments.utilfragments;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
// import com.example.mnymng.DB.models.Transaction; // Removed as transaction logic is moved
import com.example.mnymng.R;
import com.example.mnymng.adapter.MyViewPagerAdapter;
import com.example.mnymng.DB.models.Category;

import java.io.Serializable;
import java.util.concurrent.Executors;

public class PopupViewFragment extends DialogFragment {

    private Category categoryToEdit; // Field to store category for editing
    // private Transaction transactionToAdd; // Removed
    // private boolean isTransaction = false; // Removed

    private final CategoryDao categoryDao = AppDatabase.getDatabase(getContext()).categoryDao();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup, container, false);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);

        if (getArguments() != null && getArguments().containsKey("categoryToEdit")) {
            Serializable serializableCategory = getArguments().getSerializable("categoryToEdit");
            if (serializableCategory instanceof Category) {
                categoryToEdit = (Category) serializableCategory;
            }
        }
        // Removed transaction argument handling

        // The 'isTransaction' flag will be false, and transactionToAdd will be null for this fragment.
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(this, categoryToEdit,null, null, false, false);
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

        if (categoryToEdit != null) {
            Log.d("PopupViewFragment", "Editing category: " + categoryToEdit.getCata_name());
        }

        nextButton.setOnClickListener(v -> {
            ViewPager2 viewPager = requireView().findViewById(R.id.viewPager);
            if (viewPager.getAdapter() == null) {
                return;
            }
            int currentItem = viewPager.getCurrentItem();
            int itemCount = viewPager.getAdapter().getItemCount();

            PageTwoFragment pageTwoFragment = null;
            // Removed TransactionHandelerFragment related code

            for (Fragment fragment : getChildFragmentManager().getFragments()) {
                if (fragment instanceof PageTwoFragment) {
                    pageTwoFragment = (PageTwoFragment) fragment;
                    break; 
                }
            }

            if (currentItem < itemCount - 1) {
                viewPager.setCurrentItem(currentItem + 1);
                if (viewPager.getCurrentItem() == itemCount - 1) {
                    nextButton.setText(categoryToEdit != null ? "Save" : "Submit");
                } else {
                    nextButton.setText("Next");
                }
            } else {
                if (categoryToEdit != null) {
                    Log.d("PopupViewFragment", "Saving edited category: " + categoryToEdit.getCata_name());
                    if (pageTwoFragment != null) {
                        categoryToEdit.setCata_icon(pageTwoFragment.getSelectedEmoji());
                        categoryToEdit.setCata_name(pageTwoFragment.getCategoryName()); // Corrected to getCategoryName
                        categoryToEdit.setCata_desc(pageTwoFragment.getdescription());

                        Executors.newSingleThreadExecutor().execute(() -> {
                            AppDatabase.getDatabase(getContext()).performDaoAction(categoryDao, dao -> dao.update(categoryToEdit));
                        });
                    } else {
                        Log.e("PopupViewFragment", "PageTwoFragment instance not found!");
                        Toast.makeText(getContext(), "Error: Could not retrieve form data.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Log.d("PopupViewFragment", "Submitting new category");
                    if (pageTwoFragment != null) {
                        Category category = new Category(pageTwoFragment.getCategoryName(), CataListFragment.screenName, pageTwoFragment.getSelectedEmoji(), pageTwoFragment.getdescription(),
                                null,null,null,null,null);
                        Executors.newSingleThreadExecutor().execute(() -> {
                             AppDatabase.getDatabase(getContext()).performDaoAction(categoryDao, dao -> dao.insert(category));
                        });
                    } else {
                        Log.e("PopupViewFragment", "PageTwoFragment instance not found!");
                         Toast.makeText(getContext(), "Error: Could not retrieve form data.", Toast.LENGTH_SHORT).show(); // Added Toast for user feedback
                    }
                }
                dismiss();
            }
        });

        if(categoryToEdit != null ){
            cancelButton.setText("Delete");
            cancelButton.setBackgroundColor(Color.RED);
        }else {
            cancelButton.setText("Cancel");
        }
        cancelButton.setOnClickListener(v -> {
            if (categoryToEdit != null && cancelButton.getText().equals("Delete")) {
                 // Consider finding PageTwoFragment only if absolutely necessary for delete confirmation, otherwise direct delete is fine.
                Executors.newSingleThreadExecutor().execute(() -> {
                    AppDatabase.getDatabase(getContext()).performDaoAction(categoryDao, dao -> dao.delete(categoryToEdit));
                });
                dismiss();
            } else {
                dismiss(); // Standard cancel
            }
        });

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
            int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.80);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, height);
        }
    }
}
