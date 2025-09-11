package com.example.mnymng.DB.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.ForeignKey;
import androidx.room.TypeConverters;

import com.example.mnymng.DB.enums.TransactionType;
import com.example.mnymng.DB.utils.DateConverter;

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
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    public long trns_id;

    @ColumnInfo(name = "account_id", index = true)
    public long account_id;

    @ColumnInfo(name = "cata_id", index = true)
    public Long cata_id; // Changed to Long to allow NULL if category is deleted

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
    
    

    public Transaction(long trns_id, long account_id, Long cata_id, TransactionType trns_type, double trns_amount, Date trns_date, String trns_note, Long recurring_id, Long related_account_id) {
        this.trns_id = trns_id;
        this.account_id = account_id;
        this.cata_id = cata_id;
        this.trns_type = trns_type;
        this.trns_amount = trns_amount;
        this.trns_date = trns_date;
        this.trns_note = trns_note;
        this.recurring_id = recurring_id;
        this.related_account_id = related_account_id;
    }
}
