package com.example.appcheckinbyqrcode.ui.client.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.SessionManager;
import com.example.appcheckinbyqrcode.ui.admin.HomeAdminActivity;
import com.example.appcheckinbyqrcode.ui.login.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientUserFragment extends Fragment {
    Button BtnChangePass,BtnLogOut;
    private View view;
    public ClientUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_client_user, container, false);
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
}
