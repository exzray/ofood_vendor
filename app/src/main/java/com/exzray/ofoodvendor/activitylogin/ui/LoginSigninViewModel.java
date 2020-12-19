package com.exzray.ofoodvendor.activitylogin.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginSigninViewModel extends ViewModel {

    private final MutableLiveData<Boolean> boolean_login_enable = new MutableLiveData<>();


    public void setBooleanLoginEnable(boolean enable) {
        boolean_login_enable.setValue(enable);
    }

    public LiveData<Boolean> getBooleanLoginEnable() {
        return boolean_login_enable;
    }
}