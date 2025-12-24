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


    private final MutableLiveData<String> period = new MutableLiveData<>();
    private final LiveData<Map<String, Double>> spendingAnalysis;

    public MainViewModel(Application application) {
        super(application);
        mRepository = new DataRepository(application);

        mIncome = Transformations.switchMap(period, p -> mRepository.getIncome(p));
        mExpenses = Transformations.switchMap(period, p -> mRepository.getExpenses(p));


        spendingAnalysis = Transformations.switchMap(period, p -> mRepository.getSpendingAnalysis(p));
    }

    public LiveData<Double> getIncome() {
        return mIncome;
    }

    public LiveData<Double> getExpenses() {
        return mExpenses;
    }



    public void setPeriod(String newPeriod) {
        period.setValue(newPeriod);
    }

    public LiveData<Map<String, Double>> getSpendingAnalysis() {
        return spendingAnalysis;
    }
}