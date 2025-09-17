package com.example.mnymng.fragments.utilfragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mnymng.DB.enums.AccountType;
import com.example.mnymng.DB.enums.CategoryType;
import com.example.mnymng.DB.models.Account; // Assuming Account entity path
import com.example.mnymng.DB.models.Category; // Assuming Category entity path
import com.example.mnymng.DB.models.Transaction; // Assuming Transaction entity path
import com.example.mnymng.DB.enums.TransactionType;
import com.example.mnymng.R;
import com.example.mnymng.fragments.TransactionTypeFragment;
import com.example.mnymng.viewmodel.TransactionViewModel; // Assuming ViewModel path

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class TransactionHandelerFragment extends Fragment {

    private static final String TAG = "TransactionHandeler";

    private TransactionViewModel transactionViewModel;

    // Existing UI Elements
    private Spinner accountTypeSpinner;
    private Spinner accountSpinner;
    private ArrayAdapter<String> accountSpinnerAdapter;
    private EditText dateEditText;
    private CheckBox recurringCheckBox;
    private LinearLayout recurringTransactionForm;

    // New UI Elements (Must be added to R.layout.transaction_fragmnet)
//    private EditText transactionNameEditText;
//    private Spinner transactionTypeSpinner; // For TransactionType (CREDIT, DEBIT, TRANSFER)
    private EditText transactionAmountEditText;
//    private Spinner categorySpinner; // For cata_id
    private EditText transactionNoteEditText;
//    private Spinner relatedAccountSpinner; // For related_account_id (visible for transfers)
    private Transaction transactionToEdit;
    private Category categoryContext; // Added category context

    // Data holders for Spinners
    private List<Account> currentAccountsList = new ArrayList<>();
    private List<Category> currentCategoriesList = new ArrayList<>(); // Assuming you'll populate this
    // private List<Account> currentRelatedAccountsList = new ArrayList<>(); // For relatedAccountSpinner

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        if (getArguments() != null && getArguments().containsKey("transactionDataToEdit")) {
            Serializable serializableTransaction = getArguments().getSerializable("transactionDataToEdit");
            if (serializableTransaction instanceof Transaction) {
                transactionToEdit = (Transaction) serializableTransaction;
            }
        }
        if (getArguments() != null && getArguments().containsKey("categoryContext")) {
            Serializable serializableTransaction = getArguments().getSerializable("categoryContext");
            if (serializableTransaction instanceof Category) {
                categoryContext = (Category) serializableTransaction;
            }
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Ensure R.layout.transaction_fragmnet has all the new EditTexts and Spinners
        View view = inflater.inflate(R.layout.transaction_fragmnet, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize existing UI Elements
        recurringCheckBox = view.findViewById(R.id.recurringcheckBox);
        recurringTransactionForm = view.findViewById(R.id.recurringTransactionForm);
        dateEditText = view.findViewById(R.id.dateEditText);
        accountTypeSpinner = view.findViewById(R.id.accountTypeSpinner);
        accountSpinner = view.findViewById(R.id.accountSpinner);

        // Initialize new UI Elements (IDs are examples, replace with your actual IDs from XML)
//        transactionNameEditText = view.findViewById(R.id.transactionNameEditText);
//        transactionTypeSpinner = view.findViewById(R.id.transactionTypeSpinner);
        transactionAmountEditText = view.findViewById(R.id.amountEditText);
//        categorySpinner = view.findViewById(R.id.categorySpinner);
        transactionNoteEditText = view.findViewById(R.id.noteEditText);
//        relatedAccountSpinner = view.findViewById(R.id.relatedAccountSpinner);

        // Initially hide relatedAccountSpinner, show it if transaction type is TRANSFER
        //if (relatedAccountSpinner != null) relatedAccountSpinner.setVisibility(View.GONE);

        if (transactionToEdit != null) {
            if(transactionAmountEditText != null ){
                transactionAmountEditText.setText(String.valueOf(transactionToEdit.getTrns_amount()));
            }
            if(transactionNoteEditText != null ){
                transactionNoteEditText.setText(transactionToEdit.getTrns_note());
            }
            if(dateEditText != null ){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                dateEditText.setText(sdf.format(transactionToEdit.getTrns_date()));
            }
            if(recurringCheckBox != null ){
                recurringCheckBox.setChecked(transactionToEdit.getRecurring_id() != null);
            }
            accountTypeSpinner.setVisibility(View.GONE);
            accountSpinner.setVisibility(View.GONE);
        }


        recurringCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            recurringTransactionForm.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        dateEditText.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (datePicker, selectedYear, selectedMonth, selectedDay) -> {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(selectedYear, selectedMonth, selectedDay);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        dateEditText.setText(sdf.format(selectedDate.getTime()));
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        // Populate accountTypeSpinner
        ArrayAdapter<AccountType> accountTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, AccountType.values());
        accountTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeSpinner.setAdapter(accountTypeAdapter);

        // Initialize accountSpinner adapter
        accountSpinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new ArrayList<>());
        accountSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountSpinner.setAdapter(accountSpinnerAdapter);

        accountTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedItemView, int position, long id) {
                AccountType selectedAccountType = (AccountType) parent.getItemAtPosition(position);
                if (selectedAccountType != null && transactionViewModel != null) {
                    transactionViewModel.getAccountsByType(selectedAccountType).observe(getViewLifecycleOwner(), accounts -> {
                        if (accounts != null) {
                            currentAccountsList.clear();
                            currentAccountsList.addAll(accounts);
                            List<String> accountNames = accounts.stream().map(Account::getAccount_name).collect(Collectors.toList());
                            accountSpinnerAdapter.clear();
                            accountSpinnerAdapter.addAll(accountNames);
                            accountSpinnerAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                currentAccountsList.clear();
                accountSpinnerAdapter.clear();
                accountSpinnerAdapter.notifyDataSetChanged();
            }
        });

        // Populate transactionTypeSpinner (Example: needs TransactionType enum)
//        if (transactionTypeSpinner != null) {
//            ArrayAdapter<TransactionType> transactionTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, TransactionType.values());
//            transactionTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            transactionTypeSpinner.setAdapter(transactionTypeAdapter);

            // Add logic to show/hide relatedAccountSpinner based on selection
//            transactionTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    TransactionType selectedType = (TransactionType) parent.getItemAtPosition(position);
//                    if (relatedAccountSpinner != null) {
//                        relatedAccountSpinner.setVisibility(selectedType == TransactionType.TRANSFER ? View.VISIBLE : View.GONE);
//                    }
//                }
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//                     if (relatedAccountSpinner != null) relatedAccountSpinner.setVisibility(View.GONE);
//                }
//            });
//        }

        // TODO: Populate categorySpinner (similar to accountSpinner, using transactionViewModel to get categories)
        // Example:
        // transactionViewModel.getAllCategories().observe(getViewLifecycleOwner(), categories -> { ... });
        // Remember to populate currentCategoriesList as well.

        // TODO: Populate relatedAccountSpinner if needed (e.g., with all accounts, excluding the main selected account)
    }


    public Transaction getTransactionData() {
        Transaction transaction = transactionToEdit == null ? new Transaction(): transactionToEdit;
        //transactionNameEditText.setText(categoryContext.getCata_name());

        // Only On new Transaction
        if( transactionToEdit == null) {
            // Account ID
            if (accountSpinner != null && accountSpinner.getSelectedItemPosition() != AdapterView.INVALID_POSITION && !currentAccountsList.isEmpty()) {
                Account selectedAccount = currentAccountsList.get(accountSpinner.getSelectedItemPosition());
                transaction.setAccount_id(selectedAccount.getAccount_id());
            } else {
                Toast.makeText(getContext(), "Please select an account.", Toast.LENGTH_SHORT).show();
                return null;
            }


            // Transaction Catagori ID
            //transaction.setTrns_name(categoryContext.getCata_name());
            if (categoryContext != null && categoryContext.getCata_id() != 0) {
                transaction.setCata_id(categoryContext.getCata_id());
            } else {
                Toast.makeText(getContext(), "Catagori ID is required.", Toast.LENGTH_SHORT).show();
                return null; // Or handle error appropriately
            }


            // Transaction Type
            //transaction.setTrns_name(categoryContext.getCata_name());
            if (categoryContext != null && categoryContext.getCata_type() != null) {
                if (categoryContext.getCata_type().equals(CategoryType.INCOME))
                    transaction.setTrns_type(TransactionType.CREDIT);
                else if (categoryContext.getCata_type().equals(CategoryType.EXPENSE))
                    transaction.setTrns_type(TransactionType.DEBIT);
            } else {
                Toast.makeText(getContext(), "Transaction Type is required.", Toast.LENGTH_SHORT).show();
                return null; // Or handle error appropriately
            }


            // Transaction Name
            if (categoryContext != null && categoryContext.getCata_name() != null) {
                transaction.setTrns_name(categoryContext.getCata_name());
            } else {
                //transactionNameEditText.setText("Ullla");
                Toast.makeText(getContext(), "Transaction name is required.", Toast.LENGTH_SHORT).show();
                return null; // Or handle error appropriately
            }


        }


        // Transaction Amount
        if (transactionAmountEditText != null && transactionAmountEditText.getText() != null) {
            try {
                double amount = Double.parseDouble(transactionAmountEditText.getText().toString());
                if (TransactionTypeFragment.categoryType != null) {
                    if (TransactionTypeFragment.categoryType.equals(CategoryType.INCOME)) {
                        transaction.setTrns_amount(amount);
                    } else if (TransactionTypeFragment.categoryType.equals(CategoryType.EXPENSE) && amount > 0) {
                        transaction.setTrns_amount(-amount);
                    } else {
                        transaction.setTrns_amount(amount);
                    }
                }
            }catch (NumberFormatException e) {
                Log.e(TAG, "Invalid transaction amount: " + transactionAmountEditText.getText().toString(), e);
                Toast.makeText(getContext(), "Invalid transaction amount.", Toast.LENGTH_SHORT).show();
                return null;
            }
        } else {
             Toast.makeText(getContext(), "Transaction amount is required.", Toast.LENGTH_SHORT).show();
            return null;
        }


        // Transaction Date
        if (dateEditText != null && dateEditText.getText() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {
                Date date = sdf.parse(dateEditText.getText().toString());
                transaction.setTrns_date(date);
            } catch (ParseException e) {
                Log.e(TAG, "Invalid date format: " + dateEditText.getText().toString(), e);
                 Toast.makeText(getContext(), "Invalid date format. Use YYYY-MM-DD.", Toast.LENGTH_SHORT).show();
                return null;
            }
        } else {
            Toast.makeText(getContext(), "Transaction date is required.", Toast.LENGTH_SHORT).show();
            return null;
        }



        // Transaction Type
//        if (transactionTypeSpinner != null && transactionTypeSpinner.getSelectedItem() != null) {
//            transaction.setTrns_type((TransactionType) transactionTypeSpinner.getSelectedItem());
//        } else {
//             Toast.makeText(getContext(), "Please select a transaction type.", Toast.LENGTH_SHORT).show();
//            return null;
//        }

        // Category ID (cata_id)
        // TODO: Get selected category from categorySpinner and set transaction.setCata_id()
        // Example: (Ensure currentCategoriesList is populated and categorySpinner is not null)
        /*
        if (categorySpinner != null && categorySpinner.getSelectedItemPosition() != AdapterView.INVALID_POSITION && !currentCategoriesList.isEmpty()) {
            Category selectedCategory = currentCategoriesList.get(categorySpinner.getSelectedItemPosition());
            transaction.setCata_id(selectedCategory.getCata_id());
        } else {
            // Category might be optional, or you might want to prompt the user
            transaction.setCata_id(null); // Or show error if required
            Toast.makeText(getContext(), "Please select a category.", Toast.LENGTH_SHORT).show();
            return null; // if category is mandatory
        }
        */
//         if (categorySpinner == null || categorySpinner.getSelectedItemPosition() == AdapterView.INVALID_POSITION || currentCategoriesList.isEmpty()) {
//            // Making category optional for now as per original model (Long cata_id)
//            // If mandatory, uncomment the Toast and return null.
//            // Toast.makeText(getContext(), "Please select a category.", Toast.LENGTH_SHORT).show();
//            // return null;
//            transaction.setCata_id(null);
//        } else {
//            Category selectedCategory = currentCategoriesList.get(categorySpinner.getSelectedItemPosition());
//            transaction.setCata_id(selectedCategory.getCata_id());
//        }


        // Transaction Note
        if (transactionNoteEditText != null && transactionNoteEditText.getText() != null) {
            transaction.setTrns_note(transactionNoteEditText.getText().toString().trim());
        } else {
            transaction.setTrns_note(null); // Note can be optional
        }


        // Related Account ID (for transfers)
//        if (transaction.getTrns_type() == TransactionType.TRANSFER) {
//            // TODO: Get selected related account from relatedAccountSpinner and set transaction.setRelated_account_id()
//            // Example: (Ensure currentRelatedAccountsList is populated and relatedAccountSpinner is not null)
//            /*
//            if (relatedAccountSpinner != null && relatedAccountSpinner.getSelectedItemPosition() != AdapterView.INVALID_POSITION && !currentRelatedAccountsList.isEmpty()) {
//                Account selectedRelatedAccount = currentRelatedAccountsList.get(relatedAccountSpinner.getSelectedItemPosition());
//                transaction.setRelated_account_id(selectedRelatedAccount.getAccount_id());
//
//                // Optional: Check if the selected account and related account are the same
//                if (transaction.getAccount_id() == transaction.getRelated_account_id()) {
//                    Toast.makeText(getContext(), "Cannot transfer to the same account.", Toast.LENGTH_SHORT).show();
//                    return null;
//                }
//            } else {
//                Toast.makeText(getContext(), "Please select the account to transfer to.", Toast.LENGTH_SHORT).show();
//                return null; // Related account is mandatory for transfers
//            }
//            */
//            transaction.setRelated_account_id(null); // Placeholder until UI is fully implemented
//        } else {
//            transaction.setRelated_account_id(null);
//        }

        // Recurring ID
        // TODO: Implement logic for recurring_id based on recurringCheckBox and recurringTransactionForm
        // For now, setting to null. This would likely involve saving a separate RecurringTransaction entity
        // and linking its ID here if the checkbox is checked.
        if (recurringCheckBox != null && recurringCheckBox.isChecked()) {
            // Placeholder: you'll need to get an actual ID from a saved recurring transaction setup
            // transaction.setRecurring_id(someRecurringId);
            Log.w(TAG, "Recurring transaction checkbox is checked, but recurring_id logic is not fully implemented.");
             transaction.setRecurring_id(null); // Example: set to a placeholder or get from a form
        } else {
            transaction.setRecurring_id(null);
        }


        // trns_value1 to trns_value5 are not handled here, assuming they are not input through this fragment
        // or will be set elsewhere.

        return transaction;
    }
}
