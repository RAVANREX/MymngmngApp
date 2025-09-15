package com.example.mnymng.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mnymng.DB.enums.CategoryType;
import com.example.mnymng.DB.enums.Currency;
import com.example.mnymng.DB.models.Transaction;
import com.example.mnymng.R;
import com.example.mnymng.adapter.GridAdapter;
import com.example.mnymng.fragments.utilfragments.BottomDrawer;
import com.example.mnymng.fragments.utilfragments.CataListFragment;
import com.example.mnymng.fragments.utilfragments.PopupTransactionFragment;
import com.example.mnymng.model.ListItem;
import com.example.mnymng.viewmodel.TransactionViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionTypeFragment extends Fragment {
    public static CategoryType categoryType;

    private RecyclerView recyclerView;
    private GridAdapter<ListItem, MyListItemViewHolder> adapter;
    private Spinner spinnerFilter;
    private Spinner spinnerSort;

    private List<Transaction> allTransactions = new ArrayList<>();
    private List<Transaction> displayedTransactions = new ArrayList<>();

    private TransactionViewModel transactionViewModel;

    private String currentFilterOption = "None";
    private String currentSortOption = "None";

    // Date range for custom filter
    private Calendar startDateCalendar;
    private Calendar endDateCalendar;

    // Constants for spinner options (mirroring strings.xml)
    private static final String FILTER_NONE = "None";
    private static final String FILTER_THIS_MONTH = "This Month";
    private static final String FILTER_LAST_MONTH = "Last Month";
    private static final String FILTER_THIS_YEAR = "This Year";
    private static final String FILTER_LAST_YEAR = "Last Year";
    private static final String FILTER_CUSTOM_DATE_RANGE = "Custom Date Range...";

    private static final String SORT_NONE = "None";
    private static final String SORT_TIME_NEWEST_FIRST = "Time (Newest First)";
    private static final String SORT_TIME_OLDEST_FIRST = "Time (Oldest First)";
    private static final String SORT_AMOUNT_HIGH_TO_LOW = "Amount (High to Low)";
    private static final String SORT_AMOUNT_LOW_TO_HIGH = "Amount (Low to High)";


    public static class MyListItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemValue;
        TextView currency;

        public MyListItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemValue = itemView.findViewById(R.id.item_value);
            currency = itemView.findViewById(R.id.item_sign);
        }

        @SuppressLint("SetTextI18n")
        public void bind(ListItem item) {
            if (item != null) {
                itemName.setText(item.getItemName());
                itemValue.setText(item.getItemValue().toString());
                currency.setText(item.getcurrency());
            }
        }
    }

    public TransactionTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryType = TransactionTypeFragmentArgs.fromBundle(getArguments()).getTransactionType();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common, container, false);

        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        FloatingActionButton buttonOpenDrawer = view.findViewById(R.id.fab_add);
        buttonOpenDrawer.setOnClickListener(v -> {
            BottomDrawer bottomDrawerFragment = new BottomDrawer();
            CataListFragment.screenName = categoryType;
            bottomDrawerFragment.show(getParentFragmentManager(), bottomDrawerFragment.getTag());
        });

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        spinnerFilter = view.findViewById(R.id.spinner_filter);
        spinnerSort = view.findViewById(R.id.spinner_sort);

        ArrayAdapter<CharSequence> filterAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.filter_options, android.R.layout.simple_spinner_item);
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(filterAdapter);

        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.sort_options, android.R.layout.simple_spinner_item);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSort.setAdapter(sortAdapter);

        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentFilterOption = parent.getItemAtPosition(position).toString();
                if (FILTER_CUSTOM_DATE_RANGE.equals(currentFilterOption)) {
                    showStartDatePicker();
                } else {
                    startDateCalendar = null; // Clear custom range if another option is selected
                    endDateCalendar = null;
                    applyFiltersAndSort();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                currentFilterOption = FILTER_NONE;
                startDateCalendar = null;
                endDateCalendar = null;
                applyFiltersAndSort();
            }
        });

        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentSortOption = parent.getItemAtPosition(position).toString();
                applyFiltersAndSort();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                currentSortOption = SORT_NONE;
                applyFiltersAndSort();
            }
        });

        GridAdapter.ViewHolderFactory<MyListItemViewHolder> factory = itemView -> new MyListItemViewHolder(itemView);
        GridAdapter.ViewHolderBinder<ListItem, MyListItemViewHolder> binder = (holder, item) -> holder.bind(item);

        adapter = new GridAdapter<>(
                getContext(),
                new ArrayList<>(),
                R.layout.list_item,
                factory,
                binder
        );

        adapter.setOnItemClickListener((item, position) -> {
            Log.d("Fragment", "Clicked: " + item.toString() + " at position " + position);
            if (position < displayedTransactions.size()) {
                Transaction selectedTransaction = displayedTransactions.get(position);
                PopupTransactionFragment popupTransactionFragment = new PopupTransactionFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("transactionToEdit", (Serializable) selectedTransaction);
                popupTransactionFragment.setArguments(bundle);
                popupTransactionFragment.show(getParentFragmentManager(), "PopupTransactionFragmentTag");
            }
        });

        recyclerView.setAdapter(adapter);
        loadInitialTransactions();
        return view;
    }

    private void showStartDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year1, monthOfYear, dayOfMonth) -> {
                    startDateCalendar = Calendar.getInstance();
                    startDateCalendar.set(year1, monthOfYear, dayOfMonth, 0, 0, 0); // Set time to start of day
                    showEndDatePicker();
                }, year, month, day);
        datePickerDialog.setMessage("Select Start Date");
        datePickerDialog.show();
    }

    private void showEndDatePicker() {
        final Calendar c = Calendar.getInstance();
        if (startDateCalendar != null) { // Default end date to start date or current
            c.setTime(startDateCalendar.getTime());
        }
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year1, monthOfYear, dayOfMonth) -> {
                    endDateCalendar = Calendar.getInstance();
                    endDateCalendar.set(year1, monthOfYear, dayOfMonth, 23, 59, 59); // Set time to end of day

                    if (startDateCalendar != null && endDateCalendar.before(startDateCalendar)) {
                        Toast.makeText(getContext(), "End date cannot be before start date.", Toast.LENGTH_LONG).show();
                        endDateCalendar = null; // or reset to start date
                        // Optionally re-prompt for end date or reset filter selection
                        spinnerFilter.setSelection(0); // Reset to "None" or previous valid selection
                        currentFilterOption = FILTER_NONE;
                    }
                    applyFiltersAndSort();
                }, year, month, day);
        datePickerDialog.setMessage("Select End Date");
        if (startDateCalendar != null) {
            datePickerDialog.getDatePicker().setMinDate(startDateCalendar.getTimeInMillis());
        }
        datePickerDialog.show();
    }


    private void loadInitialTransactions() {
        transactionViewModel.getTransactionsByCategoryType(categoryType).observe(getViewLifecycleOwner(), transactions -> {
            allTransactions.clear();
            if (transactions != null) {
                allTransactions.addAll(transactions);
            }
            applyFiltersAndSort();
        });
    }

    private void applyFiltersAndSort() {
        List<Transaction> filteredList = new ArrayList<>(allTransactions);
        Calendar cal = Calendar.getInstance();

        // Apply Date Filters
        if (FILTER_THIS_MONTH.equals(currentFilterOption)) {
            int currentMonth = cal.get(Calendar.MONTH);
            int currentYear = cal.get(Calendar.YEAR);
            filteredList = filteredList.stream().filter(t -> {
                if (t.getTrns_date() == null) return false;
                Calendar transCal = Calendar.getInstance();
                transCal.setTime(t.getTrns_date());
                return transCal.get(Calendar.MONTH) == currentMonth && transCal.get(Calendar.YEAR) == currentYear;
            }).collect(Collectors.toList());
        } else if (FILTER_LAST_MONTH.equals(currentFilterOption)) {
            Calendar tempCal = Calendar.getInstance();
            tempCal.add(Calendar.MONTH, -1);
            int lastMonth = tempCal.get(Calendar.MONTH);
            int yearOfLastMonth = tempCal.get(Calendar.YEAR);
            filteredList = filteredList.stream().filter(t -> {
                if (t.getTrns_date() == null) return false;
                Calendar transCal = Calendar.getInstance();
                transCal.setTime(t.getTrns_date());
                return transCal.get(Calendar.MONTH) == lastMonth && transCal.get(Calendar.YEAR) == yearOfLastMonth;
            }).collect(Collectors.toList());
        } else if (FILTER_THIS_YEAR.equals(currentFilterOption)) {
            int currentYear = cal.get(Calendar.YEAR);
            filteredList = filteredList.stream().filter(t -> {
                if (t.getTrns_date() == null) return false;
                Calendar transCal = Calendar.getInstance();
                transCal.setTime(t.getTrns_date());
                return transCal.get(Calendar.YEAR) == currentYear;
            }).collect(Collectors.toList());
        } else if (FILTER_LAST_YEAR.equals(currentFilterOption)) {
            int lastYear = cal.get(Calendar.YEAR) - 1;
            filteredList = filteredList.stream().filter(t -> {
                if (t.getTrns_date() == null) return false;
                Calendar transCal = Calendar.getInstance();
                transCal.setTime(t.getTrns_date());
                return transCal.get(Calendar.YEAR) == lastYear;
            }).collect(Collectors.toList());
        } else if (FILTER_CUSTOM_DATE_RANGE.equals(currentFilterOption) && startDateCalendar != null && endDateCalendar != null) {
            Date startDate = startDateCalendar.getTime();
            Date endDate = endDateCalendar.getTime();
            filteredList = filteredList.stream().filter(t -> {
                if (t.getTrns_date() == null) return false;
                // Ensure transaction date is not before start date AND not after end date
                return !t.getTrns_date().before(startDate) && !t.getTrns_date().after(endDate);
            }).collect(Collectors.toList());
        }
        // TODO: Add categoryId filtering here if needed, similar to date filters.
        // Example: if (selectedCategoryId != null) {
        // filteredList = filteredList.stream().filter(t -> t.getCata_id().equals(selectedCategoryId)).collect(Collectors.toList());
        // }


        // Apply Sorting
        if (SORT_TIME_NEWEST_FIRST.equals(currentSortOption)) {
            Collections.sort(filteredList, (t1, t2) -> {
                if (t1.getTrns_date() == null && t2.getTrns_date() == null) return 0;
                if (t1.getTrns_date() == null) return 1;
                if (t2.getTrns_date() == null) return -1;
                return t2.getTrns_date().compareTo(t1.getTrns_date());
            });
        } else if (SORT_TIME_OLDEST_FIRST.equals(currentSortOption)) {
            Collections.sort(filteredList, (t1, t2) -> {
                if (t1.getTrns_date() == null && t2.getTrns_date() == null) return 0;
                if (t1.getTrns_date() == null) return 1;
                if (t2.getTrns_date() == null) return -1;
                return t1.getTrns_date().compareTo(t2.getTrns_date());
            });
        } else if (SORT_AMOUNT_HIGH_TO_LOW.equals(currentSortOption)) {
            Collections.sort(filteredList, Comparator.comparing(Transaction::getTrns_amount, Comparator.nullsLast(Double::compareTo)).reversed());
        } else if (SORT_AMOUNT_LOW_TO_HIGH.equals(currentSortOption)) {
            Collections.sort(filteredList, Comparator.comparing(Transaction::getTrns_amount, Comparator.nullsLast(Double::compareTo)));
        }


        displayedTransactions.clear();
        displayedTransactions.addAll(filteredList);

        List<ListItem> dataList = new ArrayList<>();
        for (Transaction transaction : displayedTransactions) {
            String name = transaction.getTrns_name();
            Double value = transaction.getTrns_amount();
            String category = transaction.getCata_id() != null ? transaction.getCata_id().toString() : "N/A";
            String date = "";
            if (transaction.getTrns_date() != null) {
                // TODO: Consider using SimpleDateFormat for better date string representation
                date = transaction.getTrns_date().toString();
            }
            dataList.add(new ListItem(name, value, Currency.INR.getSymbol(), category, date));
        }
        adapter.updateData(dataList);
    }
}
