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
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.exzray.ofoodvendor.R;
import com.exzray.ofoodvendor.activitycategory.CategoryViewModel;
import com.exzray.ofoodvendor.databinding.FragmentCategoryUpdateBinding;
import com.exzray.ofoodvendor.model.ModelCategory;
import com.exzray.ofoodvendor.utility.Convert;
import com.exzray.ofoodvendor.utility.Helper;

import org.jetbrains.annotations.NotNull;

public class CategoryUpdateFragment extends Fragment {

    private FragmentCategoryUpdateBinding binding;
    private CategoryViewModel view_model_activity;
    private CategoryUpdateViewModel view_model_fragment;
    private ProgressDialog dialog;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentCategoryUpdateBinding.inflate(inflater, container, false);
        view_model_activity = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        view_model_fragment = new ViewModelProvider(this).get(CategoryUpdateViewModel.class);
        dialog = new ProgressDialog(requireContext());

        setHasOptionsMenu(true);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String category_uid = CategoryUpdateFragmentArgs.fromBundle(getArguments()).getCategoryUid();

        view_model_fragment.setSnapshotCategory(category_uid);
        view_model_fragment
                .getBooleanReadSuccess()
                .observe(getViewLifecycleOwner(), success -> {

                    if (!success) {
                        Toast.makeText(requireContext(), "fail to read data", Toast.LENGTH_SHORT).show();
                        Helper.getNavController(this).popBackStack();
                    }

                });

        setupForm();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.submit_option, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId() == R.id.action_submit) {
            dialog.setMessage("updating category");
            dialog.show();

            view_model_fragment.doUpdateCategory();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupForm() {
        binding.editName.addTextChangedListener(watcher_name);
        binding.editDescription.addTextChangedListener(watcher_description);
        binding.swEnable.setOnCheckedChangeListener(change_enable);

        view_model_fragment
                .getStringErrorName()
                .observe(getViewLifecycleOwner(), s -> binding.layoutName.setError(s));

        view_model_fragment
                .getStringErrorDescription()
                .observe(getViewLifecycleOwner(), s -> binding.layoutDescription.setError(s));

        view_model_fragment
                .getSnapshotCategory()
                .observe(getViewLifecycleOwner(), snapshot -> {

                    if (snapshot != null) {

                        final ModelCategory category = Convert.snapshotToCategory(snapshot);

                        binding.editName.setText(category.getName());
                        binding.editDescription.setText(category.getDescription());
                        binding.swEnable.setChecked(category.getEnable());
                    }
                });

        view_model_fragment
                .getBooleanUpdateSuccess()
                .observe(getViewLifecycleOwner(), success -> {

                    dialog.dismiss();

                    if (success)
                        Toast.makeText(requireContext(), "update success", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(requireContext(), "something wrong happen", Toast.LENGTH_SHORT).show();

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

    private final CompoundButton.OnCheckedChangeListener change_enable = (buttonView, isChecked) -> {
        view_model_fragment.setBooleanEnable(isChecked);
    };
}