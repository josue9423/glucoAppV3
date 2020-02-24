package com.glucoapp.data.repositories.firebase.Impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.glucoapp.data.entities.Glucosa;
import com.glucoapp.data.entities.User;
import com.glucoapp.data.repositories.firebase.Authentication;
import com.glucoapp.data.repositories.firebase.Datastore;
import com.glucoapp.data.repositories.firebase.ListenerUser;
import com.glucoapp.data.repositories.firebase.ListenerGlucosa;
import com.glucoapp.data.utils.Constants;
import com.glucoapp.data.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DatastoreImpl implements Datastore {

    FirebaseFirestore mFirebase;

    /* Se declaran las interfaces.*/
    private ListenerUser listenerUser;
    private ListenerGlucosa listenerGlucosa;
    private int statusValue = Constants.CERO_VALUE;

    public DatastoreImpl(ListenerUser listenerUser) {
        this.mFirebase = FirebaseFirestore.getInstance();
        this.listenerUser = listenerUser;
    }

    public DatastoreImpl(ListenerGlucosa listenerGlucosa) {
        this.mFirebase = FirebaseFirestore.getInstance();
        this.listenerGlucosa = listenerGlucosa;
    }

    /* Implementación de métodos */
    @Override
    public void updateDataUser(User user) {
        mFirebase.collection(Constants.COLECTION_USER).document(user.getUid()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    listenerUser.onSuccess();
                } else {
                    if (task.getException() != null) {
                        listenerUser.onError(task.getException().getMessage());
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
                        listenerUser.onSuccessCurrentUser(document.toObject(User.class));

                    } else {
                        listenerUser.onError(task.getException().getMessage());
                    }
                } else {
                    listenerUser.onError(task.getException().getMessage());
                }
            }
        });
    }

    @Override
    public void saveGlucoData(Glucosa glucosa) {
        Authentication authentication = new AuthenticationImpl();
        String uid = authentication.isExistingUser();
        String document = glucosa.getFecha();
        mFirebase.collection(Constants.COLECTION_USER).document(uid).collection(Constants.COLECTION_DATA).document(document).set(glucosa).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    listenerGlucosa.onSuccessSaveGlucoData();
                } else {
                    if (task.getException() != null) {
                        listenerGlucosa.onError(task.getException().getMessage());
                    }
                }
            }
        });
    }

    @Override
    public void updateGlucoData() {
        Authentication authentication = new AuthenticationImpl();
        String uid = authentication.isExistingUser();
        final CollectionReference collectionReference = mFirebase.collection(Constants.COLECTION_USER).document(uid).collection(Constants.COLECTION_DATA);
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    listenerGlucosa.onError(e.getMessage().toString());
                }
                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            if(statusValue>Constants.CERO_VALUE)
                                listenerGlucosa.onSuccessUpdateGlucoData(dc.getDocument().toObject(Glucosa.class));
                            break;
                    }
                }
                statusValue++;
            }
        });
    }

    @Override
    public void getMounthlyData(){
        Authentication authentication = new AuthenticationImpl();
        String uid = authentication.isExistingUser();
        final CollectionReference collectionReference = mFirebase.collection(Constants.COLECTION_USER).document(uid).collection(Constants.COLECTION_DATA);
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null) {
                        ArrayList<Glucosa> listaGlucosa = new ArrayList<>();
                        for(DocumentSnapshot documentSnapshot: querySnapshot.getDocuments()){
                            if(documentSnapshot != null)
                                listaGlucosa.add(documentSnapshot.toObject(Glucosa.class));
                        }
                        listenerGlucosa.onSuccessGetMounthlyData(listaGlucosa);
                    } else {
                        listenerUser.onError(task.getException().getMessage());
                    }
                } else {
                    listenerUser.onError(task.getException().getMessage());
                }
            }
        });
    }


}
