package com.glucoapp.glucoappv3.ui.register;

import android.content.Context;
import android.os.Bundle;
import android.util.Patterns;

import com.glucoapp.data.entities.User;
import com.glucoapp.domain.usecases.UserUseCase;
import com.glucoapp.domain.usecases.impl.UserUseCaseImpl;
import com.glucoapp.glucoappv3.R;
import com.glucoapp.glucoappv3.interfaces.Register;
import com.glucoapp.glucoappv3.ui.BasePresenter;
import com.glucoapp.glucoappv3.utils.Constants;
import com.glucoapp.glucoappv3.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;


public class RegisterPresenter extends BasePresenter implements Register.Presenter {

    Register.View view;
    UserUseCase userUseCase;

    public RegisterPresenter() {
        userUseCase = new UserUseCaseImpl(this);
    }

    private boolean validateFields(User user) {
        return user.getNombres().length() >= Constants.UNO_VALUE && user.getApellidos().length() >= Constants.UNO_VALUE
                && user.getSexo().length() >= Constants.UNO_VALUE && user.getTipoDiabetes().length() >= Constants.UNO_VALUE
                && user.getFechaNacimiento().length() >= Constants.UNO_VALUE;
    }

    /* Implementación de métodos */
    @Override
    public void setRegisterView(Register.View view) {
        this.view = view;
    }

    @Override
    public ArrayList<String>  getListSpinnerGender(Context context) {
        return new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.inputs_gender)));
    }

    @Override
    public ArrayList<String> getListSpinnerDiabetesType(Context context) {
        return new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.inputs_typeDiabetes)));
    }

    @Override
    public void register(User user) {
        if (user == null) {
            view.isFailedRegister(Constants.REGISTER_DATABASE_ERROR);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()) {
            view.isFailedRegister(Constants.LOGIN_EMAIL_ERROR);
        } else if (user.getPassword().length() < Constants.MAX_LENGTH_PASSWORD_VALUE) {
            view.isFailedRegister(Constants.LOGIN_PASSWORD_ERROR);
        } else if (!validateFields(user)) {
            view.isFailedRegister(Constants.FIELDS_VALIDATE_ERROR);
        } else {
            view.disabledUi();
            view.showLoading();
            userUseCase.register(user);
        }
    }

    @Override
    public void update(User user) {
        if (user == null) {
            view.isFailedRegister(Constants.UPDATE_DATABASE_ERROR);
        } else if (!validateFields(user)) {
            view.isFailedRegister(Constants.FIELDS_VALIDATE_ERROR);
        } else {
            view.disabledUi();
            view.showLoading();
            userUseCase.update(user);
        }
    }

    @Override
    public void validateIntentData(Bundle extras) {
        if (extras != null) {
            view.setDataUpdate((User) extras.getSerializable(Constants.MY_USER));
        } else {
            view.setDataRegister();
        }
    }

    @Override
    public void onSuccess() {
        view.enableUi();
        view.hideLoading();
        view.isSuccessRegister();
    }

    @Override
    public void onError(String error) {
        view.enableUi();
        view.hideLoading();
        view.isFailedRegister(error);
    }
}
