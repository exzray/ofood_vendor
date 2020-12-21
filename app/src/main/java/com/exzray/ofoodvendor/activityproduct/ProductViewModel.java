package com.exzray.ofoodvendor.activityproduct;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exzray.ofoodvendor.utility.Firebase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.List;

public class ProductViewModel extends ViewModel {

    private static final String TAG = "ProductViewModel";

    private final MutableLiveData<List<DocumentSnapshot>> list_snapshot_product = new MutableLiveData<>();
    private final MutableLiveData<List<DocumentSnapshot>> list_snapshot_category = new MutableLiveData<>();

    private ListenerRegistration registration_product;
    private ListenerRegistration registration_category;


    @Override
    protected void onCleared() {
        super.onCleared();

        if (registration_product != null)
            registration_product.remove();

        if (registration_category != null)
            registration_category.remove();
    }

    public LiveData<List<DocumentSnapshot>> getListSnapshotProduct() {
        if (registration_product == null)
            registration_product = Firebase
                    .getCollectionListProduct()
                    .orderBy("category_uid")
                    .addSnapshotListener((value, error) -> {
                        if (error != null) Log.i(TAG, error.getMessage());

                        if (value == null) return;

                        list_snapshot_product.setValue(value.getDocuments());
                    });

        return list_snapshot_product;
    }

    public LiveData<List<DocumentSnapshot>> getListSnapshotCategory() {
        if (registration_category == null)
            registration_category = Firebase
                    .getCollectionListCategory()
                    .addSnapshotListener((value, error) -> {
                        if (error != null) Log.i(TAG, error.getMessage());

                        if (value == null) return;

                        list_snapshot_category.setValue(value.getDocuments());

                    });

        return list_snapshot_category;
    }
}
