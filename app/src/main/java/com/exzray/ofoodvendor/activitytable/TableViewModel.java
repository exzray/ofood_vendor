package com.exzray.ofoodvendor.activitytable;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exzray.ofoodvendor.utility.Firebase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;

public class TableViewModel extends ViewModel {

    private static final String TAG = "TableViewModel";

    private final MutableLiveData<List<DocumentSnapshot>> list_table = new MutableLiveData<>(new ArrayList<>());

    private final MutableLiveData<Integer> list_table_size = new MutableLiveData<>(0);

    private ListenerRegistration registration_list_table;


    public void init() {
        init_list_table();
    }

    public LiveData<List<DocumentSnapshot>> getListTable() {
        return list_table;
    }

    public LiveData<Integer> getListTableSize() {
        return list_table_size;
    }

    private void init_list_table() {
        if (registration_list_table == null)
            registration_list_table = Firebase
                    .getCollectionListTable()
                    .orderBy("index")
                    .addSnapshotListener((value, error) -> {

                        if (error != null)
                            Log.i(TAG, error.getMessage());

                        if (value == null)
                            return;

                        list_table.setValue(value.getDocuments());
                        list_table_size.setValue(value.size());

                    });
    }
}
