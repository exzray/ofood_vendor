package com.exzray.ofoodvendor.activityproduct.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exzray.ofoodvendor.model.ModelProduct;
import com.exzray.ofoodvendor.utility.Firebase;
import com.exzray.ofoodvendor.utility.Helper;

public class ProductCreateViewModel extends ViewModel {

    private final MediatorLiveData<Boolean> boolean_submit_enable = new MediatorLiveData<>();

    private final MutableLiveData<String> string_name = new MutableLiveData<>();
    private final MutableLiveData<String> string_price = new MutableLiveData<>();
    private final MutableLiveData<String> string_description = new MutableLiveData<>();
    private final MutableLiveData<String> string_category = new MutableLiveData<>();

    private final MutableLiveData<String> string_error_name = new MutableLiveData<>();
    private final MutableLiveData<String> string_error_price = new MutableLiveData<>();
    private final MutableLiveData<String> string_error_description = new MutableLiveData<>();
    private final MutableLiveData<String> string_error_category = new MutableLiveData<>();

    private final MutableLiveData<Boolean> boolean_task_success = new MutableLiveData<>();


    public ProductCreateViewModel() {
        boolean_submit_enable.addSource(string_name, s -> checkFieldReady());
        boolean_submit_enable.addSource(string_price, s -> checkFieldReady());
        boolean_submit_enable.addSource(string_description, s -> checkFieldReady());
        boolean_submit_enable.addSource(string_error_category, s -> checkFieldReady());

        checkFieldReady();
    }

    public void setStringName(String s) {
        string_name.setValue(s);

        if (Helper.isStringEmpty(s))
            string_error_name.setValue("product name must not empty");
        else
            string_error_name.setValue("");
    }

    public void setStringPrice(String s) {
        string_price.setValue(s);

        if (Helper.isStringEmpty(s))
            string_error_price.setValue("product price must not empty");
        else
            string_error_price.setValue("");
    }

    public void setStringDescription(String s) {
        string_description.setValue(s);

        if (Helper.isStringEmpty(s))
            string_error_description.setValue("product description must not empty");
        else
            string_error_description.setValue("");
    }

    public void setStringCategory(String s) {
        string_category.setValue(s);

        if (Helper.isStringEmpty(s))
            string_error_category.setValue("product category must not empty");
        else
            string_error_category.setValue("");
    }

    public LiveData<String> getStringErrorName() {
        return string_error_name;
    }

    public LiveData<String> getStringErrorPrice() {
        return string_error_price;
    }

    public LiveData<String> getStringErrorDescription() {
        return string_error_description;
    }

    public LiveData<String> getStringErrorCategory() {
        return string_error_category;
    }

    public LiveData<Boolean> getBooleanSubmitEnable() {
        return boolean_submit_enable;
    }

    public LiveData<Boolean> getBooleanTaskSuccess() {
        return boolean_task_success;
    }

    public void doCreateProduct(int position) {
        final ModelProduct product = new ModelProduct();
        product.setName(Helper.getString(string_name.getValue()));
        product.setPrice(Helper.parseStringToDouble(string_price.getValue()));
        product.setDescription(Helper.getString(string_description.getValue()));
        product.setCategory_uid(Helper.getString(string_category.getValue()));
        product.setPosition(position);

        Firebase
                .getCollectionListProduct()
                .add(product)
                .addOnCompleteListener(task -> boolean_task_success.setValue(task.isSuccessful()));
    }

    private void checkFieldReady() {
        final boolean b1 = Helper.isStringEmpty(string_name.getValue());
        final boolean b2 = Helper.isStringEmpty(string_price.getValue());
        final boolean b3 = Helper.isStringEmpty(string_description.getValue());
        final boolean b4 = Helper.isStringEmpty(string_category.getValue());

        final boolean ready = !(b1 || b2 || b3 || b4);

        boolean_submit_enable.setValue(ready);
    }
}