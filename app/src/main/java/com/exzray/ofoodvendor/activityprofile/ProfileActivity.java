package com.exzray.ofoodvendor.activityprofile;

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
import com.exzray.ofoodvendor.databinding.ActivityProfileBinding;
import com.exzray.ofoodvendor.model.ModelProfile;
import com.exzray.ofoodvendor.utility.Convert;
import com.exzray.ofoodvendor.utility.Firebase;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.firebase.firestore.DocumentReference;

import java.util.Date;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private ProfileViewModel view_model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        view_model = new ViewModelProvider(this).get(ProfileViewModel.class);

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
            dialog.setMessage("updating profile info");
            dialog.setCancelable(false);
            dialog.show();

            Firebase
                    .getFirebaseFirestore()
                    .runTransaction(transaction -> {

                        DocumentReference ref = Firebase.getDocumentOwnProfile();
                        ModelProfile profile = Convert.snapshotToProfile(transaction.get(ref));

                        assert binding.editName.getText() != null;

                        profile.setName(binding.editName.getText().toString());

                        transaction.set(ref, profile);

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
                        .getStorageProfile()
                        .child("image_photo")
                        .putFile(uri)
                        .addOnSuccessListener(snapshot -> {

                            assert snapshot.getMetadata() != null;
                            String path = snapshot.getMetadata().getPath();

                            Firebase
                                    .getFirebaseFirestore()
                                    .runTransaction(transaction -> {

                                        final DocumentReference ref = Firebase.getDocumentOwnProfile();
                                        final ModelProfile profile = Convert.snapshotToProfile(transaction.get(ref));

                                        profile.setImage_photo(path);
                                        profile.setEdited(new Date());

                                        transaction.set(ref, profile);

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
                .getProfileSnapshot()
                .observe(this, snapshot -> {

                    final ModelProfile profile = Convert.snapshotToProfile(snapshot);

                    binding.editName.setText(profile.getName());
                    Toast.makeText(this, profile.getName(), Toast.LENGTH_SHORT).show();

                    Firebase
                            .getStorageProfile()
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