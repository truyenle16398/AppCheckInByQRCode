package com.example.appcheckinbyqrcode.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.SessionManager;
import com.example.appcheckinbyqrcode.network.ApiClient;
import com.example.appcheckinbyqrcode.ui.admin.HomeAdminActivity;
import com.example.appcheckinbyqrcode.ui.client.HomeClientActivity;
import com.example.appcheckinbyqrcode.ui.model.ApiConfig;
import com.example.appcheckinbyqrcode.ui.model.User;
import com.example.appcheckinbyqrcode.ui.model.info;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {
    TextView TVBack;
    Button btnregister;
    EditText edtemail, edtpass, edtconfirmpass, edtaddress, edtname, edtphone;
    String email, name, address, phone, password, confirmpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initWidget();
        onClick();
    }


    void onClick() {
        //set su kien cho button register
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog pd = new ProgressDialog(RegisterActivity.this);
                pd.setMessage("loading");
                pd.show();
                hideKeybaord(view);
                email = edtemail.getText().toString();
                name = edtname.getText().toString();
                address = edtaddress.getText().toString();
                phone = edtphone.getText().toString();
                password = edtpass.getText().toString();
                confirmpassword = edtconfirmpass.getText().toString();
                if (email.isEmpty() || name.isEmpty() || address.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin để đăng ký!!", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    ApiClient.getService().register(email, name, address, phone, password, confirmpassword)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<User>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(User us) {
                                    Log.d("nnn", "onSuccess: " + us.getMessage());
                                    Toast.makeText(RegisterActivity.this, "Đăng ký tài khoản thành công!!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d("nnn", "onError: " + e.getMessage());
                                    Toast.makeText(RegisterActivity.this, "Không thể đăng ký!!", Toast.LENGTH_SHORT).show();
                                    pd.dismiss();
                                }

                                @Override
                                public void onComplete() {
                                    pd.dismiss();
                                }
                            });
                }
            }
        });


        //set su kien cho TV back lai login
        TVBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
        edtemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                email = edtemail.getText().toString().trim();
                if (isEmailValid(email))
                {
                    edtemail.setError(null);
                }
                else
                {
                    if (email.isEmpty()){
                        edtemail.setError("Vui lòng nhập trường này");
                    } else {
                        edtemail.setError("Email sai định dạng");
                    }
                }
            }
        });
    }
    private boolean validateEmail(String email) {
        String emails = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emails);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

    private void initWidget() {
        edtemail = findViewById(R.id.inputEmail);
        edtname = findViewById(R.id.inputName);
        edtaddress = findViewById(R.id.inputAddress);
        edtphone = findViewById(R.id.inputPhone);
        edtpass = findViewById(R.id.inputPass);
        edtconfirmpass = findViewById(R.id.inputPassConfirm);
        TVBack = findViewById(R.id.tvBack);
        btnregister = findViewById(R.id.btnRegister);
    }
    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }
}
