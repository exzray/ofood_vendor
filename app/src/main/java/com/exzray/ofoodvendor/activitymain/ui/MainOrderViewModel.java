package com.exzray.ofoodvendor.activitymain.ui;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exzray.ofoodvendor.utility.Firebase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.List;

public class MainOrderViewModel extends ViewModel {

    private static final String TAG = "MainOrderViewModel";

    private final MutableLiveData<List<DocumentSnapshot>> table_list_snapshot = new MutableLiveData<>();

    private ListenerRegistration registration_table_list_snapshot;


    @Override
    protected void onCleared() {
        super.onCleared();

        if (registration_table_list_snapshot != null)
            registration_table_list_snapshot.remove();
    }

    public void init() {
        if (registration_table_list_snapshot == null)
            registration_table_list_snapshot = Firebase
                    .getCollectionListTable()
                    .whereNotEqualTo("user_uid", "")
                    .addSnapshotListener((value, error) -> {

                        if (error != null) Log.i(TAG, error.getMessage());

                        if (value == null) return;

                        table_list_snapshot
                                .setValue(value.getDocuments());
                    });
    }

    public LiveData<List<DocumentSnapshot>> getTableListSnapshot() {
        return table_list_snapshot;
    }
}