package com.glucoapp.data.repositories.firebase;

import com.glucoapp.data.entities.User;

public interface Listener {
    void onSuccess();
    void onError(String error);
    void onSuccessCurrentUser(User user);
}
