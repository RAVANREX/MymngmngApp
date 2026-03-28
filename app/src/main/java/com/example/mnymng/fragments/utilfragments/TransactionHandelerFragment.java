package com.example.mnymng.fragments.utilfragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mnymng.DB.AppDatabase;
import com.example.mnymng.DB.enums.AccountType;
import com.example.mnymng.DB.enums.CategoryType;
import com.example.mnymng.DB.enums.RecurringFrequency;
import com.example.mnymng.DB.models.Account;
import com.example.mnymng.DB.models.Category;
import com.example.mnymng.DB.models.Recurring;
import com.example.mnymng.DB.models.Transaction;
import com.example.mnymng.DB.enums.TransactionType;
import com.example.mnymng.R;
import com.example.mnymng.fragments.TransactionTypeFragment;
import com.example.mnymng.viewmodel.TransactionViewModel;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class TransactionHandelerFragment extends Fragment {

    private static final String TAG = "TransactionHandeler";

    private TransactionViewModel transactionViewModel;

    // Existing UI Elements
    private Spinner accountTypeSpinner;
    private Spinner accountSpinner;
    private ArrayAdapter<String> accountSpinnerAdapter;
    private EditText dateEditText;
    private SwitchMaterial recurringCheckBox;
    private LinearLayout recurringTransactionForm;
    private Spinner recurringFrequencySpinner;
    private EditText recurringEndDateEditText;

    private EditText transactionAmountEditText;
    private EditText transactionNoteEditText;
    private Transaction transactionToEdit;
    private Category categoryContext;

    private List<Account> currentAccountsList = new ArrayList<>();
    private Account accountToPayFrom;


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

        PopupTransactionFragment popupTransactionFragment = (PopupTransactionFragment) getParentFragment();

        if (popupTransactionFragment != null) {
            accountToPayFrom = popupTransactionFragment.accountToPayFrom;
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.transaction_fragmnet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Step 1: Initialize UI Elements
        recurringCheckBox = view.findViewById(R.id.recurringcheckBox);
        recurringTransactionForm = view.findViewById(R.id.recurringTransactionForm);
        dateEditText = view.findViewById(R.id.dateEditText);
        accountTypeSpinner = view.findViewById(R.id.accountTypeSpinner);
        accountSpinner = view.findViewById(R.id.accountSpinner);
        recurringFrequencySpinner = view.findViewById(R.id.recurringFrequencySpinner);
        recurringEndDateEditText = view.findViewById(R.id.recurringEndDateEditText);
        transactionAmountEditText = view.findViewById(R.id.amountEditText);
        transactionNoteEditText = view.findViewById(R.id.noteEditText);

        // Step 2: Setup Adapters
        accountSpinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new ArrayList<>());
        accountSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountSpinner.setAdapter(accountSpinnerAdapter);

        ArrayAdapter<AccountType> accountTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, AccountType.values());
        accountTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeSpinner.setAdapter(accountTypeAdapter);

        ArrayAdapter<RecurringFrequency> recurringFrequencyAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, RecurringFrequency.values());
        recurringFrequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recurringFrequencySpinner.setAdapter(recurringFrequencyAdapter);

        // Step 3: Handle Different States (Edit, Pay from Account, New)
        if (transactionToEdit != null) {
            // STATE 1: EDITING TRANSACTION
            transactionAmountEditText.setText(String.valueOf(transactionToEdit.getTrns_amount()));
            transactionNoteEditText.setText(transactionToEdit.getTrns_note());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dateEditText.setText(sdf.format(transactionToEdit.getTrns_date()));

            accountTypeSpinner.setEnabled(false);
            accountSpinner.setEnabled(false);

            Executors.newSingleThreadExecutor().execute(() -> {
                Account account = AppDatabase.getDatabase(getContext()).accountDao().getAccountById(transactionToEdit.getAccount_id());
                if (account != null && getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        accountTypeSpinner.setSelection(account.getAccount_type().ordinal());
                        accountSpinnerAdapter.clear();
                        accountSpinnerAdapter.add(account.getAccount_name());
                        accountSpinnerAdapter.notifyDataSetChanged();
                        accountSpinner.setSelection(0);
                    });
                }
            });

            if (transactionToEdit.getRecurring_id() != null) {
                recurringCheckBox.setChecked(true);
                recurringTransactionForm.setVisibility(View.VISIBLE);
                AppDatabase.getDatabase(getContext()).recurringDao().getRecurringById(transactionToEdit.getRecurring_id()).observe(getViewLifecycleOwner(), recurring -> {
                    if (recurring != null) {
                        recurringFrequencySpinner.setSelection(recurring.getRecurring_frequency().ordinal());
                        SimpleDateFormat sdfRecurring = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        recurringEndDateEditText.setText(sdfRecurring.format(recurring.getRecurring_edt()));
                    }
                });

            } else {
                recurringFrequencySpinner.setSelection(RecurringFrequency.DAILY.ordinal());
            }

        } else if (accountToPayFrom != null) {
            // STATE 2: PAYING FROM A SPECIFIC ACCOUNT
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dateEditText.setText(sdf.format(new Date()));
            recurringFrequencySpinner.setSelection(RecurringFrequency.DAILY.ordinal());

            AccountType type = accountToPayFrom.getAccount_type();
            if (type != null) {
                accountTypeSpinner.setSelection(type.ordinal());
            }

            accountSpinnerAdapter.clear();
            accountSpinnerAdapter.add(accountToPayFrom.getAccount_name());
            accountSpinnerAdapter.notifyDataSetChanged();
            accountSpinner.setSelection(0);
            currentAccountsList.clear();
            currentAccountsList.add(accountToPayFrom);

            accountTypeSpinner.setEnabled(false);
            accountSpinner.setEnabled(false);

        } else {
            // STATE 3: CREATING A NEW TRANSACTION
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            dateEditText.setText(sdf.format(new Date()));
            recurringFrequencySpinner.setSelection(RecurringFrequency.DAILY.ordinal());
            accountTypeSpinner.setSelection(0);
            loadAccountsForType(AccountType.values()[0]);

            accountTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    AccountType selectedAccountType = (AccountType) parent.getItemAtPosition(position);
                    if (selectedAccountType != null) {
                        loadAccountsForType(selectedAccountType);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

        // Step 4: Set up Common Listeners
        recurringCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                recurringTransactionForm.setAlpha(0f);
                recurringTransactionForm.setVisibility(View.VISIBLE);
                recurringTransactionForm.animate().alpha(1f).setDuration(300).setListener(null);
            } else {
                recurringTransactionForm.animate().alpha(0f).setDuration(300).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        recurringTransactionForm.setVisibility(View.GONE);
                    }
                });
            }
        });

        dateEditText.setOnClickListener(v -> showDatePickerDialog(dateEditText));
        recurringEndDateEditText.setOnClickListener(v -> showDatePickerDialog(recurringEndDateEditText));
    }

    private void loadAccountsForType(AccountType accountType) {
        if (transactionViewModel != null) {
            transactionViewModel.getAccountsByType(accountType).observe(getViewLifecycleOwner(), accounts -> {
                if (accounts != null && !accounts.isEmpty()) {
                    currentAccountsList.clear();
                    currentAccountsList.addAll(accounts);
                    List<String> accountNames = accounts.stream().map(Account::getAccount_name).collect(Collectors.toList());
                    accountSpinnerAdapter.clear();
                    accountSpinnerAdapter.addAll(accountNames);
                    accountSpinnerAdapter.notifyDataSetChanged();
                    if (!accountNames.isEmpty()) {
                        accountSpinner.setSelection(0);
                    }
                } else {
                    accountSpinnerAdapter.clear();
                    accountSpinnerAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "No Account Found for selected type", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showDatePickerDialog(EditText dateEditText) {
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
    }


    public Transaction getTransactionData() {
        Transaction transaction = transactionToEdit == null ? new Transaction() : transactionToEdit;

        if (transactionToEdit == null) {

            if (accountSpinner.getSelectedItem() !=null){
                Account selectedAccount = currentAccountsList.get(accountSpinner.getSelectedItemPosition());
                transaction.setAccount_id(selectedAccount.getAccount_id());
            }else {
                Toast.makeText(getContext(), "Please select an account.", Toast.LENGTH_SHORT).show();
                return null;
            }

            if (accountToPayFrom == null) {
                if (categoryContext != null && categoryContext.getCata_id() != 0) {
                    transaction.setCata_id(categoryContext.getCata_id());
                } else {
                    Toast.makeText(getContext(), "Category ID is required.", Toast.LENGTH_SHORT).show();
                    return null;
                }

                if (categoryContext != null && categoryContext.getCata_type() != null) {
                    if (categoryContext.getCata_type().equals(CategoryType.INCOME)) {
                        transaction.setTrns_type(TransactionType.CREDIT);
                    } else if (categoryContext.getCata_type().equals(CategoryType.EXPENSE)) {
                        transaction.setTrns_type(TransactionType.DEBIT);
                    } else {
                        transaction.setTrns_type(TransactionType.DEBIT);
                    }
                } else {
                    Toast.makeText(getContext(), "Transaction Type is required.", Toast.LENGTH_SHORT).show();
                    return null;
                }
                if (categoryContext != null && categoryContext.getCata_name() != null) {
                    transaction.setTrns_name(categoryContext.getCata_name());
                } else {
                    //transactionNameEditText.setText("Ullla");
                    Toast.makeText(getContext(), "Transaction name is required.", Toast.LENGTH_SHORT).show();
                    return null; // Or handle error appropriately
                }


            }else{
                transaction.setTrns_name(accountToPayFrom.getAccount_name());
                switch (accountToPayFrom.getAccount_type()) {
                    case WALLET:
                    case E_WALLET:
                    case LENDING:
                        transaction.setTrns_type(TransactionType.CREDIT);
                        break;
                    case LOAN:
                    case CREDIT_CARD:
                    case INSURANCE:
                    case INVESTMENT:
                        transaction.setTrns_type(TransactionType.DEBIT);
                        break;
                    case OTHER_ASSET:
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported account type: " + accountToPayFrom.getAccount_type());
                }
            }
        }


        // Transaction Amount
        if (transactionAmountEditText != null && transactionAmountEditText.getText() != null) {
            try {
                double amount = Double.parseDouble(transactionAmountEditText.getText().toString());
                if (TransactionTypeFragment.categoryType != null) {
                    if (TransactionTypeFragment.categoryType.equals(CategoryType.INCOME)&& amount > 0) {
                        transaction.setTrns_amount(amount);
                    } else if (TransactionTypeFragment.categoryType.equals(CategoryType.EXPENSE) && amount > 0) {
                        transaction.setTrns_amount(-amount);
                    } else {
                        transaction.setTrns_amount(amount);
                    }
                } else if (accountToPayFrom != null) {
                    transaction.setTrns_amount(amount);
                }
            }catch (NumberFormatException e) {
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
                Toast.makeText(getContext(), "Invalid date format. Use YYYY-MM-DD.", Toast.LENGTH_SHORT).show();
                return null;
            }
        } else {
            Toast.makeText(getContext(), "Transaction date is required.", Toast.LENGTH_SHORT).show();
            return null;
        }

        // Transaction Note
        if (transactionNoteEditText != null && transactionNoteEditText.getText() != null) {
            transaction.setTrns_note(transactionNoteEditText.getText().toString().trim());
        } else {
            transaction.setTrns_note(null); // Note can be optional
        }
        

        return transaction;
    }
    
    public Recurring getRecurringData() {
        if (!recurringCheckBox.isChecked()) {
            return null;
        }

        Recurring recurring = new Recurring();

        if (recurringFrequencySpinner.getSelectedItem() != null) {
            recurring.setRecurring_frequency((RecurringFrequency) recurringFrequencySpinner.getSelectedItem());
        } else {
            Toast.makeText(getContext(), "Please select a recurring frequency.", Toast.LENGTH_SHORT).show();
            return null;
        }

        if (recurringEndDateEditText.getText() != null && !recurringEndDateEditText.getText().toString().isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {
                Date endDate = sdf.parse(recurringEndDateEditText.getText().toString());
                recurring.setRecurring_edt(endDate);
            } catch (ParseException e) {
                Toast.makeText(getContext(), "Invalid recurring end date. Use YYYY-MM-DD.", Toast.LENGTH_SHORT).show();
                return null;
            }
        } else {
            Toast.makeText(getContext(), "Recurring end date is required.", Toast.LENGTH_SHORT).show();
            return null;
        }

        return recurring;
    }
}
