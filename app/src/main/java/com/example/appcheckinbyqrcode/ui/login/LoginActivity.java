package com.example.appcheckinbyqrcode.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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


import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    TextView tvForgotPass, tvRegister;
    EditText edEmail, edtPass;
    String email, pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Anhxa();
        onClick();
        CheckLogin();
    }

    void onClick() {
        //Xử lý sự kiện nút Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edEmail.getText().toString();
                pass = edtPass.getText().toString();
                if (email.isEmpty() && pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ!!", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập tài khoản Email!!", Toast.LENGTH_SHORT).show();
                } else if (pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập mật khẩu!!", Toast.LENGTH_SHORT).show();
                } else {
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
                                    info info = us.getUser();
                                    if (us != null && info.getId() != null) {
                                        SessionManager.getInstance().setKeySaveToken(us.getAccessToken());
                                        SessionManager.getInstance().setKeySaveName(info.getName());
                                        SessionManager.getInstance().setKeyLogin(true);
                                        SessionManager.getInstance().setKeyRole(info.getRoleId());
                                        Log.d("nnn", "onError: " + us.getAccessToken());
                                        ////////
                                        /////event role id
                                        if (info.getRoleId().equals("3")){
                                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(LoginActivity.this, HomeClientActivity.class);
                                            startActivity(intent);
                                        }else if (info.getRoleId().equals("2")){
                                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(LoginActivity.this, HomeAdminActivity.class);
                                            startActivity(intent);
                                        }else {
                                            Toast.makeText(LoginActivity.this, "Tuoi Nao Ma Doi Vo App Tao!", Toast.LENGTH_SHORT).show();
                                        }

//                                        saveTokenFirebase();

                                        ApiConfig config = ApiConfig.builder().context(LoginActivity.this).baseUrl(SessionManager.getInstance().getKeySaveCityName())
                                                .auth(SessionManager.getInstance().getKeySaveToken())
                                                .build();
                                        ApiClient.getInstance().init(config);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không chính xác!!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d("nnn", "onError: " + e.getMessage());
                                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại!!", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }
                edEmail.onEditorAction(EditorInfo.IME_ACTION_DONE);
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

    }


    private void CheckLogin(){
        if(!SessionManager.getInstance().CheckKeyLogin()){//session.Check()
            Toast.makeText(this, "Vui lòng đăng nhập!!!!!!", Toast.LENGTH_SHORT).show();
        }else {
            if (SessionManager.getInstance().getKeyRole().equals("3")){
                Intent intent = new Intent(getApplication(),HomeClientActivity.class);
                startActivity(intent);
                finish();
            }else if (SessionManager.getInstance().getKeyRole().equals("2")){
                Intent intent = new Intent(getApplication(),HomeAdminActivity.class);
                startActivity(intent);
                finish();
                }
        }
    }

    void Anhxa() {
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPass = findViewById(R.id.tvPass);
        edEmail = findViewById(R.id.inputEmail);
        edtPass = findViewById(R.id.inputPass);
        tvRegister = findViewById(R.id.tvRegister);
    }
}
