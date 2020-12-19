package com.exzray.ofoodvendor.activitycategory;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exzray.ofoodvendor.utility.Firebase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.List;

public class CategoryViewModel extends ViewModel {

    private static final String TAG = "CategoryViewModel";

    private final MutableLiveData<List<DocumentSnapshot>> list_snapshot_category = new MutableLiveData<>();

    private ListenerRegistration registration_list_category;


    @Override
    protected void onCleared() {
        super.onCleared();

        if (registration_list_category != null) registration_list_category.remove();
    }

    public LiveData<List<DocumentSnapshot>> getListSnapshotCategory() {
        if (registration_list_category == null)
            registration_list_category = Firebase
                    .getCollectionListCategory()
                    .orderBy("position")
                    .addSnapshotListener((value, error) -> {
                        if (error != null) Log.i(TAG, error.getMessage());

                        if (value == null) return;

                        list_snapshot_category.setValue(value.getDocuments());

                    });


        return list_snapshot_category;
    }

}
