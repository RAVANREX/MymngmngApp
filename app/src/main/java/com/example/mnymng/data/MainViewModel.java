package com.example.mnymng.data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.Map;

public class MainViewModel extends AndroidViewModel {

    private final DataRepository mRepository;
    private final LiveData<Double> mIncome;
    private final LiveData<Double> mExpenses;
    private final LiveData<Double> mSavings;

    private final MutableLiveData<String> period = new MutableLiveData<>();
    private final LiveData<Map<String, Double>> spendingAnalysis;

    public MainViewModel(Application application) {
        super(application);
        mRepository = new DataRepository(application);
        mIncome = mRepository.getIncome();
        mExpenses = mRepository.getExpenses();
        mSavings = mRepository.getSavings();

        spendingAnalysis = Transformations.switchMap(period, p -> mRepository.getSpendingAnalysis(p));
    }

    public LiveData<Double> getIncome() {
        return mIncome;
    }

    public LiveData<Double> getExpenses() {
        return mExpenses;
    }

    public LiveData<Double> getSavings() {
        return mSavings;
    }

    public void setPeriod(String newPeriod) {
        period.setValue(newPeriod);
    }

    public LiveData<Map<String, Double>> getSpendingAnalysis() {
        return spendingAnalysis;
    }
}