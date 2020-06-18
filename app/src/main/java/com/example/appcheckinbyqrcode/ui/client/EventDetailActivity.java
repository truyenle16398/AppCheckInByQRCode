package com.example.appcheckinbyqrcode.ui.client;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

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

public class EventDetailActivity extends AppCompatActivity  {
    ImageView imageDetail;
    TextView txtNameEventDetail, txtDateTimeStart, txtDateTimeEnd, txtInfoDetail, txtAddressInfoDetail;
    Button btnRegisterDetail;
    Toolbar toolbar;
    private int id;
     private OnIntent home ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
//         home =  (OnIntent) EventDetailActivity.this.getBaseContext();
        InitWidget();
        setSupportActionBar(toolbar);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_while24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
// getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(drawable);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        getdata(id);
        onclick();
    }


    private void onclick() {
        btnRegisterDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog dialog = new ProgressDialog(EventDetailActivity.this);
                dialog.setMessage("please wait...");
                dialog.show();
                ApiClient.getService().registerevent(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<MessageResponse>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(MessageResponse messageResponse) {
                                Toast.makeText(EventDetailActivity.this, messageResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("nnn", "onError: " + e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                finish();
                                dialog.dismiss();
                                if (home!=null){;
                                      home.intents();
                                }
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

    private void getdata(int i) {
        ProgressDialog dialog = new ProgressDialog(EventDetailActivity.this);
        dialog.setMessage("please wait...");
        dialog.show();
        ApiClient.getService().detailevents(i).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MessageResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MessageResponse messageResponse) {
                        EventDetailResponse eventDetailResponse = messageResponse.getDetail();
                        String urls = url.getUrlimgevent()+ eventDetailResponse.getImage();
                        Glide.with(getApplicationContext()).load(urls).into(imageDetail);
                        toolbar.setTitle(eventDetailResponse.getName());
                        txtDateTimeStart.setText(eventDetailResponse.getStart_time());
                        txtDateTimeEnd.setText(eventDetailResponse.getEnd_time());
                        String plainText = Html.fromHtml(eventDetailResponse.getDetail()).toString();
                        txtInfoDetail.setText(plainText);
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
        toolbar = findViewById(R.id.toolbarDetail);
        imageDetail = findViewById(R.id.imageDetail);
        txtDateTimeStart = findViewById(R.id.txtDateTimeStart);
        txtDateTimeEnd = findViewById(R.id.txtDateTimeEnd);
        txtInfoDetail = findViewById(R.id.txtInfoDetail);
        txtAddressInfoDetail = findViewById(R.id.txtAddressInfoDetail);
        btnRegisterDetail = findViewById(R.id.btnRegisterDetail);

    }


}