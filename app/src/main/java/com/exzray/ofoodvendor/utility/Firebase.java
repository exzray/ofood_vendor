package com.exzray.ofoodvendor.utility;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Firebase {

    public static FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    public static FirebaseUser getFirebaseUser() {
        return getFirebaseAuth().getCurrentUser();
    }

    public static FirebaseFirestore getFirebaseFirestore() {
        return FirebaseFirestore.getInstance();
    }

    public static CollectionReference getCollectionListProfile() {
        return getFirebaseFirestore().collection("list_profile");
    }

    public static CollectionReference getCollectionListVendor() {
        return getFirebaseFirestore().collection("list_vendor");
    }

    public static CollectionReference getCollectionListCategory() {
        return getDocumentOwnVendor().collection("list_category");
    }

    public static CollectionReference getCollectionListProduct() {
        return getDocumentOwnVendor().collection("list_product");
    }

    public static DocumentReference getDocumentProfile(String uid) {
        return getCollectionListProfile().document(uid);
    }

    public static DocumentReference getDocumentVendor(String uid) {
        return getCollectionListVendor().document(uid);
    }

    public static DocumentReference getDocumentCategory(String uid) {
        return getCollectionListCategory().document(uid);
    }

    public static DocumentReference getDocumentOwnProfile() {
        return getDocumentProfile(getFirebaseUser().getUid());
    }

    public static DocumentReference getDocumentOwnVendor() {
        return getDocumentVendor(getFirebaseUser().getUid());
    }
}
