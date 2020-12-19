package com.exzray.ofoodvendor.utility;

import com.exzray.ofoodvendor.model.ModelCategory;
import com.exzray.ofoodvendor.model.ModelProduct;
import com.exzray.ofoodvendor.model.ModelProfile;
import com.exzray.ofoodvendor.model.ModelVendor;
import com.google.firebase.firestore.DocumentSnapshot;

public class Convert {

    public static ModelProfile snapshotToProfile(DocumentSnapshot snapshot) {
        return snapshot.toObject(ModelProfile.class);
    }

    public static ModelVendor snapshotToVendor(DocumentSnapshot snapshot) {
        return snapshot.toObject(ModelVendor.class);
    }

    public static ModelCategory snapshotToCategory(DocumentSnapshot snapshot) {
        return snapshot.toObject(ModelCategory.class);
    }

    public static ModelProduct snapshotToProduct(DocumentSnapshot snapshot) {
        return snapshot.toObject(ModelProduct.class);
    }

}
