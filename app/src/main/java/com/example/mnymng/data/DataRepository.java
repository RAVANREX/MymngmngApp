package com.example.mnymng.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.mnymng.DB.AppDatabase;
import com.example.mnymng.DB.dao.RecurringDao;
import com.example.mnymng.DB.models.Recurring;

import java.util.List;
import java.util.stream.Collectors;

public class DataRepository {

    private RecurringDao mRecurringDao;
    private LiveData<List<Recurring>> mAllRecurring;

    public DataRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mRecurringDao = db.recurringDao();
        mAllRecurring = mRecurringDao.getAllRecurringTransactions();
    }

    public LiveData<List<Recurring>> getAllRecurring() {
        return mAllRecurring;
    }

    public LiveData<Double> getIncome() {
        return Transformations.map(mAllRecurring, recurrings -> 
            recurrings.stream()
                .filter(r -> r.getRecurring_amount() > 0)
                .mapToDouble(Recurring::getRecurring_amount)
                .sum());
    }

    public LiveData<Double> getExpenses() {
        return Transformations.map(mAllRecurring, recurrings -> 
            recurrings.stream()
                .filter(r -> r.getRecurring_amount() < 0)
                .mapToDouble(r -> Math.abs(r.getRecurring_amount()))
                .sum());
    }

    public LiveData<Double> getSavings() {
        return Transformations.map(mAllRecurring, recurrings -> {
            double income = recurrings.stream()
                .filter(r -> r.getRecurring_amount() > 0)
                .mapToDouble(Recurring::getRecurring_amount)
                .sum();
            double expenses = recurrings.stream()
                .filter(r -> r.getRecurring_amount() < 0)
                .mapToDouble(r -> Math.abs(r.getRecurring_amount()))
                .sum();
            return income - expenses;
        });
    }
    
    //This is a placeholder. A more sophisticated implementation is needed here based on the actual data and desired logic
    public LiveData<java.util.Map<String, Double>> getSpendingAnalysis(String period) {
        return Transformations.map(mAllRecurring, recurrings -> {
            // The logic to group by week, month, or year should be implemented here.
            // For now, we'll just return the spending by category.
            return recurrings.stream()
                .filter(r -> r.getRecurring_amount() < 0)
                .collect(Collectors.groupingBy(r -> "Category " + r.getCata_id(), 
                    Collectors.summingDouble(r -> Math.abs(r.getRecurring_amount()))));
        });
    }
}
