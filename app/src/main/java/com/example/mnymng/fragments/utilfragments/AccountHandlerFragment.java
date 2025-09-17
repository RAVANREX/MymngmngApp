package com.example.mnymng.fragments.utilfragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mnymng.DB.enums.AccountType;
import com.example.mnymng.DB.models.Account;
import com.example.mnymng.R;
import com.example.mnymng.viewmodel.AccountViewModel; // Assuming you have an AccountViewModel
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.util.Date;

public class AccountHandlerFragment extends Fragment {

    private static final String TAG = "AccountHandler";

    private AccountViewModel accountViewModel;
    private AccountType accountType;
    private Account accountToAdd;

    private TextInputEditText accountNameEditText;
    private TextInputEditText accountOpeningBalanceEditText;

    private Account accountToEdit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // It's good practice to create the ViewModel in onCreate or onActivityCreated
        // Ensure AccountViewModel is correctly implemented and available.
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.account_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        accountNameEditText = view.findViewById(R.id.account_name_edit_text);
        accountOpeningBalanceEditText = view.findViewById(R.id.account_opening_balance_edit_text);


        if (accountToEdit != null) {
            if (accountNameEditText != null) {
                accountNameEditText.setText(accountToEdit.getAccount_name());
            }
            if (accountOpeningBalanceEditText != null && (accountToEdit.getAccount_type() == AccountType.CREDIT_CARD || accountToEdit.getAccount_type() == AccountType.LOAN ||
                    accountToEdit.getAccount_type() == AccountType.LENDING)) {
                // Assuming getOpeningBalance() or a similar method exists and returns a numeric type
                accountOpeningBalanceEditText.setText(String.valueOf(accountToEdit.getAccount_opening_balance()));
                //accountOpeningBalanceEditText.setEnabled(true); // Disable editing
            }else {
                accountOpeningBalanceEditText.setEnabled(false);
            }
        }
    }

    public Account getAccountData() {
        Account account = (accountToEdit == null) ? new Account() : accountToEdit;

        String accountName = accountNameEditText.getText().toString().trim();
        String openingBalanceStr = accountOpeningBalanceEditText.getText().toString().trim();

        if (TextUtils.isEmpty(accountName)) {
            accountNameEditText.setError("Account name is required.");
            Toast.makeText(getContext(), "Account name is required.", Toast.LENGTH_SHORT).show();
            return null;
        }
        account.setAccount_name(accountName);

        if (TextUtils.isEmpty(openingBalanceStr)) {
            accountOpeningBalanceEditText.setError("Opening balance is required.");
            Toast.makeText(getContext(), "Opening balance is required.", Toast.LENGTH_SHORT).show();
            return null;
        }

        try {
            double openingBalance = Double.parseDouble(openingBalanceStr);
            account.setAccount_opening_balance(openingBalance); // Assuming setOpeningBalance(double) exists
        } catch (NumberFormatException e) {
            accountOpeningBalanceEditText.setError("Invalid opening balance.");
            Toast.makeText(getContext(), "Invalid opening balance.", Toast.LENGTH_SHORT).show();
            return null;
        }

        account.setAccount_type(accountType); // Assuming accountType is set
        account.setAccount_updated_date(new Date());

        
        // You might want to set other Account properties here if needed.
        // For example, if AccountType is determined by some other UI element or logic:
        // account.setAccountType(selectedAccountType);

        return account;
    }
}
