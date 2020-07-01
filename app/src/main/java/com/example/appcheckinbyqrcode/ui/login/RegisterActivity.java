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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcheckinbyqrcode.CheckValidate;
import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.SessionManager;
import com.example.appcheckinbyqrcode.network.ApiClient;
import com.example.appcheckinbyqrcode.ui.admin.HomeAdminActivity;
import com.example.appcheckinbyqrcode.ui.client.HomeClientActivity;
import com.example.appcheckinbyqrcode.ui.model.ApiConfig;
import com.example.appcheckinbyqrcode.ui.model.User;
import com.example.appcheckinbyqrcode.ui.model.info;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
    private LinearLayout mLlRegister;
    TextInputEditText edtemail,edtname,edtpass, edtconfirmpass, edtaddress, edtphone;
    TextInputLayout text_input_layout_email,text_input_layout_name, text_input_layout_address, text_input_layout_phone, text_input_layout_pass, text_input_layout_passconfirm;
    String email, name, address, phone, password, confirmpassword;
    Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initWidget();
        onClick();
        onWrite();
    }

    private void onWrite() {
        edtemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String emailonWrite = edtemail.getText().toString();
                if (!CheckValidate.isEmailValid(emailonWrite)) {
                    text_input_layout_email.setErrorEnabled(true);
                    if(emailonWrite.isEmpty()){
                        text_input_layout_email.setError("Trường này không bỏ trống");
                    } else{
                        text_input_layout_email.setError("Email sai định dạng");
                    }
                }else {
                    text_input_layout_email.setErrorEnabled(false);
                }
            }
        });
        edtname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String nameonWrite = edtname.getText().toString();
                if (nameonWrite.isEmpty()) {
                    text_input_layout_name.setErrorEnabled(true);
                    text_input_layout_name.setError("Trường này không bỏ trống");
                } else if(!CheckValidate.isValidName(nameonWrite)){
                    text_input_layout_name.setErrorEnabled(true);
                    text_input_layout_name.setError("Tên không được chứa kí tự đặc biệt");
            }else {
                    text_input_layout_name.setErrorEnabled(false);
                }
            }
        });
        edtaddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                check(s);
                String addressonWrite = edtaddress.getText().toString();
                if (addressonWrite.isEmpty()) {
                    text_input_layout_address.setErrorEnabled(true);
                    text_input_layout_address.setError("Trường này không bỏ trống");
                }else {
                    text_input_layout_address.setErrorEnabled(false);
                }
            }
        });
        edtphone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String phoneonWrite = edtphone.getText().toString();
                if (phoneonWrite.isEmpty()) {
                    text_input_layout_phone.setErrorEnabled(true);
                    text_input_layout_phone.setError("Trường này không bỏ trống");
                } else if (!CheckValidate.isValidPhoneNumber(phoneonWrite)){
                    text_input_layout_phone.setError("Số điện thoại sai định dạng");
                } else {
                    text_input_layout_phone.setErrorEnabled(false);
                }
            }
        });
        edtpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String passwordonWrite = edtpass.getText().toString();
                if (!CheckValidate.isPasswordValid(passwordonWrite)) {
                    text_input_layout_pass.setError("Mật khẩu phải từ 6 đến 12 ký tự viết liền không dấu và không chứa ký tự đặc biệt");
                } else if(passwordonWrite.isEmpty()){
                    text_input_layout_pass.setError("Trường này không bỏ trống");
                }
                else {
                    text_input_layout_pass.setError(null);
                }
            }
        });
        edtconfirmpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String passconfirmonWrite = edtconfirmpass.getText().toString();
                if (!CheckValidate.isPasswordValid(passconfirmonWrite)) {
                    text_input_layout_passconfirm.setError("Mật khẩu phải từ 6 đến 12 ký tự viết liền không dấu");
                } else if(passconfirmonWrite.isEmpty()){
                    text_input_layout_passconfirm.setError("Trường này không bỏ trống");
                } else if(!edtpass.getText().toString().equals(passconfirmonWrite)){
                    text_input_layout_passconfirm.setError("Mật khẩu không trùng");
                }
                else {
                    text_input_layout_passconfirm.setError(null);
                }
            }
        });
    }


    void onClick() {
        mLlRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeybaord(v);
            }
        });
        //set su kien cho button register
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeybaord(view);
                email = edtemail.getText().toString();
                name = edtname.getText().toString();
                address = edtaddress.getText().toString();
                phone = edtphone.getText().toString();
                password = edtpass.getText().toString();
                confirmpassword = edtconfirmpass.getText().toString();
                if (email.isEmpty() || name.isEmpty() || address.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                    text_input_layout_email.setError("Vui lòng nhập trường này");
//                    text_input_layout_name.setError("Vui lòng nhập trường này");
//                    text_input_layout_address.setError("Vui lòng nhập trường này");
//                    text_input_layout_phone.setError("Vui lòng nhập trường này");
//                    text_input_layout_pass.setError("Vui lòng nhập trường này");
//                    text_input_layout_passconfirm.setError("Vui lòng nhập trường này");
                } else if(text_input_layout_email.getError() != null || text_input_layout_name.getError() != null || text_input_layout_address.getError() != null
                            || text_input_layout_phone.getError() != null || text_input_layout_pass.getError() != null || text_input_layout_passconfirm.getError() != null){
                } else{
                    ProgressDialog pd = new ProgressDialog(RegisterActivity.this);
                    pd.setMessage("loading");
                    pd.show();
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
                                    Toast.makeText(RegisterActivity.this, "Email này đã được đăng ký!!", Toast.LENGTH_SHORT).show();
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
        text_input_layout_email = findViewById(R.id.text_input_layout_email);
        text_input_layout_name = findViewById(R.id.text_input_layout_name);
        text_input_layout_address = findViewById(R.id.text_input_layout_address);
        text_input_layout_phone = findViewById(R.id.text_input_layout_phone);
        text_input_layout_passconfirm = findViewById(R.id.text_input_layout_passconfirm);
        text_input_layout_pass = findViewById(R.id.text_input_layout_pass);
        mLlRegister = findViewById(R.id.mLlRegister);
    }
    void check(Editable s){
        if (regex.matcher(s).find()) {
            Toast.makeText(this,"aaa",Toast.LENGTH_LONG).show();
            //handle your action here toast message/ snackbar or something else
            return;
        }
    }

}
