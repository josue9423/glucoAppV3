package com.glucoapp.glucoappv3.ui.history;

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
    public void onSuccessGetMounthlyData(ArrayList<Glucosa> listaGlucosa){
        ArrayList<Entry> listEntry = new ArrayList<>();
        int xAxis = Constants.CERO_VALUE;
        for(Glucosa glucosa : listaGlucosa){
            listEntry.add( new Entry(xAxis,glucosa.getNumberGlucosa()));
            xAxis++;
        }
        view.displayFilterData(glucosaUseCase.getLastData(listaGlucosa),glucosaUseCase.getBestData(listaGlucosa));
        view.displayListView(listReverse(listaGlucosa), listaGlucosa);
        view.displayData(listEntry);
        view.hideLoading();
    }

    @Override
    public void onError(String error){
        view.onError(error);
    }
}
