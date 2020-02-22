package com.glucoapp.glucoappv3.interfaces;

import android.content.Context;

import com.glucoapp.data.entities.Glucosa;

import java.util.List;

public interface Data {

    interface View{
        void showLoading();
        void hideLoading();
        void btAdapterFailed();
        void btDisabled();
        void btNotPairedDevices();
        void setListBtDevices(List<String> listBt);
        void glucometerEnabled();
        void saveEnabled(Glucosa glucosa);
        void onSuccessSaveGlucoData();
        void onError(String error);
    }

    interface Presenter{
        void setView(View view);
        void setListBtDevices(Context context);
        void connectDevice(String deviceString);
        void updateGlucoData();
        void saveGlucoData(Glucosa glucosa);
    }
}