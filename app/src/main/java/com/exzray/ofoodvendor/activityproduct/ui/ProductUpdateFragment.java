package com.exzray.ofoodvendor.activityproduct.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.exzray.ofoodvendor.R;
import com.exzray.ofoodvendor.activityproduct.ProductViewModel;
import com.exzray.ofoodvendor.databinding.FragmentProductUpdateBinding;
import com.exzray.ofoodvendor.model.ModelCategory;
import com.exzray.ofoodvendor.model.ModelProduct;
import com.exzray.ofoodvendor.model.ModelSelection;
import com.exzray.ofoodvendor.utility.Convert;
import com.exzray.ofoodvendor.utility.Firebase;
import com.exzray.ofoodvendor.utility.Helper;
import com.github.drjacky.imagepicker.ImagePicker;

import org.jetbrains.annotations.NotNull;

public class ProductUpdateFragment extends Fragment {

    private FragmentProductUpdateBinding binding;
    private ProductViewModel view_model_activity;
    private ProductUpdateViewModel view_model_fragment;
    private ProgressDialog dialog;
    private String uid;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentProductUpdateBinding.inflate(inflater, container, false);
        view_model_activity = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        view_model_fragment = new ViewModelProvider(this).get(ProductUpdateViewModel.class);
        dialog = new ProgressDialog(requireActivity());

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
            dialog.setMessage("updating product");
            dialog.show();

            view_model_fragment.doUpdateProduct();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();

                Firebase
                        .getStorageVendor()
                        .child("product/" + uid)
                        .putFile(uri)
                        .addOnSuccessListener(snapshot -> {

                            assert snapshot.getMetadata() != null;
                            String path = snapshot.getMetadata().getPath();

                            Firebase
                                    .getDocumentProduct(uid)
                                    .update("image_photo", path);

                            Glide.with(this).load(uri).into(binding.imagePhoto);
                        });
            }

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(requireContext(), ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(requireContext(), "image upload cancel", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view_model_fragment
                .getBooleanTaskSuccess()
                .observe(getViewLifecycleOwner(), success -> {
                    dialog.dismiss();

                    if (success) {
                        Toast.makeText(requireContext(), "product updated", Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(requireContext(), "something goes wrong, please try again", Toast.LENGTH_SHORT).show();

                });

        uid = ProductUpdateFragmentArgs.fromBundle(getArguments()).getProductUid();
        view_model_fragment.setSnapshotProduct(uid);

        view_model_fragment
                .getSnapshotProduct()
                .observe(getViewLifecycleOwner(), snapshot -> {

                    final ModelProduct product = Convert.snapshotToProduct(snapshot);

                    binding.editName.setText(product.getName());
                    binding.editPrice.setText(String.valueOf(product.getPrice()));
                    binding.editDescription.setText(product.getDescription());
                    binding.swEnable.setChecked(product.getEnable());

                    if (!product.getImage_photo().isEmpty())
                        Firebase
                                .getFirebaseStorage()
                                .getReference()
                                .child(product.getImage_photo())
                                .getDownloadUrl()
                                .addOnSuccessListener(uri -> Glide
                                        .with(this)
                                        .load(uri)
                                        .into(binding.imagePhoto));

                    Firebase
                            .getDocumentCategory(product.getCategory_uid())
                            .get()
                            .addOnSuccessListener(snapshot_category -> {
                                final ModelCategory category = Convert.snapshotToCategory(snapshot_category);
                                binding.editCategory.setText(category.getName(), false);
                            });

                    view_model_fragment.setStringCategory(product.getCategory_uid());
                    view_model_fragment.setBooleanEnable(product.getEnable());

                });

        setupImageUpload();
        setupForm();
    }

    private void setupImageUpload() {

        binding
                .imageCamera
                .setOnClickListener(v ->
                        ImagePicker
                                .Companion
                                .with(this)
                                .crop(1, 1)
                                .maxResultSize(800, 800)
                                .saveDir(requireContext().getCacheDir())
                                .start());

    }

    private void setupForm() {

        binding.editName.addTextChangedListener(watcher_name);
        binding.editPrice.addTextChangedListener(watcher_price);
        binding.editDescription.addTextChangedListener(watcher_description);
        binding.swEnable.setOnCheckedChangeListener((buttonView, isChecked) -> view_model_fragment.setBooleanEnable(isChecked));

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