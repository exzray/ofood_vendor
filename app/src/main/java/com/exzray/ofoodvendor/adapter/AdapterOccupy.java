package com.exzray.ofoodvendor.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.exzray.ofoodvendor.callback.CallbackSnapshot;
import com.exzray.ofoodvendor.databinding.ItemOccupyBinding;
import com.exzray.ofoodvendor.model.ModelProfile;
import com.exzray.ofoodvendor.model.ModelTable;
import com.exzray.ofoodvendor.utility.Convert;
import com.exzray.ofoodvendor.utility.Firebase;
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterOccupy extends RecyclerView.Adapter<AdapterOccupy.VH> {

    private static final String TAG = "AdapterOccupy";

    private final CallbackSnapshot callback;
    private final List<DocumentSnapshot> list = new ArrayList<>();


    public AdapterOccupy(CallbackSnapshot callback) {

        this.callback = callback;
    }

    @NonNull
    @NotNull
    @Override
    public VH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemOccupyBinding binding = ItemOccupyBinding.inflate(inflater, parent, false);
        return new VH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VH holder, int position) {
        holder.setData(list.get(position));
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

        private final ItemOccupyBinding binding;


        public VH(ItemOccupyBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void setData(DocumentSnapshot data) {

            final ModelTable table = Convert.snapshotToTable(data);

            binding.setCallback(callback);
            binding.setSnapshot(data);
            binding.setTable(table);
        }
    }
}
