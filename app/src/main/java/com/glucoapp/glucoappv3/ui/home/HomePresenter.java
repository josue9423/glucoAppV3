package com.glucoapp.glucoappv3.ui.home;

import android.os.Bundle;

import com.glucoapp.data.entities.User;
import com.glucoapp.domain.usecases.UserUseCase;
import com.glucoapp.domain.usecases.impl.UserUseCaseImpl;
import com.glucoapp.glucoappv3.interfaces.Home;
import com.glucoapp.glucoappv3.ui.BasePresenter;
import com.glucoapp.glucoappv3.utils.Constants;

public class HomePresenter extends BasePresenter implements Home.Presenter {

    Home.View view;
    UserUseCase userUseCase;

    public HomePresenter() {
        userUseCase = new UserUseCaseImpl(this);
    }

    @Override
    public void setHomeView(Home.View view) {
        this.view = view;
    }

    @Override
    public void getExistingUser() {
        view.showLoading();
        view.disableUi();
        String uid = userUseCase.isExistingUser();
        if (uid != null) {
            userUseCase.getCurrentUser(uid);
        }
        else {
            view.goToLogin();
            view.enableUi();
        }
    }

    @Override
    public void validateIntentData(Bundle extras) {
        if (extras != null) {
            view.setUserData((User) extras.getSerializable(Constants.MY_USER));
        } else {
            this.getExistingUser();
        }
    }

    @Override
    public void logOut() {
        if(userUseCase.logOut()){
            view.goToLogin();
        }else{
            view.showError(Constants.LOGOUT_ERROR);
        }
    }

    @Override
    public void onSuccessCurrentUser(User user) {
        view.enableUi();
        view.hideLoading();
        if(user != null){
            view.setUserData(user);
        }
    }

    @Override
    public void onError(String error) {
        view.hideLoading();
        view.enableUi();
        view.goToLogin();
    }
}
