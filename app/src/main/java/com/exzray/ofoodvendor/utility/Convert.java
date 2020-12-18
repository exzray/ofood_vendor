package com.exzray.ofoodvendor.utility;

import com.exzray.ofoodvendor.model.ModelCategory;
import com.google.firebase.firestore.DocumentSnapshot;

public class Convert {

    public static ModelCategory snapshotToCategory(DocumentSnapshot snapshot){
        return snapshot.toObject(ModelCategory.class);
    }

}
