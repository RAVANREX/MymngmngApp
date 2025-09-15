package com.example.mnymng.DB.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.mnymng.DB.enums.PlanPriority;
import com.example.mnymng.DB.enums.PlanStatus;
import com.example.mnymng.DB.utils.DateConverter;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "future_plan",
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
public class FuturePlan implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long fplan_id;

    @ColumnInfo(name = "fplan_name")
    public String fplan_name;

    @ColumnInfo(name = "account_id")
    public long account_id;

    @ColumnInfo(name = "cata_id")
    public Long cata_id; // Allow NULL

    @ColumnInfo(name = "fplan_etamount")
    public double fplan_etamount;

    @ColumnInfo(name = "fplan_tgdt")
    public Date fplan_tgdt;

    @ColumnInfo(name = "fplan_svamount")
    public double fplan_svamount;

    @ColumnInfo(name = "fplan_actual_amount")
    public double fplan_actual_amount;

    @ColumnInfo(name = "fplan_notes")
    public String fplan_notes;

    @ColumnInfo(name = "fplan_priority")
    public PlanPriority fplan_priority;

    @ColumnInfo(name = "fplan_status")
    public PlanStatus fplan_status;
    
    @ColumnInfo(name = "fplan_value1")
    public String fplan_value1;
    @ColumnInfo(name = "fplan_value2")
    public String fplan_value2;
    @ColumnInfo(name = "fplan_value3")
    public String fplan_value3;
    @ColumnInfo(name = "fplan_value4")
    public String fplan_value4;
    @ColumnInfo(name = "fplan_value5")
    public String fplan_value5;

    public FuturePlan(String fplan_name, long account_id, Long cata_id, double fplan_etamount, Date fplan_tgdt, double fplan_svamount, double fplan_actual_amount, String fplan_notes, PlanPriority fplan_priority, PlanStatus fplan_status, String fplan_value1, String fplan_value3, String fplan_value2, String fplan_value4, String fplan_value5) {
        this.fplan_name = fplan_name;
        this.account_id = account_id;
        this.cata_id = cata_id;
        this.fplan_etamount = fplan_etamount;
        this.fplan_tgdt = fplan_tgdt;
        this.fplan_svamount = fplan_svamount;
        this.fplan_actual_amount = fplan_actual_amount;
        this.fplan_notes = fplan_notes;
        this.fplan_priority = fplan_priority;
        this.fplan_status = fplan_status;
        this.fplan_value1 = fplan_value1;
        this.fplan_value3 = fplan_value3;
        this.fplan_value2 = fplan_value2;
        this.fplan_value4 = fplan_value4;
        this.fplan_value5 = fplan_value5;
    }
}
