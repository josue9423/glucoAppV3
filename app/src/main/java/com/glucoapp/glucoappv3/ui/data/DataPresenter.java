package com.glucoapp.glucoappv3.ui.data;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.glucoapp.data.entities.Glucosa;
import com.glucoapp.domain.usecases.GlucosaUseCase;
import com.glucoapp.domain.usecases.impl.GlucosaUseCaseImpl;
import com.glucoapp.glucoappv3.R;
import com.glucoapp.glucoappv3.interfaces.Data;
import com.glucoapp.glucoappv3.ui.BasePresenter;
import com.glucoapp.glucoappv3.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataPresenter extends BasePresenter implements Data.Presenter {

    Data.View view;

    GlucosaUseCase glucosaUseCase;

    /* Objetos de hardware */
    Map<String, String> bluetoothDevicesMap = new HashMap<>();
    List<String> bluetoothDevicesList = new ArrayList<>();
    BluetoothAdapter bluetoothAdapter;

    public DataPresenter() {
        glucosaUseCase = new GlucosaUseCaseImpl(this);
    }

    /* Implementación de métodos */
    @Override
    public void setView(Data.View view) {
        this.view = view;
    }

    @Override
    public void setListBtDevices(Context context) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            view.btAdapterFailed();
        } else if (!bluetoothAdapter.isEnabled()) {
            view.btDisabled();
        } else {
            Set<BluetoothDevice> bluetoothDevices = bluetoothAdapter.getBondedDevices();
            bluetoothDevicesList.add(context.getString(R.string.conecta));
            if (bluetoothDevices.size() > Constants.CERO_VALUE) {
                for (BluetoothDevice bluetoothDevice : bluetoothDevices) {
                    bluetoothDevicesMap.put(bluetoothDevice.getName(), bluetoothDevice.getAddress());
                    bluetoothDevicesList.add(bluetoothDevice.getName());
                }
                view.setListBtDevices(bluetoothDevicesList);
            } else {
                view.btNotPairedDevices();
            }

        }
    }

    @Override
    public void connectDevice(String deviceString) {
        view.glucometerEnabled();
    }

    @Override
    public void updateGlucoData() {
        glucosaUseCase.updateGlucoData();
    }

    @Override
    public void saveGlucoData(Glucosa glucosa) {
        view.showLoading();
        glucosaUseCase.saveGlucoData(glucosa);
    }

    @Override
    public void onSuccessUpdateGlucoData(Glucosa glucosa){
        view.saveEnabled(glucosa);
    }

    @Override
    public void onSuccessSaveGlucoData() {
        view.onSuccessSaveGlucoData();
        view.hideLoading();
    }

    @Override
    public void onError(String error){
        view.onError(error);
        view.hideLoading();
    }

}
