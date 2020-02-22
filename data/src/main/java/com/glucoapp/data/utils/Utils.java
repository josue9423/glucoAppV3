package com.glucoapp.data.utils;

public class Utils {

    public static String convertDateToDocumentId(String date){
        return date.replace(Constants.SLASH,Constants.GUION).replace(Constants.ESPACIO,Constants.GUION_BAJO);
    }
}
