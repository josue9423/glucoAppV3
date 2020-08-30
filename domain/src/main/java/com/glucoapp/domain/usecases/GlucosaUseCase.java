package com.glucoapp.domain.usecases;

import com.glucoapp.data.entities.Glucosa;

import java.util.ArrayList;

public interface GlucosaUseCase {
    void updateGlucoData();
    void getMounthlyData();
    ArrayList<Glucosa> getDataPerMonth(ArrayList<Glucosa> listaGlucosa);
    ArrayList<Glucosa> getDataPerWeek(ArrayList<Glucosa> listaGlucosa);
    void saveGlucoData(Glucosa glucosa);

    Glucosa getLastData(ArrayList<Glucosa> listaGlucosa);
    Glucosa getBestData(ArrayList<Glucosa> listaGlucosa);
}
