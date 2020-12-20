package com.exzray.ofoodvendor.activitycategory;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.exzray.ofoodvendor.R;
import com.exzray.ofoodvendor.databinding.ActivityCategoryBinding;
import com.exzray.ofoodvendor.utility.Helper;

import org.jetbrains.annotations.NotNull;

public class CategoryActivity extends AppCompatActivity {

    private ActivityCategoryBinding binding;
    private CategoryViewModel view_model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        view_model = new ViewModelProvider(this).get(CategoryViewModel.class);

        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        setNavigation();
    }

    private void setNavigation() {
        NavController controller = Helper.getNavController(getSupportFragmentManager());

        AppBarConfiguration configuration = new AppBarConfiguration.Builder(
                R.id.nav_category_list, R.id.nav_category_create, R.id.nav_category_update)
                .build();

        NavigationUI.setupActionBarWithNavController(this, controller, configuration);
    }
}