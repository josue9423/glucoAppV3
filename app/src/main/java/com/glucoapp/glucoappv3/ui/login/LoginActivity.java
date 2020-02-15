package com.glucoapp.glucoappv3.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.glucoapp.glucoappv3.R;
import com.glucoapp.glucoappv3.interfaces.Login;
import com.glucoapp.glucoappv3.ui.BaseActivity;
import com.glucoapp.glucoappv3.ui.home.HomeActivity;
import com.glucoapp.glucoappv3.ui.register.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class LoginActivity extends BaseActivity implements Login.View{

    /* Elementos de la UI */
    @BindView(R.id.lbl_register) TextView btnRegister;
    @BindView(R.id.btn_unlockPassword) ImageView btnUnlockPassword;
    @BindView(R.id.btn_login) Button btnLogin;
    @BindView(R.id.input_email) EditText inputEmail;
    @BindView(R.id.input_password) EditText inputPassword;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    /* Se declaran las interfaces.*/
    private Login.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Seteamos el Binding
        ButterKnife.bind(this);

        // Presenter
        presenter = new LoginPresenter();
        presenter.setLoginView(this);

        configView();
    }


    /* Metodos internos */
    private void configView(){
        btnUnlockPassword.setImageResource(R.drawable.ic_unlock);
    }
    private void goToRegister(){
        Intent intentRegister = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intentRegister);
    }
    private void goToHome(){
        Intent intentHome = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intentHome);
        finish();
    }
    private void toggleBtnPassword(){
        if (inputPassword.getTransformationMethod().getClass().getSimpleName().equals("SingleLineTransformationMethod")) {
            inputPassword.setTransformationMethod(new PasswordTransformationMethod());
            btnUnlockPassword.setImageResource(R.drawable.ic_unlock);
        } else {
            inputPassword.setTransformationMethod(new SingleLineTransformationMethod());
            btnUnlockPassword.setImageResource(R.drawable.ic_lock);
        }
        inputPassword.setSelection(inputPassword.getText().length());
    }

    /* Implementación de eventos de UI */
    @OnClick({R.id.btn_login, R.id.lbl_register, R.id.btn_unlockPassword})
    public void onClick(View view){
        switch(view.getId()){
            case R.id.btn_login:{
                presenter.login(inputEmail.getText().toString(), inputPassword.getText().toString());
                break;
            }
            case R.id.lbl_register:{
                goToRegister();
                break;
            }
            case R.id.btn_unlockPassword:{
                toggleBtnPassword();
                break;
            }
        }
    }

    @OnTouch(R.id.activity)
    public boolean onTouch(View view){
        hideKeyboard(view, this);
        return false;
    }

    /* Implementación de métodos */
    @Override
    public void isSuccessLogin(){
        goToHome();
    }

    @Override
    public void isFailedLogin(String error){
        Toast.makeText(this,error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void enabledUi() {
        inputEmail.setEnabled(Boolean.TRUE);
        inputPassword.setEnabled(Boolean.TRUE);
        btnLogin.setEnabled(Boolean.TRUE);
        btnRegister.setEnabled(Boolean.TRUE);
        btnUnlockPassword.setEnabled(Boolean.TRUE);
    }

    @Override
    public void disabledUi() {
        inputEmail.setEnabled(Boolean.FALSE);
        inputPassword.setEnabled(Boolean.FALSE);
        btnLogin.setEnabled(Boolean.FALSE);
        btnRegister.setEnabled(Boolean.FALSE);
        btnUnlockPassword.setEnabled(Boolean.FALSE);
    }


}
