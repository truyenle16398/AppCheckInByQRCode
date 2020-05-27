package com.example.appcheckinbyqrcode.ui.client.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.SessionManager;
import com.example.appcheckinbyqrcode.network.ApiClient;
import com.example.appcheckinbyqrcode.network.response.MessageResponse;
import com.example.appcheckinbyqrcode.network.response.userResponse;
import com.example.appcheckinbyqrcode.ui.admin.HomeAdminActivity;
import com.example.appcheckinbyqrcode.ui.login.ForgotPassActivity;
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
    Button btnChangePass,btnLogOut,btnChangeInfo;
    EditText edtName,edtEmail,edtPhone,edtAddress;
    String name,email,phone,address;
    private View view;
    public ClientUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_client_user, container, false);
        anhxa();
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
                        Log.d(TAG, "onError: "+ e.getMessage());
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
                AlertDialog builder = new AlertDialog.Builder(getActivity()).setMessage("Bạn có muốn thoát không?")
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                logout();
                            }
                        }).setIcon(android.R.drawable.ic_dialog_info).show();
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
                if (name.equals(namea) && email.equals(emaila) && phone.equals(phonea) && address.equals(addressa)){
                    Toast.makeText(getContext(), "Bạn chưa chỉnh sửa!", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
                else {
                    if (namea.equals("") || emaila.equals("") || phonea.equals("") || addressa.equals("")){
                        Toast.makeText(getContext(), "Không được để trống!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                    else {
                        ApiClient.getService().update_info(namea,emaila,phonea,addressa)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<userResponse>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(userResponse userResponse) {
                                        Toast.makeText(getActivity(), "Thay đổi thông tin thành công!", Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "onNext: "+ userResponse.getName());
                                        btnChangeInfo.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.d(TAG, "onError update pass "+ e.getMessage());
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

    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager =  (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

    private void anhxa() {
        btnLogOut = view.findViewById(R.id.btnLogout);
        btnChangeInfo = view.findViewById(R.id.btnChangeInfo);
        edtName = view.findViewById(R.id.edtNameClient);
        edtEmail = view.findViewById(R.id.edtEmailClient);
        edtPhone = view.findViewById(R.id.edtPhoneClient);
        edtAddress = view.findViewById(R.id.edtAddressClient);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || event.getAction() == EditorInfo.IME_ACTION_DONE
                || event.getAction() == KeyEvent.KEYCODE_ENTER){
            btnChangeInfo.setVisibility(View.VISIBLE);
        }
        return false;
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
