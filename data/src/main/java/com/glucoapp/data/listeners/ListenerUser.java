package com.glucoapp.data.listeners;

import com.glucoapp.data.entities.User;

public interface ListenerUser {
    void onSuccess();
    void onError(String error);
    void onSuccessCurrentUser(User user);
}
