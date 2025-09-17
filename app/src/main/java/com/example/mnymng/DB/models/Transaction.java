package com.example.mnymng.DB.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.ForeignKey;
import androidx.room.TypeConverters;

import com.example.mnymng.DB.enums.TransactionType;
import com.example.mnymng.DB.utils.DateConverter;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "transactions",
        foreignKeys = {
                @ForeignKey(entity = Account.class,
                        parentColumns = "account_id",
                        childColumns = "account_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Category.class,
                        parentColumns = "cata_id",
                        childColumns = "cata_id",
                        onDelete = ForeignKey.SET_NULL),
                @ForeignKey(entity = Account.class, // For related_account_id
                        parentColumns = "account_id",
                        childColumns = "related_account_id",
                        onDelete = ForeignKey.SET_NULL)
                // TODO: Add ForeignKey for recurring_id if Recurring table/entity exists
        })
@TypeConverters(DateConverter.class)
public class Transaction implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long trns_id;
    @ColumnInfo(name = "account_id", index = true)
    public long account_id;
    @ColumnInfo(name = "cata_id", index = true)
    public Long cata_id; // Changed to Long to allow NULL if category is deleted
    @ColumnInfo(name = "trns_name")
    public String trns_name;
    @ColumnInfo(name = "trns_type")
    public TransactionType trns_type; // TODO: Replace with Enum (credit/debit/transfer) -> Done
    @ColumnInfo(name = "trns_amount")
    public double trns_amount;
    @ColumnInfo(name = "trns_date")
    public Date trns_date;
    @ColumnInfo(name = "trns_note")
    public String trns_note;
    @ColumnInfo(name = "recurring_id", index = true)
    public Long recurring_id; // Changed to Long to allow NULL
    @ColumnInfo(name = "related_account_id", index = true)
    public Long related_account_id; // Changed to Long to allow NULL
    @ColumnInfo(name = "trns_value1")
    public String trns_value1;
    @ColumnInfo(name = "trns_value2")
    public String trns_value2;
    @ColumnInfo(name = "trns_value3")
    public String trns_value3;
    @ColumnInfo(name = "trns_value4")
    public String trns_value4;
    @ColumnInfo(name = "trns_value5")
    public String trns_value5;

    @Ignore
    public Transaction(){}

    public Transaction(long account_id, Long cata_id, String trns_name, TransactionType trns_type, double trns_amount, Date trns_date, String trns_note, Long recurring_id, Long related_account_id, String trns_value1, String trns_value2, String trns_value3, String trns_value4, String trns_value5) {
        this.account_id = account_id;
        this.cata_id = cata_id;
        this.trns_name = trns_name;
        this.trns_type = trns_type;
        this.trns_amount = trns_amount;
        this.trns_date = trns_date;
        this.trns_note = trns_note;
        this.recurring_id = recurring_id;
        this.related_account_id = related_account_id;
        this.trns_value1 = trns_value1;
        this.trns_value2 = trns_value2;
        this.trns_value3 = trns_value3;
        this.trns_value4 = trns_value4;
        this.trns_value5 = trns_value5;
    }

    public long getTrns_id() {
        return trns_id;
    }

    public void setTrns_id(long trns_id) {
        this.trns_id = trns_id;
    }

    public long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public Long getCata_id() {
        return cata_id;
    }

    public void setCata_id(Long cata_id) {
        this.cata_id = cata_id;
    }

    public String getTrns_name() {
        return trns_name;
    }

    public void setTrns_name(String trns_name) {
        this.trns_name = trns_name;
    }

    public TransactionType getTrns_type() {
        return trns_type;
    }

    public void setTrns_type(TransactionType trns_type) {
        this.trns_type = trns_type;
    }

    public double getTrns_amount() {
        return trns_amount;
    }

    public void setTrns_amount(double trns_amount) {
        this.trns_amount = trns_amount;
    }

    public Date getTrns_date() {
        return trns_date;
    }

    public void setTrns_date(Date trns_date) {
        this.trns_date = trns_date;
    }

    public String getTrns_note() {
        return trns_note;
    }

    public void setTrns_note(String trns_note) {
        this.trns_note = trns_note;
    }

    public Long getRecurring_id() {
        return recurring_id;
    }

    public void setRecurring_id(Long recurring_id) {
        this.recurring_id = recurring_id;
    }

    public Long getRelated_account_id() {
        return related_account_id;
    }

    public void setRelated_account_id(Long related_account_id) {
        this.related_account_id = related_account_id;
    }

    public String getTrns_value1() {
        return trns_value1;
    }

    public void setTrns_value1(String trns_value1) {
        this.trns_value1 = trns_value1;
    }

    public String getTrns_value2() {
        return trns_value2;
    }

    public void setTrns_value2(String trns_value2) {
        this.trns_value2 = trns_value2;
    }

    public String getTrns_value3() {
        return trns_value3;
    }

    public void setTrns_value3(String trns_value3) {
        this.trns_value3 = trns_value3;
    }

    public String getTrns_value4() {
        return trns_value4;
    }

    public void setTrns_value4(String trns_value4) {
        this.trns_value4 = trns_value4;
    }

    public String getTrns_value5() {
        return trns_value5;
    }

    public void setTrns_value5(String trns_value5) {
        this.trns_value5 = trns_value5;
    }
}
