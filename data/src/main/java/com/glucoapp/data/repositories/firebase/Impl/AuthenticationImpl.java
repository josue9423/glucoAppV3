package com.glucoapp.data.repositories.firebase.Impl;

import androidx.annotation.NonNull;

import com.glucoapp.data.entities.User;
import com.glucoapp.data.repositories.firebase.Authentication;
import com.glucoapp.data.listeners.ListenerUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class AuthenticationImpl implements Authentication {

    private DatastoreImpl datastoreImpl;
    private FirebaseAuth mAuth;

    /* Se declaran las interfaces.*/
    private ListenerUser listenerUser;

    public AuthenticationImpl(){
        this.mAuth = FirebaseAuth.getInstance();
    }

    public AuthenticationImpl(ListenerUser listenerUser) {
        this.mAuth = FirebaseAuth.getInstance();
        this.listenerUser = listenerUser;
    }

    /* Implementación de métodos */
    @Override
    public void loginWithEmail(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                    listenerUser.onSuccess();
                else
                    if(task.getException() != null){
                        listenerUser.onError(task.getException().getMessage());
                    }
                }
        });
    }

    @Override
    public void registerWithEmail(User user) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    user.setUid(mAuth.getCurrentUser().getUid());
                    datastoreImpl = new DatastoreImpl(listenerUser);
                    datastoreImpl.updateDataUser(user);
                }
                else {
                    if(task.getException() != null){
                        listenerUser.onError(task.getException().getMessage());
                    }
                }
            }
        });
    }

    @Override
    public String isExistingUser() {
        if(mAuth.getCurrentUser() != null){
            return mAuth.getCurrentUser().getUid();
        }
        else{
            return null;
        }
    }

    @Override
    public boolean logOut() {
        if(mAuth.getCurrentUser() != null){
            mAuth.signOut();
            return Boolean.TRUE;
        }
        else{
            return Boolean.FALSE;
        }
    }
}
