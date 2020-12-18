package com.exzray.ofoodvendor.activitymain.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exzray.ofoodvendor.R;
import com.exzray.ofoodvendor.model.ModelNavigation;

import java.util.ArrayList;
import java.util.List;

public class MainDashboardViewModel extends ViewModel {

    private final MutableLiveData<List<ModelNavigation>> list_navigation = new MutableLiveData<>();


    public LiveData<List<ModelNavigation>> getListNavigation(){

        final ModelNavigation order = new ModelNavigation();
        order.setName("Order");
        order.setDescription("view order history");
        order.setTag("order");
        order.setIcon(R.drawable.ic_outline_history_24);

        final ModelNavigation booking = new ModelNavigation();
        booking.setName("Booking");
        booking.setDescription("view booking history");
        booking.setTag("booking");
        booking.setIcon(R.drawable.ic_outline_history_24);

        final ModelNavigation product = new ModelNavigation();
        product.setName("Product");
        product.setDescription("view product list");
        product.setTag("product");
        product.setIcon(R.drawable.ic_baseline_list_24);

        final ModelNavigation category = new ModelNavigation();
        category.setName("Category");
        category.setDescription("view category list");
        category.setTag("category");
        category.setIcon(R.drawable.ic_baseline_list_24);

        final ModelNavigation table = new ModelNavigation();
        table.setName("Table");
        table.setDescription("view table list");
        table.setTag("table");
        table.setIcon(R.drawable.ic_baseline_list_24);

        final List<ModelNavigation> list = new ArrayList<>();
        list.add(order);
        list.add(booking);
        list.add(product);
        list.add(category);
        list.add(table);

        list_navigation.setValue(list);

        return list_navigation;
    }
}