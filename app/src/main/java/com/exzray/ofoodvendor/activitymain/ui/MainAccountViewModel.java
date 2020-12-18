package com.exzray.ofoodvendor.activitymain.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exzray.ofoodvendor.R;
import com.exzray.ofoodvendor.model.ModelNavigation;

import java.util.ArrayList;
import java.util.List;

public class MainAccountViewModel extends ViewModel {

    private final MutableLiveData<List<ModelNavigation>> list_navigation = new MutableLiveData<>();


    public LiveData<List<ModelNavigation>> getListNavigation(){

        final ModelNavigation vendor = new ModelNavigation();
        vendor.setName("Vendor");
        vendor.setDescription("view vendor information");
        vendor.setTag("vendor");
        vendor.setIcon(R.drawable.ic_outline_account_24);

        final ModelNavigation location = new ModelNavigation();
        location.setName("Location");
        location.setDescription("view vendor location");
        location.setTag("location");
        location.setIcon(R.drawable.ic_outline_location_24);

        final ModelNavigation promotion = new ModelNavigation();
        promotion.setName("Promotions");
        promotion.setDescription("view promotion list");
        promotion.setTag("promotion");
        promotion.setIcon(R.drawable.ic_baseline_list_24);

        final ModelNavigation setting = new ModelNavigation();
        setting.setName("Settings");
        setting.setDescription("view app setting");
        setting.setTag("setting");
        setting.setIcon(R.drawable.ic_outline_settings_24);

        final List<ModelNavigation> list = new ArrayList<>();
        list.add(vendor);
        list.add(location);
        list.add(promotion);
        list.add(setting);

        list_navigation.setValue(list);

        return list_navigation;
    }

}