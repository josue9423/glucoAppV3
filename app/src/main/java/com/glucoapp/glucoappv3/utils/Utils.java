package com.glucoapp.glucoappv3.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.glucoapp.glucoappv3.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Utils {

    public static ArrayAdapter<String> createSpinnerItems(List<String> list, Context context) {
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                context, R.layout.layout_spinner_item, list) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == Constants.CERO_VALUE) {
                    tv.setTextColor(Constants.GRAY_COLOR);
                } else {
                    tv.setTextColor(Constants.BEAUTYBLACK_COLOR);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.layout_spinner_item);
        return spinnerArrayAdapter;
    }

    public static int getPosition(String value, List<String> list){
        int contador = 0;
        int position = 0;
        for(String val : list){
            if(val.equals(value)){
                position = contador;
            }
            contador++;
        }
        return position;
    }

    public static String formatDateStringToString(String dateString) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.FORMAT_DATE);
        try {
            date = dateFormat.parse(dateString);
        } catch (Exception e) {
            Log.d("MS: ", "MS: Error");
        }
        return dateFormat.format(date);

    }

}
