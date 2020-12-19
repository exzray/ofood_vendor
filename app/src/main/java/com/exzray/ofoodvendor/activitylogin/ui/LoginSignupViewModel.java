package com.exzray.ofoodvendor.activitylogin.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exzray.ofoodvendor.model.ModelProfile;
import com.exzray.ofoodvendor.model.ModelVendor;
import com.exzray.ofoodvendor.utility.Firebase;
import com.exzray.ofoodvendor.utility.Helper;

public class LoginSignupViewModel extends ViewModel {

    private final MediatorLiveData<Boolean> boolean_create_ready = new MediatorLiveData<>();
    private final MediatorLiveData<Boolean> boolean_create_success = new MediatorLiveData<>();

    private final MutableLiveData<String> string_profile = new MutableLiveData<>();
    private final MutableLiveData<String> string_vendor = new MutableLiveData<>();
    private final MutableLiveData<String> string_error_profile = new MutableLiveData<>();
    private final MutableLiveData<String> string_error_vendor = new MutableLiveData<>();

    private final MutableLiveData<Boolean> boolean_profile = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> boolean_vendor = new MutableLiveData<>(false);


    public LoginSignupViewModel() {
        boolean_create_ready.addSource(string_profile, s -> checkCreateReady());
        boolean_create_ready.addSource(string_vendor, s -> checkCreateReady());

        boolean_create_success.addSource(boolean_profile, success -> checkCreateSuccess());
        boolean_create_success.addSource(boolean_vendor, success -> checkCreateSuccess());

        checkCreateReady();
    }

    public void setStringProfile(String profile) {
        string_profile.setValue(profile);

        if (Helper.isStringEmpty(profile))
            string_error_profile.setValue("profile name must not empty");
        else
            string_error_profile.setValue("");
    }

    public void setStringVendor(String vendor) {
        string_vendor.setValue(vendor);

        if (Helper.isStringEmpty(vendor))
            string_error_vendor.setValue("vendor name must not empty");
        else
            string_error_vendor.setValue("");
    }

    public LiveData<Boolean> getBooleanCreateReady() {
        return boolean_create_ready;
    }

    public LiveData<Boolean> getBooleanCreateSuccess() {
        return boolean_create_success;
    }

    public LiveData<String> getStringErrorName() {
        return string_error_profile;
    }

    public LiveData<String> getStringErrorDescription() {
        return string_error_vendor;
    }

    public void create() {

        final ModelProfile profile = new ModelProfile();
        profile.setName(string_profile.getValue());

        final ModelVendor vendor = new ModelVendor();
        vendor.setName(string_vendor.getValue());

        Firebase
                .getDocumentOwnProfile()
                .set(profile)
                .addOnCompleteListener(task -> boolean_profile.setValue(task.isSuccessful()));

        Firebase
                .getDocumentOwnVendor()
                .set(vendor)
                .addOnCompleteListener(task -> boolean_vendor.setValue(task.isSuccessful()));

    }

    private void checkCreateReady() {
        final boolean s1 = Helper.isStringEmpty(string_profile.getValue());
        final boolean s2 = Helper.isStringEmpty(string_vendor.getValue());

        final boolean ready = !(s1 || s2);

        boolean_create_ready.setValue(ready);
    }

    private void checkCreateSuccess() {
        assert boolean_profile.getValue() != null;
        assert boolean_vendor.getValue() != null;

        boolean b1 = boolean_profile.getValue();
        boolean b2 = boolean_vendor.getValue();

        boolean_create_success.setValue((b1 && b2));

    }
}