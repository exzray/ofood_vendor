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

import com.bumptech.glide.Glide;
import com.exzray.ofoodvendor.activitymain.MainViewModel;
import com.exzray.ofoodvendor.activityprofile.ProfileActivity;
import com.exzray.ofoodvendor.activityvendor.VendorActivity;
import com.exzray.ofoodvendor.adapter.AdapterNavigation;
import com.exzray.ofoodvendor.databinding.FragmentMainAccountBinding;
import com.exzray.ofoodvendor.model.ModelNavigation;
import com.exzray.ofoodvendor.model.ModelProfile;
import com.exzray.ofoodvendor.utility.Convert;
import com.exzray.ofoodvendor.utility.Firebase;
import com.exzray.ofoodvendor.utility.Helper;

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

        view_model_activity
                .getSnapshotProfile()
                .observe(getViewLifecycleOwner(), snapshot -> {

                    final ModelProfile profile = Convert.snapshotToProfile(snapshot);

                    binding.includeInfoProfile.textUserName.setText(profile.getName());
                    binding.includeInfoProfile.textUserEmail.setText(Firebase.getFirebaseUser().getEmail());
                    binding.includeInfoProfile.textUserJoined.setText(Helper.getStringJoined(profile.getJoined()));

                    Firebase
                            .getStorageProfile()
                            .child("image_photo")
                            .getDownloadUrl()
                            .addOnSuccessListener(uri -> Glide.with(this).load(uri).into(binding.includeInfoProfile.imagePhoto));

                });

        binding
                .includeInfoProfile
                .button
                .setOnClickListener(v -> {
                    Intent intent = new Intent(requireActivity(), ProfileActivity.class);
                    startActivity(intent);
                });

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

        if (navigation.getTag().equals("vendor")) {
            Intent intent = new Intent(requireActivity(), VendorActivity.class);
            startActivity(intent);
        }

    }
}