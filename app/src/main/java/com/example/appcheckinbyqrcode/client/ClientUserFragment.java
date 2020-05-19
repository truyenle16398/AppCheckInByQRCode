package com.example.appcheckinbyqrcode.client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appcheckinbyqrcode.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientUserFragment extends Fragment {

    public ClientUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_user, container, false);
    }
}
