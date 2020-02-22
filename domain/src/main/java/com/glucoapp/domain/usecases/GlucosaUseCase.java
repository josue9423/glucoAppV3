package com.glucoapp.domain.usecases;

import com.glucoapp.data.entities.Glucosa;

import java.util.ArrayList;

public interface GlucosaUseCase {
    void updateGlucoData();
    void getMounthlyData();
    void saveGlucoData(Glucosa glucosa);

    Glucosa getLastData(ArrayList<Glucosa> listaGlucosa);
    Glucosa getBestData(ArrayList<Glucosa> listaGlucosa);
}
