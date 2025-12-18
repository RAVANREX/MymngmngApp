package com.example.mnymng.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mnymng.R;
import com.example.mnymng.data.MainViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.Map;

public class DashboardFragment extends Fragment {

    private MainViewModel mViewModel;
    private BarChart barChart;
    private TextView tvIncome, tvExpense, tvSaving;
    private MaterialButtonToggleGroup toggleGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        barChart = view.findViewById(R.id.bar_chart);
        tvIncome = view.findViewById(R.id.tv_income);
        tvExpense = view.findViewById(R.id.tv_expense);
        tvSaving = view.findViewById(R.id.tv_saving);
        toggleGroup = view.findViewById(R.id.toggle_group);

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setupBarChart();
        setupToggleGroup();
        observeViewModel();

        // Set default view and trigger data load
        toggleGroup.check(R.id.btn_monthly);
        mViewModel.setPeriod("monthly");

        return view;
    }

    private void setupBarChart() {
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);

        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getLegend().setEnabled(false);
    }

    private void setupToggleGroup() {
        toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.btn_weekly) {
                    mViewModel.setPeriod("weekly");
                } else if (checkedId == R.id.btn_monthly) {
                    mViewModel.setPeriod("monthly");
                } else if (checkedId == R.id.btn_yearly) {
                    mViewModel.setPeriod("yearly");
                }
            }
        });
    }

    private void observeViewModel() {
        mViewModel.getIncome().observe(getViewLifecycleOwner(), income -> {
            if (income != null) {
                tvIncome.setText(String.format("₹%.2f", income));
            } else {
                tvIncome.setText("₹0.00");
            }
        });

        mViewModel.getExpenses().observe(getViewLifecycleOwner(), expense -> {
            if (expense != null) {
                tvExpense.setText(String.format("₹%.2f", expense));
            } else {
                tvExpense.setText("₹0.00");
            }
        });

        mViewModel.getSavings().observe(getViewLifecycleOwner(), saving -> {
            if (saving != null) {
                tvSaving.setText(String.format("₹%.2f", saving));
            } else {
                tvSaving.setText("₹0.00");
            }
        });

        mViewModel.getSpendingAnalysis().observe(getViewLifecycleOwner(), spendingData -> {
            if (spendingData != null && !spendingData.isEmpty()) {
                updateBarChart(spendingData);
            } else {
                barChart.clear();
                barChart.invalidate();
            }
        });
    }

    private void updateBarChart(Map<String, Double> spendingData) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, Double> entry : spendingData.entrySet()) {
            entries.add(new BarEntry(index, entry.getValue().floatValue()));
            labels.add(entry.getKey());
            index++;
        }

        BarDataSet dataSet = new BarDataSet(entries, "Spending");
        dataSet.setColor(Color.parseColor("#3498db"));

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.5f);

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        barChart.setData(barData);
        barChart.invalidate();
        barChart.animateY(1000);
    }
}