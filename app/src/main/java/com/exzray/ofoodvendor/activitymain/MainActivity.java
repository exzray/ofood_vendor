package com.exzray.ofoodvendor.activitymain;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.exzray.ofoodvendor.R;
import com.exzray.ofoodvendor.activitymessage.MessageActivity;
import com.exzray.ofoodvendor.databinding.ActivityMainBinding;
import com.exzray.ofoodvendor.utility.Helper;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        setupDrawerNavigation();
        setupFab();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Helper.getNavController(getSupportFragmentManager());
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setupDrawerNavigation() {
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_dashboard, R.id.nav_order, R.id.nav_booking, R.id.nav_account)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Helper.getNavController(getSupportFragmentManager());
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() != R.id.nav_dashboard)
                binding.appBarMain.fab.shrink();
            else
                binding.appBarMain.fab.extend();

            if (destination.getId() == R.id.nav_account)
                binding.appBarMain.fab.hide();
            else
                binding.appBarMain.fab.show();
        });

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void setupFab() {
        binding.appBarMain.fab.setOnClickListener(view -> {
                    Intent intent = new Intent(this, MessageActivity.class);
                    startActivity(intent);
                }
        );
    }
}