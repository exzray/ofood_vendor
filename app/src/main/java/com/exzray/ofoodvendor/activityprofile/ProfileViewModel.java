package com.exzray.ofoodvendor.activityprofile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exzray.ofoodvendor.utility.Firebase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;

public class ProfileViewModel extends ViewModel {

    private static final String TAG = "ProfileViewModel";

    private final MutableLiveData<DocumentSnapshot> profile_snapshot = new MutableLiveData<>();

    private ListenerRegistration registration_profile_snapshot;


    @Override
    protected void onCleared() {
        super.onCleared();

        if (registration_profile_snapshot != null)
            registration_profile_snapshot.remove();
    }

    public void init() {
        init_profile_snapshot();
    }

    public LiveData<DocumentSnapshot> getProfileSnapshot() {
        return profile_snapshot;
    }

    private void init_profile_snapshot() {
        if (registration_profile_snapshot == null)
            registration_profile_snapshot = Firebase
                    .getDocumentOwnProfile()
                    .addSnapshotListener((value, error) -> {

                        if (error != null)
                            Log.i(TAG, error.getMessage());

                        if (value == null)
                            return;

                        profile_snapshot.setValue(value);
                    });
    }
}
