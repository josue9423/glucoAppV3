package com.glucoapp.data.repositories.firebase.Impl;

import androidx.annotation.NonNull;

import com.glucoapp.data.entities.User;
import com.glucoapp.data.repositories.firebase.Listener;
import com.glucoapp.data.repositories.firebase.Datastore;
import com.glucoapp.data.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DatastoreImpl implements Datastore {

    FirebaseFirestore mFirebase;

    /* Se declaran las interfaces.*/
    private Listener listener;

    public DatastoreImpl(Listener listener) {
        this.mFirebase = FirebaseFirestore.getInstance();
        this.listener = listener;
    }

    /* Implementación de métodos */
    @Override
    public void updateDataUser(User user) {
        mFirebase.collection(Constants.COLECTION_USER).document(user.getUid()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    listener.onSuccess();
                } else {
                    if (task.getException() != null) {
                        listener.onError(task.getException().getMessage());
                    }
                }

            }
        });
    }

    @Override
    public void getCurrentUser(String uid) {
        DocumentReference docRef = mFirebase.collection(Constants.COLECTION_USER).document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        listener.onSuccessCurrentUser(document.toObject(User.class));

                    } else {
                        listener.onError(task.getException().getMessage());
                    }
                } else {
                    listener.onError(task.getException().getMessage());
                }
            }
        });
    }
}
