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
import com.example.mnymng.DB.dao.AccountDao;
import com.example.mnymng.DB.enums.AccountType;
import com.example.mnymng.DB.logic.TransactionChainingManager;
import com.example.mnymng.DB.models.Account;
import com.example.mnymng.R;
import com.example.mnymng.adapter.MyViewPagerAdapter; // This adapter might need to be changed or made generic
import com.google.android.material.appbar.MaterialToolbar;
// import com.example.mnymng.DB.models.Category; // Category might not be relevant for Account

import java.io.Serializable;
import java.util.concurrent.Executors;

public class PopupAccountFragment extends DialogFragment {

    private Account accountToAdd;
    private Account accountToEdit; // Field to store account for editing
    public static AccountType accountType;

    private AccountDao accountDao; // Added AccountDao
    // private Category categoryContext; // Category context might not be needed for Account

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup, container, false); // Assuming R.layout.popup can be reused
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);

        accountDao = AppDatabase.getDatabase(getContext()).accountDao(); // Initialize AccountDao

        if (getArguments() != null) {
            if (getArguments().containsKey("accountAddCall")) { // Changed from transactionAddCall
                // Logic for adding an account if needed, adjust type accordingly
                 Serializable serializableAccount = getArguments().getSerializable("accountAddCall");
                 if (serializableAccount instanceof AccountType) {
                     // ...
                     accountType = (AccountType) serializableAccount;
                     accountToEdit = null; // Ensure only one mode is active
                 }
            }
            if (getArguments().containsKey("accountToEdit")) { // Check for accountToEdit
                Serializable serializableAccount = getArguments().getSerializable("accountToEdit");
                if (serializableAccount instanceof Account) {
                    accountToEdit = (Account) serializableAccount;
                    accountToAdd = null; // Ensure only one mode is active
                }
            }
        }

        Log.d("PopupAccountFrag", "Account to Add: " + (accountToAdd != null));
        Log.d("PopupAccountFrag", "Account to Edit: " + (accountToEdit != null));

        // Adapt MyViewPagerAdapter or ensure it can handle accountToEdit.
        // This likely needs a new adapter or a generic one.
        // For now, passing accountToEdit (if available) or accountToAdd.
        // The adapter needs to be aware if it's in an edit mode for accounts.
         MyViewPagerAdapter adapter = new MyViewPagerAdapter(this, null, null,accountToEdit != null ? accountToEdit : accountToAdd,accountType, true,false); // Pass null for categoryContext or remove
         viewPager.setAdapter(adapter); // This will need an appropriate adapter for Accounts

        viewPager.post(() -> {
            Log.d("PopupAccountFrag", "ViewPager2 height: " + viewPager.getMeasuredHeight());
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button nextButton = view.findViewById(R.id.nextButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);
        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);

        if (accountToEdit != null) {
            Log.d("PopupAccountFrag", "Editing account: " + accountToEdit);
            toolbar.setTitle("Edit Account");
        }else{
            toolbar.setTitle("Add Account");
        }

        nextButton.setOnClickListener(v -> {
            ViewPager2 viewPager = requireView().findViewById(R.id.viewPager);
            if (viewPager.getAdapter() == null) {
                // Potentially show a message or log if adapter is not set, as operations depend on it
                Log.e("PopupAccountFrag", "ViewPager adapter is null!");
                Toast.makeText(getContext(), "Error: Component not fully initialized.", Toast.LENGTH_SHORT).show();
                return;
            }
            int currentItem = viewPager.getCurrentItem();
            int itemCount = viewPager.getAdapter().getItemCount();

            // This needs to be an AccountHandlerFragment or similar
            // TransactionHandelerFragment transactionHandelerFragment = null;
            Fragment handlerFragment = (AccountHandlerFragment) getChildFragmentManager().getFragments().stream().filter(fragment -> fragment instanceof AccountHandlerFragment).findFirst().orElse(null); // Placeholder for a generic or Account specific handler
            // TODO: Replace TransactionHandelerFragment with the appropriate fragment for accounts

            if (currentItem < itemCount - 1) {
                viewPager.setCurrentItem(currentItem + 1);
                if (viewPager.getCurrentItem() == itemCount - 1) {
                    nextButton.setText(accountToEdit != null ? "Save" : "Submit");
                } else {
                    nextButton.setText("Next");
                }
            } else {
                // TODO: Replace TransactionHandelerFragment with the appropriate fragment for accounts
                // if (handlerFragment instanceof AccountHandlerFragment) {
                //    AccountHandlerFragment accountHandlerFragment = (AccountHandlerFragment) handlerFragment;
                    if (accountToEdit != null) {
                        Log.d("PopupAccountFrag", "Saving edited account: " + accountToEdit);
                        // Populate accountToEdit with data from accountHandlerFragment
                        // Example:
                        // accountToEdit.setName(accountHandlerFragment.getAccountName());
                        // accountToEdit.setBalance(accountHandlerFragment.getBalance());
                        // TODO: Implement actual data retrieval from AccountHandlerFragment

                        // if (accountHandlerFragment != null) { // Replace with actual fragment
                            // accountToEdit.setSomeField(accountHandlerFragment.getAccountData().getSomeField());
                            if (accountToEdit != null) { // Redundant check, but safe
                                Executors.newSingleThreadExecutor().execute(() -> {
                                    // AppDatabase.getDatabase(getContext()).performDaoAction(accountDao, dao -> dao.update(accountToEdit));
                                    accountDao.update(((AccountHandlerFragment) handlerFragment).getAccountData()); // Direct DAO call
                                });
                                Toast.makeText(getContext(), "Account updated", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Account data is null", Toast.LENGTH_SHORT).show();
                            }
                        // }
                    } else {
                        Log.d("PopupAccountFrag", "Submitting new account");
                        // TODO: Ensure 'AccountHandlerFragment' or similar is correctly implemented
                        // and 'newAccount' is populated with data from the fragment.
                        // For example:
                        // AccountHandlerFragment accountHandlerFragment = (AccountHandlerFragment) handlerFragment;
                         if (handlerFragment != null) {
                             Account newAccount =  ((AccountHandlerFragment) handlerFragment).getAccountData(); // This needs to be correctly implemented
                             if (newAccount == null) {
                                 Toast.makeText(getContext(), "Account data is null", Toast.LENGTH_SHORT).show();
                                 return;
                             }
                             // newAccount.type = accountType; // Ensure accountType is set
                             Executors.newSingleThreadExecutor().execute(() -> {
                                 accountDao.insert(newAccount); // Direct DAO call
                             });
                             Toast.makeText(getContext(), "Account added", Toast.LENGTH_SHORT).show();
                         } else {
                             Log.e("PopupAccountFrag", "Account handler fragment not found or data retrieval failed.");
                             Toast.makeText(getContext(), "Error: Could not retrieve form data.", Toast.LENGTH_SHORT).show();
                         }
                    }
                // } else {
                //    Log.e("PopupAccountFrag", "AccountHandlerFragment instance not found!");
                //    Toast.makeText(getContext(), "Error: Could not retrieve form data.", Toast.LENGTH_SHORT).show();
                //    return; // Return early if fragment not found
                // }
                dismiss();
            }
        });

        if(accountToEdit != null ){
            cancelButton.setText("Delete");
            cancelButton.setBackgroundColor(Color.RED);
            nextButton.setText("Save"); // Set save if on last page initially or only one page
        } else {
            cancelButton.setText("Cancel");
            // Set Submit only if on the last page or only one page initially
            // ViewPager2 viewPagerForButtonText = requireView().findViewById(R.id.viewPager);
            // if (viewPagerForButtonText.getAdapter() != null && viewPagerForButtonText.getAdapter().getItemCount() <=1){
                 nextButton.setText("Submit");
            // } else {
            //    nextButton.setText("Next");
            // }
        }

        cancelButton.setOnClickListener(v -> {
            if (accountToEdit != null && cancelButton.getText().toString().equalsIgnoreCase("Delete")) {
                Log.d("PopupAccountFrag", "Deleting account: " + accountToEdit);
                Executors.newSingleThreadExecutor().execute(() -> {
                    TransactionChainingManager.getInstance(getContext()).deleteAccountAndTransactions(accountToEdit);
                });
                Toast.makeText(getContext(), "Account deleted", Toast.LENGTH_SHORT).show();
                dismiss();
            } else {
                dismiss(); // Standard cancel
            }
        });

        ViewPager2 viewPagerForButton = requireView().findViewById(R.id.viewPager);
        if (viewPagerForButton.getAdapter() != null && viewPagerForButton.getCurrentItem() == viewPagerForButton.getAdapter().getItemCount() - 1) {
            nextButton.setText(accountToEdit != null ? "Save" : "Submit");
        } else if (viewPagerForButton.getAdapter() != null) { // if not on the last page
            nextButton.setText("Next");
        } else { // No adapter, or adapter has no items
             nextButton.setText(accountToEdit != null ? "Save" : "Submit");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.80); // Or a more suitable height
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, height);
        }
    }
}
