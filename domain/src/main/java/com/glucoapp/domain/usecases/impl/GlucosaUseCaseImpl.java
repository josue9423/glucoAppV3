package com.glucoapp.domain.usecases.impl;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.glucoapp.data.entities.Glucosa;
import com.glucoapp.data.repositories.firebase.Datastore;
import com.glucoapp.data.repositories.firebase.Impl.DatastoreImpl;
import com.glucoapp.data.repositories.firebase.ListenerGlucosa;
import com.glucoapp.data.utils.Constants;
import com.glucoapp.domain.usecases.GlucosaUseCase;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class GlucosaUseCaseImpl implements GlucosaUseCase, ListenerGlucosa {

    Datastore datastore;
    ListenerGlucosa listenerGlucosa;

    public GlucosaUseCaseImpl(ListenerGlucosa listenerGlucosa) {
        this.listenerGlucosa = listenerGlucosa;
        datastore = new DatastoreImpl(this);
    }

    @Override
    public void updateGlucoData() {
        datastore.updateGlucoData();
    }

    @Override
    public void getMounthlyData() {
        datastore.getMounthlyData();
    }

    @Override
    public void saveGlucoData(Glucosa glucosa) {
        datastore.saveGlucoData(glucosa);
    }

    @Override
    public Glucosa getBestData(ArrayList<Glucosa> listaGlucosa) {
        return listaGlucosa.stream().min(Comparator.comparing(Glucosa::getNumberGlucosa)).get();
    }

    @Override
    public Glucosa getLastData(ArrayList<Glucosa> listaGlucosa) {
        int last = listaGlucosa.size();
        return (listaGlucosa.size() > Constants.CERO_VALUE) ? listaGlucosa.get(last - Constants.UNO_VALUE) : new Glucosa();
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Glucosa> getDataPerMonth(ArrayList<Glucosa> listaGlucosa) {
        /* CALCULO DE LAS FECHAS*/
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.roll(Calendar.MONTH, false);
        String haceMes = format.format(calendar.getTime());
        LocalDate localDate = LocalDate.parse(haceMes);
        /* FIN CALCULO DE LAS FECHAS*/

        List<Glucosa> listaFiltrada = listaGlucosa
                .stream()
                .filter(g -> g.localDate().isAfter(localDate))
                .collect(Collectors.toList());

        return (ArrayList<Glucosa>) listaFiltrada;
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Glucosa> getDataPerWeek(ArrayList<Glucosa> listaGlucosa) {
        /* CALCULO DE LAS FECHAS*/
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.roll(Calendar.WEEK_OF_YEAR, false);
        String haceMes = format.format(calendar.getTime());
        LocalDate localDate = LocalDate.parse(haceMes);
        /* FIN CALCULO DE LAS FECHAS*/

        List<Glucosa> listaFiltrada = listaGlucosa
                .stream()
                .filter(g -> g.localDate().isAfter(localDate))
                .collect(Collectors.toList());

        return (ArrayList<Glucosa>) listaFiltrada;
    }

    /* Inicio Listener */
    @Override
    public void onSuccessUpdateGlucoData(Glucosa glucosa) {
        listenerGlucosa.onSuccessUpdateGlucoData(glucosa);
    }

    @Override
    public void onSuccessGetMounthlyData(ArrayList<Glucosa> listaGlucosa) {
        if (listaGlucosa.size() > Constants.CERO_VALUE)
            listenerGlucosa.onSuccessGetMounthlyData(listaGlucosa);
        else
            listenerGlucosa.onError(Constants.GLUCODATA_ERROR);
    }

    @Override
    public void onSuccessSaveGlucoData() {
        listenerGlucosa.onSuccessSaveGlucoData();
    }

    @Override
    public void onError(String error) {
        listenerGlucosa.onError(error);
    }
    /* Fin Listener */
}
