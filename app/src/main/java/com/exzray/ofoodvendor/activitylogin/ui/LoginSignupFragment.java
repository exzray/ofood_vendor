package com.exzray.ofoodvendor.activitylogin.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.exzray.ofoodvendor.activitylogin.LoginViewModel;
import com.exzray.ofoodvendor.databinding.FragmentLoginSignupBinding;
import com.exzray.ofoodvendor.model.ModelProfile;
import com.exzray.ofoodvendor.utility.Convert;

public class LoginSignupFragment extends Fragment {

    private FragmentLoginSignupBinding binding;
    private LoginViewModel view_model_activity;
    private LoginSignupViewModel view_model_fragment;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentLoginSignupBinding.inflate(inflater, container, false);
        view_model_activity = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        view_model_fragment = new ViewModelProvider(this).get(LoginSignupViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupForm();
    }

    private void setupForm() {
        binding.editProfile.addTextChangedListener(watcher_profile);
        binding.editVendor.addTextChangedListener(watcher_vendor);

        view_model_activity
                .getProfile()
                .observe(getViewLifecycleOwner(), snapshot -> {

                    if (snapshot != null && snapshot.exists()) {
                        final ModelProfile profile = Convert.snapshotToProfile(snapshot);

                        binding.editProfile.setText(profile.getName());
                    }

                });

        view_model_fragment
                .getStringErrorName()
                .observe(getViewLifecycleOwner(), s -> binding.layoutProfile.setError(s));

        view_model_fragment
                .getStringErrorDescription()
                .observe(getViewLifecycleOwner(), s -> binding.layoutVendor.setError(s));

        view_model_fragment
                .getBooleanCreateReady()
                .observe(getViewLifecycleOwner(), ready -> binding.button.setEnabled(ready));

        binding.button.setOnClickListener(v -> {

            view_model_fragment.create();

        });

        view_model_fragment
                .getBooleanCreateSuccess()
                .observe(getViewLifecycleOwner(), success -> {

                    if (success)
                        view_model_activity.verification();

                });

    }


    private final TextWatcher watcher_profile = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            view_model_fragment.setStringProfile(s.toString());
        }
    };

    private final TextWatcher watcher_vendor = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            view_model_fragment.setStringVendor(s.toString());
        }
    };

}