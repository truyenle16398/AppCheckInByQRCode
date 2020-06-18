package com.example.appcheckinbyqrcode.ui.client;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.network.ApiClient;
import com.example.appcheckinbyqrcode.network.response.UploadAvatarResponse;
import com.example.appcheckinbyqrcode.network.response.UserResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

;

public class UpdatedProfileActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private Toolbar toolbar;
    private LinearLayout lnimg, lnname, lnemail, lnphone, lnaddress, lnupdatepass;
    private TextView tvName, tvEmail, tvPhone, tvAddress, tvupdatepass, tvHoi;
    private String name, email, phone, address;
    private EditText edtNhap;
    private CircleImageView circleimg;
    String realpath = "";
    Rect pic1Rect;
    Intent inte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updated_profile);
        initWidget();
        addinfo();
        pic1Rect = new Rect();
        circleimg.getDrawingRect(pic1Rect);
        onclick();
         inte = new Intent(this, ChangeActivity.class);
    }


    private void addinfo() {
        Intent intent = getIntent();
        realpath = intent.getStringExtra("url");
        Glide.with(getApplicationContext())
                .load(realpath)
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
        circleimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
        lnimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
        lnname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inte.putExtra("title","Sửa tên");
                inte.putExtra("edtnhan",tvName.getText().toString());
                inte.putExtra("check","name");
                startActivityForResult(inte, 9999);
//                showdialog(tvName.getText().toString(),"Sửa tên?",tvName);
            }
        });
        lnemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                inte.putExtra("title","Sửa email");
//                inte.putExtra("edtnhan",tvName.getText().toString());
//                inte.putExtra("check","email");
//                startActivityForResult(inte, 9999);
//                showdialog(tvEmail.getText().toString(),"Sửa email?",tvEmail);
            }
        });
        lnphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inte.putExtra("title","Sửa số điện thoại");
                inte.putExtra("edtnhan",tvPhone.getText().toString());
                inte.putExtra("check","phone");
                startActivityForResult(inte, 9999);
//                showdialog(tvPhone.getText().toString(),"Sửa số điện thoại?",tvPhone);
            }
        });
        lnaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inte.putExtra("title","Sửa địa chỉ");
                inte.putExtra("edtnhan",tvAddress.getText().toString());
                inte.putExtra("check","address");
                startActivityForResult(inte, 9999);
//                showdialog(tvAddress.getText().toString(),"Sửa địa chỉ?",tvAddress);
            }
        });
        lnupdatepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 9999:
                if (resultCode==RESULT_OK){
                    assert data != null;
                    if(data.getStringExtra("check").equals("name")){
                        tvName.setText(data.getStringExtra("edttrave"));
                    } else if(data.getStringExtra("check").equals("phone")){
                        tvPhone.setText(data.getStringExtra("edttrave"));
                    } else if(data.getStringExtra("check").equals("address")){
                        tvAddress.setText(data.getStringExtra("edttrave"));
                    }
                }
                break;
            case 1:
                if (data != null && data.getData() != null) {
                    Uri uri = data.getData();
                    realpath = getRealPathFromURI(uri);
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        circleimg.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    private void changeInfo(){
        String namea = tvName.getText().toString();
        String emaila = tvEmail.getText().toString();
        String phonea = tvPhone.getText().toString();
        String addressa = tvAddress.getText().toString();
        ApiClient.getService().updateinfo(namea, emaila, phonea, addressa)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(UserResponse userResponse) {
//                                Toast.makeText(UpdatedProfileActivity.this, "Thay đổi thông tin thành công!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("nnn", "onError update pass " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(UpdatedProfileActivity.this, "Đã lưu hồ sơ!", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(UpdatedProfileActivity.this, "Đã cập nhật!", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent();
//                                setResult(RESULT_OK, intent);
//                                finish();
                    }
                });
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
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED, intent);
                onBackPressed();
                return true;
            case R.id.updateok:
                if (realpath.isEmpty()){
                    changeInfo();
                } else {
                    upDateUserAvatar(realpath);
                    changeInfo();
                }
//                Intent intent = new Intent();
                Log.d("nnn", "onOptionsItemSelected: "+tvName.getText().toString());
                intent.putExtra("name",tvName.getText().toString());
                intent.putExtra("email",tvEmail.getText().toString());
                intent.putExtra("phone",tvPhone.getText().toString());
                intent.putExtra("address",tvAddress.getText().toString());
                intent.putExtra("avatar",realpath);
                setResult(RESULT_OK, intent);
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //update avatar
    private void upDateUserAvatar(String filename) {
//        ProgressDialog pd = new ProgressDialog(this);
//        pd.setMessage("loading");
//        pd.show();
//        MultipartBody.Part body =
//                MultipartBody.Part.createFormData("image", "avatar.png", fbody);
        File file = new File(filename);
        String filepath = file.getAbsolutePath();
        String[] arraynamefile = filepath.split("\\.");
        filepath = arraynamefile[0] + System.currentTimeMillis() + "." + arraynamefile[1];
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", filepath, requestBody);
        Log.d("nnn", "upDateUserAvatar: " + filepath);
        ApiClient.getService().updateAvatar(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UploadAvatarResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(UploadAvatarResponse uploadAvatarResponse) {
//                        Toast.makeText(UpdatedProfileActivity.this, "Thay đổi thành công!", Toast.LENGTH_SHORT).show();
                        Log.d("nnn", "log api upload image: " + uploadAvatarResponse.getName());
//                        String urls = url.getUrlimg() + uploadAvatarResponse.getAvatar();
//                        Glide.with(getContext()).load(urls).into(circleimg);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("nn", "onError: " + e.getMessage());
//                        pd.dismiss();
                    }

                    @Override
                    public void onComplete() {
//                        pd.dismiss();
                    }
                });
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
}
