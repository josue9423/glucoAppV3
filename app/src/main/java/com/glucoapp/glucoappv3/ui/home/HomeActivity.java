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
import com.glucoapp.glucoappv3.ui.data.DataActivity;
import com.glucoapp.glucoappv3.ui.history.HistoryActivity;
import com.glucoapp.glucoappv3.ui.login.LoginActivity;
import com.glucoapp.glucoappv3.ui.register.RegisterActivity;
import com.glucoapp.glucoappv3.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity implements Home.View {

    /* Objetos y datos*/
    private User user = new User();

    /* Se declaran las interfaces.*/
    private Home.Presenter presenter;

    /* Elementos de la UI */
    @BindView(R.id.toolbar) Toolbar myToolbar;
    @BindView(R.id.btn_add) Button btnAddData;
    @BindView(R.id.btn_history) Button btnHistory;
    @BindView(R.id.btn_map) Button btnMap;
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
    private void logOut() {
        presenter.logOut();
    }
    private void goToProfile() {
        Intent intentProfile = new Intent(HomeActivity.this, RegisterActivity.class);
        intentProfile.putExtra(Constants.MY_USER, user);
        startActivity(intentProfile);
    }
    private void goToData(){
        Intent intentData = new Intent(HomeActivity.this, DataActivity.class);
        startActivity(intentData);
    }
    private void goToHistory(){
        Intent intentHistory = new Intent(HomeActivity.this, HistoryActivity.class);
        startActivity(intentHistory);
    }
    private void goToMap(){}

    @OnClick ({R.id.btn_add, R.id.btn_history, R.id.btn_map})
    public void onClick(View view){
        switch(view.getId()){
            case R.id.btn_add:{
                goToData();
                break;
            }
            case R.id.btn_history:{
                goToHistory();
                break;
            }
            case R.id.btn_map:{
                goToMap();
                break;
            }
        }
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
        btnHistory.setEnabled(Boolean.TRUE);
        btnMap.setEnabled(Boolean.TRUE);
    }

    @Override
    public void disableUi() {
        myToolbar.setEnabled(Boolean.FALSE);
        btnAddData.setEnabled(Boolean.FALSE);
        btnHistory.setEnabled(Boolean.FALSE);
        btnMap.setEnabled(Boolean.FALSE);
    }
}
