package com.example.mnymng.DB.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "recurring_transaction",
        primaryKeys = {"recurring_id", "transaction_id"},
        foreignKeys = {
                @ForeignKey(entity = Recurring.class,
                        parentColumns = "recurring_id",
                        childColumns = "recurring_id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Transaction.class,
                        parentColumns = "trns_id",
                        childColumns = "transaction_id",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("recurring_id"), @Index("transaction_id")})
public class RecurringTransaction {
    @ColumnInfo(name = "recurring_id")
    public long recurring_id;

    @ColumnInfo(name = "transaction_id")
    public long transaction_id;
}
