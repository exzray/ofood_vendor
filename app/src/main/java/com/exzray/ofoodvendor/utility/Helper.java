package com.exzray.ofoodvendor.utility;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.exzray.ofoodvendor.R;
import com.exzray.ofoodvendor.model.ModelCategory;
import com.exzray.ofoodvendor.model.ModelSelection;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

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

    public static Double parseStringToDouble(String s) {
        return Double.parseDouble(s);
    }

    public static List<ModelSelection> getModelSelectionList(List<DocumentSnapshot> snapshots) {
        final List<ModelSelection> list = new ArrayList<>();

        for (DocumentSnapshot snapshot : snapshots) {
            final ModelCategory category = Convert.snapshotToCategory(snapshot);

            final ModelSelection selection = new ModelSelection();
            selection.setName(category.getName());
            selection.setTag(snapshot.getId());

            list.add(selection);
        }

        return list;
    }

}
