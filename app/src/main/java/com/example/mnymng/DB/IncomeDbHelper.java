package com.example.mnymng.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class IncomeDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "income.db";
    private static final int DATABASE_VERSION = 1;

    // Example Table: income_transactions
    public static final String TABLE_INCOME_TRANSACTIONS = "income_transactions";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DATE = "date"; // Store as TEXT in ISO8601 format ("YYYY-MM-DD HH:MM:SS.SSS") or INTEGER as Unix timestamp
    public static final String COLUMN_SOURCE_ID = "source_id"; // Foreign key to an income_sources table if you have one

    private static final String TABLE_CREATE_INCOME_TRANSACTIONS = 
        "CREATE TABLE " + TABLE_INCOME_TRANSACTIONS + " (" +
        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_AMOUNT + " REAL NOT NULL, " +
        COLUMN_DESCRIPTION + " TEXT, " +
        COLUMN_DATE + " TEXT NOT NULL, " +
        COLUMN_SOURCE_ID + " INTEGER" +
        ");";

    public IncomeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_INCOME_TRANSACTIONS);
        // Add other table creations for the income schema here
        // Example: db.execSQL(TABLE_CREATE_INCOME_SOURCES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades here. For simplicity, we'll drop and recreate.
        // In a production app, you'd implement proper migration logic.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME_TRANSACTIONS);
        onCreate(db);
    }
}
