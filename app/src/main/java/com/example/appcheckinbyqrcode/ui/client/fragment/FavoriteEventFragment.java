package com.example.appcheckinbyqrcode.ui.client.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.ui.client.adapter.FavoriteAdapter;
import com.example.appcheckinbyqrcode.ui.client.model.Favorite;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteEventFragment extends Fragment {

    RecyclerView mRCycMs;
    private FavoriteAdapter adapter;
    private List<Favorite> data;
    View view;
    public FavoriteEventFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorite_event, container, false);
        mRCycMs = view.findViewById(R.id.recyclerviewFavorite);
        data = new ArrayList<>();
        data.add(new Favorite("name event111111111111111111","day","person","aaaa","https://xinhevent.com/wp-content/uploads/2018/08/b%C3%A0n-trang-tr%C3%AD-sinh-nh%E1%BA%ADt-tr%E1%BB%8Dn-g%C3%B3i-01-1200x800.jpg"));
        data.add(new Favorite("name event eventname","day","person","aaaa","https://xinhevent.com/wp-content/uploads/2018/08/b%C3%A0n-trang-tr%C3%AD-sinh-nh%E1%BA%ADt-tr%E1%BB%8Dn-g%C3%B3i-01-1200x800.jpg"));
        data.add(new Favorite("name eventeven 11111111111111","day","person","aaaa","https://xinhevent.com/wp-content/uploads/2018/08/b%C3%A0n-trang-tr%C3%AD-sinh-nh%E1%BA%ADt-tr%E1%BB%8Dn-g%C3%B3i-01-1200x800.jpg"));
        data.add(new Favorite("name event111111111111111111","day","person","aaaa","https://xinhevent.com/wp-content/uploads/2018/08/b%C3%A0n-trang-tr%C3%AD-sinh-nh%E1%BA%ADt-tr%E1%BB%8Dn-g%C3%B3i-01-1200x800.jpg"));
        data.add(new Favorite("name event eventname","day","person","aaaa","https://xinhevent.com/wp-content/uploads/2018/08/b%C3%A0n-trang-tr%C3%AD-sinh-nh%E1%BA%ADt-tr%E1%BB%8Dn-g%C3%B3i-01-1200x800.jpg"));
        data.add(new Favorite("name eventeven 11111111111111","day","person","aaaa","https://xinhevent.com/wp-content/uploads/2018/08/b%C3%A0n-trang-tr%C3%AD-sinh-nh%E1%BA%ADt-tr%E1%BB%8Dn-g%C3%B3i-01-1200x800.jpg"));
        data.add(new Favorite("name event111111111111111111","day","person","aaaa","https://xinhevent.com/wp-content/uploads/2018/08/b%C3%A0n-trang-tr%C3%AD-sinh-nh%E1%BA%ADt-tr%E1%BB%8Dn-g%C3%B3i-01-1200x800.jpg"));
        data.add(new Favorite("name event eventname","day","person","aaaa","https://xinhevent.com/wp-content/uploads/2018/08/b%C3%A0n-trang-tr%C3%AD-sinh-nh%E1%BA%ADt-tr%E1%BB%8Dn-g%C3%B3i-01-1200x800.jpg"));
        data.add(new Favorite("name eventeven 11111111111111","day","person","aaaa","https://xinhevent.com/wp-content/uploads/2018/08/b%C3%A0n-trang-tr%C3%AD-sinh-nh%E1%BA%ADt-tr%E1%BB%8Dn-g%C3%B3i-01-1200x800.jpg"));

        //,"Pool Party and Live Music","19-05-2020","08:00 đến 17:00","41 Le Duan, Hai Chau, DN","https://xinhevent.com/wp-content/uploads/2018/08/b%C3%A0n-trang-tr%C3%AD-sinh-nh%E1%BA%ADt-tr%E1%BB%8Dn-g%C3%B3i-01-1200x800.jpg"

        adapter = new FavoriteAdapter(data,getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRCycMs.setLayoutManager(linearLayoutManager);
        mRCycMs.setAdapter(adapter);
        return view;
    }
}
