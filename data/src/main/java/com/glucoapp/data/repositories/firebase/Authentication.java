package com.glucoapp.data.repositories.firebase;

import com.glucoapp.data.entities.User;

public interface Authentication {

    void loginWithEmail(String email, String password);

    void registerWithEmail(User user);

    String isExistingUser();

    boolean logOut();

}
