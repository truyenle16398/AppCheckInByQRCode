package com.example.appcheckinbyqrcode.ui.admin.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.sqlite.MyDatabaseHelper;
import com.example.appcheckinbyqrcode.ui.admin.ScanCodeActivity;
import com.example.appcheckinbyqrcode.ui.admin.model.InfoQR;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScannerFragment extends Fragment {
    public static TextView resulttextview;
    Button scanbutton;
    private MyDatabaseHelper myDatabaseHelper;

    public ScannerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scanner, container, false);
        myDatabaseHelper = new MyDatabaseHelper(getActivity());
        resulttextview = view.findViewById(R.id.barcodetextview);
        scanbutton = view.findViewById(R.id.buttonscan);
        scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                myDatabaseHelper.addInfo(new InfoQR("aaa","aaa","aaa","aaaa","aaaa"));
//                myDatabaseHelper.addInfo(new InfoQR("aaa","aaa","aaa","aaaa","aaaa"));
//                myDatabaseHelper.addInfo(new InfoQR("aaa","aaa","aaa","aaaa","aaaa"));
//                myDatabaseHelper.addInfo(new InfoQR("aaa","aaa","aaa","aaaa","aaaa"));
//                startActivity(new Intent(getActivity(), ScanCodeActivity.class));
            }
        });

        return view;

    }
}
