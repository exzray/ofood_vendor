package com.exzray.ofoodvendor.callback;

import com.google.firebase.firestore.DocumentSnapshot;

public interface CallbackSnapshot {

    void onClick(DocumentSnapshot snapshot);

}
