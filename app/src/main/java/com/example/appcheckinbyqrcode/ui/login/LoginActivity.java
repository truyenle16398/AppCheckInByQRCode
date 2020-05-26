package com.example.appcheckinbyqrcode.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.ui.admin.HomeAdminActivity;

public class LoginActivity extends AppCompatActivity {
    Button BtnLogin;
    TextView TVForgotPass, TVemail, TVpass, TVregister;
    String email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Anhxa();
        onClick();
    }

    void onClick() {
        //Xử lý sự kiện nút Login
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = TVemail.getText().toString();
                pass = TVpass.getText().toString();
                if (email.isEmpty() && pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ!!", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập tài khoản Email!!", Toast.LENGTH_SHORT).show();
                } else if (pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập mật khẩu!!", Toast.LENGTH_SHORT).show();
                } else {
                    //code in here
                    Intent intent = new Intent(LoginActivity.this, HomeAdminActivity.class);
                    startActivity(intent);
                }
            }
        });

        //Xử lý sự kiện TextView Quên mật khẩu
        TVForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassActivity.class);
                startActivity(intent);
            }
        });
        TVregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    void Anhxa() {
        BtnLogin = findViewById(R.id.btnLogin);
        TVForgotPass = findViewById(R.id.tvPass);
        TVemail = findViewById(R.id.inputEmail);
        TVpass = findViewById(R.id.inputPass);
        TVregister = findViewById(R.id.tvRegister);
    }
}
