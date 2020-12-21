package com.exzray.ofoodvendor.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.exzray.ofoodvendor.callback.CallbackSnapshot;
import com.exzray.ofoodvendor.databinding.ItemProductBinding;
import com.exzray.ofoodvendor.model.ModelProduct;
import com.exzray.ofoodvendor.utility.Convert;
import com.exzray.ofoodvendor.utility.Firebase;
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.VH> {

    private final CallbackSnapshot callback_update;
    private final CallbackSnapshot callback_delete;
    private final List<DocumentSnapshot> list;


    public AdapterProduct(CallbackSnapshot update, CallbackSnapshot delete) {
        this.callback_update = update;
        this.callback_delete = delete;
        this.list = new ArrayList<>();
    }

    @NonNull
    @NotNull
    @Override
    public VH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemProductBinding binding = ItemProductBinding.inflate(inflater, parent, false);
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

        private final ItemProductBinding binding;


        public VH(ItemProductBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void setData(DocumentSnapshot doc) {

            final ModelProduct product = Convert.snapshotToProduct(doc);

            binding.setCallbackUpdate(callback_update);
            binding.setCallbackDelete(callback_delete);
            binding.setSnapshot(doc);
            binding.setProduct(product);

            Firebase
                    .getDocumentCategory(product.getCategory_uid())
                    .get()
                    .addOnSuccessListener(snapshot -> binding.setCategory(Convert.snapshotToCategory(snapshot)));

            if (product.getImage_photo() == null || product.getImage_photo().isEmpty()) return;

            Firebase
                    .getFirebaseStorage()
                    .getReference()
                    .child(product.getImage_photo())
                    .getDownloadUrl()
                    .addOnSuccessListener(uri -> Glide
                            .with(binding.getRoot())
                            .load(uri)
                            .into(binding.image));

        }
    }
}
