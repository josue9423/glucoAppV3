package com.glucoapp.glucoappv3.interfaces;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.glucoapp.data.entities.User;

import java.util.ArrayList;
import java.util.List;

public interface Register {

    interface View{
        void isSuccessRegister();
        void isFailedRegister(String error);
        void showLoading();
        void hideLoading();
        void enableUi();
        void disabledUi();
        void setDataRegister();
        void setDataUpdate(User user);
    }

    interface Presenter{
        void setRegisterView(Register.View view);
        List<String> getListSpinnerGender(Context context);
        List<String>  getListSpinnerDiabetesType(Context context);
        void validateIntentData(Bundle extras);
        void register(User user);
        void update(User user);
    }
}
