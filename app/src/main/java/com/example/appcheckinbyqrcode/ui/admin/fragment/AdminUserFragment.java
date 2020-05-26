package com.example.appcheckinbyqrcode.ui.admin.fragment;

        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;

        import androidx.fragment.app.Fragment;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.appcheckinbyqrcode.R;
        import com.example.appcheckinbyqrcode.SessionManager;
        import  com.example.appcheckinbyqrcode.ui.admin.fragment.AdminUserFragment;
        import com.example.appcheckinbyqrcode.ui.login.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminUserFragment extends Fragment {
    private Button BtnChangePass, BtnChangePassDialog,BtnLogOut;
    private EditText oldPass, newPass;
    private AlertDialog dialog;
    private TextView tv_mess;
    private View view;

    public AdminUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_admin, container, false);
        BtnChangePass = view.findViewById(R.id.btnLogin);
        BtnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        BtnLogOut = view.findViewById(R.id.btnLogout);


        BtnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Logout thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // hủy trong stack
                startActivity(intent);
                SessionManager.getInstance().setKeySaveToken("");
                SessionManager.getInstance().setKeySaveCheck(true);
                SessionManager.getInstance().setKeyLogin(false);
//                finish();
                getActivity().finish();
            }
        });


        return view;

    }

    private void showDialog() {
        //hien dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.dialog_changepass,null);
        oldPass = view.findViewById(R.id.inputOldPass);
        newPass = view.findViewById(R.id.inputNewPass);
        BtnChangePassDialog = view.findViewById(R.id.btnChangePassDl);
        tv_mess = view.findViewById(R.id.tvMess);
        tv_mess.setVisibility(View.GONE);
        builder.setView(view);
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog =builder.create();
        dialog.show();
        BtnChangePassDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String oldPassStr = oldPass.getText().toString();
                final String newPassStr = newPass.getText().toString();
                BtnChangePassDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (oldPassStr.isEmpty() || newPassStr.isEmpty()){
                            tv_mess.setVisibility(View.VISIBLE);
                            tv_mess.setText("Vui lòng điền đầy đủ");
                        }else {
                            if (oldPassStr == newPassStr){
                                tv_mess.setText("Trùng mật khẩu");
                            }else {
                                tv_mess.setText("Đổi mật khẩu Okie");
                            }
                        }

                    }
                });

            }
        });
    }

    public static class HistoryCheckInFragment extends Fragment {

        public HistoryCheckInFragment() {
            // Required empty public constructor
        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_user_admin, container, false);
            return view;
        }
    }

}
