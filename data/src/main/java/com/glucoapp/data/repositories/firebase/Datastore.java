package com.glucoapp.data.repositories.firebase;

import com.glucoapp.data.entities.Glucosa;
import com.glucoapp.data.entities.User;

public interface Datastore {
    void updateDataUser(User user);
    void getCurrentUser(String uid);
    void saveGlucoData(Glucosa glucosa);
    void updateGlucoData();
    void getMounthlyData();
}
