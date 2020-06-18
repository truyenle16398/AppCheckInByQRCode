package com.example.appcheckinbyqrcode.ui.admin.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.SessionManager;
import com.example.appcheckinbyqrcode.network.ApiClient;
import com.example.appcheckinbyqrcode.network.response.MessageResponse;
import com.example.appcheckinbyqrcode.network.response.UserResponse;
import com.example.appcheckinbyqrcode.network.url;
import com.example.appcheckinbyqrcode.ui.login.LoginActivity;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AdminUserFragment extends Fragment {

    SwipeRefreshLayout refreshLayout;
    private Button btnLogoutadmin;
    private EditText oldPass, newPass, edtName, edtEmail, edtPhone, edtAddress;
    private AlertDialog dialog;
    private TextView myprofile;
    private View view;
    CircleImageView circleImageView;
    ViewGroup viewchangepass_logoutadmin;

    public AdminUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_admin, container, false);
        InitWidget();
        getinfo();
        onclick();
        return view;
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
                        String name = userResponse.getName();
                        String email = userResponse.getEmail();
                        String phone = userResponse.getPhone();
                        String address = userResponse.getAddress();
                        String urls = url.getUrlimg() + userResponse.getAvatar();
                        Log.d("nnn", "onNext: " + userResponse.toString());
                        edtName.setText(name);
                        edtEmail.setText(email);
                        edtPhone.setText(phone);
                        edtAddress.setText(address);
//                        Picasso.get().load(urls).into(circleimg);
                        Glide.with(getActivity())
                                .load(urls)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(circleImageView);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("nnn", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void onclick() {
        btnLogoutadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog builder = new AlertDialog.Builder(getActivity()).setMessage("Bạn có muốn thoát không?")
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                logout();
                            }
                        }).setIcon(android.R.drawable.ic_dialog_info).show();
            }
        });
        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewchangepass_logoutadmin.getVisibility() == View.INVISIBLE) {
                    viewchangepass_logoutadmin.setVisibility(View.VISIBLE);
                } else {
                    viewchangepass_logoutadmin.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void InitWidget() {
        viewchangepass_logoutadmin = view.findViewById(R.id.viewchangepass_logoutadmin);
        myprofile = view.findViewById(R.id.myprofileadmin);
        circleImageView = view.findViewById(R.id.profilePicadmin);
        edtName = view.findViewById(R.id.nameadmin);
        edtEmail = view.findViewById(R.id.emailadmin);
        edtPhone = view.findViewById(R.id.phoneadmin);
        edtAddress = view.findViewById(R.id.addressadmin);
        btnLogoutadmin = view.findViewById(R.id.btnLogoutadmin);
        refreshLayout = view.findViewById(R.id.swipeRefreshLayoutadminUser);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getinfo();
                refreshLayout.setRefreshing(false);
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

}
