package com.glucoapp.data.listeners;

import com.glucoapp.data.entities.Glucosa;

import java.util.ArrayList;

public interface ListenerGlucosa {
    void onSuccessUpdateGlucoData(Glucosa glucosa);
    void onSuccessGetMounthlyData(ArrayList<Glucosa> listaGlucosa);
    void onSuccessSaveGlucoData();
    void onError(String error);
}
