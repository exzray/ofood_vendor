package com.exzray.ofoodvendor.activitylogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.exzray.ofoodvendor.R;
import com.exzray.ofoodvendor.activitymain.MainActivity;
import com.exzray.ofoodvendor.databinding.ActivityLoginBinding;
import com.exzray.ofoodvendor.utility.Helper;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel view_model;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        view_model = new ViewModelProvider(this).get(LoginViewModel.class);
        dialog = new ProgressDialog(this);

        view_model
                .getUser()
                .observe(this, this::updateUI);

        setContentView(binding.getRoot());

        view_model
                .getBooleanDetailsExist()
                .observe(this, exist -> {

                    if (exist) {
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);

                    } else {

                        Helper
                                .getNavController(getSupportFragmentManager())
                                .navigate(R.id.nav_login_signup);

                    }

                    dialog.dismiss();

                });
    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {
            dialog.setMessage("please wait");
            dialog.show();

            view_model.verification();
        }

    }
}