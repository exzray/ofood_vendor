package com.exzray.ofoodvendor.activitycategory.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exzray.ofoodvendor.model.ModelCategory;
import com.exzray.ofoodvendor.utility.Convert;
import com.exzray.ofoodvendor.utility.Firebase;
import com.exzray.ofoodvendor.utility.Helper;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;

public class CategoryUpdateViewModel extends ViewModel {

    private final MediatorLiveData<Boolean> boolean_update_ready = new MediatorLiveData<>();

    private final MutableLiveData<DocumentSnapshot> snapshot_category = new MutableLiveData<>();

    private final MutableLiveData<String> string_name = new MutableLiveData<>();
    private final MutableLiveData<String> string_description = new MutableLiveData<>();
    private final MutableLiveData<String> string_error_name = new MutableLiveData<>();
    private final MutableLiveData<String> string_error_description = new MutableLiveData<>();

    private final MutableLiveData<Boolean> boolean_enable = new MutableLiveData<>();
    private final MutableLiveData<Boolean> boolean_read_success = new MutableLiveData<>();
    private final MutableLiveData<Boolean> boolean_update_success = new MutableLiveData<>();


    public CategoryUpdateViewModel() {
        boolean_update_ready.addSource(string_name, s -> checkUpdateReady());
        boolean_update_ready.addSource(string_description, s -> checkUpdateReady());

        checkUpdateReady();
    }

    public void setSnapshotCategory(String uid) {
        Firebase
                .getCollectionListCategory()
                .document(uid)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        boolean_read_success.setValue(true);
                        snapshot_category.setValue(task.getResult());
                    } else
                        boolean_read_success.setValue(false);

                });
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

    public void setBooleanEnable(Boolean b) {
        boolean_enable.setValue(b);
    }

    public LiveData<DocumentSnapshot> getSnapshotCategory() {
        return snapshot_category;
    }

    public LiveData<String> getStringErrorName() {
        return string_error_name;
    }

    public LiveData<String> getStringErrorDescription() {
        return string_error_description;
    }

    public LiveData<Boolean> getBooleanReadSuccess() {
        return boolean_read_success;
    }

    public LiveData<Boolean> getBooleanUpdateSuccess() {
        return boolean_update_success;
    }

    public void doUpdateCategory() {
        assert snapshot_category.getValue() != null;

        final DocumentSnapshot snapshot = snapshot_category.getValue();
        final ModelCategory category = Convert.snapshotToCategory(snapshot);
        category.setName(Helper.getString(string_name.getValue()));
        category.setDescription(Helper.getString(string_description.getValue()));
        category.setUpdated(new Date());
        category.setEnable(Helper.getBoolean(boolean_enable.getValue()));

        snapshot
                .getReference()
                .set(category)
                .addOnCompleteListener(task -> boolean_update_success.setValue(task.isSuccessful()));

    }

    private void checkUpdateReady() {
        final boolean s1 = Helper.isStringEmpty(string_name.getValue());
        final boolean s2 = Helper.isStringEmpty(string_description.getValue());

        final boolean ready = !(s1 || s2);

        boolean_update_ready.setValue(ready);
    }
}