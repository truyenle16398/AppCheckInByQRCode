package com.example.appcheckinbyqrcode.ui.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.network.ApiClient;
import com.example.appcheckinbyqrcode.network.response.UserResponse;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UpdatedProfileActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private Toolbar toolbar;
    private LinearLayout lnimg, lnname, lnemail, lnphone, lnaddress, lnupdatepass;
    private TextView tvName, tvEmail, tvPhone, tvAddress, tvupdatepass, tvHoi;
    private String name, email, phone, address;
    private EditText edtNhap;
    private CircleImageView circleimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updated_profile);
        initWidget();
        addinfo();
        onclick();
    }

    private void addinfo() {
        Intent intent = getIntent();
        Glide.with(getApplicationContext())
                .load(intent.getStringExtra("url"))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(circleimg);
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        phone = intent.getStringExtra("phone");
        address = intent.getStringExtra("address");
        tvName.setText(name);
        tvEmail.setText(email);
        tvPhone.setText(phone);
        tvAddress.setText(address);
    }

    private void onclick(){
        lnimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        lnname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog(tvName.getText().toString(),"Sửa tên?",tvName);
            }
        });
        lnemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog(tvEmail.getText().toString(),"Sửa email?",tvEmail);
            }
        });
        lnphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog(tvPhone.getText().toString(),"Sửa số điện thoại?",tvPhone);
            }
        });
        lnaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialog(tvAddress.getText().toString(),"Sửa địa chỉ?",tvAddress);
            }
        });
        lnupdatepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void changeInfo(){
        String namea = tvName.getText().toString();
        String emaila = tvEmail.getText().toString();
        String phonea = tvPhone.getText().toString();
        String addressa = tvAddress.getText().toString();

// Log.d("nnn", "onClick: "+email+"=="+emailedt +" và "+ name+"=="+nameedt);
        if (name.equals(namea) && email.equals(emaila) && phone.equals(phonea) && address.equals(addressa)) {
            Toast.makeText(UpdatedProfileActivity.this, "Bạn chưa chỉnh sửa!", Toast.LENGTH_SHORT).show();
        } else {
            if (namea.equals("") || emaila.equals("") || phonea.equals("") || addressa.equals("")) {
                Toast.makeText(UpdatedProfileActivity.this, "Không được để trống!", Toast.LENGTH_SHORT).show();
            } else {
                ApiClient.getService().updateinfo(namea, emaila, phonea, addressa)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<UserResponse>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(UserResponse userResponse) {
                                Toast.makeText(UpdatedProfileActivity.this, "Thay đổi thông tin thành công!", Toast.LENGTH_SHORT).show();
                                Log.d("nnn", "onNext: " + userResponse.getName());
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("nnn", "onError update pass " + e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                Toast.makeText(UpdatedProfileActivity.this, "Đã cập nhật!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        });
            }
        }
    }

    private void showdialog(String text, String hoi,TextView tvnhan){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogupdate, null);
        TextView back = view.findViewById(R.id.tvBackDialogUpdate);
        TextView ok = view.findViewById(R.id.tvOkDialogUpdate);
        TextView thongbao = view.findViewById(R.id.tvthongbaoupdate);
        tvHoi = view.findViewById(R.id.tvhoi);
        edtNhap = view.findViewById(R.id.edtnhap);
        tvHoi.setText(hoi);
        edtNhap.setText(text);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtNhap.getText().toString().isEmpty()){
                    tvnhan.setText(edtNhap.getText().toString());
                    dialog.dismiss();
                }else {
                    thongbao.setText("Vui lòng không bỏ trống trường này!");
                    thongbao.setVisibility(View.VISIBLE);

                }
            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.updateok:
//                changeInfo();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initWidget() {
        toolbar = findViewById(R.id.updateToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setHomeAsUpIndicator(drawable);
        lnimg = findViewById(R.id.updateImage);
        lnname =  findViewById(R.id.updateName);
        lnemail = findViewById(R.id.updateEmail);
        lnphone = findViewById(R.id.updatePhone);
        lnaddress = findViewById(R.id.updateAddress);
        lnupdatepass = findViewById(R.id.updatePass);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);
        circleimg = findViewById(R.id.avatarCircler);

    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }
}
