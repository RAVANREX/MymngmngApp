package com.example.mnymng.DB.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.mnymng.DB.enums.CategoryType;
import com.example.mnymng.DB.enums.RecurringFrequency;
import com.example.mnymng.DB.utils.DateConverter;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "recurring",
        foreignKeys = {
                @ForeignKey(entity = Account.class,
                        parentColumns = "account_id",
                        childColumns = "account_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Category.class,
                        parentColumns = "cata_id",
                        childColumns = "cata_id",
                        onDelete = ForeignKey.SET_NULL) // Or CASCADE
        })
@TypeConverters(DateConverter.class)
public class Recurring implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long recurring_id;

    @ColumnInfo(name = "account_id")
    public long account_id;

    @ColumnInfo(name = "cata_id")
    public Long cata_id; // Allow NULL

    @ColumnInfo(name = "recurring_amount")
    public double recurring_amount;

    @ColumnInfo(name = "recurring_frequency")
    public RecurringFrequency recurring_frequency;

    @ColumnInfo(name = "recurring_sdt")
    public Date recurring_sdt;

    @ColumnInfo(name = "recurring_edt")
    public Date recurring_edt;

    @ColumnInfo(name = "recurring_nxt_duedt")
    public Date recurring_nxt_duedt;

    @ColumnInfo(name = "recurring_alarm_rem")
    public boolean recurring_alarm_rem;

    @ColumnInfo(name = "recurring_is_auto")
    public boolean recurring_is_auto;
    
    @ColumnInfo(name = "recurring_value1")
    public String recurring_value1;
    @ColumnInfo(name = "recurring_value2")
    public String recurring_value2;
    @ColumnInfo(name = "recurring_value3")
    public String recurring_value3;
    @ColumnInfo(name = "recurring_value4")
    public String recurring_value4;
    @ColumnInfo(name = "recurring_value5")
    public String recurring_value5;

    public Recurring(long account_id, Long cata_id, double recurring_amount, RecurringFrequency recurring_frequency, Date recurring_sdt, Date recurring_edt, Date recurring_nxt_duedt, boolean recurring_alarm_rem, boolean recurring_is_auto, String recurring_value1, String recurring_value2, String recurring_value3, String recurring_value4, String recurring_value5) {
        this.account_id = account_id;
        this.cata_id = cata_id;
        this.recurring_amount = recurring_amount;
        this.recurring_frequency = recurring_frequency;
        this.recurring_sdt = recurring_sdt;
        this.recurring_edt = recurring_edt;
        this.recurring_nxt_duedt = recurring_nxt_duedt;
        this.recurring_alarm_rem = recurring_alarm_rem;
        this.recurring_is_auto = recurring_is_auto;
        this.recurring_value1 = recurring_value1;
        this.recurring_value2 = recurring_value2;
        this.recurring_value3 = recurring_value3;
        this.recurring_value4 = recurring_value4;
        this.recurring_value5 = recurring_value5;
    }
}
