package com.example.appcheckinbyqrcode.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.SessionManager;
import com.example.appcheckinbyqrcode.network.ApiClient;
import com.example.appcheckinbyqrcode.network.response.forgetPassResponse;
import com.example.appcheckinbyqrcode.network.response.resetPassResponse;
import com.example.appcheckinbyqrcode.ui.admin.HomeAdminActivity;
import com.example.appcheckinbyqrcode.ui.client.HomeClientActivity;
import com.example.appcheckinbyqrcode.ui.client.fragment.EventFragment;
import com.example.appcheckinbyqrcode.ui.model.ApiConfig;
import com.example.appcheckinbyqrcode.ui.model.User;
import com.example.appcheckinbyqrcode.ui.model.info;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ForgotPassActivity extends AppCompatActivity {
    Button BtnGetCode;
    TextView TVBack, TVtimer;
    EditText EDTcode, EDTpass, EDTemail;
    String email, pass, code, message;
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
                email = EDTemail.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(ForgotPassActivity.this, "Vui lòng nhập Email của bạn", Toast.LENGTH_SHORT).show();
                } else {
                    //////API
                    ApiClient.getService().forgetPass(email)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<forgetPassResponse>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(forgetPassResponse forgetPassResponse) {
                                    //forgetPassResponse.getMessage().equals("We cant find a user with that e-mail address.");
                                    Log.d("nnn", "OnMess" + forgetPassResponse.getMessage());
                                    if (forgetPassResponse.getMessage().equals("We cant find a user with that e-mail address.")) {
                                        Toast.makeText(ForgotPassActivity.this, "Email của bạn khong dung", Toast.LENGTH_SHORT).show();
                                    } else {
                                        EDTemail.setVisibility(View.GONE);
                                        EDTcode.setVisibility(View.VISIBLE);
                                        EDTpass.setVisibility(View.VISIBLE);
                                        TVtimer.setVisibility(View.VISIBLE);
                                        BtnGetCode.setText("Change Password");
                                        startCountdownTimer();
                                        BtnGetCode.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                code = EDTcode.getText().toString();
                                                pass = EDTpass.getText().toString();

                                                if (code.isEmpty() || pass.isEmpty()) {
                                                    Toast.makeText(ForgotPassActivity.this, "Vui lòng nhập Code và Pass đầy đủ", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    ApiClient.getService().resetPass(email, pass, pass, code, message)
                                                            .subscribeOn(Schedulers.io())
                                                            .observeOn(AndroidSchedulers.mainThread())
                                                            .subscribe(new Observer<resetPassResponse>() {
                                                                @Override
                                                                public void onSubscribe(Disposable d) {

                                                                }

                                                                @Override
                                                                public void onNext(resetPassResponse resetPassResponse) {
                                                                    Toast.makeText(ForgotPassActivity.this, "Email của bạn khong dung", Toast.LENGTH_SHORT).show();
                                                                }

                                                                @Override
                                                                public void onError(Throwable e) {

                                                                }

                                                                @Override
                                                                public void onComplete() {

                                                                }
                                                            });


                                                    Intent intent = new Intent(ForgotPassActivity.this, LoginActivity.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        });
                                    }


                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(ForgotPassActivity.this, "ban Sai Vl", Toast.LENGTH_SHORT).show();
                                    Log.d("nnn","Error" +e.getMessage());
                                }

                                @Override
                                public void onComplete() {

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
        EDTcode = findViewById(R.id.inputCode);
        EDTpass = findViewById(R.id.inputPass);
        EDTemail = findViewById(R.id.inputEmail);
        EDTcode.setVisibility(View.GONE);
        EDTpass.setVisibility(View.GONE);
        TVtimer = findViewById(R.id.timer);
        TVtimer.setVisibility(View.GONE);
    }
}
