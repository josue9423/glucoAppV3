package com.glucoapp.glucoappv3.ui.history;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.glucoapp.data.entities.Glucosa;
import com.glucoapp.glucoappv3.R;
import com.glucoapp.glucoappv3.interfaces.History;
import com.glucoapp.glucoappv3.ui.home.HomeActivity;
import com.glucoapp.glucoappv3.utils.Constants;
import com.glucoapp.glucoappv3.utils.ui.AdapterListHistory;
import com.glucoapp.glucoappv3.utils.ui.CustomMarkerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryActivity extends AppCompatActivity implements History.View {

    @BindView(R.id.chart) LineChart chart;
    @BindView(R.id.toolbar) Toolbar myToolbar;
    @BindView(R.id.ultima_muestra) TextView lblUltimaMuestra;
    @BindView(R.id.mejor_muestra) TextView lblMejorMuestra;
    @BindView(R.id.gluco_list) ListView glucoList;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.btn_semanal) Button btnSemanal;
    @BindView(R.id.btn_mensual) Button btnMensual;
    @BindView(R.id.btn_ver) Button btnVer;

    History.Presenter presenter;

    ArrayList<Glucosa> listaGlucosa = new ArrayList<>();
    ArrayList<Glucosa> listaReversa = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Seteamos el Binding
        ButterKnife.bind(this);

        presenter = new HistoryPresenter();
        presenter.setView(this);

        configView();

        presenter.getMounthlyData();

    }

    private void configView(){
        myToolbar.setTitle(getText(R.string.title_activity_myData));
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    private void goToHome(){
        Intent intentHome = new Intent(HistoryActivity.this, HomeActivity.class);
        startActivity(intentHome);
        finish();
    }
    private LineDataSet setLineDataSet(ArrayList<Entry> listEntry){
        // LINEA
        LineDataSet lineDataSet = new LineDataSet(listEntry,"");
        lineDataSet.setLineWidth(3f);
        lineDataSet.setCircleRadius(5.5f);
        lineDataSet.setCircleHoleRadius(2.5f);
        lineDataSet.setCircleColor(getColor(R.color.colorWhite));
        lineDataSet.setCircleHoleColor(getColor(R.color.colorPrimary));
        lineDataSet.setColor(getColor(R.color.colorWhite));
        lineDataSet.disableDashedLine();

        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawVerticalHighlightIndicator(false);

        //FILL
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillAlpha(25);
        lineDataSet.setFillColor(getColor(R.color.colorAccent));

        // VALUES
        lineDataSet.setDrawValues(false);
        lineDataSet.setValueTextColor(getColor(R.color.colorAccent));
        lineDataSet.setValueTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.BOLD));
        lineDataSet.setValueTextSize(12f);

        // SHAPE
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        return lineDataSet;
    }
    private void setChart(ArrayList<Entry> dataValues){
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(setLineDataSet(dataValues));
        LineData lineData = new LineData(dataSets);

        chart.setData(lineData);

        chart.setDrawGridBackground(Boolean.FALSE);
        chart.setBorderColor(getColor(R.color.colorPrimary));
        chart.setDrawBorders(Boolean.FALSE);
        chart.setDrawMarkers(Boolean.TRUE);
        chart.getDescription().setEnabled(Boolean.FALSE);

        /* Lineas en background */
        chart.getXAxis().setEnabled(Boolean.FALSE);
        chart.getXAxis().setDrawLabels(Boolean.FALSE);
        chart.getXAxis().setDrawGridLines(Boolean.FALSE);
        chart.getAxisLeft().setEnabled(Boolean.FALSE);
        chart.getAxisLeft().setDrawLabels(Boolean.FALSE);
        chart.getAxisLeft().setDrawGridLines(Boolean.FALSE);
        chart.getAxisLeft().setDrawTopYLabelEntry(Boolean.FALSE);
        chart.getAxisRight().setEnabled(Boolean.FALSE);
        chart.getAxisRight().setDrawGridLines(Boolean.FALSE);
        chart.getAxisRight().setDrawTopYLabelEntry(Boolean.FALSE);
        chart.getAxisRight().setDrawLabels(Boolean.FALSE);

        chart.getLegend().setEnabled(Boolean.FALSE);
        chart.setGridBackgroundColor(getColor(R.color.colorWhite));
        chart.setMinOffset(18f);
        chart.setTouchEnabled(Boolean.TRUE);

        chart.animateX(700);
        chart.setNoDataText(Constants.NO_CHART_DATA);
        /* Marker */
        IMarker marker = new CustomMarkerView(this,R.layout.layout_marker,listaGlucosa);
        chart.setMarker(marker);

        chart.invalidate();
    }
    private void displayAllListView(){
        glucoList.setAdapter(new AdapterListHistory(this,listaReversa));
        btnVer.setText(getString(R.string.ver_menos));
    }


    /*Implementacion eventos UI*/
    @OnClick({R.id.btn_semanal, R.id.btn_mensual, R.id.btn_ver})
    public void onClick(View view){
        switch(view.getId()){
            case R.id.btn_semanal:{
                //presenter.login(inputEmail.getText().toString(), inputPassword.getText().toString());
                break;
            }
            case R.id.btn_mensual:{
                //goToRegister();
                break;
            }
            case R.id.btn_ver:{
                if(btnVer.getText().equals(getText(R.string.ver_todos)))
                    displayAllListView();
                else
                    displayListView(listaReversa, listaGlucosa);
                break;
            }
        }
    }


    /* Implementacion metodos*/
    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void displayData(ArrayList<Entry> listEntry) {
        setChart(listEntry);
    }

    @Override
    public void displayListView(ArrayList<Glucosa> listaReversa, ArrayList<Glucosa> listaGlucosa) {
        this.listaGlucosa = listaGlucosa;
        this.listaReversa = listaReversa;
        btnVer.setText(getString(R.string.ver_todos));
        ArrayList<Glucosa> tinyList = (listaReversa.size() >= Constants.TRES_VALUE) ? new ArrayList<>(listaReversa.subList(Constants.CERO_VALUE,Constants.TRES_VALUE)) : listaReversa;
        glucoList.setAdapter(new AdapterListHistory(this,tinyList));
    }

    @Override
    public void displayFilterData(Glucosa ultimaMuestra, Glucosa mejorMuestra) {
        lblMejorMuestra.setText(mejorMuestra.getGlucosa());
        lblUltimaMuestra.setText(ultimaMuestra.getGlucosa());
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this,error,Toast.LENGTH_LONG).show();
        goToHome();
    }


}

