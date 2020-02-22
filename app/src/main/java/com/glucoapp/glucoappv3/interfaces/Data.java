package com.glucoapp.glucoappv3.interfaces;

import android.content.Context;

import java.util.List;

public interface Data {

    interface View{
        void btAdapterFailed();
        void btDisabled();
        void btNotPairedDevices();
        void setListBtDevices(List<String> listBt);
        void enableUi();
        void disableUi();
        void glucometerEnabled();
        void saveEnabled(String glucoValue);
        void onError(String error);
    }

    interface Presenter{
        void setView(View view);
        void setListBtDevices(Context context);
        void connectDevice(String deviceString);
        void updateGlucoData();
    }
}