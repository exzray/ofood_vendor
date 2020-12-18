package com.exzray.ofoodvendor.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exzray.ofoodvendor.callback.CallbackSnapshot;
import com.exzray.ofoodvendor.databinding.ItemCategoryBinding;
import com.exzray.ofoodvendor.model.ModelCategory;
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.VH> {

    private static int counter = 0;

    private final CallbackSnapshot callback_update;
    private final CallbackSnapshot callback_delete;
    private final List<DocumentSnapshot> list;


    public AdapterCategory(CallbackSnapshot update, CallbackSnapshot delete) {
        this.callback_update = update;
        this.callback_delete = delete;
        this.list = new ArrayList<>();
    }

    @NonNull
    @NotNull
    @Override
    public VH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(inflater, parent, false);
        return new VH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VH holder, int position) {
//        final DocumentSnapshot snapshot = list.get(position);
        holder.setData(null);

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public void update(List<DocumentSnapshot> snapshots) {
        list.clear();
        list.addAll(snapshots);
        notifyDataSetChanged();
    }


    class VH extends RecyclerView.ViewHolder {

        private ItemCategoryBinding binding;


        public VH(ItemCategoryBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void setData(DocumentSnapshot snapshot) {

            ModelCategory category = new ModelCategory();
            category.setName("Category");
            category.setDescription("Lorem ipsum");
            category.setPosition(counter);

            counter += 1;

            binding.setCallbackUpdate(callback_update);
            binding.setCallbackDelete(callback_delete);
            binding.setSnapshot(snapshot);
            binding.setCategory(category);

        }
    }
}
