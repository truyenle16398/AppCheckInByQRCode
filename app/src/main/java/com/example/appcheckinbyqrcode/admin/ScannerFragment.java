package com.example.appcheckinbyqrcode.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.appcheckinbyqrcode.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScannerFragment extends Fragment {
    public static TextView resulttextview;
    Button scanbutton;

    public ScannerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scanner, container, false);
        resulttextview = view.findViewById(R.id.barcodetextview);
        scanbutton = view.findViewById(R.id.buttonscan);
        scanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ScanCodeActivity.class));
            }
        });

        return view;

    }
}
