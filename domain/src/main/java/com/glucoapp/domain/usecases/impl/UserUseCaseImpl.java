package com.glucoapp.domain.usecases.impl;

import com.glucoapp.data.entities.User;
import com.glucoapp.data.repositories.firebase.Authentication;
import com.glucoapp.data.repositories.firebase.Datastore;
import com.glucoapp.data.repositories.firebase.Impl.AuthenticationImpl;
import com.glucoapp.data.repositories.firebase.Impl.DatastoreImpl;
import com.glucoapp.data.repositories.firebase.Listener;
import com.glucoapp.domain.usecases.UserUseCase;

public class UserUseCaseImpl implements UserUseCase, Listener {

    Listener listener;
    Authentication authentication;
    Datastore datastore;

    public UserUseCaseImpl(Listener listener) {
        this.listener = listener;
        authentication = new AuthenticationImpl(this);
        datastore = new DatastoreImpl(this);
    }

    @Override
    public String isExistingUser() {
        return authentication.isExistingUser();
    }

    @Override
    public void getCurrentUser(String uid) {
        datastore.getCurrentUser(uid);
    }

    @Override
    public boolean logOut() {
        return authentication.logOut();
    }

    @Override
    public void login(String email, String password) {
        authentication.loginWithEmail(email,password);
    }

    @Override
    public void register(User user) {
        authentication.registerWithEmail(user);
    }

    @Override
    public void update(User user) {
        datastore.updateDataUser(user);
    }


    /* Listener */

    @Override
    public void onSuccess() {
        listener.onSuccess();
    }

    @Override
    public void onError(String error) {
        listener.onError(error);
    }

    @Override
    public void onSuccessCurrentUser(User user) {
        listener.onSuccessCurrentUser(user);
    }

}
