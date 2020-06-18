package com.example.appcheckinbyqrcode.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.network.ApiClient;
import com.example.appcheckinbyqrcode.network.response.MessageResponse;
import com.google.android.material.textfield.TextInputEditText;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ForgotPassActivity extends AppCompatActivity {
    Button btnGetCode;
    TextView tvBack, tvTimer;
    EditText edtCode, edtEmail;
    TextInputEditText edtPass;
    String email, pass, code, message;
    CountDownTimer countDownTimer;
    private String TAG = "nnn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        InitWidget();
        onClick();
    }

    void onClick() {
        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog pd = new ProgressDialog(ForgotPassActivity.this);
                pd.setMessage("loading");
                pd.show();
                hideKeybaord(view);
                email = edtEmail.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(ForgotPassActivity.this, "Vui lòng nhập Email của bạn", Toast.LENGTH_SHORT).show();
                } else {
                    //////API
                    ApiClient.getService().forgetPass(email)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<MessageResponse>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(MessageResponse messageResponse) {
                                    //forgetPassResponse.getMessage().equals("We cant find a user with that e-mail address.");
                                    Log.d("nnn", "OnMess" + messageResponse.getMessage());
                                    if (messageResponse.getMessage().equals("We cant find a user with that e-mail address.")) {
                                        Toast.makeText(ForgotPassActivity.this, "Email của bạn không tồn tại!", Toast.LENGTH_SHORT).show();
                                        pd.dismiss();
                                    } else {
                                        pd.dismiss();
                                        edtEmail.setVisibility(View.GONE);
                                        edtCode.setVisibility(View.VISIBLE);
                                        edtPass.setVisibility(View.VISIBLE);
                                        tvTimer.setVisibility(View.VISIBLE);
                                        btnGetCode.setText("Change Password");
                                        startCountdownTimer();
                                        btnGetCode.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                ProgressDialog bb = new ProgressDialog(ForgotPassActivity.this);
                                                bb.setMessage("loading");
                                                bb.show();
                                                hideKeybaord(view);
                                                code = edtCode.getText().toString();
                                                pass = edtPass.getText().toString();
                                                if (code.isEmpty() || pass.isEmpty()) {
                                                    Toast.makeText(ForgotPassActivity.this, "Vui lòng nhập Code và Pass đầy đủ", Toast.LENGTH_SHORT).show();
                                                    bb.dismiss();
                                                } else {
                                                    ApiClient.getService().resetPass(email, pass, pass, code)
                                                            .subscribeOn(Schedulers.io())
                                                            .observeOn(AndroidSchedulers.mainThread())
                                                            .subscribe(new Observer<MessageResponse>() {
                                                                @Override
                                                                public void onSubscribe(Disposable d) {

                                                                }

                                                                @Override
                                                                public void onNext(MessageResponse messageResponse) {
                                                                    Toast.makeText(ForgotPassActivity.this, messageResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(ForgotPassActivity.this, LoginActivity.class);
                                                                    startActivity(intent);
                                                                }

                                                                @Override
                                                                public void onError(Throwable e) {
                                                                    Log.d(TAG, "onError in click BtnGetCode: " + e.getMessage());
                                                                }

                                                                @Override
                                                                public void onComplete() {
                                                                    bb.dismiss();
                                                                }
                                                            });
                                                }
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(ForgotPassActivity.this, "ban Sai Vl", Toast.LENGTH_SHORT).show();
                                    Log.d("nnn", "Error" + e.getMessage());
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

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }

    private void startCountdownTimer() {
        countDownTimer = new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTimer.setText("Time remaining : " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Toast.makeText(ForgotPassActivity.this, "Time Out ! Request again to reset password.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }.start();
    }

    private void InitWidget() {
        btnGetCode = findViewById(R.id.btnGetCode);
        tvBack = findViewById(R.id.tvBack);
        edtCode = findViewById(R.id.inputCode);
        edtPass = findViewById(R.id.inputPass);
        edtEmail = findViewById(R.id.inputEmail);
        edtCode.setVisibility(View.GONE);
        edtPass.setVisibility(View.GONE);
        tvTimer = findViewById(R.id.timer);
        tvTimer.setVisibility(View.GONE);
    }
}
