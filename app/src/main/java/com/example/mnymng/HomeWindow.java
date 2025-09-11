package com.example.mnymng;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mnymng.fragments.BottomDrawer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class HomeWindow extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private DrawerLayout drawerLayout;
    private OnBackPressedCallback onBackPressedCallback;

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
        }



        drawerLayout = findViewById(R.id.activity_home); // Assuming R.id.fragment_home is your DrawerLayout in fragment_home.xml

        NavigationView navView = findViewById(R.id.nav_view);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        ViewGroup.LayoutParams params = navView.getLayoutParams();
        params.width = screenWidth / 2;
        navView.setLayoutParams(params);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();

            // Assuming R.id.fragment_home is a top-level destination if it's also the drawer ID.
            // Or, it might be a different ID if fragment_home is a layout for a specific screen
            // that is part of the navigation graph.
            appBarConfiguration = new AppBarConfiguration.Builder(
                    /* Assuming these are top-level destinations */
                    R.id.fragment_dashboard, R.id.fragment_expense,
                    R.id.fragment_mtm, R.id.fragment_income,
                    R.id.fragment_investments, R.id.fragment_lend,
                    R.id.fragment_loan, R.id.fragment_asset, R.id.fragment_trip)
                    .setOpenableLayout(drawerLayout)
                    .build();

            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(navView, navController);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Setup OnBackPressedDispatcher
        onBackPressedCallback = new OnBackPressedCallback(false /* initially disabled */) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                // The callback will be disabled by the drawer listener when the drawer closes.
            }
        };
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                onBackPressedCallback.setEnabled(true);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                onBackPressedCallback.setEnabled(false);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController != null && appBarConfiguration != null && NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
