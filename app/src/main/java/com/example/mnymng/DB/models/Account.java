package com.example.mnymng.DB.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;

import com.example.mnymng.DB.enums.AccountType;
import com.example.mnymng.DB.utils.DateConverter;


import java.util.Date;

@Entity(tableName = "accounts")
@TypeConverters(DateConverter.class)
public class Account {
    @PrimaryKey(autoGenerate = true)
    public long account_id;

    @ColumnInfo(name = "account_name")
    public String account_name;

    @ColumnInfo(name = "account_type")
    public AccountType account_type; // TODO: Replace with Enum (Bank/Wallet/Loan/Lending/Credit Card/..etc ) -> Done

    @ColumnInfo(name = "account_opening_balance")
    public double account_opening_balance;

    @ColumnInfo(name = "account_current_balance")
    public double account_current_balance;

    @ColumnInfo(name = "account_interest_rate")
    public double account_interest_rate;

    @ColumnInfo(name = "account_due_date")
    public Date account_due_date;

    @ColumnInfo(name = "account_next_due_date")
    public Date account_next_due_date;

    @ColumnInfo(name = "account_currency")
    public String account_currency;

    @ColumnInfo(name = "account_updated_date")
    public Date account_updated_date;

    @ColumnInfo(name = "account_value1")
    public String account_value1;

    @ColumnInfo(name = "account_value2")
    public String account_value2;

    @ColumnInfo(name = "account_value3")
    public String account_value3;

    @ColumnInfo(name = "account_value4")
    public String account_value4;

    @ColumnInfo(name = "account_value5")
    public String account_value5;

    public Account(long account_id, String account_name, AccountType account_type, double account_opening_balance, double account_current_balance, double account_interest_rate, Date account_due_date, Date account_next_due_date, String account_currency, Date account_updated_date) {
        this.account_id = account_id;
        this.account_name = account_name;
        this.account_type = account_type;
        this.account_opening_balance = account_opening_balance;
        this.account_current_balance = account_current_balance;
        this.account_interest_rate = account_interest_rate;
        this.account_due_date = account_due_date;
        this.account_next_due_date = account_next_due_date;
        this.account_currency = account_currency;
        this.account_updated_date = account_updated_date;
    }
}
