package com.glucoapp.glucoappv3.interfaces;

import com.github.mikephil.charting.data.Entry;
import com.glucoapp.data.entities.Glucosa;

import java.util.ArrayList;

public interface History  {
    interface View{
        void showLoading();
        void hideLoading();
        void displayData(ArrayList<Entry> listEntry, ArrayList<Glucosa> listaFiltrada);
        void displayListView(ArrayList<Glucosa> listaReversaGlucosa,ArrayList<Glucosa> listaGlucosa);
        void displayFilterData(Glucosa ultimaMuestra, Glucosa mejorMuestra);
        void onError(String error);
    }
    interface Presenter{
        void setView(History.View view);
        void getMounthlyData();
        void getDataPerMounth(ArrayList<Glucosa> listaGlucosa);
        void getDataPerWeek(ArrayList<Glucosa> listaGlucosa);
        void getDataPerAllTime(ArrayList<Glucosa> listaGlucosa);
    }
}
