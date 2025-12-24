package com.example.mnymng.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mnymng.DB.enums.AccountType;
import com.example.mnymng.DB.enums.CategoryType;
import com.example.mnymng.DB.enums.Currency;
import com.example.mnymng.DB.models.Account;
import com.example.mnymng.DB.models.Transaction;
import com.example.mnymng.R;
import com.example.mnymng.adapter.GridAdapter;
import com.example.mnymng.fragments.utilfragments.BottomDrawer;
import com.example.mnymng.fragments.utilfragments.CataListFragment;
import com.example.mnymng.fragments.utilfragments.PopupAccountFragment;
import com.example.mnymng.fragments.utilfragments.PopupTransactionFragment;
import com.example.mnymng.model.AccountsItem;
import com.example.mnymng.model.ListItem;
import com.example.mnymng.viewmodel.AccountViewModel;
import com.example.mnymng.viewmodel.TransactionViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AccountsFragment extends Fragment {
    private static AccountType accountType;

    private RecyclerView recyclerView;
    private GridAdapter<AccountsItem, AccountsFragment.MyListItemViewHolder> adapter;

    private List<Account> currentAccounts = new ArrayList<>(); // To store loaded accounts
    private AccountViewModel accountViewModel;

    public static class MyListItemViewHolder extends RecyclerView.ViewHolder {
        TextView originalName;
        TextView currentBalance;
        TextView cBalance;
        TextView remainingBalance;
        TextView rBalanceCurrency;
        TextView rBalance;
        ImageButton editButton; // Added edit button
        Button payButton; // Added pay button

        //TextView totalAmount;


        public MyListItemViewHolder(@NonNull View itemView) {
            super(itemView);
            originalName = itemView.findViewById(R.id.originalName);
            currentBalance = itemView.findViewById(R.id.currentBalance);
            cBalance = itemView.findViewById(R.id.cBalance);
            remainingBalance = itemView.findViewById(R.id.remainingBalance);
            rBalanceCurrency = itemView.findViewById(R.id.rBalanceCurrency);
            rBalance = itemView.findViewById(R.id.rBalance);
            editButton = itemView.findViewById(R.id.editButton); // Initialize edit button
            payButton = itemView.findViewById(R.id.payButton); // Initialize pay button

            //totalAmount = itemView.findViewById(R.id.totalAmount);


            if(accountType != null) {
                if(accountType == AccountType.BANK) {
                    currentBalance.setText("Balance");
                    remainingBalance.setVisibility(View.GONE);
                    rBalanceCurrency.setVisibility(View.GONE);
                    rBalance.setVisibility(View.GONE);
                    payButton.setVisibility(View.GONE);
                    //payButton.setText("Transfer");

            } else if (accountType == AccountType.CREDIT_CARD) {
                    currentBalance.setText("Remaining");
                    remainingBalance.setText("Total");;
                    payButton.setText("Pay");

                }
            }else if (accountType == AccountType.LENDING) {
                currentBalance.setText("Remaining");
                remainingBalance.setText("Total");;
                payButton.setText("Payment");

            }else if (accountType == AccountType.LOAN) {
                currentBalance.setText("Remaining");
                remainingBalance.setText("Total");
                payButton.setText("Pay");

            }else if (accountType == AccountType.INSURANCE) {
                currentBalance.setText("Balance");
                remainingBalance.setVisibility(View.GONE);
                rBalanceCurrency.setVisibility(View.GONE);
                rBalance.setVisibility(View.GONE);
                payButton.setText("Pay");

            }else if (accountType == AccountType.INVESTMENT) {
                currentBalance.setText("Balance");
                remainingBalance.setVisibility(View.GONE);
                rBalanceCurrency.setVisibility(View.GONE);
                rBalance.setVisibility(View.GONE);
                payButton.setText("Invest");

            }else if (accountType == AccountType.E_WALLET) {
                currentBalance.setText("Balance");
                remainingBalance.setVisibility(View.GONE);
                rBalanceCurrency.setVisibility(View.GONE);
                rBalance.setVisibility(View.GONE);
                payButton.setText("Add");

            }else if (accountType == AccountType.WALLET) {
                currentBalance.setText("Balance");
                remainingBalance.setVisibility(View.GONE);
                rBalanceCurrency.setVisibility(View.GONE);
                rBalance.setVisibility(View.GONE);
                payButton.setText("Add");

            }
            else if (accountType == AccountType.OTHER_ASSET) {
                currentBalance.setText("Balance");
                remainingBalance.setVisibility(View.GONE);
                rBalanceCurrency.setVisibility(View.GONE);
                rBalance.setVisibility(View.GONE);
                payButton.setText("Pay");

            }
            }

        @SuppressLint("SetTextI18n")
        public void bind(AccountsItem item) {
            if (item != null) {
                originalName.setText(item.getName());
                cBalance.setText(item.getHowMuch().toString());
                rBalance.setText(item.getRemaining().toString());
            }
        }
    }
    public AccountsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            accountType = AccountsFragmentArgs.fromBundle(getArguments()).getAccountType();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common, container, false);
        LinearLayout filterSortSection = view.findViewById(R.id.filter_sort_section);
        filterSortSection.setVisibility(View.GONE);
        LinearLayout headerTotalSection = view.findViewById(R.id.header_total_section);
        headerTotalSection.setVisibility(View.GONE);

        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        FloatingActionButton buttonOpenDrawer = view.findViewById(R.id.fab_add);
        buttonOpenDrawer.setOnClickListener(v -> {
            PopupAccountFragment popupAccountFragment = new PopupAccountFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("accountAddCall", (Serializable) accountType);
            popupAccountFragment.setArguments(bundle);
            popupAccountFragment.show(getParentFragmentManager(), "PopupAccountFragmentTag");
        });

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        GridAdapter.ViewHolderFactory<AccountsFragment.MyListItemViewHolder> factory = itemView -> new AccountsFragment.MyListItemViewHolder(itemView);

        GridAdapter.ViewHolderBinder<AccountsItem, AccountsFragment.MyListItemViewHolder> binder = (holder, item) -> {
            holder.bind(item);
            // Set OnClickListener for the edit button
            holder.editButton.setOnClickListener(v -> {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && position < currentAccounts.size()) {
                    Account selectedAccount = currentAccounts.get(position);
                    PopupAccountFragment popupAccountFragment = new PopupAccountFragment();
                    Bundle bundle = new Bundle();
                    // Ensure Account class implements Serializable
                    bundle.putSerializable("accountToEdit", (Serializable) selectedAccount);
                    popupAccountFragment.setArguments(bundle);
                    popupAccountFragment.show(getParentFragmentManager(), "PopupAccountFragment_EditTag");
                }
            });

            // Set OnClickListener for the pay button
            holder.payButton.setOnClickListener(v -> {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && position < currentAccounts.size()) {
                    Account selectedAccount = currentAccounts.get(position);
                    // Add your payment functionality here
                    Log.d("AccountsFragment", "Pay button clicked for account: " + selectedAccount.getAccount_name());
                    // Example: navigate to a payment screen or show a payment dialog
                     PopupTransactionFragment popupTransactionFragment = new PopupTransactionFragment();
                     Bundle bundle = new Bundle();
                     bundle.putSerializable("accountToPayFrom", (Serializable) selectedAccount);
                     popupTransactionFragment.setArguments(bundle);
                     popupTransactionFragment.show(getParentFragmentManager(), "PopupTransactionFragment_PayTag");
                }
            });
        };

        adapter = new GridAdapter<>(
                getContext(),
                new ArrayList<>(),
                R.layout.accounts_item,
                factory,
                binder
        );

        // This existing item click listener is for the whole card view.
        // If you only want the edit button to be clickable, you might remove or adjust this.
        adapter.setOnItemClickListener((item, position) -> {
            Log.d("Fragment", "Card clicked: " + item.toString() + " at position " + position);
            // Example: if (position < currentAccounts.size()) { Account selectedAccount = currentAccounts.get(position); /* handle card click */ }
            if (position != RecyclerView.NO_POSITION && position < currentAccounts.size()) {
                Account selectedAccount = currentAccounts.get(position);
                // Replace the current fragment with TransactionTypeFragment
                //FragmentManager fragmentManager = getParentFragmentManager();
                //FragmentManager activityFragmentManager = requireActivity().getSupportFragmentManager();

                TransactionTypeFragment transactionTypeFragment = new TransactionTypeFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedAccount", selectedAccount);
                // Optional: Pass a CategoryType if clicking an account card should
                // default to a specific transaction type view (e.g., all transactions, or expenses).
                // If TransactionTypeFragment should show all transactions for the account
                // when no CategoryType is passed, you can omit this.
                // bundle.putSerializable("transactionType", CategoryType.EXPENSE); // Example
                transactionTypeFragment.setArguments(bundle);


                //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // ** IMPORTANT: Replace R.id.your_fragment_container_id with the actual ID of the FrameLayout in your activity **
//                activityFragmentManager.beginTransaction().replace(R.id.fragment_common, transactionTypeFragment)
//                .addToBackStack(null) // Optional: if you want back navigation to return here
//                .commit();
                NavController navController =
                        NavHostFragment.findNavController(AccountsFragment.this);
                navController.navigate(
                        R.id.action_accountsFragment_to_accountTransactionFragment,
                        bundle
                );
            } else {
                Log.e("AccountsFragment", "Invalid position or currentAccounts list out of sync on item click.");
            }

        });

        recyclerView.setAdapter(adapter);

        loadAccountsByType(accountType); // Renamed from loadTransactionsByCategoryType for clarity

        return view;
    }

    // Renamed from loadTransactionsByCategoryType
    private void loadAccountsByType(AccountType accountType) {
        accountViewModel.getAccountsByType(accountType).observe(getViewLifecycleOwner(), accounts -> {
            List<AccountsItem> dataList = new ArrayList<>();
            currentAccounts.clear();
            if (accounts != null) {
                currentAccounts.addAll(accounts);
                for (Account account : accounts) {
                    String name = account.getAccount_name();
                    Double cBalance = account.getAccount_current_balance();
                    // Assuming AccountsItem constructor expects name, current balance, and opening balance (or similar)
                    // The third parameter was cBalance again, which might be a placeholder.
                    // If rBalance is opening_balance, use account.getAccount_opening_balance()
                    dataList.add(new AccountsItem(name, cBalance, account.getAccount_opening_balance() != 0 ? account.getAccount_opening_balance() : 0.0));
                }
                adapter.updateData(dataList); // Update adapter with new list
            }
        });
    }
}
