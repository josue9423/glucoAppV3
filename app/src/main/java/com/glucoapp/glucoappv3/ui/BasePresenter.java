package com.glucoapp.glucoappv3.ui;

import com.glucoapp.data.entities.User;
import com.glucoapp.data.repositories.firebase.Listener;

public class BasePresenter implements Listener {

    /* Estos metodos serán sobre escritos */

    @Override
    public void onSuccess() {}
    @Override
    public void onError(String error) {}
    @Override
    public void onSuccessCurrentUser(User user) {}

}