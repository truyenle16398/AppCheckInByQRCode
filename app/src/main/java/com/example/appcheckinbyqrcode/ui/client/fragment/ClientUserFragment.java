package com.example.appcheckinbyqrcode.ui.client.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.SessionManager;
import com.example.appcheckinbyqrcode.network.ApiClient;
import com.example.appcheckinbyqrcode.network.response.MessageResponse;
import com.example.appcheckinbyqrcode.network.response.userResponse;
import com.example.appcheckinbyqrcode.ui.login.LoginActivity;

import butterknife.OnEditorAction;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientUserFragment extends Fragment implements TextView.OnEditorActionListener {
    private static final String TAG = "nnn";
    Button btnChangePass, btnLogOut, btnChangeInfo;
    EditText edtName, edtEmail, edtPhone, edtAddress, edt_OldPassword, edt_NewPassword;
    String name, email, phone, address;
    private View view;
    private AlertDialog dialog;
    private ProgressBar progress;

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
        oncheck();
        return view;
    }

    private void getinfo() {
        ApiClient.getService().showinfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<userResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(userResponse userResponse) {
                        name = userResponse.getName();
                        email = userResponse.getEmail();
                        phone = userResponse.getPhone();
                        address = userResponse.getAddress();
                        edtName.setText(name);
                        edtEmail.setText(email);
                        edtPhone.setText(phone);
                        edtAddress.setText(address);
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

    private void oncheck() {
        edtName.setOnEditorActionListener(this::onEditorAction);
        edtEmail.setOnEditorActionListener(this::onEditorAction);
        edtPhone.setOnEditorActionListener(this::onEditorAction);
        edtAddress.setOnEditorActionListener(this::onEditorAction);

    }

    private void onclick() {
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Logout thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // hủy trong stack
                startActivity(intent);
                SessionManager.getInstance().setKeySaveToken("");
                SessionManager.getInstance().setKeySaveCheck(true);
                SessionManager.getInstance().setKeyLogin(false);
                getActivity().finish();
            }
        });
        btnChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeybaord(v);
                ProgressDialog pd = new ProgressDialog(getActivity());
                pd.setMessage("loading");
                pd.show();
                String namea = edtName.getText().toString();
                String emaila = edtEmail.getText().toString();
                String phonea = edtPhone.getText().toString();
                String addressa = edtAddress.getText().toString();

//        Log.d("nnn", "onClick: "+email+"=="+emailedt +" và "+ name+"=="+nameedt);
                if (name.equals(namea) && email.equals(emaila) && phone.equals(phonea) && address.equals(addressa)) {
                    Toast.makeText(getContext(), "Bạn chưa chỉnh sửa!", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                } else {
                    if (namea.equals("") || emaila.equals("") || phonea.equals("") || addressa.equals("")) {
                        Toast.makeText(getContext(), "Không được để trống!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    } else {
                        ApiClient.getService().update_info(namea, emaila, phonea, addressa)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<userResponse>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(userResponse userResponse) {
                                        Toast.makeText(getActivity(), "Thay đổi thông tin thành công!", Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "onNext: " + userResponse.getName());
                                        btnChangeInfo.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.d(TAG, "onError update pass " + e.getMessage());
                                        pd.dismiss();
                                    }

                                    @Override
                                    public void onComplete() {
                                        pd.dismiss();
                                    }
                                });
                    }
                }
            }
        });
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

    }

    private void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_changepass, null);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        builder.setView(view);
        builder.setTitle("Đổi mật khẩu mới");
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
                String old_password = edt_OldPassword.getText().toString();
                String new_password = edt_NewPassword.getText().toString();
                if (!old_password.isEmpty() && !new_password.isEmpty()) {
                    if (true) {
                        progress.setVisibility(View.VISIBLE);
                        changePassword(old_password, new_password);
                    } else {
                        Toast.makeText(getActivity(), "Trùng mật khẩu!!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
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


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || event.getAction() == EditorInfo.IME_ACTION_DONE
                || event.getAction() == KeyEvent.KEYCODE_ENTER) {
            btnChangeInfo.setVisibility(View.VISIBLE);
        }
        return false;
    }

    private void initWidget() {
        edt_OldPassword = view.findViewById(R.id.inputOldPass);
        edt_NewPassword = view.findViewById(R.id.inputNewPass);
        btnChangePass = view.findViewById(R.id.btnChangePass);
        btnLogOut = view.findViewById(R.id.btnLogout);
        btnChangeInfo = view.findViewById(R.id.btnChangeInfo);
        edtName = view.findViewById(R.id.edtNameClient);
        edtEmail = view.findViewById(R.id.edtEmailClient);
        edtPhone = view.findViewById(R.id.edtPhoneClient);
        edtAddress = view.findViewById(R.id.edtAddressClient);
    }
}
