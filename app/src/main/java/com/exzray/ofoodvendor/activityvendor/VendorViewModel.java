package com.exzray.ofoodvendor.activityvendor;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exzray.ofoodvendor.utility.Firebase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;

public class VendorViewModel extends ViewModel {

    private static final String TAG = "VendorViewModel";

    private final MutableLiveData<DocumentSnapshot> vendor_snapshot = new MutableLiveData<>();

    private ListenerRegistration registration_vendor_snapshot;


    @Override
    protected void onCleared() {
        super.onCleared();

        if (registration_vendor_snapshot != null)
            registration_vendor_snapshot.remove();
    }

    public void init() {
        init_vendor_snapshot();
    }

    public LiveData<DocumentSnapshot> getVendorSnapshot() {
        return vendor_snapshot;
    }

    private void init_vendor_snapshot() {
        if (registration_vendor_snapshot == null)
            registration_vendor_snapshot = Firebase
                    .getDocumentOwnVendor()
                    .addSnapshotListener((value, error) -> {

                        if (error != null)
                            Log.i(TAG, error.getMessage());

                        if (value == null)
                            return;

                        vendor_snapshot.setValue(value);
                    });
    }
}
