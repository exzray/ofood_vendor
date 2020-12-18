package com.exzray.ofoodvendor.activitymessage;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.exzray.ofoodvendor.R;
import com.exzray.ofoodvendor.databinding.ActivityMessageBinding;
import com.exzray.ofoodvendor.utility.Helper;

public class MessageActivity extends AppCompatActivity {

    private ActivityMessageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMessageBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        setupNavigation();
    }

    private void setupNavigation() {
        AppBarConfiguration configuration = new AppBarConfiguration.Builder(
                R.id.nav_message_list, R.id.nav_message_setting)
                .build();

        NavController navController = Helper.getNavController(getSupportFragmentManager());
        NavigationUI.setupActionBarWithNavController(this, navController, configuration);
        NavigationUI.setupWithNavController(binding.bottom, navController);
    }

}