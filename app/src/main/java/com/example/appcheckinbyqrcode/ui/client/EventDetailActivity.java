package com.example.appcheckinbyqrcode.ui.client;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.network.ApiClient;
import com.example.appcheckinbyqrcode.network.response.EventDetailResponse;
import com.example.appcheckinbyqrcode.network.response.MessageResponse;
import com.example.appcheckinbyqrcode.network.url;
import com.example.appcheckinbyqrcode.sqlite.MyDatabaseHelper;
import com.example.appcheckinbyqrcode.ui.admin.model.FavoriteList;
import com.example.appcheckinbyqrcode.ui.admin.model.InfoQR;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EventDetailActivity extends AppCompatActivity {
    private MyDatabaseHelper myDatabaseHelper;
    ArrayList<FavoriteList> favoriteLists;
    FavoriteList favorites;
    ImageView imageDetail;
    TextView txtNameEventDetail, txtDateTimeStart, txtDateTimeEnd, txtInfoDetail, txtAddressInfoDetail, txtFavorite;
    Button btnRegisterDetail, btnNo, btnYes;
    Toolbar toolbar;
    private int id;
    private OnIntent home;
    private AlertDialog dialog;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
//         home =  (OnIntent) EventDetailActivity.this.getBaseContext();
        InitWidget();
        myDatabaseHelper = new MyDatabaseHelper(this);
        myDatabaseHelper.getWritableDatabase();
        favoriteLists = new ArrayList<>();
        favorites = new FavoriteList(0, 1);
        setSupportActionBar(toolbar);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_while24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                                if (home != null) {
                                    ;
                                    home.intents();
                                }
                            }
                        });

            }
        });
        txtFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
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
                        String urls = url.getUrlimgevent() + eventDetailResponse.getImage();
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

    //    private void showDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = this.getLayoutInflater();
//        View view = inflater.inflate(R.layout.dialog_favoriteevent, null);
//        progress = view.findViewById(R.id.progress);
//        dialog = builder.create();
//
//        builder.setView(view);
//        builder.setTitle("Yeu Thich Su Kien Nay");
//        builder.setPositiveButton("Dong Y", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                myDatabaseHelper.insertFavorite(favorites);
//                Intent intent = new Intent(getApplication(), HomeClientActivity.class);
//                startActivity(intent);
//            }
//        });
//        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//
////        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                btnYes = view.findViewById(R.id.btnYes);
////                btnNo = view.findViewById(R.id.btnNo);
////                btnNo.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        dialog.dismiss();
////                    }
////                });
////                btnYes.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        myDatabaseHelper.insertFavorite(favorites);
////                        Intent intent = new Intent(getApplication(), HomeClientActivity.class);
////                        startActivity(intent);
////                    }
////                });
////            }
////        });
//    }
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_favoriteevent, null);
        progress = view.findViewById(R.id.progress);
        builder.setView(view);
        builder.setPositiveButton("Thich", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(EventDetailActivity.this, "okInster", Toast.LENGTH_SHORT).show();
                myDatabaseHelper.
                myDatabaseHelper.insertFavorite(favorites);

//                Intent intent = new Intent(getApplication(), HomeClientActivity.class);
//                startActivity(intent);

            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dialog.dismiss();
                myDatabaseHelper.insertFavorite(favorites);
                Toast.makeText(EventDetailActivity.this, "okInster", Toast.LENGTH_SHORT).show();
            }
        });
        dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void InitWidget() {
        toolbar = findViewById(R.id.toolbarDetail);
        imageDetail = findViewById(R.id.imageDetail);
        txtDateTimeStart = findViewById(R.id.txtDateTimeStart);
        txtFavorite = findViewById(R.id.txtFavorite);
        txtDateTimeEnd = findViewById(R.id.txtDateTimeEnd);
        txtInfoDetail = findViewById(R.id.txtInfoDetail);
        txtAddressInfoDetail = findViewById(R.id.txtAddressInfoDetail);
        btnRegisterDetail = findViewById(R.id.btnRegisterDetail);

    }


}