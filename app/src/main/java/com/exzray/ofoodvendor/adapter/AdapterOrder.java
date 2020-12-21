package com.exzray.ofoodvendor.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.exzray.ofoodvendor.callback.CallbackOrder;
import com.exzray.ofoodvendor.databinding.ItemOrderBinding;
import com.exzray.ofoodvendor.model.ModelOrder;
import com.exzray.ofoodvendor.utility.Convert;
import com.exzray.ofoodvendor.utility.Firebase;
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterOrder extends RecyclerView.Adapter<AdapterOrder.VH> {

    private final CallbackOrder callback;
    private final List<DocumentSnapshot> list = new ArrayList<>();


    public AdapterOrder(CallbackOrder callback) {
        this.callback = callback;
    }

    @NonNull
    @NotNull
    @Override
    public VH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemOrderBinding binding = ItemOrderBinding.inflate(inflater, parent, false);
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

        private final ItemOrderBinding binding;


        public VH(ItemOrderBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void setData(DocumentSnapshot data) {

            final ModelOrder order = Convert.snapshotToOrder(data);

            binding.setOrder(order);

            binding
                    .cb
                    .setOnClickListener(v -> callback.call(data, binding.cb.isChecked()));

            binding
                    .cb
                    .setChecked(order.getStatus() == ModelOrder.STATUS.SERVING);

            if (order.getImage_photo().isEmpty()) return;

            Firebase
                    .getFirebaseStorage()
                    .getReference()
                    .child(order.getImage_photo())
                    .getDownloadUrl()
                    .addOnSuccessListener(uri -> Glide
                            .with(binding.getRoot())
                            .load(uri)
                            .into(binding.image));
        }
    }
}
