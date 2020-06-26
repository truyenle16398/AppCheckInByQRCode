package com.example.appcheckinbyqrcode.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
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
import com.example.appcheckinbyqrcode.network.ApiClient;
import com.example.appcheckinbyqrcode.ui.admin.HomeAdminActivity;
import com.example.appcheckinbyqrcode.ui.client.HomeClientActivity;
import com.example.appcheckinbyqrcode.ui.model.ApiConfig;
import com.example.appcheckinbyqrcode.SessionManager;
import com.example.appcheckinbyqrcode.ui.model.User;
import com.example.appcheckinbyqrcode.ui.model.info;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    TextView tvForgotPass, tvRegister;
//    EditText edtEmail, edtPass;
    TextInputEditText edtEmail,edtPass;
    TextInputLayout tilemail,tilpass;
    String email;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitWidget();
        onClick();
        CheckLogin();
    }

    void onClick() {
        //Xử lý sự kiện nút Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edtEmail.getText().toString();
                pass = edtPass.getText().toString();
                if (email.isEmpty() && pass.isEmpty()) {
                    tilemail.setError("Vui lòng nhập trường này");
                    tilpass.setError("Vui lòng nhập trường này");
//                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ!!", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    tilemail.setError("Vui lòng nhập trường này");
//                    Toast.makeText(LoginActivity.this, "Vui lòng nhập tài khoản Email!!", Toast.LENGTH_SHORT).show();
                } else if (pass.isEmpty()) {
                    tilpass.setError("Vui lòng nhập trường này");
//                    Toast.makeText(LoginActivity.this, "Vui lòng nhập mật khẩu!!", Toast.LENGTH_SHORT).show();
                } else {
                    tilemail.setError(null);
                    tilpass.setError(null);
                    ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                    pd.setMessage("loading");
                    pd.show();
                    //code in here
                    ApiClient.getService().loginnew(email, pass)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<User>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(User us) {
                                    Log.d("nnn", "onSuccess: " + us.getMessage());
                                    if (us.getMessage().equals("Tài khoản của bạn chưa xác thực!")) {
                                        Toast.makeText(LoginActivity.this, "Vui lòng xác thực tài khoản của bạn", Toast.LENGTH_SHORT).show();
                                    } else if (us.getMessage().equals("Email hoặc mật khẩu không chính xác!")) {
                                        Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không chính xác!!", Toast.LENGTH_SHORT).show();
                                        pd.dismiss();
                                    } else if (us.getMessage().equals("Tài khoản của bạn đã bị khóa!")) {
                                        Toast.makeText(LoginActivity.this, "Tài khoản của bạn đã bị khóa!!", Toast.LENGTH_SHORT).show();
                                        pd.dismiss();

                                    } else {
                                        info info = us.getUser();
                                        if (us != null && info.getId() != null) {
                                            SessionManager.getInstance().setKeySaveToken(us.getAccessToken());
                                            SessionManager.getInstance().setKeySaveName(info.getName());
                                            SessionManager.getInstance().setKeyLogin(true);
                                            SessionManager.getInstance().setKeyRole(info.getRoleId());
                                            Log.d("nnn", "onError: " + us.getAccessToken());
                                            ////////
                                            /////event role id
                                            if (info.getRoleId().equals("3")) {
                                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(LoginActivity.this, HomeClientActivity.class);
                                                startActivity(intent);
                                            } else if (info.getRoleId().equals("2")) {
                                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(LoginActivity.this, HomeAdminActivity.class);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(LoginActivity.this, "Bạn không đủ quyền!", Toast.LENGTH_SHORT).show();
                                            }

                                            ApiConfig config = ApiConfig.builder().context(LoginActivity.this).baseUrl(SessionManager.getInstance().getKeySaveCityName())
                                                    .auth(SessionManager.getInstance().getKeySaveToken())
                                                    .build();
                                            ApiClient.getInstance().init(config);
                                            finish();
                                        }
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d("nnn", "onError: " + e.getMessage());
                                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại!!", Toast.LENGTH_SHORT).show();
                                    pd.dismiss();
                                }

                                @Override
                                public void onComplete() {
                                    pd.dismiss();
                                }
                            });
                }
                edtEmail.onEditorAction(EditorInfo.IME_ACTION_DONE);
                edtPass.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });

        //Xử lý sự kiện TextView Quên mật khẩu
        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassActivity.class);
                startActivity(intent);
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                email = edtEmail.getText().toString().trim();
                if (isEmailValid(email))
                {
                    tilemail.setError(null);
                }
                else
                {
                    if (email.isEmpty()){
                        tilemail.setError("Vui lòng nhập trường này");
                    } else {
                        tilemail.setError("Email sai định dạng");
                    }
                }
            }
        });
        edtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                pass = edtPass.getText().toString().trim();
                if (pass.isEmpty())
                {
                    tilpass.setError("Vui lòng nhập trường này");
                }
                else
                {
                    tilpass.setError(null);
                }
            }
        });

    }

    private void CheckLogin() {
        if (SessionManager.getInstance().CheckKeyLogin()) {
            //session.Check()
            if (SessionManager.getInstance().getKeyRole().equals("3")) {
                Intent intent = new Intent(getApplication(), HomeClientActivity.class);
                startActivity(intent);
                finish();
            } else if (SessionManager.getInstance().getKeyRole().equals("2")) {
                Intent intent = new Intent(getApplication(), HomeAdminActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private void InitWidget() {
        tilemail = findViewById(R.id.text_input_layout_email);
        tilpass = findViewById(R.id.text_input_layout_pass);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPass = findViewById(R.id.tvPass);
        edtEmail = findViewById(R.id.inputEmail);
        edtPass = findViewById(R.id.inputPass);
        tvRegister = findViewById(R.id.tvRegister);
    }

//    private boolean validateEmail(String email) {
//        String emails = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
//        Pattern pattern = Pattern.compile(emails);
//        Matcher matcher = pattern.matcher(email);
//        return matcher.matches();
//    }

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
