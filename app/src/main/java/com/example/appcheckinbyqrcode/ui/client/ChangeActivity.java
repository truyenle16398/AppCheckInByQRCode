package com.example.appcheckinbyqrcode.ui.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcheckinbyqrcode.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText editText;
    private TextInputLayout inputLayout;
    String check = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        toolbar = findViewById(R.id.changeToolBar);
        editText = findViewById(R.id.changeEdt);
        inputLayout = findViewById(R.id.inputlayout_change);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        Intent intent = getIntent();
        toolbar.setTitle(intent.getStringExtra("title"));
        editText.setText(intent.getStringExtra("edtnhan"));
        check = intent.getStringExtra("check");
        if (check.equals("phone")){
            editText.setRawInputType(Configuration.KEYBOARD_QWERTY);
        }
        editText.setFocusable(true);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkedt();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void checkedt() {
        String edt = editText.getText().toString();
        if (check.equals("name")){
            if (edt.isEmpty()) {
                inputLayout.setError("Vui lòng nhập trường này");
            } else {
                inputLayout.setError(null);
            }
        } else if (check.equals("email")){
            if (edt.isEmpty()){
                inputLayout.setError("Vui lòng nhập trường này");
            } else if (!validateEmail(edt)){
                inputLayout.setError("Sai định dạng");
            } else {
                inputLayout.setError(null);
            }
        } else if (check.equals("phone")){
            if (edt.isEmpty()){
                inputLayout.setError("Vui lòng nhập trường này");
            } else if (!    isValidPhoneNumber(edt)){
                inputLayout.setError("Sai định dạng");
            } else {
                inputLayout.setError(null);
            }
        } else {
            if (edt.isEmpty()){
                inputLayout.setError("Vui lòng nhập trường này");
            } else {
                inputLayout.setError(null);
            }
        }
    }

    private void setresult(String edt){
        if (check.equals("name")){
            if (edt.isEmpty()) {
                inputLayout.setError("Vui lòng nhập trường này");
            } else {
                inputLayout.setError(null);
                result("name");
            }
        } else if (check.equals("email")){
            if (edt.isEmpty()){
                inputLayout.setError("Vui lòng nhập trường này");
            } else if (!validateEmail(edt)){
                inputLayout.setError("Sai định dạng");
            } else {
                inputLayout.setError(null);
                result("email");
            }
        } else if (check.equals("phone")){
            if (edt.isEmpty()){
                inputLayout.setError("Vui lòng nhập trường này");
            } else if (!isValidPhoneNumber(edt)){
                inputLayout.setError("Sai định dạng");
            } else {
                inputLayout.setError(null);
                result("phone");
            }
        } else {
            if (edt.isEmpty()){
                inputLayout.setError("Vui lòng nhập trường này");
            } else {
                inputLayout.setError(null);
                result("address");
            }
        }
    }

    private void result(String check) {
        Intent intent = new Intent();
        intent.putExtra("check",check);
        intent.putExtra("edttrave",editText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }


    public boolean isValidPhoneNumber(String number)
    {
        String validNumber = "^[+]?[0-9]{8,15}$";
        if (number.matches(validNumber)) {
            return true;
        }
        return false;
    }

    private boolean validateEmail(String email) {
        String emails = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emails);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuchange, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                hideKeyboard(this);
                return true;
            case R.id.changeok:
                setresult(editText.getText().toString());
                hideKeyboard(this);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}