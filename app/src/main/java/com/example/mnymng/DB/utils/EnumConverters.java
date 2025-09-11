package com.example.mnymng.DB.utils;

import androidx.room.TypeConverter;
import com.example.mnymng.DB.enums.AccountType;
import com.example.mnymng.DB.enums.CategoryType;
import com.example.mnymng.DB.enums.TransactionType;
import com.example.mnymng.DB.enums.RecurringFrequency;
import com.example.mnymng.DB.enums.PlanPriority;
import com.example.mnymng.DB.enums.PlanStatus;

public class EnumConverters {

    // AccountType Converters
    @TypeConverter
    public static String fromAccountType(AccountType accountType) {
        return accountType == null ? null : accountType.name();
    }
    @TypeConverter
    public static AccountType toAccountType(String name) {
        return name == null ? null : AccountType.valueOf(name);
    }

    // CategoryType Converters
    @TypeConverter
    public static String fromCategoryType(CategoryType categoryType) {
        return categoryType == null ? null : categoryType.name();
    }
    @TypeConverter
    public static CategoryType toCategoryType(String name) {
        return name == null ? null : CategoryType.valueOf(name);
    }

    // TransactionType Converters
    @TypeConverter
    public static String fromTransactionType(TransactionType transactionType) {
        return transactionType == null ? null : transactionType.name();
    }
    @TypeConverter
    public static TransactionType toTransactionType(String name) {
        return name == null ? null : TransactionType.valueOf(name);
    }

    // RecurringFrequency Converters
    @TypeConverter
    public static String fromRecurringFrequency(RecurringFrequency recurringFrequency) {
        return recurringFrequency == null ? null : recurringFrequency.name();
    }
    @TypeConverter
    public static RecurringFrequency toRecurringFrequency(String name) {
        return name == null ? null : RecurringFrequency.valueOf(name);
    }

    // PlanPriority Converters
    @TypeConverter
    public static String fromPlanPriority(PlanPriority planPriority) {
        return planPriority == null ? null : planPriority.name();
    }
    @TypeConverter
    public static PlanPriority toPlanPriority(String name) {
        return name == null ? null : PlanPriority.valueOf(name);
    }

    // PlanStatus Converters
    @TypeConverter
    public static String fromPlanStatus(PlanStatus planStatus) {
        return planStatus == null ? null : planStatus.name();
    }
    @TypeConverter
    public static PlanStatus toPlanStatus(String name) {
        return name == null ? null : PlanStatus.valueOf(name);
    }
}
