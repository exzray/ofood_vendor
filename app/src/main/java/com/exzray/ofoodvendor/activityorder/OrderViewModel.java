package com.exzray.ofoodvendor.activityorder;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exzray.ofoodvendor.model.ModelOrder;
import com.exzray.ofoodvendor.utility.Convert;
import com.exzray.ofoodvendor.utility.Firebase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;

public class OrderViewModel extends ViewModel {

    private static final String TAG = "OrderViewModel";

    private final MutableLiveData<DocumentSnapshot> table_snapshot = new MutableLiveData<>();
    private final MediatorLiveData<List<DocumentSnapshot>> order_list_snapshot = new MediatorLiveData<>();
    private final MediatorLiveData<Boolean> serving_complete = new MediatorLiveData<>();

    private final MutableLiveData<Double> total_double = new MutableLiveData<>();

    private ListenerRegistration registration_table_snapshot;
    private ListenerRegistration registration_order_list_snapshot;


    public OrderViewModel() {

        order_list_snapshot
                .addSource(table_snapshot, this::updateOrderListSnapshot);

        serving_complete
                .addSource(order_list_snapshot, this::updateServingComplete);

        updateServingComplete(new ArrayList<>());
    }

    private void updateServingComplete(List<DocumentSnapshot> snapshots) {
        if (snapshots.isEmpty()) {
            serving_complete.setValue(false);
            return;
        }

        double total = 0.0;

        for (DocumentSnapshot snapshot : snapshots) {
            ModelOrder order = Convert.snapshotToOrder(snapshot);

            if (order.getStatus() == ModelOrder.STATUS.PREPARING) {

                serving_complete.setValue(false);

                return;
            } else total += order.getPrice() * ((double) order.getQuantity());
        }

        serving_complete.setValue(true);
        total_double.setValue(total);
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (registration_table_snapshot != null)
            registration_table_snapshot.remove();

        if (registration_order_list_snapshot != null)
            registration_order_list_snapshot.remove();
    }

    public void init(String path) {
        if (registration_table_snapshot == null)
            registration_table_snapshot = Firebase
                    .getFirebaseFirestore()
                    .document(path)
                    .addSnapshotListener((value, error) -> {

                        if (error != null) Log.i(TAG, error.getMessage());

                        if (value == null) return;

                        table_snapshot.setValue(value);
                    });
    }

    public LiveData<DocumentSnapshot> getTableSnapshot() {
        return table_snapshot;
    }

    public LiveData<List<DocumentSnapshot>> getOrderListSnapshot() {
        return order_list_snapshot;
    }

    public LiveData<Boolean> getServingComplete() {
        return serving_complete;
    }

    public LiveData<Double> getTotal(){
        return total_double;
    }

    private void updateOrderListSnapshot(DocumentSnapshot snapshot) {
        if (registration_order_list_snapshot == null)
            registration_order_list_snapshot = snapshot
                    .getReference()
                    .collection("list_order")
                    .addSnapshotListener((value, error) -> {

                        if (error != null) Log.i(TAG, error.getMessage());

                        if (value == null) return;

                        order_list_snapshot.setValue(value.getDocuments());
                    });
    }
}
