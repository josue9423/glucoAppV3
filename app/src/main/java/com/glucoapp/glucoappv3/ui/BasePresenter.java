package com.glucoapp.glucoappv3.ui;

import com.glucoapp.data.entities.Glucosa;
import com.glucoapp.data.entities.User;
import com.glucoapp.data.repositories.firebase.ListenerUser;
import com.glucoapp.data.repositories.firebase.ListenerGlucosa;

import java.util.ArrayList;

public class BasePresenter implements ListenerUser, ListenerGlucosa {

    /* Estos metodos serán sobre escritos */

    @Override
    public void onSuccess() {}
    @Override
    public void onSuccessUpdateGlucoData(Glucosa glucosa) {}
    @Override
    public void onSuccessGetMounthlyData(ArrayList<Glucosa> listaGlucosa){}
    @Override
    public void onSuccessSaveGlucoData() {}
    @Override
    public void onSuccessCurrentUser(User user) {}

    /*Errores*/
    @Override
    public void onError(String error) {}

}