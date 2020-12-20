package com.exzray.ofoodvendor.activitytable;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.exzray.ofoodvendor.R;
import com.exzray.ofoodvendor.databinding.ActivityTableBinding;
import com.exzray.ofoodvendor.utility.Helper;

public class TableActivity extends AppCompatActivity {

    private ActivityTableBinding binding;
    private TableViewModel view_model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTableBinding.inflate(getLayoutInflater());
        view_model = new ViewModelProvider(this).get(TableViewModel.class);

        view_model.init();

        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        setNavigation();
    }

    private void setNavigation() {
        NavController controller = Helper.getNavController(getSupportFragmentManager());

        AppBarConfiguration configuration = new AppBarConfiguration.Builder(
                R.id.nav_table_list, R.id.nav_table_create, R.id.nav_table_update)
                .build();

        NavigationUI.setupActionBarWithNavController(this, controller, configuration);
    }
}