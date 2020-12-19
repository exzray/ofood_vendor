package com.exzray.ofoodvendor.activitycategory.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.exzray.ofoodvendor.R;
import com.exzray.ofoodvendor.activitycategory.CategoryViewModel;
import com.exzray.ofoodvendor.databinding.FragmentCategoryCreateBinding;
import com.exzray.ofoodvendor.utility.Helper;
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CategoryCreateFragment extends Fragment {

    private FragmentCategoryCreateBinding binding;
    private CategoryViewModel view_model_activity;
    private CategoryCreateViewModel view_model_fragment;
    private ProgressDialog dialog;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentCategoryCreateBinding.inflate(inflater, container, false);
        view_model_activity = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        view_model_fragment = new ViewModelProvider(this).get(CategoryCreateViewModel.class);
        dialog = new ProgressDialog(requireContext());

        setHasOptionsMenu(true);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupForm();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.submit_option, menu);

        final MenuItem item = menu.getItem(0);
        view_model_fragment
                .getBooleanCreateReady()
                .observe(getViewLifecycleOwner(), item::setEnabled);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId() == R.id.action_submit) {
            dialog.setMessage("adding new category");
            dialog.show();

            List<DocumentSnapshot> list = view_model_activity.getListSnapshotCategory().getValue();
            int position = list == null ? 0 : list.size();

            view_model_fragment.createCategory(position);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupForm() {
        binding.editName.addTextChangedListener(watcher_name);
        binding.editDescription.addTextChangedListener(watcher_description);

        view_model_fragment
                .getStringErrorName()
                .observe(getViewLifecycleOwner(), s -> binding.layoutName.setError(s));

        view_model_fragment
                .getStringErrorDescription()
                .observe(getViewLifecycleOwner(), s -> binding.layoutDescription.setError(s));

        view_model_fragment
                .getBooleanCreateSuccess()
                .observe(getViewLifecycleOwner(), success -> {

                    if (success)
                        Helper.getNavController(this).popBackStack();

                    dialog.dismiss();

                });
    }


    private final TextWatcher watcher_name = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            view_model_fragment.setStringName(s.toString());
        }
    };

    private final TextWatcher watcher_description = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            view_model_fragment.setStringDescription(s.toString());
        }
    };
}