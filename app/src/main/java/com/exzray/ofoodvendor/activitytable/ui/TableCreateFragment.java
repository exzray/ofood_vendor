package com.exzray.ofoodvendor.activitytable.ui;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.exzray.ofoodvendor.R;
import com.exzray.ofoodvendor.activitytable.TableViewModel;
import com.exzray.ofoodvendor.databinding.FragmentTableCreateBinding;
import com.exzray.ofoodvendor.utility.Helper;

import org.jetbrains.annotations.NotNull;

public class TableCreateFragment extends Fragment {

    private FragmentTableCreateBinding binding;
    private TableViewModel view_model_activity;
    private TableCreateViewModel view_model_fragment;
    private ProgressDialog dialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentTableCreateBinding.inflate(inflater, container, false);
        view_model_activity = new ViewModelProvider(requireActivity()).get(TableViewModel.class);
        view_model_fragment = new ViewModelProvider(this).get(TableCreateViewModel.class);
        dialog = new ProgressDialog(requireContext());

        setHasOptionsMenu(true);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view_model_fragment
                .getTaskBooleanSuccess()
                .observe(getViewLifecycleOwner(), success -> {

                    if (success) {
                        Toast.makeText(requireContext(), "table success added", Toast.LENGTH_SHORT).show();
                        Helper.getNavController(this).popBackStack();
                    } else
                        Toast.makeText(requireContext(), "something goes wrong", Toast.LENGTH_SHORT).show();

                    dialog.dismiss();

                });

        view_model_fragment
                .getTableStringName()
                .observe(getViewLifecycleOwner(), s -> binding.editName.setText(s));

        setupForm();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.submit_option, menu);

        final MenuItem item = menu.getItem(0);
        view_model_fragment
                .getButtonBooleanEnable()
                .observe(getViewLifecycleOwner(), item::setEnabled);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {

        if (item.getItemId() == R.id.action_submit) {
            dialog.setMessage("adding new table");
            dialog.show();

            view_model_fragment
                    .doTask();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupForm() {
        binding.editSize.addTextChangedListener(watcher_size);

        view_model_activity
                .getListTableSize()
                .observe(getViewLifecycleOwner(), index -> view_model_fragment.setTableIntegerIndex(index));

        view_model_fragment
                .getTableStringSizeError()
                .observe(getViewLifecycleOwner(), error -> binding.layoutSize.setError(error));
    }

    private final TextWatcher watcher_size = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            view_model_fragment.setTableIntegerSize(s.toString());
        }
    };
}