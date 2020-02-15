package com.glucoapp.glucoappv3.ui.splash;


import com.glucoapp.data.entities.User;
import com.glucoapp.domain.usecases.UserUseCase;
import com.glucoapp.domain.usecases.impl.UserUseCaseImpl;
import com.glucoapp.glucoappv3.interfaces.Splash;
import com.glucoapp.glucoappv3.ui.BasePresenter;

public class SplashPresenter extends BasePresenter implements Splash.Presenter {

    Splash.View view;
    UserUseCase userUseCase;

    public SplashPresenter() {
        userUseCase = new UserUseCaseImpl(this);
    }

    @Override
    public void setHomeView(Splash.View view) {
        this.view = view;
    }

    @Override
    public void getExistingUser() {
        view.enableAnimation();
        String uid = userUseCase.isExistingUser();
        if (uid != null) {
            userUseCase.getCurrentUser(uid);
        }
        else {
            view.goToLogin();
        }
    }

    @Override
    public void onSuccessCurrentUser(User user) {
        view.disableAnimation();
        view.goToHome(user);
    }

    @Override
    public void onError(String error) {
        view.goToLogin();
        view.disableAnimation();
    }
}
