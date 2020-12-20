package com.exzray.ofoodvendor.activitymain;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exzray.ofoodvendor.utility.Firebase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";

    private final MediatorLiveData<Map<String, String>> map_table_info = new MediatorLiveData<>();

    private final MutableLiveData<List<DocumentSnapshot>> list_table_total = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<DocumentSnapshot>> list_table_occupy = new MutableLiveData<>(new ArrayList<>());

    private final MutableLiveData<DocumentSnapshot> snapshot_profile = new MutableLiveData<>();
    private final MutableLiveData<DocumentSnapshot> snapshot_vendor = new MutableLiveData<>();

    private ListenerRegistration registration_table_total;
    private ListenerRegistration registration_table_occupy;
    private ListenerRegistration registration_profile;
    private ListenerRegistration registration_vendor;


    public MainViewModel() {
        map_table_info.addSource(list_table_total, snapshots -> checkTableInfo(snapshots.size(), null));
        map_table_info.addSource(list_table_occupy, snapshots -> checkTableInfo(null, snapshots.size()));

        checkTableInfo(0, 0);
    }

    private void checkTableInfo(Integer total, Integer occupy) {
        int _total = 0;
        int _occupy = 0;

        if (total != null)
            _total = total;

        if (occupy != null)
            _occupy = occupy;

        String str_label = String.format(Locale.getDefault(), "%d / %d", _occupy, _total);
        String str_status;

        if (_total == _occupy)
            str_status = "unvailable";
        else
            str_status = "available";

        Map<String, String> info = new HashMap<>();
        info.put("label", str_label);
        info.put("status", str_status);

        map_table_info.setValue(info);
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (registration_table_total != null)
            registration_table_total.remove();

        if (registration_table_occupy != null)
            registration_table_occupy.remove();

        if (registration_profile != null)
            registration_profile.remove();

        if (registration_vendor != null)
            registration_vendor.remove();
    }

    public void init() {
        if (registration_table_total == null)
            registration_table_total = Firebase
                    .getCollectionListTable()
                    .addSnapshotListener((value, error) -> {
                        if (error != null) Log.i(TAG, error.getMessage());

                        if (value == null) return;

                        list_table_total.setValue(value.getDocuments());
                    });

        if (registration_table_occupy == null)
            registration_table_occupy = Firebase
                    .getCollectionListTable()
                    .whereEqualTo("user_uid", "")
                    .addSnapshotListener((value, error) -> {
                        if (error != null) Log.i(TAG, error.getMessage());

                        if (value == null) return;

                        list_table_occupy.setValue(value.getDocuments());
                    });

        if (registration_profile == null)
            registration_profile = Firebase
                    .getDocumentOwnProfile()
                    .addSnapshotListener((value, error) -> {
                        if (error != null) Log.i(TAG, error.getMessage());

                        if (value == null) return;

                        snapshot_profile.setValue(value);
                    });

        if (registration_vendor == null)
            registration_vendor = Firebase
                    .getDocumentOwnVendor()
                    .addSnapshotListener((value, error) -> {
                        if (error != null) Log.i(TAG, error.getMessage());

                        if (value == null) return;

                        snapshot_vendor.setValue(value);
                    });
    }

    public LiveData<Map<String, String>> getMapTableInfo() {
        return map_table_info;
    }

    public LiveData<List<DocumentSnapshot>> getListTableTotal() {
        return list_table_total;
    }

    public LiveData<List<DocumentSnapshot>> getListTableOccupy() {
        return list_table_occupy;
    }

    public LiveData<DocumentSnapshot> getSnapshotProfile() {
        return snapshot_profile;
    }

    public LiveData<DocumentSnapshot> getSnapshotVendor() {
        return snapshot_vendor;
    }
}
