package com.glucoapp.domain.usecases;

import com.glucoapp.data.entities.User;

public interface UserUseCase {
    /* Home - Splash */
    String isExistingUser();
    void getCurrentUser(String uid);
    boolean logOut();
    /* Login */
    void login(String email, String password);
    /* Register */
    void register(User user);
    void update(User user);

}
