package com.exzray.ofoodvendor.activitylogin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.exzray.ofoodvendor.activitylogin.LoginViewModel;
import com.exzray.ofoodvendor.databinding.FragmentLoginSigninBinding;
import com.exzray.ofoodvendor.utility.Firebase;
import com.exzray.ofoodvendor.utility.Helper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginSigninFragment extends Fragment {

    private static final int REQUEST_CODE_GOOGLE = 1001;

    private FragmentLoginSigninBinding binding;
    private LoginViewModel view_model_activity;
    private LoginSigninViewModel view_model_fragment;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginSigninBinding.inflate(inflater, container, false);
        view_model_activity = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        view_model_fragment = new ViewModelProvider(this).get(LoginSigninViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.setFragment(this);

        view_model_fragment
                .getBooleanLoginEnable()
                .observe(getViewLifecycleOwner(), enable -> binding.button.setEnabled(enable));
    }

    @Override
    public void onStart() {
        super.onStart();

        view_model_activity.authenticate();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_GOOGLE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                signInFirebase(account.getIdToken());

            } catch (ApiException e) {
                Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void signInFirebase(String token) {
        view_model_fragment.setBooleanLoginEnable(false);

        AuthCredential credential = GoogleAuthProvider.getCredential(token, null);
        Firebase
                .getFirebaseAuth()
                .signInWithCredential(credential)
                .addOnSuccessListener(result -> view_model_activity.authenticate())
                .addOnFailureListener(e -> {
                    view_model_fragment.setBooleanLoginEnable(true);
                    Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    public void buttonClick() {
        Intent intent = Helper.getGoogleSignInClient(requireContext()).getSignInIntent();
        startActivityForResult(intent, REQUEST_CODE_GOOGLE);
    }
}