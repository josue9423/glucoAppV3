package com.glucoapp.domain.usecases;

import com.glucoapp.data.entities.Glucosa;

import java.util.ArrayList;

public interface GlucosaUseCase {
    void updateGlucoData();
    void getMounthlyData();

    Glucosa getLastData(ArrayList<Glucosa> listaGlucosa);
    Glucosa getBestData(ArrayList<Glucosa> listaGlucosa);
}
