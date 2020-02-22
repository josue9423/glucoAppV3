package com.glucoapp.glucoappv3.utils.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.glucoapp.data.entities.Glucosa;
import com.glucoapp.glucoappv3.R;
import com.glucoapp.glucoappv3.utils.Constants;

import java.util.ArrayList;

public class  AdapterListHistory extends BaseAdapter {

    private static LayoutInflater layoutInflater = null;

    Context context;
    ArrayList<Glucosa> listaGlucosa = new ArrayList<Glucosa>();


    public AdapterListHistory(Context context, ArrayList<Glucosa> listaGlucosa){
        this.context = context;
        this.listaGlucosa = listaGlucosa;
        this.layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = layoutInflater.inflate(R.layout.layout_list_item, null);
        TextView textGlucosa = view.findViewById(R.id.text_glucosa);
        TextView textFecha = view.findViewById(R.id.text_fecha);
        ImageView imageView = view.findViewById(R.id.image_list);

        Glucosa glucosa = listaGlucosa.get(position);

        textGlucosa.setText(glucosa.getGlucosa());
        textFecha.setText(glucosa.getFecha());

        if(glucosa.getNumberGlucosa() > Constants.DIABETES_RANGE_VALUE)
            imageView.setBackground(context.getDrawable(R.drawable.bg_status_ko));
        else
            imageView.setBackground(context.getDrawable(R.drawable.bg_status_ok));
        return view;
    }


    @Override
    public int getCount() {
        return listaGlucosa.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
