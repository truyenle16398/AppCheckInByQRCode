package com.example.appcheckinbyqrcode.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.ui.client.HomeClientActivity;
import com.example.appcheckinbyqrcode.ui.client.fragment.EventFragment;

public class ForgotPassActivity extends AppCompatActivity {
    Button BtnGetCode;
    TextView TVBack, TVemail, TVcode, TVpass, TVtimer;
    String email, pass,code;
    CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        Anhxa();
        onClick();
    }

    void onClick() {
        BtnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = TVemail.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(ForgotPassActivity.this, "Vui lòng nhập Email của bạn", Toast.LENGTH_SHORT).show();
                } else {
                    TVemail.setVisibility(View.GONE);
                    TVcode.setVisibility(View.VISIBLE);
                    TVpass.setVisibility(View.VISIBLE);
                    TVtimer.setVisibility(View.VISIBLE);
                    BtnGetCode.setText("Change Password");
                    startCountdownTimer();
                    BtnGetCode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            code = TVcode.getText().toString();
                            pass = TVpass.getText().toString();

                            if (code.isEmpty() || pass.isEmpty()){
                                Toast.makeText(ForgotPassActivity.this, "Vui lòng nhập Code và Pass đầy đủ", Toast.LENGTH_SHORT).show();
                            }else {
                                Intent intent = new Intent(ForgotPassActivity.this, HomeClientActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });

        TVBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void startCountdownTimer() {
        countDownTimer = new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                TVtimer.setText("Time remaining : " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Toast.makeText(ForgotPassActivity.this, "Time Out ! Request again to reset password.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }.start();
    }

    void Anhxa() {
        BtnGetCode = findViewById(R.id.btnGetCode);
        TVBack = findViewById(R.id.tvBack);
        TVcode = findViewById(R.id.inputCode);
        TVpass = findViewById(R.id.inputPass);
        TVemail = findViewById(R.id.inputEmail);
        TVcode.setVisibility(View.GONE);
        TVpass.setVisibility(View.GONE);
        TVtimer = findViewById(R.id.timer);
        TVtimer.setVisibility(View.GONE);
    }
}
