package com.example.appcheckinbyqrcode.ui.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.network.ApiClient;
import com.example.appcheckinbyqrcode.network.response.EventDetailResponse;
import com.example.appcheckinbyqrcode.network.response.MessageResponse;
import com.example.appcheckinbyqrcode.network.url;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HistoryDetailActivity extends AppCompatActivity {
    ImageView imageDetail;
    TextView txtNameEventDetail, txtDateTimeStart, txtDateTimeEnd, txtInfoDetail, txtAddressInfoDetail;
    Button btnRegisterDetail;
    Toolbar toolbar;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        InitWidget();
        setSupportActionBar(toolbar);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_while24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(drawable);

        Intent intent = getIntent();
        id = intent.getIntExtra("idhistory", 0);
        getdata(id);
        onclick();
    }

    private void onclick() {
        btnRegisterDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog dialog = new ProgressDialog(HistoryDetailActivity.this);
                dialog.setMessage("please wait...");
                dialog.show();
                ApiClient.getService().cancelevent(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<MessageResponse>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(MessageResponse messageResponse) {
                                Toast.makeText(HistoryDetailActivity.this, messageResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("nnn", "onError: "+e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                dialog.dismiss();
                            }
                        });

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getdata(int i ) {
        ProgressDialog dialog = new ProgressDialog(HistoryDetailActivity.this);
        dialog.setMessage("please wait...");
        dialog.show();
        ApiClient.getService().detailevents(i).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EventDetailResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(EventDetailResponse eventDetailResponse) {
                        String urls = url.getUrlimg()+ eventDetailResponse.getImage();
                        Glide.with(getApplicationContext()).load(urls).into(imageDetail);
                        toolbar.setTitle(eventDetailResponse.getName());
                        txtDateTimeStart.setText(eventDetailResponse.getStart_time());
                        txtDateTimeEnd.setText(eventDetailResponse.getEnd_time());
                        txtInfoDetail.setText(eventDetailResponse.getDetail());
                        txtAddressInfoDetail.setText(eventDetailResponse.getPlace());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("nnn", "onError nnn" + e.getMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onComplete() {
                        dialog.dismiss();
                    }
                });
    }

    private void InitWidget() {
        toolbar = findViewById(R.id.toolbarDetailHistory);
        imageDetail = findViewById(R.id.imageDetailHistory);
        txtDateTimeStart = findViewById(R.id.txtDateTimeStartHistory);
        txtDateTimeEnd = findViewById(R.id.txtDateTimeEndHistory);
        txtInfoDetail = findViewById(R.id.txtInfoDetailHistory);
        txtAddressInfoDetail = findViewById(R.id.txtAddressInfoDetailHistory);
        btnRegisterDetail = findViewById(R.id.btnRegisterDetailHistory);

    }
}
