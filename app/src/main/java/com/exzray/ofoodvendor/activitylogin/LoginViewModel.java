package com.exzray.ofoodvendor.activitylogin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exzray.ofoodvendor.utility.Firebase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

public class LoginViewModel extends ViewModel {

    private final MediatorLiveData<Boolean> boolean_details_exist = new MediatorLiveData<>();

    private final MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private final MutableLiveData<DocumentSnapshot> profile = new MutableLiveData<>();
    private final MutableLiveData<DocumentSnapshot> vendor = new MutableLiveData<>();


    public LoginViewModel() {
        boolean_details_exist.addSource(profile, snapshot -> doSnapshotExist());
        boolean_details_exist.addSource(vendor, snapshot -> doSnapshotExist());
    }

    public LiveData<Boolean> getBooleanDetailsExist() {
        return boolean_details_exist;
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public LiveData<DocumentSnapshot> getProfile() {
        return profile;
    }

    public LiveData<DocumentSnapshot> getVendor() {
        return vendor;
    }

    public void authenticate() {
        user.setValue(Firebase.getFirebaseUser());
    }

    public void verification() {
        Firebase
                .getDocumentOwnProfile()
                .get()
                .addOnSuccessListener(profile::setValue)
                .addOnFailureListener(e -> profile.setValue(null));

        Firebase
                .getDocumentOwnVendor()
                .get()
                .addOnSuccessListener(vendor::setValue)
                .addOnFailureListener(e -> vendor.setValue(null));
    }

    private boolean checkSnapshotExist(DocumentSnapshot snapshot) {
        return snapshot != null && snapshot.exists();
    }

    private void doSnapshotExist() {
        final boolean s1 = checkSnapshotExist(profile.getValue());
        final boolean s2 = checkSnapshotExist(vendor.getValue());

        boolean_details_exist.setValue(s1 && s2);
    }
}
