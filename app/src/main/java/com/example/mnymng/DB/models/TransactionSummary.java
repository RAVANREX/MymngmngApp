package com.example.mnymng.DB.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.mnymng.DB.enums.TransactionType;
import com.example.mnymng.DB.utils.DateConverter;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "transaction_summary",
        foreignKeys = {
                @ForeignKey(entity = Account.class,
                        parentColumns = "account_id",
                        childColumns = "account_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Category.class,
                        parentColumns = "cata_id",
                        childColumns = "cata_id",
                        onDelete = ForeignKey.SET_NULL), // Or CASCADE, depending on desired behavior
                @ForeignKey(entity = Recurring.class,
                        parentColumns = "recurring_id",
                        childColumns = "recurring_id",
                        onDelete = ForeignKey.SET_NULL) // Or CASCADE
        })
@TypeConverters(DateConverter.class)
public class TransactionSummary implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long trnsum_id;

    @ColumnInfo(name = "account_id")
    public long account_id;

    @ColumnInfo(name = "cata_id")
    public Long cata_id; // Allow NULL if category can be optional or deleted

    @ColumnInfo(name = "trnsum_type")
    public TransactionType trnsum_type;

    @ColumnInfo(name = "trnsum_amount")
    public double trnsum_amount;

    @ColumnInfo(name = "trnsum_last_updt")
    public Date trnsum_last_updt;

    @ColumnInfo(name = "recurring_id")
    public Long recurring_id; // Allow NULL if recurring can be optional or deleted
    
    @ColumnInfo(name = "trnsum_value1")
    public String trnsum_value1;
    @ColumnInfo(name = "trnsum_value2")
    public String trnsum_value2;
    @ColumnInfo(name = "trnsum_value3")
    public String trnsum_value3;
    @ColumnInfo(name = "trnsum_value4")
    public String trnsum_value4;
    @ColumnInfo(name = "trnsum_value5")
    public String trnsum_value5;

    public TransactionSummary(long trnsum_id, long account_id, Long cata_id, TransactionType trnsum_type, double trnsum_amount, Date trnsum_last_updt, Long recurring_id) {
        this.trnsum_id = trnsum_id;
        this.account_id = account_id;
        this.cata_id = cata_id;
        this.trnsum_type = trnsum_type;
        this.trnsum_amount = trnsum_amount;
        this.trnsum_last_updt = trnsum_last_updt;
        this.recurring_id = recurring_id;
    }
}
