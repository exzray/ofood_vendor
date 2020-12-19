package com.exzray.ofoodvendor.activityproduct.ui;

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
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.exzray.ofoodvendor.R;
import com.exzray.ofoodvendor.activityproduct.ProductViewModel;
import com.exzray.ofoodvendor.databinding.FragmentProductCreateBinding;
import com.exzray.ofoodvendor.model.ModelSelection;
import com.exzray.ofoodvendor.utility.Helper;
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductCreateFragment extends Fragment {

    private FragmentProductCreateBinding binding;
    private ProductViewModel view_model_activity;
    private ProductCreateViewModel view_model_fragment;
    private ProgressDialog dialog;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentProductCreateBinding.inflate(inflater);
        view_model_activity = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        view_model_fragment = new ViewModelProvider(this).get(ProductCreateViewModel.class);
        dialog = new ProgressDialog(requireContext());

        setHasOptionsMenu(true);

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.submit_option, menu);

        final MenuItem item = menu.getItem(0);
        view_model_fragment
                .getBooleanSubmitEnable()
                .observe(getViewLifecycleOwner(), item::setEnabled);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {

        if (item.getItemId() == R.id.action_submit) {
            dialog.setMessage("adding new product");
            dialog.show();

            final List<DocumentSnapshot> list = view_model_activity.getListSnapshotProduct().getValue();
            final int position = (list == null ? 0 : list.size());

            view_model_fragment.doCreateProduct(position);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view_model_fragment
                .getBooleanTaskSuccess()
                .observe(getViewLifecycleOwner(), success -> {
                    dialog.dismiss();

                    if (success) {
                        Toast.makeText(requireContext(), "product added", Toast.LENGTH_SHORT).show();
                        Helper.getNavController(this).popBackStack();

                    } else
                        Toast.makeText(requireContext(), "something goes wrong, please try again", Toast.LENGTH_SHORT).show();

                });

        setupForm();
    }

    private void setupForm() {

        binding.editName.addTextChangedListener(watcher_name);
        binding.editPrice.addTextChangedListener(watcher_price);
        binding.editDescription.addTextChangedListener(watcher_description);

        view_model_activity
                .getListSnapshotCategory()
                .observe(getViewLifecycleOwner(), snapshots -> {

                    final ArrayAdapter<ModelSelection> adapter = new ArrayAdapter<>(
                            requireContext(),
                            android.R.layout.simple_list_item_1,
                            Helper.getModelSelectionList(snapshots));

                    binding.editCategory.setAdapter(adapter);
                    binding.editCategory.setOnItemClickListener((parent, view1, position, id) -> {
                        final String uid = adapter.getItem(position).getTag();
                        view_model_fragment.setStringCategory(uid);
                    });

                });

        view_model_fragment
                .getStringErrorName()
                .observe(getViewLifecycleOwner(), s -> binding.layoutName.setError(s));

        view_model_fragment
                .getStringErrorPrice()
                .observe(getViewLifecycleOwner(), s -> binding.layoutPrice.setError(s));

        view_model_fragment
                .getStringErrorDescription()
                .observe(getViewLifecycleOwner(), s -> binding.layoutDescription.setError(s));

        view_model_fragment
                .getStringErrorCategory()
                .observe(getViewLifecycleOwner(), s -> binding.layoutCategory.setError(s));
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

    private final TextWatcher watcher_price = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            view_model_fragment.setStringPrice(s.toString());
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