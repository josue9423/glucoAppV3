package com.glucoapp.glucoappv3.utils.ui;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.glucoapp.data.entities.Glucosa;
import com.glucoapp.glucoappv3.R;

import java.util.ArrayList;

public class CustomMarkerView extends MarkerView {

    private TextView tvContent;
    private ArrayList<Glucosa> listaGlucosa;
    private MPPointF mOffset;

    public CustomMarkerView(Context context, int layoutResource, ArrayList<Glucosa> listaGlucosa) {
        super(context, layoutResource);
        this.tvContent = (TextView) findViewById(R.id.tvContent);
        this.listaGlucosa = listaGlucosa;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        int xAxis = (int) e.getX();
        tvContent.setText("" + Math.round(e.getY())
                +"\n"+listaGlucosa.get(xAxis).getFechaCorta()
                +"\n"+listaGlucosa.get(xAxis).getHora());
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        if(mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), - getHeight());
        }
        return mOffset;
    }
}