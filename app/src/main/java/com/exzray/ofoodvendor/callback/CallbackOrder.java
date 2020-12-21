package com.exzray.ofoodvendor.callback;

import com.google.firebase.firestore.DocumentSnapshot;

public interface CallbackOrder {

    void call(DocumentSnapshot snapshot, boolean state);
}
