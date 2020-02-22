package com.glucoapp.data.repositories.firebase;

import com.glucoapp.data.entities.User;

public interface ListenerUser {
    void onSuccess();
    void onError(String error);
    void onSuccessCurrentUser(User user);
}
