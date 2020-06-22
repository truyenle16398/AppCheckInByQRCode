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

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EventDetailActivity extends AppCompatActivity {
    MyDatabaseHelper myDatabaseHelper;
    ArrayList<FavoriteList> favoriteLists;
    FavoriteList favoritemodel;
    ImageView imageDetail;
    TextView txtNameEventDetail, txtDateTimeStart, txtDateTimeEnd, txtInfoDetail, txtAddressInfoDetail, txtFavorite;
    Button btnRegisterDetail;
    Toolbar toolbar;
    private int id;
    private OnIntent home;
    private AlertDialog dialog;
    private static final String TAG = "nnn";
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
//         home =  (OnIntent) EventDetailActivity.this.getBaseContext();
        InitWidget();
        myDatabaseHelper = new MyDatabaseHelper(this);
//        myDatabaseHelper.getWritableDatabase();
        favoriteLists = new ArrayList<>();
        favoritemodel = new FavoriteList(0, 0);
        setSupportActionBar(toolbar);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_while24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
// getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        txtFavorite = findViewById(R.id.txtFavorite);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        getdata(id);
        onclick();
        if (myDatabaseHelper.getFavoriteID(id) == 1) {
            Log.d(TAG, "onBindViewHolder: id okkkkkkkkkk" + id);
            txtFavorite.setVisibility(View.GONE);
//            Toast.makeText(context, "aaaa" + , Toast.LENGTH_SHORT).show();
        } else {
            Log.d(TAG, "ttttttttttttttttttt: ");
            txtFavorite.setVisibility(View.VISIBLE);
        }
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

                myDatabaseHelper.insertFavorite(id);

                Intent intent = new Intent(getApplication(), HomeClientActivity.class);
                startActivity(intent);

            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                myDatabaseHelper.insertFavorite(id);
                Toast.makeText(EventDetailActivity.this, "okInster", Toast.LENGTH_SHORT).show();

            }
        });
        dialog = builder.create();
        dialog.show();
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