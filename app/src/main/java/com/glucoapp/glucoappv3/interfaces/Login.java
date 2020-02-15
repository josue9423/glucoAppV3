package com.glucoapp.glucoappv3.interfaces;

public interface Login {

    interface View{
        void isSuccessLogin();
        void isFailedLogin(String error);
        void showLoading();
        void hideLoading();
        void enabledUi();
        void disabledUi();
    }

    interface Presenter{
        void setLoginView(View view);
        void login(String email, String password);
    }



}
