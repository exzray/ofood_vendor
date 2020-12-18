package com.exzray.ofoodvendor.activitycategory.ui;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exzray.ofoodvendor.R;
import com.exzray.ofoodvendor.activitycategory.CategoryViewModel;

import org.jetbrains.annotations.NotNull;

public class CategoryCreateFragment extends Fragment {

    private CategoryViewModel view_model_activity;
    private CategoryCreateViewModel view_model_fragment;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view_model_activity = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        view_model_fragment = new ViewModelProvider(this).get(CategoryCreateViewModel.class);

        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_category_create, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.submit_option, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}