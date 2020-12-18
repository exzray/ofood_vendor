package com.exzray.ofoodvendor.utility;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.exzray.ofoodvendor.R;

import java.util.Locale;

public class Helper {

    public static NavController getNavController(FragmentManager manager){
        NavHostFragment fragment = ((NavHostFragment) manager.findFragmentById(R.id.host));
        assert fragment != null;

        return fragment.getNavController();
    }

    public static NavController getNavController(Fragment fragment){
        return NavHostFragment.findNavController(fragment);
    }

}
