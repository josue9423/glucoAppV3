package com.glucoapp.glucoappv3.ui.register;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.glucoapp.data.entities.User;
import com.glucoapp.glucoappv3.R;
import com.glucoapp.glucoappv3.interfaces.Register;
import com.glucoapp.glucoappv3.ui.BaseActivity;
import com.glucoapp.glucoappv3.ui.fragments.DatePickerFragment;
import com.glucoapp.glucoappv3.ui.home.HomeActivity;
import com.glucoapp.glucoappv3.utils.Constants;
import com.glucoapp.glucoappv3.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTouch;

public class RegisterActivity extends BaseActivity implements Register.View{

    /* Objetos y datos*/
    private User user = new User();
    public Boolean registerOrUpdate = Boolean.TRUE;

    /* Elementos de la UI */
    @BindView(R.id.btn_unlockPassword) ImageView btnUnlockPassword;
    @BindView(R.id.btn_registrar) Button btnRegistrar;
    @BindView(R.id.input_email) EditText inputEmail;
    @BindView(R.id.input_password) EditText inputPassword;
    @BindView(R.id.input_nombres) EditText inputNombres;
    @BindView(R.id.input_apellidos) EditText inputApellidos;
    @BindView(R.id.input_date) EditText inputDate;
    @BindView(R.id.spinner_typeDiabetes) Spinner spinnerDiabetesType;
    @BindView(R.id.spinner_gender) Spinner spinnerGender;
    @BindView(R.id.progressBarRegister) ProgressBar progressBar;

    /* Se declaran las interfaces.*/
    Register.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Seteamos el Binding
        ButterKnife.bind(this);

        // Presenter
        presenter = new RegisterPresenter();
        presenter.setRegisterView(this);

        configView();
    }

    /* Metodos internos */
    private void configView(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinnerDiabetesType.setAdapter(Utils.createSpinnerItems(presenter.getListSpinnerDiabetesType(this), RegisterActivity.this));
        spinnerGender.setAdapter(Utils.createSpinnerItems(presenter.getListSpinnerGender(this), RegisterActivity.this));
        btnUnlockPassword.setImageResource(R.drawable.ic_unlock);
        presenter.validateIntentData(getIntent().getExtras());
    }
    private void goToHome(){
        Intent intentHome = new Intent(RegisterActivity.this, HomeActivity.class);
        startActivity(intentHome);
        intentHome.putExtra(Constants.MY_USER, user);
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
    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = day + "/" + (month + 1) + "/" + year;
                inputDate.setText(Utils.formatDateStringToString(selectedDate));
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    private void setUserFromInputs() {
        this.user.setEmail(inputEmail.getText().toString());
        this.user.setPassword(inputPassword.getText().toString());
        this.user.setNombres(inputNombres.getText().toString());
        this.user.setApellidos(inputApellidos.getText().toString());
        this.user.setFechaNacimiento(inputDate.getText().toString());
    }

    /* Implementación de eventos de UI */
    @OnClick({R.id.btn_registrar, R.id.btn_unlockPassword, R.id.input_date})
    public void onClick(View view){
        switch(view.getId()){
            case R.id.btn_registrar:{
                setUserFromInputs();
                if(registerOrUpdate) presenter.register(user);
                else presenter.update(user);
                break;
            }
            case R.id.btn_unlockPassword:{
                toggleBtnPassword();
                break;
            }
            case R.id.input_date:{
                showDatePickerDialog();
                break;
            }
        }
    }

    @OnItemSelected(R.id.spinner_typeDiabetes)
    public void onItemSelected(Spinner spinner, int pos){
        this.user.setTipoDiabetes((String) spinner.getAdapter().getItem(pos));
    }

    @OnItemSelected(R.id.spinner_gender)
    public void onItemSelectedGender(Spinner spinner, int pos){
        this.user.setSexo((String) spinner.getAdapter().getItem(pos));
    }

    @OnTouch(R.id.activity)
    public boolean onTouch(View view){
        hideKeyboard(view, this);
        return false;
    }

    /* Implementación de métodos */
    @Override
    public void isSuccessRegister() {
        if(registerOrUpdate) goToHome();
        else Toast.makeText(this, Constants.UPDATE_DATABASE_OK, Toast.LENGTH_LONG).show(); goToHome();
    }

    @Override
    public void isFailedRegister(String error) {
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
    public void enableUi() {
        btnRegistrar.setEnabled(Boolean.TRUE);
        btnUnlockPassword.setEnabled(Boolean.TRUE);
        inputEmail.setEnabled(Boolean.TRUE);
        inputPassword.setEnabled(Boolean.TRUE);
        inputNombres.setEnabled(Boolean.TRUE);
        inputApellidos.setEnabled(Boolean.TRUE);
        spinnerDiabetesType.setEnabled(Boolean.TRUE);
        spinnerGender.setEnabled(Boolean.TRUE);
        inputDate.setEnabled(Boolean.TRUE);
    }

    @Override
    public void disabledUi() {
        btnRegistrar.setEnabled(Boolean.FALSE);
        btnUnlockPassword.setEnabled(Boolean.FALSE);
        inputEmail.setEnabled(Boolean.FALSE);
        inputPassword.setEnabled(Boolean.FALSE);
        inputNombres.setEnabled(Boolean.FALSE);
        inputApellidos.setEnabled(Boolean.FALSE);
        spinnerDiabetesType.setEnabled(Boolean.FALSE);
        spinnerGender.setEnabled(Boolean.FALSE);
        inputDate.setEnabled(Boolean.FALSE);
    }

    @Override
    public void setDataRegister(){
        registerOrUpdate = Boolean.TRUE;
        getSupportActionBar().setTitle(getResources().getText(R.string.title_activity_register));
    }

    @Override
    public void setDataUpdate(User user){
        registerOrUpdate = Boolean.FALSE;
        this.user = user;
        getSupportActionBar().setTitle(getResources().getText(R.string.title_activity_profile));
        btnRegistrar.setText(getResources().getText(R.string.btn_modificar));
        inputEmail.setText(user.getEmail());
        inputPassword.setText(user.getPassword());
        inputNombres.setText(user.getNombres());
        inputApellidos.setText(user.getApellidos());
        inputDate.setText(user.getFechaNacimiento());
        spinnerDiabetesType.setSelection(Utils.getPosition(user.getTipoDiabetes(),presenter.getListSpinnerDiabetesType(this)));
        spinnerGender.setSelection(Utils.getPosition(user.getSexo(),presenter.getListSpinnerGender(this)));
        inputEmail.setEnabled(Boolean.FALSE);
        inputPassword.setFocusable(Boolean.FALSE);
    }

}
