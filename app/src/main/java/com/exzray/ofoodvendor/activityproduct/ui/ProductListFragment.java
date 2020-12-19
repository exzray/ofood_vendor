package com.exzray.ofoodvendor.activityproduct.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.exzray.ofoodvendor.R;
import com.exzray.ofoodvendor.activityproduct.ProductViewModel;
import com.exzray.ofoodvendor.adapter.AdapterProduct;
import com.exzray.ofoodvendor.databinding.FragmentProductListBinding;
import com.exzray.ofoodvendor.utility.Helper;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.NotNull;

public class ProductListFragment extends Fragment {

    private FragmentProductListBinding binding;
    private ProductViewModel view_model_activity;
    private ProductListViewModel view_model_fragment;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentProductListBinding.inflate(inflater, container, false);
        view_model_activity = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        view_model_fragment = new ViewModelProvider(this).get(ProductListViewModel.class);

        setHasOptionsMenu(true);

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_option, menu);

        // show when product list is loaded
        final MenuItem item = menu.getItem(0);
        item.setEnabled(false);

        view_model_activity
                .getListSnapshotProduct()
                .observe(getViewLifecycleOwner(), snapshots -> item.setEnabled(true));

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            NavDirections directions = ProductListFragmentDirections.productListToCreate();
            Helper.getNavController(this).navigate(directions);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupAdapter();
    }

    private void setupAdapter() {
        final AdapterProduct adapter = new AdapterProduct(this::callbackUpdate, this::callbackDelete);
        final LinearLayoutManager manager = new LinearLayoutManager(requireContext());

        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(manager);

        view_model_activity
                .getListSnapshotProduct()
                .observe(getViewLifecycleOwner(), adapter::update);
    }

    private void callbackUpdate(DocumentSnapshot snapshot) {
        NavDirections directions = ProductListFragmentDirections.productListToUpdate(snapshot.getId());
        Helper.getNavController(this).navigate(directions);
    }

    private void callbackDelete(DocumentSnapshot snapshot) {
        if (snapshot != null)
            new MaterialAlertDialogBuilder(requireContext())
                    .setMessage("Are sure you want to delete this product?")
                    .setPositiveButton("Yes", (dialog, which) -> snapshot.getReference().delete())
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
    }
}