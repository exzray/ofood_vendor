package com.exzray.ofoodvendor.activitymain;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.exzray.ofoodvendor.R;
import com.exzray.ofoodvendor.activitymessage.MessageActivity;
import com.exzray.ofoodvendor.databinding.ActivityMainBinding;
import com.exzray.ofoodvendor.databinding.NavHeaderMainBinding;
import com.exzray.ofoodvendor.model.ModelVendor;
import com.exzray.ofoodvendor.utility.Convert;
import com.exzray.ofoodvendor.utility.Firebase;
import com.exzray.ofoodvendor.utility.Helper;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel view_model;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        view_model = new ViewModelProvider(this).get(MainViewModel.class);

        view_model.init();

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

        NavHeaderMainBinding headerBinding = NavHeaderMainBinding.bind(navigationView.getHeaderView(0));

        view_model
                .getSnapshotVendor()
                .observe(this, snapshot -> {
                    final ModelVendor vendor = Convert.snapshotToVendor(snapshot);

                    headerBinding.textVendorName.setText(vendor.getName());
                    headerBinding.textUserEmail.setText(Firebase.getFirebaseUser().getEmail());

                    if (!vendor.getImage_photo().isEmpty())
                        Firebase
                                .getFirebaseStorage()
                                .getReference()
                                .child(vendor.getImage_photo())
                                .getDownloadUrl()
                                .addOnSuccessListener(uri -> Glide
                                        .with(this)
                                        .load(uri)
                                        .into(headerBinding.imageBanner));
                });

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_dashboard, R.id.nav_order, R.id.nav_booking, R.id.nav_account, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Helper.getNavController(getSupportFragmentManager());
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() != R.id.nav_dashboard)
                binding.appBarMain.fab.shrink();
            else
                binding.appBarMain.fab.extend();

            if (destination.getId() == R.id.nav_account || destination.getId() == R.id.nav_logout)
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