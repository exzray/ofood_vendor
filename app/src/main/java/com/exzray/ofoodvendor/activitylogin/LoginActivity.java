package com.exzray.ofoodvendor.activitylogin;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.exzray.ofoodvendor.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel view_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        view_model = new ViewModelProvider(this).get(LoginViewModel.class);

        view_model
                .getUser()
                .observe(this, this::updateUI);

        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();

        view_model.authenticate();
    }

    private void updateUI(FirebaseUser user) {

        if (user != null)
            Toast.makeText(this, "do something", Toast.LENGTH_SHORT).show();

    }
}