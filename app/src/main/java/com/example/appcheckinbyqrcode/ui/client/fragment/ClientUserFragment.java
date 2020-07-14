package com.example.appcheckinbyqrcode.ui.client.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.SessionManager;
import com.example.appcheckinbyqrcode.network.ApiClient;
import com.example.appcheckinbyqrcode.network.response.MessageResponse;
import com.example.appcheckinbyqrcode.network.response.UploadAvatarResponse;
import com.example.appcheckinbyqrcode.network.response.UserResponse;
import com.example.appcheckinbyqrcode.network.url;
import com.example.appcheckinbyqrcode.ui.client.UpdatedProfileActivity;
import com.example.appcheckinbyqrcode.ui.login.LoginActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientUserFragment extends Fragment{
    private static final String TAG = "nnn";
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvMess, tvName, tvEmail, tvPhone, tvAddress,tvbackAvatar;
    private LinearLayout lnchangInfo, lnchangPass, lnlogOut;
    private EditText edt_OldPassword, edt_NewPassword ,edt_NewPasswordAgain;
    private String name, email, phone, address;
    String urls;
    private FrameLayout showAvatar;
    private ImageView mAvatar;
    private CircleImageView circleimg;
    private View view;
    private AlertDialog dialog;
    private ProgressBar progress;
    private static final int PICK_IMAGE = 100;
    String realpath = "";
    Uri imageUri;
    private static final int REQUEST_CODE = 5462;

    public ClientUserFragment() {
// Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_client_user, container, false);
        initWidget();
        getinfo();
        onclick();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getinfo();
    }

    private void getinfo() {

        ApiClient.getService().showinfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(UserResponse userResponse) {
                        name = userResponse.getName();
                        email = userResponse.getEmail();
                        phone = userResponse.getPhone();
                        address = userResponse.getAddress();
                        urls = url.getUrlimg() + userResponse.getAvatar();
                        tvName.setText(name);
                        tvEmail.setText(email);
                        tvPhone.setText(phone);
                        tvAddress.setText(address);
                        Log.d(TAG, "onNext: " + urls);
// Picasso.get().load(urls).into(circleimg);
                        Glide.with(getActivity())
                                .load(urls)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(circleimg);
                        Glide.with(getActivity())
                                .load(urls)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(mAvatar);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void onclick() {
//button log out
        lnlogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog builder = new AlertDialog.Builder(getActivity()).setMessage("Bạn có muốn thoát không?")
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                logout();
                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
// do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info).show();
            }
        });
        lnchangPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
//button change infor
        lnchangInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdatedProfileActivity.class);
                intent.putExtra("name",tvName.getText().toString());
                intent.putExtra("email",tvEmail.getText().toString());
                intent.putExtra("phone",tvPhone.getText().toString());
                intent.putExtra("address",tvAddress.getText().toString());
                intent.putExtra("url",urls);
                startActivityForResult(intent, 5462);
                lnchangInfo.setEnabled(false);
// getActivity().startActivity(intent);
            }
        });

//        circleimg.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, PICK_IMAGE);
////                ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1999);
//                return true;
//            }
//        });
        circleimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAvatar.setVisibility(View.VISIBLE);
            }
        });
        tvbackAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAvatar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1999){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE);
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode==RESULT_OK){
                    tvName.setText(name = data.getStringExtra("name"));
                    tvEmail.setText(email = data.getStringExtra("email"));
                    tvPhone.setText(phone = data.getStringExtra("phone"));
                    tvAddress.setText(address = data.getStringExtra("address"));
                    Glide.with(getActivity())
                            .load(data.getStringExtra("avatar"))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(circleimg);
                    Glide.with(getActivity())
                            .load(urls)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(mAvatar);
                    Log.d(TAG, "onActivityResult: "+name+ email+phone+address);
                }
                break;
            case PICK_IMAGE:
                if (data != null && data.getData() != null) {
                    Uri uri = data.getData();
                    realpath = getRealPathFromURI(uri);
                    try {
                        InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
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
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_changepass, null);
        progress = view.findViewById(R.id.progress);
        builder.setView(view);
        builder.setPositiveButton("Đổi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvMess = view.findViewById(R.id.tvMess);
                edt_OldPassword = view.findViewById(R.id.inputOldPass);
                edt_NewPassword = view.findViewById(R.id.inputNewPass);
                edt_NewPasswordAgain = view.findViewById(R.id.inputNewPassAgain);

                String old_password = edt_OldPassword.getText().toString();
                String new_password = edt_NewPassword.getText().toString();
                String new_passwordAgain = edt_NewPasswordAgain.getText().toString();

                if (new_password.equalsIgnoreCase(new_passwordAgain)){
                    if (!old_password.isEmpty() && !new_password.isEmpty()) {
                        tvMess.setVisibility(View.GONE);
                        if (true) {
                            progress.setVisibility(View.VISIBLE);
                            changePassword(old_password, new_password);
                        } else {
                            Toast.makeText(getActivity(), "Trùng mật khẩu!!", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ!!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    tvMess.setVisibility(View.VISIBLE);
                }
            }
        });
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

    private void changePassword(String old_password1, String new_password1) {
        if (old_password1 != new_password1) {
            ApiClient.getService().updatepass(old_password1, new_password1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<MessageResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(MessageResponse messageResponse) {
                            Toast.makeText(getActivity(), messageResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                            dialog.dismiss();

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("nnn", "onError: change pass" + e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            Toast.makeText(getActivity(), "Trùng mật khẩu!!", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("CutPasteId")
    private void initWidget() {
        circleimg = view.findViewById(R.id.profilePic_client);
        showAvatar = view.findViewById(R.id.showAvatar);
        tvbackAvatar = view.findViewById(R.id.tvbackAvatar);
        mAvatar = view.findViewById(R.id.imageAvatarFull);
        tvName = view.findViewById(R.id.tvNameClient);
        tvEmail = view.findViewById(R.id.tvEmailClient);
        tvPhone = view.findViewById(R.id.tvPhoneClient);
        tvAddress = view.findViewById(R.id.tvAddressClient);

        lnchangInfo = view.findViewById(R.id.linearChangeInfo);
        lnchangPass = view.findViewById(R.id.linearChangePass);
        lnlogOut = view.findViewById(R.id.linearLogOut);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutuser);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getinfo();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void logout() {
        ApiClient.getService().logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MessageResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MessageResponse messageResponse) {
                        if (messageResponse.getMessage() != null) {
                            Log.d("nnn", "logout del token: " + SessionManager.getInstance().getKeySaveToken());
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // hủy trong stack
                            startActivity(intent);
                            SessionManager.getInstance().setKeySaveToken("");
                            SessionManager.getInstance().setKeySaveCheck(true);
                            SessionManager.getInstance().setKeyLogin(false);
                            getActivity().finish();
                        } else {
                            Toast.makeText(getActivity(), messageResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("nnn", "onError: logout " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        lnchangInfo.setEnabled(true);
    }
}