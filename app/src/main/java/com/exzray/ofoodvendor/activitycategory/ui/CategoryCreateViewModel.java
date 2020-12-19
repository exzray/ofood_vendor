package com.exzray.ofoodvendor.activitycategory.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exzray.ofoodvendor.utility.Helper;

public class CategoryCreateViewModel extends ViewModel {

    private final MediatorLiveData<Boolean> boolean_create_ready = new MediatorLiveData<>();
    private final MediatorLiveData<Boolean> boolean_create_running = new MediatorLiveData<>();

    private final MutableLiveData<String> string_name = new MutableLiveData<>();
    private final MutableLiveData<String> string_description = new MutableLiveData<>();

    private final MutableLiveData<String> string_error_name = new MutableLiveData<>();
    private final MutableLiveData<String> string_error_description = new MutableLiveData<>();


    public CategoryCreateViewModel() {
        boolean_create_ready.addSource(string_name, s -> checkCreateReady());
        boolean_create_ready.addSource(string_description, s -> checkCreateReady());

        checkCreateReady();
    }

    public void setStringName(String name) {
        string_name.setValue(name);

        if (Helper.isStringEmpty(name))
            string_error_name.setValue("category name must not empty");
        else
            string_error_name.setValue("");
    }

    public void setStringDescription(String description) {
        string_description.setValue(description);

        if (Helper.isStringEmpty(description))
            string_error_description.setValue("category description must not empty");
        else
            string_error_description.setValue("");
    }

    public LiveData<Boolean> getBooleanCreateReady() {
        return boolean_create_ready;
    }

    public LiveData<Boolean> getBooleanCreateRunning() {
        return boolean_create_running;
    }

    public LiveData<String> getStringName() {
        return string_name;
    }

    public LiveData<String> getStringDescription() {
        return string_description;
    }

    public LiveData<String> getStringErrorName() {
        return string_error_name;
    }

    public LiveData<String> getStringErrorDescription() {
        return string_error_description;
    }

    private void checkCreateReady() {
        final boolean s1 = Helper.isStringEmpty(string_name.getValue());
        final boolean s2 = Helper.isStringEmpty(string_description.getValue());

        final boolean ready = !(s1 || s2);

        boolean_create_ready.setValue(ready);
    }
}