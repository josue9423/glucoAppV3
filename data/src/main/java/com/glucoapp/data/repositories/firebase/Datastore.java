package com.glucoapp.data.repositories.firebase;

import com.glucoapp.data.entities.User;

public interface Datastore {
    void updateDataUser(User user);
    void getCurrentUser(String uid);
}
