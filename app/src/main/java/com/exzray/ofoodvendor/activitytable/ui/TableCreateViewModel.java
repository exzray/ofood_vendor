package com.exzray.ofoodvendor.activitytable.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exzray.ofoodvendor.model.ModelTable;
import com.exzray.ofoodvendor.utility.Firebase;

import java.util.Locale;

public class TableCreateViewModel extends ViewModel {

    private final MediatorLiveData<Boolean> button_boolean_enable = new MediatorLiveData<>();

    private final MutableLiveData<String> table_string_name = new MutableLiveData<>();
    private final MutableLiveData<String> table_string_size_error = new MutableLiveData<>();

    private final MutableLiveData<Integer> table_integer_index = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> table_integer_size = new MutableLiveData<>(0);

    private final MutableLiveData<Boolean> task_boolean_success = new MutableLiveData<>();


    public TableCreateViewModel() {
        button_boolean_enable.addSource(table_integer_index, index -> updateButtonBooleanEnable());
        button_boolean_enable.addSource(table_integer_size, size -> updateButtonBooleanEnable());

        updateButtonBooleanEnable();
    }

    public void setTableIntegerIndex(int index) {
        String str_table_name = String.format(Locale.getDefault(), "Table No. %d", index);

        table_string_name.setValue(str_table_name);
        table_integer_index.setValue(index);
    }

    public void setTableIntegerSize(String s) {
        if (s.isEmpty()) {
            table_string_size_error.setValue("size should not empty");
            return;
        } else
            table_string_size_error.setValue("");

        int size = Integer.parseInt(s);
        table_integer_size.setValue(size);
    }

    public LiveData<Boolean> getButtonBooleanEnable() {
        return button_boolean_enable;
    }

    public LiveData<String> getTableStringName() {
        return table_string_name;
    }

    public LiveData<String> getTableStringSizeError() {
        return table_string_size_error;
    }

    public LiveData<Boolean> getTaskBooleanSuccess() {
        return task_boolean_success;
    }

    public void doTask() {
        final ModelTable table = new ModelTable();
        table.setIndex(table_integer_index.getValue());
        table.setSize(table_integer_size.getValue());

        Firebase
                .getCollectionListTable()
                .add(table)
                .addOnCompleteListener(task -> task_boolean_success.setValue(task.isSuccessful()));
    }

    private void updateButtonBooleanEnable() {
        boolean b1 = table_integer_index.getValue() != null;
        boolean b2 = table_integer_size.getValue() != null && table_integer_size.getValue() > 0;

        button_boolean_enable.setValue((b1 && b2));
    }
}