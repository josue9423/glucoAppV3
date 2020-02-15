package com.glucoapp.glucoappv3.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.glucoapp.data.entities.User;
import com.glucoapp.glucoappv3.R;
import com.glucoapp.glucoappv3.interfaces.Home;
import com.glucoapp.glucoappv3.ui.login.LoginActivity;
import com.glucoapp.glucoappv3.ui.register.RegisterActivity;
import com.glucoapp.glucoappv3.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements Home.View {

    /* Objetos y datos*/
    private User user = new User();

    /* Se declaran las interfaces.*/
    private Home.Presenter presenter;

    /* Elementos de la UI */
    @BindView(R.id.toolbar) Toolbar myToolbar;
    @BindView(R.id.btn_add) Button btnAddData;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Seteamos el Binding
        ButterKnife.bind(this);

        // Presenter
        presenter = new HomePresenter();
        presenter.setHomeView(this);

        configView();
    }

    private void configView(){
        myToolbar.setTitle(Constants.ESPACIO);
        setSupportActionBar(myToolbar);
        presenter.validateIntentData(getIntent().getExtras());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.profile:
                goToProfile();
                return true;
            case R.id.logout:
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goToProfile() {
        Intent intentProfile = new Intent(HomeActivity.this, RegisterActivity.class);
        intentProfile.putExtra(Constants.MY_USER, user);
        startActivity(intentProfile);
    }

    private void logOut() {
        presenter.logOut();
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
    public void goToLogin(){
        Intent intentLogin = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intentLogin);
        finish();
    }

    @Override
    public void setUserData(User user) {
        this.user = user;
        getSupportActionBar().setTitle(getString(R.string.lbl_bienvenido) + Constants.ESPACIO + user.getNombres());

    }

    @Override
    public void showError(String error) {
        Toast.makeText(this,error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void enableUi() {
        myToolbar.setEnabled(Boolean.TRUE);
        btnAddData.setEnabled(Boolean.TRUE);
    }

    @Override
    public void disableUi() {
        myToolbar.setEnabled(Boolean.FALSE);
        btnAddData.setEnabled(Boolean.FALSE);
    }
}
