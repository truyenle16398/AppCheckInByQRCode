package com.example.appcheckinbyqrcode.client.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.client.adapter.EventAdapter;
import com.example.appcheckinbyqrcode.client.model.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

//    @BindView(R.id.recyclerviewEvent)
    RecyclerView mRCycMs;

    private static final int NUM_COLUMNS = 2;
    private EventAdapter adapter;
    private List<Event> data;
    private View view;
    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_event, container, false);
        mRCycMs = view.findViewById(R.id.recyclerviewEvent);

        data = new ArrayList<>();
        data.add(new Event("Kiet's BirthDay Party at home","Pool Party and Live Music","19-05-2020","08:00 đến 17:00","41 Le Duan, Hai Chau, DN","https://xinhevent.com/wp-content/uploads/2018/08/b%C3%A0n-trang-tr%C3%AD-sinh-nh%E1%BA%ADt-tr%E1%BB%8Dn-g%C3%B3i-01-1200x800.jpg"));
        data.add(new Event("Kiet's BirthDay Party at home","Pool Party and Live Music","19-05-2020","08:00 đến 17:00","41 Le Duan, Hai Chau, DN","https://xinhevent.com/wp-content/uploads/2018/08/b%C3%A0n-trang-tr%C3%AD-sinh-nh%E1%BA%ADt-tr%E1%BB%8Dn-g%C3%B3i-01-1200x800.jpg"));
        data.add(new Event("Kiet's BirthDay Party at home","Pool Party and Live Music","19-05-2020","08:00 đến 17:00","41 Le Duan, Hai Chau, DN","https://xinhevent.com/wp-content/uploads/2018/08/b%C3%A0n-trang-tr%C3%AD-sinh-nh%E1%BA%ADt-tr%E1%BB%8Dn-g%C3%B3i-01-1200x800.jpg"));
        data.add(new Event("Kiet's BirthDay Party at home","Pool Party and Live Music","19-05-2020","08:00 đến 17:00","41 Le Duan, Hai Chau, DN","https://xinhevent.com/wp-content/uploads/2018/08/b%C3%A0n-trang-tr%C3%AD-sinh-nh%E1%BA%ADt-tr%E1%BB%8Dn-g%C3%B3i-01-1200x800.jpg"));
        data.add(new Event("Kiet's BirthDay Party at home","Pool Party and Live Music","19-05-2020","08:00 đến 17:00","41 Le Duan, Hai Chau, DN","https://xinhevent.com/wp-content/uploads/2018/08/b%C3%A0n-trang-tr%C3%AD-sinh-nh%E1%BA%ADt-tr%E1%BB%8Dn-g%C3%B3i-01-1200x800.jpg"));
        data.add(new Event("Kiet's BirthDay Party at home","Pool Party and Live Music","19-05-2020","08:00 đến 17:00","41 Le Duan, Hai Chau, DN","https://xinhevent.com/wp-content/uploads/2018/08/b%C3%A0n-trang-tr%C3%AD-sinh-nh%E1%BA%ADt-tr%E1%BB%8Dn-g%C3%B3i-01-1200x800.jpg"));
        data.add(new Event("Kiet's BirthDay Party at home","Pool Party and Live Music","19-05-2020","08:00 đến 17:00","41 Le Duan, Hai Chau, DN","https://xinhevent.com/wp-content/uploads/2018/08/b%C3%A0n-trang-tr%C3%AD-sinh-nh%E1%BA%ADt-tr%E1%BB%8Dn-g%C3%B3i-01-1200x800.jpg"));
        data.add(new Event("Kiet's BirthDay Party at home","Pool Party and Live Music","19-05-2020","08:00 đến 17:00","41 Le Duan, Hai Chau, DN","https://zstudio.vn/wp-content/uploads/2017/12/chup-anh-su-kien-chat-luong.jpg"));
        adapter = new EventAdapter(data,getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRCycMs.setLayoutManager(linearLayoutManager);
        mRCycMs.setAdapter(adapter);
        return view;
    }
}
