package com.exzray.ofoodvendor.activitycategory.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.exzray.ofoodvendor.R;
import com.exzray.ofoodvendor.activitycategory.CategoryViewModel;
import com.exzray.ofoodvendor.adapter.AdapterCategory;
import com.exzray.ofoodvendor.databinding.FragmentCategoryListBinding;
import com.exzray.ofoodvendor.utility.Helper;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.NotNull;

public class CategoryListFragment extends Fragment {

    private FragmentCategoryListBinding binding;
    private CategoryViewModel view_model_activity;
    private CategoryListViewModel view_model_fragment;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentCategoryListBinding.inflate(inflater, container, false);
        view_model_activity = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        view_model_fragment = new ViewModelProvider(this).get(CategoryListViewModel.class);

        setHasOptionsMenu(true);

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_option, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupCategories();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {

        if (item.getItemId() == R.id.action_add)
            Helper.getNavController(this).navigate(R.id.nav_category_create);

        return super.onOptionsItemSelected(item);
    }

    private void setupCategories() {
        final AdapterCategory adapter = new AdapterCategory(this::callbackUpdate, this::callbackDelete);
        final LinearLayoutManager manager = new LinearLayoutManager(requireContext());

        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(manager);

        view_model_activity
                .getListSnapshotCategory()
                .observe(getViewLifecycleOwner(), adapter::update);
    }

    private void callbackUpdate(DocumentSnapshot snapshot) {
        NavDirections directions = CategoryListFragmentDirections.categoryListToUpdate(snapshot.getId());

        Helper
                .getNavController(this)
                .navigate(directions);
    }

    private void callbackDelete(DocumentSnapshot snapshot) {
        AlertDialog alert = new MaterialAlertDialogBuilder(requireContext())
                .setMessage("Are sure you want to delete this category?")
                .setPositiveButton("Yes", (dialog, which) -> deleteSnapshot(snapshot))
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create();

        alert.show();
    }

    private void deleteSnapshot(DocumentSnapshot snapshot) {
        if (snapshot == null) return;
        snapshot.getReference().delete();
    }
}