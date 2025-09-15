package com.example.mnymng.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private AccountType accountType;

    private RecyclerView recyclerView;
    private GridAdapter<AccountsItem, AccountsFragment.MyListItemViewHolder> adapter;

    private List<Account> currentAccounts = new ArrayList<>(); // To store loaded transactions
    private AccountViewModel accountViewModel;

    public static class MyListItemViewHolder extends RecyclerView.ViewHolder {
        TextView originalName;
        TextView cBalance;
        TextView rBalance;

        public MyListItemViewHolder(@NonNull View itemView) {
            super(itemView);
            originalName = itemView.findViewById(R.id.originalName);
            cBalance = itemView.findViewById(R.id.cBalance);
            rBalance = itemView.findViewById(R.id.rBalance);
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

        // Initialize ViewModel
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        FloatingActionButton buttonOpenDrawer = view.findViewById(R.id.fab_add);
        buttonOpenDrawer.setOnClickListener(v -> {
            PopupAccountFragment popupAccountFragment = new PopupAccountFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("accountAddCall", (Serializable) accountType);
            popupAccountFragment.setArguments(bundle);
            popupAccountFragment.show(getParentFragmentManager(), "PopupAccountFragmentTag");
//            BottomDrawer bottomDrawerFragment = new BottomDrawer();
//            CataListFragment.screenName = accountType;
//            bottomDrawerFragment.show(getParentFragmentManager(), bottomDrawerFragment.getTag());
        });

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        // Create the ViewHolderFactory
        GridAdapter.ViewHolderFactory<AccountsFragment.MyListItemViewHolder> factory = itemView -> new AccountsFragment.MyListItemViewHolder(itemView);

        // Create the ViewHolderBinder
        GridAdapter.ViewHolderBinder<AccountsItem, AccountsFragment.MyListItemViewHolder> binder = (holder, item) -> holder.bind(item);

        // Create the adapter
        adapter = new GridAdapter<>(
                getContext(),
                new ArrayList<>(),
                R.layout.accounts_item,
                factory,
                binder
        );

        adapter.setOnItemClickListener((item, position) -> {
            Log.d("Fragment", "Clicked: " + item.toString() + " at position " + position);
            // Handle item clicks here
            if (position < currentAccounts.size()) {
                //ListItem clickedItem = dataList.get(position);
                // Do something with the clicked item
                Account selectedTransaction = (Account) currentAccounts.get(position); // Cast to Transaction

//                PopupTransactionFragment popupTransactionFragment = new PopupTransactionFragment();
//                Bundle bundle = new Bundle();
//                // Make sure your Category class implements Serializable or Parcelable
//                // Ensure PopupTransactionFragment expects "transactionAddCall" or adjust as needed
//                bundle.putSerializable("transactionToEdit", (Serializable) selectedTransaction);
//                popupTransactionFragment.setArguments(bundle);
//                popupTransactionFragment.show(getParentFragmentManager(), "PopupTransactionFragmentTag");
            }
        });

        recyclerView.setAdapter(adapter);

        // Load initial transactions
        loadTransactionsByCategoryType(accountType);

        return view;
    }

    private void loadTransactionsByCategoryType(AccountType accountType) {
        accountViewModel.getAccountsByType(accountType).observe(getViewLifecycleOwner(), accounts -> {
            List<AccountsItem> dataList = new ArrayList<>();
            currentAccounts.clear(); // Clear previous transactions
            if (accounts != null) {
                currentAccounts.addAll(accounts); // Store fetched transactions
                for (Account account : accounts) {
                    // TODO: Adjust these getters to match your Transaction model
                    // And ensure your ListItem constructor matches this usage
                    String name = account.getAccount_name(); // Or transaction.getName() or similar
                    Double cBalance = account.getAccount_current_balance();
                    Double rBalance = account.getAccount_opening_balance(); // Or derive from transaction

                    dataList.add(new AccountsItem(name, cBalance, cBalance));
                    adapter.updateData(dataList);
                }
                //adapter.notifyDataSetChanged();
            }
        });
    }
}