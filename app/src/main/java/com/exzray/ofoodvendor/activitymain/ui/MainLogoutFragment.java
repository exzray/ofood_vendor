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

import com.exzray.ofoodvendor.activitylogin.LoginActivity;
import com.exzray.ofoodvendor.databinding.FragmentMainLogoutBinding;

import org.jetbrains.annotations.NotNull;

public class MainLogoutFragment extends Fragment {

    private FragmentMainLogoutBinding binding;
    private MainLogoutViewModel view_model_fragment;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentMainLogoutBinding.inflate(inflater, container, false);
        view_model_fragment = new ViewModelProvider(this).get(MainLogoutViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view_model_fragment
                .getBooleanTaskSuccess()
                .observe(getViewLifecycleOwner(), success -> {
                    if (success) {
                        Intent intent = new Intent(requireContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                    }
                });

        binding.setFragment(this);
    }

    public void doLogOut() {
        view_model_fragment.doLogout(requireContext());
    }

}