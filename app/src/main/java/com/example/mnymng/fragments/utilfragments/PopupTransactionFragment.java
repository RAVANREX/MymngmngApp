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
import com.example.mnymng.DB.dao.TransactionDao;
import com.example.mnymng.DB.logic.TransactionChainingManager;
import com.example.mnymng.DB.models.Transaction;
import com.example.mnymng.R;
import com.example.mnymng.adapter.MyViewPagerAdapter;
import com.example.mnymng.DB.models.Category;
import com.google.android.material.appbar.MaterialToolbar;

import java.io.Serializable;
import java.util.concurrent.Executors;

public class PopupTransactionFragment extends DialogFragment {

    private Transaction transactionToAdd;
    private Transaction transactionToEdit;
    private TransactionDao transactionDao;
    private Category categoryContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup, container, false);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);

        transactionDao = AppDatabase.getDatabase(getContext()).transactionDao(); // Initialize TransactionDao

        if (getArguments() != null) {
            if (getArguments().containsKey("transactionAddCall")) {
                Serializable serializableTransaction = getArguments().getSerializable("transactionAddCall");
                if (serializableTransaction instanceof Category) {
                    categoryContext = (Category) serializableTransaction;
                }
            }
            if (getArguments().containsKey("transactionToEdit")) { // Check for transactionToEdit
                Serializable serializableTransaction = getArguments().getSerializable("transactionToEdit");
                if (serializableTransaction instanceof Transaction) {
                    transactionToEdit = (Transaction) serializableTransaction;
                    transactionToAdd = null; // Ensure only one mode is active
                }
            }
            // Category might still be relevant for picking a category for the transaction,
            // but not for "editing" a category itself in this fragment.
            // If a category context is needed for new/edited transactions, it can be passed and used.
            // categoryContext = null;
            if (getArguments().containsKey("categoryContext")) { // Example: passing category context
                 Serializable serializableCategory = getArguments().getSerializable("categoryContext");
                 if (serializableCategory instanceof Category) {
                     categoryContext = (Category) serializableCategory;
                 }
            }
        }

        Log.d("PopupTransactionFrag", "Transaction to Add: " + (transactionToAdd != null));
        Log.d("PopupTransactionFrag", "Transaction to Edit: " + (transactionToEdit != null));

        // Adapt MyViewPagerAdapter or ensure it can handle transactionToEdit.
        // For now, passing transactionToEdit (if available) or transactionToAdd.
        // The adapter needs to be aware if it's in an edit mode for transactions.
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(this, categoryContext, transactionToEdit != null ? transactionToEdit : transactionToAdd,null, null,false,true);
        viewPager.setAdapter(adapter);

        viewPager.post(() -> {
            Log.d("PopupTransactionFrag", "ViewPager2 height: " + viewPager.getMeasuredHeight());
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button nextButton = view.findViewById(R.id.nextButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);
        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);

        if (transactionToEdit != null) {
            Log.d("PopupTransactionFrag", "Editing transaction: " + transactionToEdit);
            toolbar.setTitle("Edit Transaction");
        }else{
            toolbar.setTitle("Add Transaction");
        }

        nextButton.setOnClickListener(v -> {
            ViewPager2 viewPager = requireView().findViewById(R.id.viewPager);
            if (viewPager.getAdapter() == null) {
                return;
            }
            int currentItem = viewPager.getCurrentItem();
            int itemCount = viewPager.getAdapter().getItemCount();

            TransactionHandelerFragment transactionHandelerFragment = (TransactionHandelerFragment) getChildFragmentManager().getFragments().stream()
                    .filter(fragment -> fragment instanceof TransactionHandelerFragment).findFirst().orElse(null);

            if (currentItem < itemCount - 1) {
                viewPager.setCurrentItem(currentItem + 1);
                if (viewPager.getCurrentItem() == itemCount - 1) {
                    nextButton.setText(transactionToEdit != null ? "Save" : "Submit");
                } else {
                    nextButton.setText("Next");
                }
            } else {
                if (transactionHandelerFragment != null) {
                    if (transactionToEdit != null) {
                        Log.d("PopupTransactionFrag", "Saving edited transaction: " + transactionToEdit);
                        // Populate transactionToEdit with data from transactionHandelerFragment
                        // Example:
                        // transactionToEdit.setDescription(transactionHandelerFragment.getDescription());
                        // transactionToEdit.setAmount(transactionHandelerFragment.getAmount());
                        // transactionToEdit.setDate(transactionHandelerFragment.getDate());
                        // ... and so on for other fields ...
                        // TODO: Implement actual data retrieval from TransactionHandelerFragment

                        // Executors.newSingleThreadExecutor().execute(() -> transactionDao.update(transactionToEdit));
                        if (transactionHandelerFragment != null) {
//                            transactionToEdit.setTrns_amount( transactionHandelerFragment.getTransactionData().getTrns_amount());
//                            transactionToEdit.setTrns_note(transactionHandelerFragment.getTransactionData().getTrns_note());
//                            transactionToEdit.setTrns_date(transactionHandelerFragment.getTransactionData().getTrns_date());
                            if (transactionToEdit != null) {
                                Executors.newSingleThreadExecutor().execute(() -> {
                                    TransactionChainingManager.getInstance(getContext()).updateTransaction(transactionHandelerFragment.getTransactionData(),transactionToEdit);
                                });
                            } else {
                                Toast.makeText(getContext(), "Transaction data is null", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Log.d("PopupTransactionFrag", "Submitting new transaction");
                        // Create a new Transaction object and populate it
                        // Example:
                        // Transaction newTransaction = new Transaction(...);
                        // newTransaction.setDescription(transactionHandelerFragment.getDescription());
                        // ...
                        // TODO: Implement actual data retrieval and object creation
                        //Toast.makeText(getContext(), "Submit new transaction logic here", Toast.LENGTH_SHORT).show();
                        if (transactionHandelerFragment != null) {
                            Transaction newTransaction =transactionHandelerFragment.getTransactionData();
                            if (newTransaction == null) {
                                Toast.makeText(getContext(), "Transaction data is null", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Executors.newSingleThreadExecutor().execute(() -> {
                                TransactionChainingManager.getInstance(getContext()).createTransaction(newTransaction);
                        });
                        }
                        // Executors.newSingleThreadExecutor().execute(() -> transactionDao.insert(newTransaction));
                    }
                } else {
                    Log.e("PopupTransactionFrag", "TransactionHandelerFragment instance not found!");
                    Toast.makeText(getContext(), "Error: Could not retrieve form data.", Toast.LENGTH_SHORT).show();
                    return; // Return early if fragment not found
                }
                dismiss();
            }
        });

        if(transactionToEdit != null ){
            cancelButton.setText("Delete");
            cancelButton.setBackgroundColor(Color.RED);
            nextButton.setText("Save"); // Set save if on last page initially or only one page
        } else {
            cancelButton.setText("Cancel");
            nextButton.setText("Submit"); // Set submit if on last page initially or only one page
        }

        cancelButton.setOnClickListener(v -> {
            if (transactionToEdit != null && cancelButton.getText().toString().equalsIgnoreCase("Delete")) {
                Log.d("PopupTransactionFrag", "Deleting transaction: " + transactionToEdit);
                Executors.newSingleThreadExecutor().execute(() -> {
                    TransactionChainingManager.getInstance(getContext()).deleteTransaction(transactionToEdit);
                });
                Toast.makeText(getContext(), "Transaction deleted", Toast.LENGTH_SHORT).show();
                dismiss();
            } else {
                dismiss(); // Standard cancel
            }
        });

        ViewPager2 viewPager = requireView().findViewById(R.id.viewPager);
        if (viewPager.getAdapter() != null && viewPager.getCurrentItem() == viewPager.getAdapter().getItemCount() - 1) {
            nextButton.setText(transactionToEdit != null ? "Save" : "Submit");
        } else if (viewPager.getAdapter() != null) { // if not on the last page
            nextButton.setText("Next");
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