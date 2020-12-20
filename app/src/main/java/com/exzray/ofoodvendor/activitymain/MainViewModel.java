package com.exzray.ofoodvendor.activitymain;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exzray.ofoodvendor.utility.Firebase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;

public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";

    private final MutableLiveData<DocumentSnapshot> snapshot_profile = new MutableLiveData<>();
    private final MutableLiveData<DocumentSnapshot> snapshot_vendor = new MutableLiveData<>();

    private ListenerRegistration registration_profile;
    private ListenerRegistration registration_vendor;


    @Override
    protected void onCleared() {
        super.onCleared();

        if (registration_profile != null)
            registration_profile.remove();

        if (registration_vendor != null)
            registration_vendor.remove();
    }

    public LiveData<DocumentSnapshot> getSnapshotProfile() {
        if (registration_profile == null)
            registration_profile = Firebase
                    .getDocumentOwnProfile()
                    .addSnapshotListener((value, error) -> {
                        if (error != null) Log.i(TAG, error.getMessage());

                        if (value == null) return;

                        snapshot_profile.setValue(value);
                    });

        return snapshot_profile;
    }

    public LiveData<DocumentSnapshot> getSnapshotVendor() {
        if (registration_vendor == null)
            registration_vendor = Firebase
                    .getDocumentOwnVendor()
                    .addSnapshotListener((value, error) -> {
                        if (error != null) Log.i(TAG, error.getMessage());

                        if (value == null) return;

                        snapshot_vendor.setValue(value);
                    });

        return snapshot_vendor;
    }
}
