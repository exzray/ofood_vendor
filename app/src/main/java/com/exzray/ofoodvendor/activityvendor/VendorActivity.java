package com.exzray.ofoodvendor.activityvendor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.exzray.ofoodvendor.R;
import com.exzray.ofoodvendor.databinding.ActivityVendorBinding;
import com.exzray.ofoodvendor.model.ModelVendor;
import com.exzray.ofoodvendor.utility.Convert;
import com.exzray.ofoodvendor.utility.Firebase;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.firebase.firestore.DocumentReference;

import java.util.Date;

public class VendorActivity extends AppCompatActivity {

    private ActivityVendorBinding binding;
    private VendorViewModel view_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVendorBinding.inflate(getLayoutInflater());
        view_model = new ViewModelProvider(this).get(VendorViewModel.class);

        view_model.init();

        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        setupForm();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.update_option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_update) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage("updating vendor info");
            dialog.setCancelable(false);
            dialog.show();

            Firebase
                    .getFirebaseFirestore()
                    .runTransaction(transaction -> {

                        DocumentReference ref = Firebase.getDocumentOwnVendor();
                        ModelVendor vendor = Convert.snapshotToVendor(transaction.get(ref));

                        assert binding.editName.getText() != null;

                        vendor.setName(binding.editName.getText().toString());

                        transaction.set(ref, vendor);

                        return null;

                    })
                    .addOnSuccessListener(o -> dialog.dismiss());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();

                Firebase
                        .getStorageVendor()
                        .child("image_photo")
                        .putFile(uri)
                        .addOnSuccessListener(snapshot -> {

                            assert snapshot.getMetadata() != null;
                            String path = snapshot.getMetadata().getPath();

                            Firebase
                                    .getFirebaseFirestore()
                                    .runTransaction(transaction -> {

                                        final DocumentReference ref = Firebase.getDocumentOwnVendor();
                                        final ModelVendor vendor = Convert.snapshotToVendor(transaction.get(ref));

                                        vendor.setImage_photo(path);
                                        vendor.setEdited(new Date());

                                        transaction.set(ref, vendor);

                                        return null;
                                    });
                        });
            }

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "image upload cancel", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupForm() {
        view_model
                .getVendorSnapshot()
                .observe(this, snapshot -> {

                    final ModelVendor vendor = Convert.snapshotToVendor(snapshot);

                    binding.editName.setText(vendor.getName());

                    Firebase
                            .getStorageVendor()
                            .child("image_photo")
                            .getDownloadUrl()
                            .addOnSuccessListener(uri -> Glide.with(this).load(uri).into(binding.imagePhoto));

                });

        binding
                .imageCamera
                .setOnClickListener(v ->
                        ImagePicker
                                .Companion
                                .with(this)
                                .crop(1, 1)
                                .maxResultSize(800, 800)
                                .saveDir(this.getCacheDir())
                                .start());
    }
}