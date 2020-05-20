package com.example.appcheckinbyqrcode.admin.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.admin.adapter.ItemHistoryAdapter;
import com.example.appcheckinbyqrcode.admin.model.HistoryCheckIn;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminUserFragment extends Fragment {

    public AdminUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    public static class HistoryCheckInFragment extends Fragment {
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
    //        items.add("First CardView Item");
    //        items.add("Second CardView Item");
    //        items.add("Third CardView Item");
    //        items.add("Fourth CardView Item");
    //        items.add("Fifth CardView Item");
    //        items.add("Sixth CardView Item");
            items.add(new HistoryCheckIn(" Truyen Le Huy ","https://genk.mediacdn.vn/thumb_w/640/2019/10/16/pixel-image-1-15711948680921149315497-crop-15711968629591144940646.jpg","12:00"));
            items.add(new HistoryCheckIn(" Long Duc Nguyen ","https://interactive-examples.mdn.mozilla.net/media/examples/grapefruit-slice-332-332.jpg","1:00"));
            items.add(new HistoryCheckIn(" Truyen Le Huy ","https://assets.jpegmini.com/user/images/slider_puffin_jpegmini_mobile.jpg","13:00"));
            items.add(new HistoryCheckIn(" Long Duc Nguyen ","https://interactive-examples.mdn.mozilla.net/media/examples/grapefruit-slice-332-332.jpg","2:00"));
            items.add(new HistoryCheckIn(" Truyen Le Huy ","https://genk.mediacdn.vn/thumb_w/640/2019/10/16/pixel-image-1-15711948680921149315497-crop-15711968629591144940646.jpg","14:00"));
            items.add(new HistoryCheckIn(" Long Duc Nguyen ","https://assets.jpegmini.com/user/images/slider_puffin_jpegmini_mobile.jpg","3:00"));
            items.add(new HistoryCheckIn(" Truyen Le Huy ","https://genk.mediacdn.vn/thumb_w/640/2019/10/16/pixel-image-1-15711948680921149315497-crop-15711968629591144940646.jpg","15:00"));
            items.add(new HistoryCheckIn(" Long Duc Nguyen ","https://interactive-examples.mdn.mozilla.net/media/examples/grapefruit-slice-332-332.jpg","4:00"));
            items.add(new HistoryCheckIn(" Truyen Le Huy ","https://assets.jpegmini.com/user/images/slider_puffin_jpegmini_mobile.jpg","16:00"));
            items.add(new HistoryCheckIn(" Long Duc Nguyen ","https://interactive-examples.mdn.mozilla.net/media/examples/grapefruit-slice-332-332.jpg","5:00"));



            recyclerView = view.findViewById(R.id.recycle_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new ItemHistoryAdapter(getActivity(),items);
            recyclerView.setAdapter(adapter);

            return view;
        }
    }
}
