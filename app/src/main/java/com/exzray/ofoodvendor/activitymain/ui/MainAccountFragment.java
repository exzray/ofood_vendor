package com.exzray.ofoodvendor.activitymain.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.exzray.ofoodvendor.R;
import com.exzray.ofoodvendor.activitymain.MainViewModel;
import com.exzray.ofoodvendor.adapter.AdapterNavigation;
import com.exzray.ofoodvendor.databinding.FragmentMainAccountBinding;
import com.exzray.ofoodvendor.model.ModelNavigation;

import java.util.ArrayList;
import java.util.List;

public class MainAccountFragment extends Fragment {

    private FragmentMainAccountBinding binding;
    private MainViewModel view_model_activity;
    private MainAccountViewModel view_model_fragment;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentMainAccountBinding.inflate(inflater, container, false);
        view_model_activity = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        view_model_fragment = new ViewModelProvider(this).get(MainAccountViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Glide
                .with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/ofood-cae06.appspot.com/o/test%2Fuser_photo.jpg?alt=media&token=2e4917cd-06df-4946-b548-7ebb585b404b")
                .centerCrop()
                .into(binding.includeInfoProfile.imagePhoto);

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

    }
}