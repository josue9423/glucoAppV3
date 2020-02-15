package com.glucoapp.glucoappv3.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.gigamole.library.PulseView;
import com.glucoapp.data.entities.User;
import com.glucoapp.glucoappv3.R;
import com.glucoapp.glucoappv3.interfaces.Splash;
import com.glucoapp.glucoappv3.ui.home.HomeActivity;
import com.glucoapp.glucoappv3.ui.login.LoginActivity;
import com.glucoapp.glucoappv3.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements Splash.View {

    @BindView(R.id.img_logo) PulseView imgLogo;

    Splash.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Seteamos el Binding
        ButterKnife.bind(this);

        presenter = new SplashPresenter();
        presenter.setHomeView(this);

        presenter.getExistingUser();
    }

    @Override
    public void enableAnimation() {
        imgLogo.startPulse();
    }

    @Override
    public void disableAnimation() {
        imgLogo.finishPulse();
    }

    @Override
    public void goToHome(User user) {
        Intent intentHome = new Intent(SplashActivity.this, HomeActivity.class);
        intentHome.putExtra(Constants.MY_USER, user);
        startActivity(intentHome);
    }

    @Override
    public void goToLogin() {
        Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentLogin = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                finish();
            }
        },Constants.SPLASH_SCREEN_TIMER);

    }
}
