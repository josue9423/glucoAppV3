package com.glucoapp.glucoappv3.ui.data;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.gigamole.library.PulseView;
import com.glucoapp.data.entities.Glucosa;
import com.glucoapp.glucoappv3.R;
import com.glucoapp.glucoappv3.interfaces.Data;
import com.glucoapp.glucoappv3.ui.home.HomeActivity;
import com.glucoapp.glucoappv3.utils.Constants;
import com.glucoapp.glucoappv3.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class DataActivity extends AppCompatActivity implements Data.View{

    @BindView(R.id.btn_gluco) PulseView btnGluco;
    @BindView(R.id.spinner_devices) Spinner spinnerDevices;
    @BindView(R.id.btn_save) Button btnSave;
    @BindView(R.id.toolbar) Toolbar myToolbar;
    @BindView(R.id.lbl_result) TextView lblResult;
    @BindView(R.id.lbl_status) TextView lblStatus;
    @BindView(R.id.blood_stick) ImageView bloodStick;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    AnimationSet animationSet;
    TranslateAnimation translateDownAnimation;

    Glucosa glucosa = new Glucosa();

    Data.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        // Seteamos el Binding
        ButterKnife.bind(this);

        presenter = new DataPresenter();
        presenter.setView(this);

        configView();

    }

    private void configView(){
        setAnimation();
        myToolbar.setTitle(getText(R.string.title_activity_newdata));
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnGluco.setEnabled(Boolean.FALSE);
        presenter.setListBtDevices(this);
    }
    private void setAnimation(){
        animationSet = new AnimationSet(false);
        translateDownAnimation = new TranslateAnimation(0f, 0f, 0f, 50.5f);
        translateDownAnimation.setDuration(800);
        translateDownAnimation.setRepeatCount(TranslateAnimation.INFINITE);
        animationSet.addAnimation(translateDownAnimation);
    }
    private void onClickBtnGluco(){
        lblStatus.setText(getText(R.string.tercer_estado));
        btnGluco.setEnabled(Boolean.FALSE);
        btnGluco.finishPulse();
        bloodStick.startAnimation(animationSet);
    }

    /* Implementación de eventos de UI */
    @OnClick({R.id.btn_gluco, R.id.btn_save})
    public void onClick(View view){
        switch(view.getId()){
            case R.id.btn_gluco:{
                onClickBtnGluco();
                presenter.updateGlucoData();
                break;
            }
            case R.id.btn_save:{
                presenter.saveGlucoData(glucosa);
                break;
            }
        }
    }

    @OnItemSelected(R.id.spinner_devices)
    public void onItemSelected(Spinner spinner, int pos){
        if(pos > Constants.CERO_VALUE) presenter.connectDevice((String) spinner.getAdapter().getItem(pos));
    }

    /* Bluetooth */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.REQUEST_ENABLED_BT:
                if (resultCode == RESULT_OK) {
                    presenter.setListBtDevices(this);
                } else {
                    Toast.makeText(DataActivity.this, Constants.DONT_USE_BT_ERROR, Toast.LENGTH_LONG).show();
                    Intent homeIntent = new Intent(DataActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /* Implementación de métodos de interface*/
    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void btAdapterFailed() {
        Toast.makeText(DataActivity.this, Constants.DONT_HAVE_BT_ERROR, Toast.LENGTH_LONG).show();
        Intent homeIntent = new Intent(DataActivity.this, HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }

    @Override
    public void btDisabled() {
        Intent intentBt = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intentBt, Constants.REQUEST_ENABLED_BT);
    }

    @Override
    public void btNotPairedDevices() {
        Toast.makeText(DataActivity.this, Constants.DONT_PAIRED_DEVICES_ERROR, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setListBtDevices(List<String> listBt) {
        spinnerDevices.setAdapter(Utils.createSpinnerItems(listBt,DataActivity.this));
    }

    @Override
    public void glucometerEnabled() {
        lblStatus.setText(getText(R.string.segundo_estado));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        btnGluco.startPulse();
        btnGluco.setEnabled(Boolean.TRUE);
        spinnerDevices.setEnabled(Boolean.FALSE);
        spinnerDevices.setClickable(Boolean.FALSE);
        btnSave.setEnabled(Boolean.FALSE);
        btnSave.setBackground(getDrawable(R.drawable.bg_btn_primary_disabled));
    }

    @Override
    public void saveEnabled(Glucosa glucosa) {
        this.glucosa = glucosa;
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        bloodStick.clearAnimation();
        lblResult.setText(glucosa.getGlucosa());
        lblStatus.setText(getText(R.string.cuarto_estado));
        btnGluco.setEnabled(Boolean.FALSE);
        spinnerDevices.setEnabled(Boolean.FALSE);
        spinnerDevices.setClickable(Boolean.FALSE);
        btnSave.setEnabled(Boolean.TRUE);
        btnSave.setBackground(getDrawable(R.drawable.bg_btn_primary));
    }

    @Override
    public void onSuccessSaveGlucoData() {
        this.glucosa = new Glucosa();
        spinnerDevices.setEnabled(Boolean.TRUE);
        spinnerDevices.setClickable(Boolean.TRUE);
        btnGluco.setEnabled(Boolean.FALSE);
        btnSave.setEnabled(Boolean.FALSE);
        btnSave.setBackground(getDrawable(R.drawable.bg_btn_primary_disabled));
        lblResult.setText(getString(R.string.valor_vacio));
        lblStatus.setText(getString(R.string.primer_estado));
        Toast.makeText(this, Constants.SUCCESS_GLUCO_DATA, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        Intent intentHome = new Intent(DataActivity.this, HomeActivity.class);
        startActivity(intentHome);
        finish();
    }

}
