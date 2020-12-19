package com.exzray.ofoodvendor.utility;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.exzray.ofoodvendor.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import javax.annotation.Nullable;

public class Helper {

    public static NavController getNavController(FragmentManager manager) {
        NavHostFragment fragment = ((NavHostFragment) manager.findFragmentById(R.id.host));
        assert fragment != null;

        return fragment.getNavController();
    }

    public static NavController getNavController(Fragment fragment) {
        return NavHostFragment.findNavController(fragment);
    }

    public static Boolean isStringEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static GoogleSignInClient getGoogleSignInClient(Context context) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        return GoogleSignIn.getClient(context, gso);
    }

    public static String getString(String s) {
        return s == null ? "" : s;
    }

    public static Boolean getBoolean(Boolean b) {
        return b == null || b;
    }

}
