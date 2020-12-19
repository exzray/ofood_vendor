package com.exzray.ofoodvendor.activityproduct;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.exzray.ofoodvendor.R;
import com.exzray.ofoodvendor.databinding.ActivityProductBinding;
import com.exzray.ofoodvendor.utility.Helper;

public class ProductActivity extends AppCompatActivity {

    private ActivityProductBinding binding;
    private ProductViewModel view_model;
    private AppBarConfiguration configuration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProductBinding.inflate(getLayoutInflater());
        view_model = new ViewModelProvider(this).get(ProductViewModel.class);

        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        setupNavigation();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController controller = Helper.getNavController(getSupportFragmentManager());
        return NavigationUI.navigateUp(controller, configuration) || super.onSupportNavigateUp();
    }

    private void setupNavigation() {
        configuration = new AppBarConfiguration.Builder(
                R.id.nav_product_list, R.id.nav_product_create, R.id.nav_product_update)
                .build();

        NavController controller = Helper.getNavController(getSupportFragmentManager());

        NavigationUI.setupActionBarWithNavController(this, controller, configuration);
    }
}