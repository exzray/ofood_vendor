package com.exzray.ofoodvendor.activitymain.ui;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exzray.ofoodvendor.utility.Firebase;
import com.exzray.ofoodvendor.utility.Helper;

public class MainLogoutViewModel extends ViewModel {

    private final MutableLiveData<Boolean> boolean_task_success = new MutableLiveData<>();

    public LiveData<Boolean> getBooleanTaskSuccess() {
        return boolean_task_success;
    }

    public void doLogout(Context context) {
        Helper
                .getGoogleSignInClient(context)
                .signOut()
                .addOnSuccessListener(unused -> {
                            Firebase
                                    .getFirebaseAuth()
                                    .signOut();
                            boolean_task_success.setValue(true);
                        }
                );
    }

}