package com.exzray.ofoodvendor;

import android.app.Application;
import android.widget.Toast;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
    }
}
