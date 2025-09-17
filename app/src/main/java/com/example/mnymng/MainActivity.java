package com.example.mnymng;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

import com.example.mnymng.DB.AppDatabase;
import com.example.mnymng.DB.logic.DbOperationOnStart;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private Button buttonOpenDrawer;
    private ProgressBar loadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        buttonOpenDrawer = findViewById(R.id.gp_to_home_button);
        loadingSpinner = findViewById(R.id.loading_spinner);

        // Disable button and show spinner
        buttonOpenDrawer.setEnabled(false);
        loadingSpinner.setVisibility(View.VISIBLE);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            // Background work
            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
            DbOperationOnStart dbOperationOnStart = new DbOperationOnStart(db);
            dbOperationOnStart.deleteCompletedRecurringTransactions();
            dbOperationOnStart.completePendingRecurringTransactions();

            // UI Thread work
            handler.post(() -> {
                // Enable button and hide spinner
                buttonOpenDrawer.setEnabled(true);
                loadingSpinner.setVisibility(View.GONE);

                buttonOpenDrawer.setOnClickListener(v -> {
                    Intent intent = new Intent(MainActivity.this, HomeWindow.class);
                    startActivity(intent);
                });
            });
        });
    }
}
