package com.glucoapp.data.entities;

import com.glucoapp.data.utils.Constants;

import java.io.Serializable;

public class Glucosa implements Serializable {

    private String glucosa;
    private String fecha;

    public Glucosa() {
    }

    public Glucosa(String glucosa, String fecha) {
        this.glucosa = glucosa;
        this.fecha = fecha;
    }

    public String getGlucosa() {
        return glucosa;
    }

    public void setGlucosa(String glucosa) {
        this.glucosa = glucosa;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getNumberGlucosa(){ return Integer.valueOf(this.glucosa);}

    public String getFechaCorta(){ return this.fecha.split(Constants.ESPACIO)[0];}

    public String getHora(){ return this.fecha.split(Constants.ESPACIO)[1];}


    @Override
    public String toString() {
        return "Glucosa{" +
                "glucosa='" + glucosa + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
