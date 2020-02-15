package com.glucoapp.glucoappv3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class BaseActivity extends AppCompatActivity {

    protected void hideKeyboard(View view, Context context)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
