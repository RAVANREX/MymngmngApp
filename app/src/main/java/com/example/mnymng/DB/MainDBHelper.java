package com.example.mnymng.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

public class MainDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "MainDBHelper";

    private static final String DATABASE_NAME = "main_money_manager.db";
    private static final int DATABASE_VERSION = 1;

    private Context context;

    // Schema (attached database) names
    public static final String SCHEMA_DASHBOARD = "DashboardDB";
    public static final String SCHEMA_INCOME = "IncomeDB";
    public static final String SCHEMA_EXPENSE = "ExpenseDB";
    public static final String SCHEMA_MTM = "MarkToMarketDB";
    public static final String SCHEMA_INVESTMENTS = "InvestmentsDB";
    public static final String SCHEMA_LEND = "LendDB";
    public static final String SCHEMA_LOAN = "LoanDB";
    public static final String SCHEMA_ASSET = "AssetDB";
    public static final String SCHEMA_TRIP = "TripDB";

    private static final String[] ATTACHED_DB_NAMES = {
            "dashboard.db",
            "income.db",
            "expense.db",
            "mark_to_market.db",
            "investments.db",
            "lend.db",
            "loan.db",
            "asset.db",
            "trip.db"
    };

    private static final String[] ATTACHED_DB_ALIASES = {
            SCHEMA_DASHBOARD,
            SCHEMA_INCOME,
            SCHEMA_EXPENSE,
            SCHEMA_MTM,
            SCHEMA_INVESTMENTS,
            SCHEMA_LEND,
            SCHEMA_LOAN,
            SCHEMA_ASSET,
            SCHEMA_TRIP
    };

    public MainDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Main database tables can be created here if needed
        // For now, we are focusing on attaching other databases as schemas
        Log.i(TAG, "Main database created.");
        // We will call attachDatabases in onOpen, as onCreate is only called once.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades for the main database
        // Potentially detach and re-attach databases if schema structures change significantly.
        Log.w(TAG, "Upgrading main database from version " + oldVersion + " to " + newVersion);
        // For simplicity, we might drop and recreate, or implement more complex migration.
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            attachDatabases(db);
        }
    }

    private void attachDatabases(SQLiteDatabase db) {
        File dbPathDir = context.getDatabasePath("dummy").getParentFile();
        if (dbPathDir == null) {
            Log.e(TAG, "Could not retrieve database directory path.");
            return;
        }
        if (!dbPathDir.exists()) {
            dbPathDir.mkdirs();
        }

        for (int i = 0; i < ATTACHED_DB_NAMES.length; i++) {
            String dbName = ATTACHED_DB_NAMES[i];
            String dbAlias = ATTACHED_DB_ALIASES[i];
            File dbFile = new File(dbPathDir, dbName);

            // Ensure the individual database file exists, SQLite will create it on attach if not.
            // However, to manage their schemas (tables), we'd typically have separate helpers
            // or create them on first access after attaching.

            String attachSql = "ATTACH DATABASE '" + dbFile.getAbsolutePath() + "' AS " + dbAlias + ";";
            try {
                db.execSQL(attachSql);
                Log.i(TAG, "Attached database: " + dbName + " AS " + dbAlias);

                // Initialize schema for the attached database if it's newly created.
                // This is a good place to call a method to create tables for this specific "schema" (attached DB).
                // For example: createTablesForSchema(db, dbAlias);
            } catch (Exception e) {
                Log.e(TAG, "Failed to attach database: " + dbName + " AS " + dbAlias, e);
            }
        }
    }

    // Example method to initialize tables for a given attached schema
    // You would call this after attaching each database.
    /*
    private void createTablesForSchema(SQLiteDatabase db, String schemaAlias) {
        if (SCHEMA_INCOME.equals(schemaAlias)) {
            // db.execSQL("CREATE TABLE IF NOT EXISTS " + schemaAlias + ".income_table (id INTEGER PRIMARY KEY, amount REAL, description TEXT);");
            Log.i(TAG, "Tables for " + schemaAlias + " checked/created.");
        } else if (SCHEMA_EXPENSE.equals(schemaAlias)) {
            // db.execSQL("CREATE TABLE IF NOT EXISTS " + schemaAlias + ".expense_table (id INTEGER PRIMARY KEY, amount REAL, description TEXT);");
            Log.i(TAG, "Tables for " + schemaAlias + " checked/created.");
        }
        // ... and so on for other schemas
    }
    */

    // It's generally better to have separate DBHelper classes for each "schema" (database file)
    // to manage their specific tables and versions.
    // The MainDBHelper would then focus solely on attaching them.
}
