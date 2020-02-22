package com.glucoapp.glucoappv3.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.glucoapp.data.entities.User;
import com.glucoapp.glucoappv3.R;
import com.glucoapp.glucoappv3.interfaces.Splash;
import com.glucoapp.glucoappv3.ui.home.HomeActivity;
import com.glucoapp.glucoappv3.ui.login.LoginActivity;
import com.glucoapp.glucoappv3.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements Splash.View {

    @BindView(R.id.img_logo) ImageView imgLogo;
    @BindView(R.id.lbl_title) TextView lblTitle;
    Animation splashAnimation;
    Animation splashTranslateAnimation;

    Splash.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Seteamos el Binding
        ButterKnife.bind(this);

        presenter = new SplashPresenter();
        presenter.setHomeView(this);

        splashAnimation = AnimationUtils.loadAnimation(this,R.anim.splash_animation);
        splashTranslateAnimation = AnimationUtils.loadAnimation(this,R.anim.splash_translate_animation);

        presenter.getExistingUser();
    }

    @Override
    public void enableAnimation() {
        imgLogo.startAnimation(splashTranslateAnimation);
        lblTitle.startAnimation(splashAnimation);
    }

    @Override
    public void disableAnimation() {
        /* Nada por el momento */
    }

    @Override
    public void goToHome(User user) {
        Intent intentHome = new Intent(SplashActivity.this, HomeActivity.class);
        intentHome.putExtra(Constants.MY_USER, user);
        startActivity(intentHome);
        finish();
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
