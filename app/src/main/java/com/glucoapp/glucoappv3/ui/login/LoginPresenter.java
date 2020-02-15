package com.glucoapp.glucoappv3.ui.login;

import android.util.Patterns;

import com.glucoapp.domain.usecases.UserUseCase;
import com.glucoapp.domain.usecases.impl.UserUseCaseImpl;
import com.glucoapp.glucoappv3.interfaces.Login;
import com.glucoapp.glucoappv3.ui.BasePresenter;
import com.glucoapp.glucoappv3.utils.Constants;

public class LoginPresenter extends BasePresenter implements Login.Presenter {

    /* Se declaran las interfaces.*/
    Login.View view;
    UserUseCase userUseCase;


    public LoginPresenter(){
        userUseCase = new UserUseCaseImpl(this);
    }

    /* Implementación de métodos */
    @Override
    public void setLoginView(Login.View view) {
        this.view = view;
    }

    @Override
    public void login(String email, String password) {
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.isFailedLogin(Constants.LOGIN_EMAIL_ERROR);
        }
        else if(password.length() < Constants.MAX_LENGTH_PASSWORD_VALUE){
            view.isFailedLogin(Constants.LOGIN_PASSWORD_ERROR);
        }
        else{
            view.disabledUi();
            view.showLoading();
            userUseCase.login(email,password);
        }
    }

    @Override
    public void onSuccess() {
        view.enabledUi();
        view.hideLoading();
        view.isSuccessLogin();
    }

    @Override
    public void onError(String error) {
        view.enabledUi();
        view.hideLoading();
        view.isFailedLogin(error);

    }
}
