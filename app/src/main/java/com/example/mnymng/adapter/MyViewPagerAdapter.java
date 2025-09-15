package com.example.mnymng.adapter;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mnymng.DB.models.Account;
import com.example.mnymng.DB.models.Category; // Import Category
import com.example.mnymng.DB.models.Transaction;
import com.example.mnymng.fragments.utilfragments.AccountHandlerFragment; // Added import
import com.example.mnymng.fragments.utilfragments.PageOneFragment;
import com.example.mnymng.fragments.utilfragments.PageTwoFragment;
// import com.example.mnymng.fragments.utilfragments.PopupViewFragment; // No longer specific to PopupViewFragment
import com.example.mnymng.fragments.utilfragments.TransactionHandelerFragment;

import java.io.Serializable; // Import Serializable if Category is Serializable

public class MyViewPagerAdapter extends FragmentStateAdapter {

    private static final int NUM_PAGES_CATEGORY_ADD_MODE = 2;
    private static final int NUM_PAGES_TRANSACTION_MODE = 1;
    private static final int NUM_PAGES_ACCOUNT_MODE = 1; // Added for Account mode

    private final Category category; // Category object for category edit mode
    private final Transaction transaction; // Transaction object for transaction add mode
    private final Account account; // Account object for account add/edit mode
    private final boolean isTransactionMode;
    private final boolean isAccountMode; // Added for Account mode

    // Constructor updated to accept a generic Fragment
    public MyViewPagerAdapter(@NonNull Fragment fragment, @Nullable Category category, @Nullable Transaction transaction, @Nullable Account account, boolean isAccountMode, boolean isTransactionMode) {
        super(fragment);
        this.category = category;
        this.transaction = transaction;
        this.account = account; // Store the account
        this.isAccountMode = isAccountMode; // Store isAccountMode
        this.isTransactionMode = isTransactionMode;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (isTransactionMode) {
            // Transaction mode: Only TransactionHandelerFragment is shown
            TransactionHandelerFragment transactionHandelerFragment = new TransactionHandelerFragment();
            Bundle args = new Bundle();
            if (transaction != null) { // Pass transaction data if available (e.g., for pre-filling)
                args.putSerializable("transactionDataToEdit", transaction);
            }
            // If you need to pass the category context to TransactionHandelerFragment as well
            if (category != null) {
                args.putSerializable("categoryContext", category);
            }
            transactionHandelerFragment.setArguments(args);
            return transactionHandelerFragment;
        } else if (isAccountMode) { // New block for Account Mode
            AccountHandlerFragment accountHandlerFragment = new AccountHandlerFragment();
            Bundle args = new Bundle();
            if (this.account != null) { // Pass account data if available (e.g., for editing)
                args.putSerializable("accountToEdit", this.account);
            }
            accountHandlerFragment.setArguments(args);
            return accountHandlerFragment;
        } else if (category != null) {
            // Category Edit mode: Only PageTwoFragment is shown
            PageTwoFragment pageTwoFragment = new PageTwoFragment();
            Bundle args = new Bundle();
            args.putSerializable("categoryDataToEdit", category);
            pageTwoFragment.setArguments(args);
            return pageTwoFragment;
        } else {
            // Category Add mode: Standard two-page setup
            switch (position) {
                case 0:
                    return new PageOneFragment();
                case 1:
                    // Pass categoryForEditing which will be null in add mode, handled by PageTwoFragment
                    PageTwoFragment pageTwoFragment = new PageTwoFragment();
                    Bundle args = new Bundle();
                    args.putSerializable("categoryDataToEdit", category); // This will be null for add mode
                    pageTwoFragment.setArguments(args);
                    return pageTwoFragment;
                default:
                    return new PageOneFragment(); // Should not happen
            }
        }
    }

    @Override
    public int getItemCount() {
        if (isTransactionMode) {
            return NUM_PAGES_TRANSACTION_MODE; // Transaction mode: one page
        } else if (isAccountMode) { // New block for Account Mode
            return NUM_PAGES_ACCOUNT_MODE; // Account mode: one page
        } else if (category != null) {
            return 1; // Category Edit mode: only one page (PageTwoFragment)
        } else {
            return NUM_PAGES_CATEGORY_ADD_MODE; // Category Add mode: two pages
        }
    }
}
