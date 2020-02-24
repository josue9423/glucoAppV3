package com.glucoapp.glucoappv3.ui.history;

import android.widget.ArrayAdapter;

import com.github.mikephil.charting.data.Entry;
import com.glucoapp.data.entities.Glucosa;
import com.glucoapp.domain.usecases.GlucosaUseCase;
import com.glucoapp.domain.usecases.impl.GlucosaUseCaseImpl;
import com.glucoapp.glucoappv3.interfaces.History;
import com.glucoapp.glucoappv3.ui.BasePresenter;
import com.glucoapp.glucoappv3.utils.Constants;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class HistoryPresenter extends BasePresenter implements History.Presenter {

    History.View view;
    GlucosaUseCase glucosaUseCase;

    public HistoryPresenter(){
        glucosaUseCase = new GlucosaUseCaseImpl(this);
    }

    private ArrayList<Glucosa> listReverse(ArrayList<Glucosa> listaGlucosa){
        ArrayList<Glucosa> listaReversa = new ArrayList<>();
        listaGlucosa.stream()
                .collect(Collectors.toCollection(LinkedList::new))
                .descendingIterator()
                .forEachRemaining(listaReversa::add);
        return listaReversa;
    }

    @Override
    public void setView(History.View view) {
        this.view = view;
    }

    @Override
    public void getMounthlyData() {
        view.showLoading();
        glucosaUseCase.getMounthlyData();
    }

    @Override
    public void getDataPerMounth(ArrayList<Glucosa> listaGlucosa) {
        view.showLoading();
        ArrayList<Glucosa> listaGlucosaFiltrada = glucosaUseCase.getDataPerMonth(listaGlucosa);
        ArrayList<Entry> listEntry = getEntrys(listaGlucosaFiltrada);
        view.displayData(listEntry, listaGlucosaFiltrada);
        view.hideLoading();
    }

    @Override
    public void getDataPerWeek(ArrayList<Glucosa> listaGlucosa) {
        view.showLoading();
        ArrayList<Glucosa> listaGlucosaFiltrada = glucosaUseCase.getDataPerWeek(listaGlucosa);
        ArrayList<Entry> listEntry = getEntrys(listaGlucosaFiltrada);
        view.displayData(listEntry, listaGlucosaFiltrada);
        view.hideLoading();
    }

    @Override
    public void getDataPerAllTime(ArrayList<Glucosa> listaGlucosa) {
        view.showLoading();
        ArrayList<Entry> listEntry = getEntrys(listaGlucosa);
        view.displayData(listEntry, listaGlucosa);
        view.hideLoading();
    }

    private ArrayList<Entry> getEntrys(ArrayList<Glucosa> listaGlucosa){
        ArrayList<Entry> listEntry = new ArrayList<>();
        int xAxis = Constants.CERO_VALUE;
        for(Glucosa glucosa : listaGlucosa){
            listEntry.add( new Entry(xAxis,glucosa.getNumberGlucosa()));
            xAxis++;
        }
        return listEntry;
    }

    /* Inicio Listener*/
    @Override
    public void onSuccessGetMounthlyData(ArrayList<Glucosa> listaGlucosa){
        ArrayList<Entry> listEntry = getEntrys(listaGlucosa);
        view.displayFilterData(glucosaUseCase.getLastData(listaGlucosa),glucosaUseCase.getBestData(listaGlucosa));
        view.displayListView(listReverse(listaGlucosa), listaGlucosa);
        view.displayData(listEntry, listaGlucosa);
        view.hideLoading();
    }

    @Override
    public void onError(String error){
        view.onError(error);
    }
    /* Fin Listener */
}
