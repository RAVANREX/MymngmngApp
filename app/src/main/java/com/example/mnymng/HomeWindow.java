package com.example.mnymng;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
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
                    R.id.fragment_income,
                   R.id.fragment_trip)
                    .setOpenableLayout(drawerLayout)
                    .build();

            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(navView, navController);

//            Menu menu = navView.getMenu();
//            menu.findItem(R.id.fragment_bank).setVisible(false);
//            menu.findItem(R.id.fragment_wallet).setVisible(false);
//            menu.findItem(R.id.fragment_loan).setVisible(false);
//            menu.findItem(R.id.fragment_lending).setVisible(false);
//            menu.findItem(R.id.fragment_credit_card).setVisible(false);
//            menu.findItem(R.id.fragment_investment).setVisible(false);
//            menu.findItem(R.id.fragment_e_wallet).setVisible(false);
//            menu.findItem(R.id.fragment_asset).setVisible(false);





//
//            navView.setNavigationItemSelectedListener(item -> {
//               // menu.findItem(R.id.accounts_group).setChecked(true);
//                // Handle navigation view item clicks here.
//                int id = item.getItemId();
//
//                // Example: Navigate to a destination or perform an action
//                 if (id == R.id.accounts_group) {
//                     Log.d("NavDrawer", "onCreate: "+id+"acc"+R.id.accounts_group);
//                     if(menu.findItem(R.id.fragment_bank).isVisible()){
//                         menu.findItem(R.id.fragment_bank).setVisible(false);
//                         menu.findItem(R.id.fragment_wallet).setVisible(false);
//                         menu.findItem(R.id.fragment_loan).setVisible(false);
//                         menu.findItem(R.id.fragment_lending).setVisible(false);
//                         menu.findItem(R.id.fragment_credit_card).setVisible(false);
//                         menu.findItem(R.id.fragment_investment).setVisible(false);
//                         menu.findItem(R.id.fragment_e_wallet).setVisible(false);
//                         menu.findItem(R.id.fragment_asset).setVisible(false);
//                     }else {
//                         menu.findItem(R.id.fragment_bank).setVisible(true);
//                         menu.findItem(R.id.fragment_wallet).setVisible(true);
//                         menu.findItem(R.id.fragment_loan).setVisible(true);
//                         menu.findItem(R.id.fragment_lending).setVisible(true);
//                         menu.findItem(R.id.fragment_credit_card).setVisible(true);
//                         menu.findItem(R.id.fragment_investment).setVisible(true);
//                         menu.findItem(R.id.fragment_e_wallet).setVisible(true);
//                         menu.findItem(R.id.fragment_asset).setVisible(true);
//                     }
//
//                 }
//                 // else if (id == R.id.another_menu_item_id) {
//                //     // Do something else
//                //     Toast.makeText(HomeWindow.this, "Another item clicked", Toast.LENGTH_SHORT).show();
//                // }
//
//                // Close the navigation drawer
//                //drawerLayout.closeDrawer(GravityCompat.START);
//                // Return true to display the item as the selected item
//                // Return false if you don't want to select the item
//                // If you are using NavigationUI.setupWithNavController,
//                // it usually handles the navigation and selection state.
//                // So, you might want to call it or let it handle the event.
//                // For custom handling, you can return true after handling the navigation.
//                return NavigationUI.onNavDestinationSelected(item, navController) || HomeWindow.super.onOptionsItemSelected(item);
//            });
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_overflow_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_notification) {
            Toast.makeText(this, "Notification clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.action_profile) {
            Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.action_settings) {
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.action_about_us) {
            Toast.makeText(this, "About Us clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController != null && appBarConfiguration != null && NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
