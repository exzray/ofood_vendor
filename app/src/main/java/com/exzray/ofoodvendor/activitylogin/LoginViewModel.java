package com.exzray.ofoodvendor.activitylogin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exzray.ofoodvendor.utility.Firebase;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public void authenticate() {
        user.setValue(Firebase.getFirebaseUser());
    }
}
