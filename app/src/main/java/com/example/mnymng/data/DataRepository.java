package com.example.mnymng.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;

import com.example.mnymng.DB.AppDatabase;
import com.example.mnymng.DB.dao.CategoryDao;
import com.example.mnymng.DB.dao.RecurringDao;
import com.example.mnymng.DB.dao.TransactionDao;
import com.example.mnymng.DB.models.Category;
import com.example.mnymng.DB.models.Recurring;
import com.example.mnymng.DB.models.Transaction;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataRepository {

    private RecurringDao mRecurringDao;
    private TransactionDao mTransactionDao;
    private CategoryDao mCategoryDao;
    private LiveData<List<Recurring>> mAllRecurring;
    private LiveData<List<Transaction>> mAllTransactions;
    private LiveData<List<Category>> mAllCategories;


    public DataRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mRecurringDao = db.recurringDao();
        mTransactionDao = db.transactionDao();
        mCategoryDao = db.categoryDao();
        mAllRecurring = mRecurringDao.getAllRecurringTransactions();
        mAllTransactions = mTransactionDao.getAllTransactions();
        mAllCategories = mCategoryDao.getAllCategories();
    }

    public LiveData<List<Recurring>> getAllRecurring() {
        return mAllRecurring;
    }

    public LiveData<Double> getIncome() {
        return getIncome("monthly");
    }

    public LiveData<Double> getIncome(String period) {
        return Transformations.map(mAllTransactions, transactions -> {
            Date startDate = getStartDate(period);
            return transactions.stream()
                    .filter(t -> t.trns_date.after(startDate) && t.trns_amount > 0)
                    .mapToDouble(Transaction::getTrns_amount)
                    .sum();
        });
    }


    public LiveData<Double> getExpenses(String period) {
        return Transformations.map(mAllTransactions, transactions -> {
            Date startDate = getStartDate(period);
            return transactions.stream()
                    .filter(t -> t.trns_date.after(startDate) && t.trns_amount < 0)
                    .mapToDouble(Transaction::getTrns_amount)
                    .sum();
        });
    }


    public LiveData<Map<String, Double>> getSpendingAnalysis(String period) {
        MediatorLiveData<Map<String, Double>> result = new MediatorLiveData<>();

        result.addSource(mAllTransactions, transactions -> {
            List<Category> categories = mAllCategories.getValue();
            if (categories != null) {
                result.setValue(calculateSpending(transactions, categories, period));
            }
        });

        result.addSource(mAllCategories, categories -> {
            List<Transaction> transactions = mAllTransactions.getValue();
            if (transactions != null) {
                result.setValue(calculateSpending(transactions, categories, period));
            }
        });

        return result;
    }

    private Map<String, Double> calculateSpending(List<Transaction> transactions, List<Category> categories, String period) {
        Date startDate = getStartDate(period);
        Map<Long, String> categoryIdToNameMap = categories.stream()
                .collect(Collectors.toMap(Category::getCata_id, Category::getCata_name));

        return transactions.stream()
                .filter(t -> t.trns_date.after(startDate) && t.trns_amount < 0)
                .collect(Collectors.groupingBy(t -> categoryIdToNameMap.getOrDefault(t.getCata_id(), "Unknown"),
                        Collectors.summingDouble(t -> Math.abs(t.getTrns_amount()))));
    }

    private Date getStartDate(String period) {
        Calendar cal = Calendar.getInstance();
        switch (period) {
            case "weekly":
                cal.add(Calendar.WEEK_OF_YEAR, -1);
                break;
            case "monthly":
                cal.add(Calendar.MONTH, -1);
                break;
            case "yearly":
                cal.add(Calendar.YEAR, -1);
                break;
        }
        return cal.getTime();
    }
}