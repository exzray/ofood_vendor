package com.exzray.ofoodvendor.activitymain.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.exzray.ofoodvendor.activitybooking.BookingActivity;
import com.exzray.ofoodvendor.activitycategory.CategoryActivity;
import com.exzray.ofoodvendor.activitymain.MainViewModel;
import com.exzray.ofoodvendor.activityorder.OrderActivity;
import com.exzray.ofoodvendor.activityproduct.ProductActivity;
import com.exzray.ofoodvendor.activitytable.TableActivity;
import com.exzray.ofoodvendor.adapter.AdapterNavigation;
import com.exzray.ofoodvendor.databinding.FragmentMainDashboardBinding;
import com.exzray.ofoodvendor.model.ModelNavigation;

import org.jetbrains.annotations.NotNull;

public class MainDashboardFragment extends Fragment {

    private FragmentMainDashboardBinding binding;
    private MainViewModel view_model_activity;
    private MainDashboardViewModel view_model_fragment;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentMainDashboardBinding.inflate(inflater, container, false);
        view_model_activity = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        view_model_fragment = new ViewModelProvider(this).get(MainDashboardViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecycler();
    }

    private void setupRecycler() {
        final AdapterNavigation adapter = new AdapterNavigation(this::callback_navigation);
        final LinearLayoutManager manager = new LinearLayoutManager(requireContext());

        view_model_fragment
                .getListNavigation()
                .observe(getViewLifecycleOwner(), adapter::update);

        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(manager);
    }

    private void callback_navigation(ModelNavigation navigation) {

        if (navigation.getTag().equals("order"))
            startActivityOrder();

        if (navigation.getTag().equals("booking"))
            startActivityBooking();

        if (navigation.getTag().equals("product"))
            startActivityProduct();

        if (navigation.getTag().equals("category"))
            startActivityCategory();

        if (navigation.getTag().equals("table"))
            startActivityTable();
    }

    private void startActivityOrder() {
        Intent intent = new Intent(requireContext(), OrderActivity.class);
        startActivity(intent);
    }

    private void startActivityBooking() {
        Intent intent = new Intent(requireContext(), BookingActivity.class);
        startActivity(intent);
    }

    private void startActivityProduct() {
        Intent intent = new Intent(requireContext(), ProductActivity.class);
        startActivity(intent);
    }

    private void startActivityCategory() {
        Intent intent = new Intent(requireContext(), CategoryActivity.class);
        startActivity(intent);
    }

    private void startActivityTable() {
        Intent intent = new Intent(requireContext(), TableActivity.class);
        startActivity(intent);
    }
}