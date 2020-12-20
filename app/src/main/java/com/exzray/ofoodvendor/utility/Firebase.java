package com.exzray.ofoodvendor.utility;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Firebase {

    // auth
    public static FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    public static FirebaseUser getFirebaseUser() {
        return getFirebaseAuth().getCurrentUser();
    }

    // firestore
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

    public static CollectionReference getCollectionListTable() {
        return getDocumentOwnVendor().collection("list_table");
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

    public static DocumentReference getDocumentProduct(String uid) {
        return getCollectionListProduct().document(uid);
    }

    public static DocumentReference getDocumentTable(String uid) {
        return getCollectionListTable().document(uid);
    }

    public static DocumentReference getDocumentOwnProfile() {
        return getDocumentProfile(getFirebaseUser().getUid());
    }

    public static DocumentReference getDocumentOwnVendor() {
        return getDocumentVendor(getFirebaseUser().getUid());
    }

    // storage
    public static FirebaseStorage getFirebaseStorage() {
        return FirebaseStorage.getInstance();
    }

    public static StorageReference getStorageProfile() {
        return getFirebaseStorage().getReference("profile/" + getFirebaseUser().getUid());
    }

    public static StorageReference getStorageVendor() {
        return getFirebaseStorage().getReference("vendor/" + getFirebaseUser().getUid());
    }
}
