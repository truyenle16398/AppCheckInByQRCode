package com.example.appcheckinbyqrcode.admin.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.admin.adapter.ItemHistoryAdapter;
import com.example.appcheckinbyqrcode.admin.model.HistoryCheckIn;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryCheckInFragment extends Fragment {

        RecyclerView recyclerView;
        ItemHistoryAdapter adapter;
        ArrayList<HistoryCheckIn> items;

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
            View view = inflater.inflate(R.layout.fragment_history_check_in, container, false);
            items = new ArrayList<>();
            items.add(new HistoryCheckIn(" Truyen Le Huy ","https://assets.jpegmini.com/user/images/slider_puffin_jpegmini_mobile.jpg","Truyen Sexy Dancer","7:00"));
            items.add(new HistoryCheckIn(" Long Duc Nguyen ","https://interactive-examples.mdn.mozilla.net/media/examples/grapefruit-slice-332-332.jpg","THĂM NGÀN KẸP NGẦN THEO PHONG CÁCH Truyen Le","13:00"));
            items.add(new HistoryCheckIn(" Truyen Le Huy ","https://assets.jpegmini.com/user/images/slider_puffin_jpegmini_mobile.jpg","Truyen Class Learning MakeUp","8:00"));
            items.add(new HistoryCheckIn(" Long Duc Nguyen ","https://interactive-examples.mdn.mozilla.net/media/examples/grapefruit-slice-332-332.jpg","TalkShow to truyen Le Business ","8:00"));
            items.add(new HistoryCheckIn(" Truyen Le Huy ","https://assets.jpegmini.com/user/images/slider_puffin_jpegmini_mobile.jpg","Truyen Sexy Dancer","6:00"));
            items.add(new HistoryCheckIn(" Long Duc Nguyen ","https://interactive-examples.mdn.mozilla.net/media/examples/grapefruit-slice-332-332.jpg","Sing Truyen Le Song","15:00"));
            items.add(new HistoryCheckIn(" Truyen Le Huy ","https://assets.jpegmini.com/user/images/slider_puffin_jpegmini_mobile.jpg","THĂM NGÀN KẸP NGẦN THEO PHONG CÁCH Truyen Le","8:00"));
            items.add(new HistoryCheckIn(" Long Duc Nguyen ","https://interactive-examples.mdn.mozilla.net/media/examples/grapefruit-slice-332-332.jpg","TalkShow to truyen Le Love and Girl ","9:00"));




            recyclerView = view.findViewById(R.id.recycle_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new ItemHistoryAdapter(getActivity(),items);
            recyclerView.setAdapter(adapter);

            return view;
        }

}
