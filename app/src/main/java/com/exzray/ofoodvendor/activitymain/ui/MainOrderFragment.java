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

import com.exzray.ofoodvendor.activitymain.MainViewModel;
import com.exzray.ofoodvendor.activityorder.OrderActivity;
import com.exzray.ofoodvendor.adapter.AdapterOccupy;
import com.exzray.ofoodvendor.databinding.FragmentMainOrderBinding;
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.NotNull;

public class MainOrderFragment extends Fragment {

    private FragmentMainOrderBinding binding;
    private MainViewModel view_model_activity;
    private MainOrderViewModel view_model_fragment;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentMainOrderBinding.inflate(inflater, container, false);
        view_model_activity = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        view_model_fragment = new ViewModelProvider(this).get(MainOrderViewModel.class);

        view_model_fragment.init();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecycler();
    }

    private void setupRecycler() {

        AdapterOccupy adapter = new AdapterOccupy(this::callback);
        LinearLayoutManager manager = new LinearLayoutManager(requireContext());

        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(manager);

        view_model_fragment
                .getTableListSnapshot()
                .observe(getViewLifecycleOwner(), adapter::update);
    }

    private void callback(DocumentSnapshot snapshot) {

        String path = snapshot.getReference().getPath();

        Intent intent = new Intent(requireContext(), OrderActivity.class);
        intent.putExtra("path", path);

        startActivity(intent);
    }
}