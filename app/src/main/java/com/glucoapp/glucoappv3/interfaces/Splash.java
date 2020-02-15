package com.glucoapp.glucoappv3.interfaces;

import com.glucoapp.data.entities.User;

public interface Splash {

    interface View{
        void enableAnimation();
        void disableAnimation();
        void goToHome(User user);
        void goToLogin();
    }

    interface Presenter{
        void setHomeView(Splash.View view);
        void getExistingUser();
    }
}
