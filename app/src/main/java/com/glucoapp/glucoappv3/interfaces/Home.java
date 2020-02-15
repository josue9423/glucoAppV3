package com.glucoapp.glucoappv3.interfaces;

import android.os.Bundle;

import com.glucoapp.data.entities.User;

public interface Home {

    interface View {
        void showLoading();
        void hideLoading();
        void goToLogin();
        void setUserData(User user);
        void showError(String error);
        void enableUi();
        void disableUi();
    }

    interface Presenter {
        void setHomeView(Home.View view);
        void getExistingUser();
        void validateIntentData(Bundle extras);
        void logOut();
    }
}
