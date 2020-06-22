package com.example.appcheckinbyqrcode.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.appcheckinbyqrcode.network.ApiClient;
import com.example.appcheckinbyqrcode.network.response.MessageResponse;
import com.example.appcheckinbyqrcode.network.response.UserQRRespon;
import com.example.appcheckinbyqrcode.sqlite.MyDatabaseHelper;
import com.example.appcheckinbyqrcode.ui.admin.fragment.ScannerFragment;
import com.example.appcheckinbyqrcode.ui.admin.model.InfoQR;
import com.example.appcheckinbyqrcode.ui.model.User;
import com.google.zxing.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    int MY_PERMISSIONS_REQUEST_CAMERA=0;
    private MyDatabaseHelper myDatabaseHelper;
    ArrayList<InfoQR> listinfo;
    InfoQR infoQR;
    ZXingScannerView scannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        myDatabaseHelper = new MyDatabaseHelper(this);
        listinfo = new ArrayList<>();
        infoQR = new InfoQR(0,"","","","","","");
    }

    @Override
    public void handleResult(Result result) {
        String re = result.getText().toString();
//        String re = "9 Fe59Xm09a3WXMUf04nmvlhJEDJpIMOLrGMLdJDoFjatUb55DbXkTZjYJIkcj";
        String[] words = re.split("-");
//        Log.d("nnn", "111handleResult: "+re.substring(0,1)+ " code: "+ re.substring(1));
        int id = Integer.parseInt(words[0].trim());
        String code = words[1].trim();
        Log.d("nnn", " và id: "+id+ " code: "+ code);
//        Toast.makeText(this, "id:"+id +" code:"+ code, Toast.LENGTH_SHORT).show();
        ApiClient.getService().savecheckin(id,code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MessageResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(MessageResponse messageResponse) {
                        Toast.makeText(ScanCodeActivity.this, messageResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        if (messageResponse.getUser()!=null){
                                Log.d("nnn", "for: "+ messageResponse.getUser().get(0).getName());
                            ScannerFragment.resulttextview.setText(messageResponse.getUser().get(0).getName());
                            infoQR.name=messageResponse.getUser().get(0).getName();
                            infoQR.email=messageResponse.getUser().get(0).getEmail();
                            infoQR.avatar=messageResponse.getUser().get(0).getAvatar();
                            infoQR.phone=messageResponse.getUser().get(0).getPhone();
                            infoQR.address=messageResponse.getUser().get(0).getAddress();
                            Date date = new Date();
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                            String strDate = formatter.format(date);
                            infoQR.timecheckin=strDate;

                            Log.d("nnn", "aaaaaaaaaaa: "+ strDate);
                        } else {
                            ScannerFragment.resulttextview.setText(messageResponse.getMessage());
                            Log.d("nnn", "onNext: "+messageResponse.getMessage());
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d("nnn", "onError ScanCodeActivity: "+e.getMessage());
                    }
                    @Override
                    public void onComplete() {

                    }
                });
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        }
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}
