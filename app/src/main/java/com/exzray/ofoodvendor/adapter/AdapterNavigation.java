package com.exzray.ofoodvendor.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exzray.ofoodvendor.callback.CallbackNavigation;
import com.exzray.ofoodvendor.databinding.ItemNavigationBinding;
import com.exzray.ofoodvendor.model.ModelNavigation;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdapterNavigation extends RecyclerView.Adapter<AdapterNavigation.VH> {

    private final CallbackNavigation callback;
    private final List<ModelNavigation> list;


    public AdapterNavigation(CallbackNavigation callback) {
        this.callback = callback;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ItemNavigationBinding binding = ItemNavigationBinding.inflate(inflater, parent, false);
        return new VH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VH holder, int position) {
        final ModelNavigation navigation = list.get(position);
        holder.setData(navigation);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update(List<ModelNavigation> navigations) {
        list.clear();
        list.addAll(navigations);
        notifyDataSetChanged();
    }


    class VH extends RecyclerView.ViewHolder {


        private ItemNavigationBinding binding;

        public VH(ItemNavigationBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void setData(ModelNavigation navigation) {
            binding.setCallback(callback);
            binding.setNavigation(navigation);
        }
    }
}
