package com.example.appcheckinbyqrcode.ui.client;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.network.ApiClient;
import com.example.appcheckinbyqrcode.network.response.EventDetailResponse;
import com.example.appcheckinbyqrcode.network.response.MessageResponse;
import com.example.appcheckinbyqrcode.network.url;
import com.example.appcheckinbyqrcode.ui.client.fragment.FavoriteEventFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HistoryDetailActivity extends AppCompatActivity {
    FrameLayout showImageFull;
    ImageView imageDetail,imageDetailFull;
    TextView tvshowqrcode, txtDateTimeStart, txtDateTimeEnd, txtInfoDetail, txtAddressInfoDetail,tvbackdetail;
    Button btnRegisterDetail;
    Toolbar toolbar;
    private int id;
    private String code;
    BottomSheetDialog bottomSheetDialog;
    View bottomSheetView;
    public static final String EXTRA_DATA = "EXTRA_DATA";


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
        code = intent.getStringExtra("code");
        getdata(id);
        onclick();
        createBottomSheet();
    }

    private void createBottomSheet() {
        String qr = id+"-"+code;
        bottomSheetDialog = new BottomSheetDialog(HistoryDetailActivity.this,R.style.BottomSheetDialogTheme);
        bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_dialog_qrcode,(LinearLayout)findViewById(R.id.viewidbottom));
        ImageView imageView = bottomSheetView.findViewById(R.id.imagebottomdialog);
        String urls = "https://chart.googleapis.com/chart?cht=qr&chs=500x500&chl="+qr;
        new ImageDownloaderTask(imageView).execute(urls);
        bottomSheetDialog.setContentView(bottomSheetView);
    }

    private void onclick() {
        tvbackdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageFull.setVisibility(View.GONE);
            }
        });
        imageDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageFull.setVisibility(View.VISIBLE);
            }
        });
        tvshowqrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ProgressDialog dialog = new ProgressDialog(HistoryDetailActivity.this);
//                dialog.setMessage("Please wait...");
//                dialog.show();
                bottomSheetDialog.show();
            }
        });
        btnRegisterDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoriteEventFragment.checkBack=false;
                ProgressDialog dialog = new ProgressDialog(HistoryDetailActivity.this);
                dialog.setMessage(getResources().getString(R.string.load));
                dialog.setCancelable(false);
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
                                Intent intent = new Intent();
//                                intent.putExtra("EXTRA_DATA",postion);
                                setResult(RESULT_OK, intent);
                                finish();
//                                onBackPressed();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("nnn", "onError: " + e.getMessage());
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

    private void getdata(int id) {
        ProgressDialog dialog = new ProgressDialog(HistoryDetailActivity.this);
        dialog.setMessage("please wait...");
        dialog.show();
        ApiClient.getService().detailevents(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MessageResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MessageResponse messageResponse) {
                        EventDetailResponse eventDetailResponse = messageResponse.getDetail();
                        String urls = url.getUrlimgevent()+ eventDetailResponse.getImage();
//                        Log.d("nnn", "onNext: "+urls);
                        Glide.with(getApplicationContext()).load(urls).into(imageDetail);
                        Glide.with(getApplicationContext()).load(urls).into(imageDetailFull);
                        toolbar.setTitle(eventDetailResponse.getName());
//                        txtDateTimeStart.setText(eventDetailResponse.getStart_time());
//                        txtDateTimeEnd.setText(eventDetailResponse.getEnd_time());
                        txtInfoDetail.setText(Html.fromHtml(eventDetailResponse.getDetail()));
                        txtAddressInfoDetail.setText(eventDetailResponse.getPlace());

                        Date datestart = null;
                        Date dateend = null;
                        try {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            datestart = format.parse(eventDetailResponse.getStart_time());
                            dateend = format.parse(eventDetailResponse.getEnd_time());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd-MM-yyyy");
                        txtDateTimeStart.setText("Bắt đầu: "+formatter.format(datestart));
                        txtDateTimeEnd.setText("Kết thúc: "+formatter.format(dateend));
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
        tvshowqrcode =findViewById(R.id.tvshowqrcode);
        imageDetailFull = findViewById(R.id.imageDetailFullHis);
        tvbackdetail = findViewById(R.id.tvbackdetailHis);
        showImageFull = findViewById(R.id.showImageFullHis);

    }
}
