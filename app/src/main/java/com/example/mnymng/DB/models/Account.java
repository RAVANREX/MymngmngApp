package com.example.mnymng.DB.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;

import com.example.mnymng.DB.enums.AccountType;
import com.example.mnymng.DB.utils.DateConverter;


import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "accounts")
@TypeConverters(DateConverter.class)
public class Account implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "account_id")
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

    @ColumnInfo(name = "account_isPrimary")
    public String account_isPrimary;

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

    @Ignore
    public Account() {
    }

    public Account(String account_name, AccountType account_type, double account_opening_balance, double account_current_balance, double account_interest_rate, Date account_due_date, Date account_next_due_date, String account_currency, Date account_updated_date, String account_isPrimary, String account_value1, String account_value2, String account_value3, String account_value4, String account_value5) {
        this.account_name = account_name;
        this.account_type = account_type;
        this.account_opening_balance = account_opening_balance;
        this.account_current_balance = account_current_balance;
        this.account_interest_rate = account_interest_rate;
        this.account_due_date = account_due_date;
        this.account_next_due_date = account_next_due_date;
        this.account_currency = account_currency;
        this.account_updated_date = account_updated_date;
        this.account_isPrimary = account_isPrimary;
        this.account_value1 = account_value1;
        this.account_value2 = account_value2;
        this.account_value3 = account_value3;
        this.account_value4 = account_value4;
        this.account_value5 = account_value5;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public void setAccount_type(AccountType account_type) {
        this.account_type = account_type;
    }

    public void setAccount_opening_balance(double account_opening_balance) {
        this.account_opening_balance = account_opening_balance;
    }

    public void setAccount_current_balance(double account_current_balance) {
        this.account_current_balance = account_current_balance;
    }

    public void setAccount_interest_rate(double account_interest_rate) {
        this.account_interest_rate = account_interest_rate;
    }

    public void setAccount_due_date(Date account_due_date) {
        this.account_due_date = account_due_date;
    }

    public void setAccount_next_due_date(Date account_next_due_date) {
        this.account_next_due_date = account_next_due_date;
    }

    public void setAccount_currency(String account_currency) {
        this.account_currency = account_currency;
    }

    public void setAccount_updated_date(Date account_updated_date) {
        this.account_updated_date = account_updated_date;
    }

    public void setAccount_isPrimary(String account_isPrimary) {
        this.account_isPrimary = account_isPrimary;
    }

    public void setAccount_value1(String account_value1) {
        this.account_value1 = account_value1;
    }

    public void setAccount_value2(String account_value2) {
        this.account_value2 = account_value2;
    }

    public void setAccount_value3(String account_value3) {
        this.account_value3 = account_value3;
    }

    public void setAccount_value4(String account_value4) {
        this.account_value4 = account_value4;
    }

    public void setAccount_value5(String account_value5) {
        this.account_value5 = account_value5;
    }

    public long getAccount_id() {
        return account_id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public AccountType getAccount_type() {
        return account_type;
    }

    public double getAccount_opening_balance() {
        return account_opening_balance;
    }

    public double getAccount_current_balance() {
        return account_current_balance;
    }

    public double getAccount_interest_rate() {
        return account_interest_rate;
    }

    public Date getAccount_due_date() {
        return account_due_date;
    }

    public Date getAccount_next_due_date() {
        return account_next_due_date;
    }

    public String getAccount_currency() {
        return account_currency;
    }

    public Date getAccount_updated_date() {
        return account_updated_date;
    }

    public String getAccount_isPrimary() {
        return account_isPrimary;
    }

    public String getAccount_value1() {
        return account_value1;
    }

    public String getAccount_value2() {
        return account_value2;
    }

    public String getAccount_value3() {
        return account_value3;
    }

    public String getAccount_value4() {
        return account_value4;
    }

    public String getAccount_value5() {
        return account_value5;
    }
}
