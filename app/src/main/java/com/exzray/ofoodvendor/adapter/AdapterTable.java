package com.exzray.ofoodvendor.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exzray.ofoodvendor.callback.CallbackSnapshot;
import com.exzray.ofoodvendor.databinding.ItemTableBinding;
import com.exzray.ofoodvendor.model.ModelTable;
import com.exzray.ofoodvendor.utility.Convert;
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterTable extends RecyclerView.Adapter<AdapterTable.VH> {

    private final CallbackSnapshot callback_update;
    private final CallbackSnapshot callback_delete;
    private final List<DocumentSnapshot> list;


    public AdapterTable(CallbackSnapshot update, CallbackSnapshot delete) {
        this.callback_update = update;
        this.callback_delete = delete;
        this.list = new ArrayList<>();
    }

    @NonNull
    @NotNull
    @Override
    public VH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemTableBinding binding = ItemTableBinding.inflate(inflater, parent, false);
        return new VH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VH holder, int position) {
        final DocumentSnapshot snapshot = list.get(position);
        holder.setData(snapshot);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update(List<DocumentSnapshot> snapshots) {
        list.clear();
        list.addAll(snapshots);
        notifyDataSetChanged();
    }


    class VH extends RecyclerView.ViewHolder {

        private final ItemTableBinding binding;


        public VH(ItemTableBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void setData(DocumentSnapshot snapshot) {
            final ModelTable table = Convert.snapshotToTable(snapshot);

            binding.setCallbackUpdate(callback_update);
            binding.setCallbackDelete(callback_delete);
            binding.setSnapshot(snapshot);
            binding.setTable(table);

        }
    }
}
