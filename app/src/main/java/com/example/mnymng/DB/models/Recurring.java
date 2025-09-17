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
                        childColumns = "account_id"),
                @ForeignKey(entity = Category.class,
                        parentColumns = "cata_id",
                        childColumns = "cata_id"), // Or CASCADE
                @ForeignKey(entity = Transaction.class,
                        parentColumns = "trns_id",
                        childColumns = "last_transaction_id") // Or CASCADE
        })
@TypeConverters(DateConverter.class)
public class Recurring implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long recurring_id;
    @ColumnInfo(name = "account_id", index = true)
    public long account_id;
    @ColumnInfo(name = "cata_id", index = true)
    public Long cata_id; // Allow NULL
    @ColumnInfo(name = "last_transaction_id", index = true)
    public Long lastTransactionId;
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

    public Recurring(long account_id, Long cata_id, Long lastTransactionId, double recurring_amount, RecurringFrequency recurring_frequency, Date recurring_sdt, Date recurring_edt,
                     Date recurring_nxt_duedt, boolean recurring_alarm_rem, boolean recurring_is_auto, String recurring_value1, String recurring_value2, String recurring_value3,
                     String recurring_value4, String recurring_value5) {
        this.account_id = account_id;
        this.cata_id = cata_id;
        this.lastTransactionId = lastTransactionId;
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

    public long getRecurring_id() {
        return recurring_id;
    }

    public void setRecurring_id(long recurring_id) {
        this.recurring_id = recurring_id;
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

    public Long getLastTransactionId() {
        return lastTransactionId;
    }

    public void setLastTransactionId(Long lastTransactionId) {
        this.lastTransactionId = lastTransactionId;
    }

    public double getRecurring_amount() {
        return recurring_amount;
    }

    public void setRecurring_amount(double recurring_amount) {
        this.recurring_amount = recurring_amount;
    }

    public RecurringFrequency getRecurring_frequency() {
        return recurring_frequency;
    }

    public void setRecurring_frequency(RecurringFrequency recurring_frequency) {
        this.recurring_frequency = recurring_frequency;
    }

    public Date getRecurring_sdt() {
        return recurring_sdt;
    }

    public void setRecurring_sdt(Date recurring_sdt) {
        this.recurring_sdt = recurring_sdt;
    }

    public Date getRecurring_edt() {
        return recurring_edt;
    }

    public void setRecurring_edt(Date recurring_edt) {
        this.recurring_edt = recurring_edt;
    }

    public Date getRecurring_nxt_duedt() {
        return recurring_nxt_duedt;
    }

    public void setRecurring_nxt_duedt(Date recurring_nxt_duedt) {
        this.recurring_nxt_duedt = recurring_nxt_duedt;
    }

    public boolean isRecurring_alarm_rem() {
        return recurring_alarm_rem;
    }

    public void setRecurring_alarm_rem(boolean recurring_alarm_rem) {
        this.recurring_alarm_rem = recurring_alarm_rem;
    }

    public boolean isRecurring_is_auto() {
        return recurring_is_auto;
    }

    public void setRecurring_is_auto(boolean recurring_is_auto) {
        this.recurring_is_auto = recurring_is_auto;
    }

    public String getRecurring_value1() {
        return recurring_value1;
    }

    public void setRecurring_value1(String recurring_value1) {
        this.recurring_value1 = recurring_value1;
    }

    public String getRecurring_value2() {
        return recurring_value2;
    }

    public void setRecurring_value2(String recurring_value2) {
        this.recurring_value2 = recurring_value2;
    }

    public String getRecurring_value3() {
        return recurring_value3;
    }

    public void setRecurring_value3(String recurring_value3) {
        this.recurring_value3 = recurring_value3;
    }

    public String getRecurring_value4() {
        return recurring_value4;
    }

    public void setRecurring_value4(String recurring_value4) {
        this.recurring_value4 = recurring_value4;
    }

    public String getRecurring_value5() {
        return recurring_value5;
    }

    public void setRecurring_value5(String recurring_value5) {
        this.recurring_value5 = recurring_value5;
    }
}
