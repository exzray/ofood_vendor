package com.exzray.ofoodvendor.activityorder;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.exzray.ofoodvendor.adapter.AdapterOrder;
import com.exzray.ofoodvendor.databinding.ActivityOrderBinding;
import com.exzray.ofoodvendor.model.ModelOrder;
import com.exzray.ofoodvendor.model.ModelProfile;
import com.exzray.ofoodvendor.model.ModelTable;
import com.exzray.ofoodvendor.utility.Convert;
import com.exzray.ofoodvendor.utility.Firebase;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.Locale;

public class OrderActivity extends AppCompatActivity {

    private ActivityOrderBinding binding;
    private OrderViewModel view_model;
    private DocumentSnapshot table_snapshot;
    private ModelTable table;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        view_model = new ViewModelProvider(this).get(OrderViewModel.class);

        setContentView(binding.getRoot());

        String path = getIntent().getStringExtra("path");
        view_model.init(path);

        setupViewModel();
    }

    private void setupViewModel() {
        AdapterOrder adapter = new AdapterOrder(this::callback);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(manager);

        view_model
                .getOrderListSnapshot()
                .observe(this, adapter::update);

        view_model
                .getTableSnapshot()
                .observe(this, snapshot -> {

                    table_snapshot = snapshot;

                    table = Convert.snapshotToTable(snapshot);

                    if (table.getUser_uid().isEmpty())
                        finish();

                    String str = "Table No. " + table.getIndex();

                    binding.textTable.setText(str);
                });

        view_model
                .getServingComplete()
                .observe(this, complete -> binding.button.setEnabled(complete));

        view_model
                .getTotal()
                .observe(this, price -> {

                    String str = String.format(Locale.getDefault(), "PAY(%.2f)", price);
                    binding.button.setText(str);
                });

        binding
                .button
                .setOnClickListener(v -> pay());
    }

    private void pay() {

        new MaterialAlertDialogBuilder(this)
                .setCancelable(false)
                .setMessage("are sure customer already pay?")
                .setPositiveButton("Yes", (dialog, which) -> clearTable())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void clearTable() {

        DocumentReference table_ref = table_snapshot.getReference();
        DocumentReference profile_ref = Firebase.getDocumentProfile(table.getUser_uid());

        table_ref
                .collection("list_order")
                .get()
                .addOnSuccessListener(query -> {

                    if (query == null) return;

                    for (DocumentSnapshot snapshot : query.getDocuments()) {
                        snapshot.getReference().delete();
                    }
                });

//        table_ref
//                .get()
//                .addOnSuccessListener(snapshot -> {
//                    ModelTable table = Convert.snapshotToTable(snapshot);
//                    table.setUser_uid("");
//
//                    table_ref.
//
//                });

        Firebase
                .getFirebaseFirestore()
                .runTransaction(transaction -> {

                    DocumentSnapshot snapshot = transaction.get(table_ref);

                    if (!snapshot.exists()) return null;

                    ModelTable table = Convert.snapshotToTable(snapshot);
                    table.setUser_uid("");

                    transaction.set(table_ref, table, SetOptions.merge());

                    return null;
                });

        Firebase
                .getFirebaseFirestore()
                .runTransaction(transaction -> {

                    DocumentSnapshot snapshot = transaction.get(profile_ref);

                    if (!snapshot.exists()) return null;

                    ModelProfile profile = Convert.snapshotToProfile(snapshot);
                    profile.setSitting("");

                    transaction.set(profile_ref, profile, SetOptions.merge());

                    return null;
                });
    }

    private void callback(DocumentSnapshot snapshot, boolean state) {
        Firebase
                .getFirebaseFirestore()
                .runTransaction(transaction -> {

                    DocumentReference reference = snapshot.getReference();
                    ModelOrder order = Convert.snapshotToOrder(transaction.get(reference));
                    order.setStatus(state ? ModelOrder.STATUS.SERVING : ModelOrder.STATUS.PREPARING);

                    transaction.set(reference, order);

                    return null;
                });
    }
}