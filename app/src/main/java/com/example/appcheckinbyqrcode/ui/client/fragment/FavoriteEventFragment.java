package com.example.appcheckinbyqrcode.ui.client.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.network.ApiClient;
import com.example.appcheckinbyqrcode.network.response.EventFavoriteResponse;
import com.example.appcheckinbyqrcode.ui.client.adapter.FavoriteAdapter;
import com.example.appcheckinbyqrcode.ui.client.model.Favorite;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
        InitWidget();
        getData();

// data.add(new Favorite("name event111111111111111111","day","person","aaaa","https://xinhevent.com/wp-content/uploads/2018/08/b%C3%A0n-trang-tr%C3%AD-sinh-nh%E1%BA%ADt-tr%E1%BB%8Dn-g%C3%B3i-01-1200x800.jpg"));
        return view;
    }

    private void getData() {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("please wait...");
        dialog.show();
        ApiClient.getService().listhistoryregis().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<EventFavoriteResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<EventFavoriteResponse> eventFavoriteResponses) {
                        Log.d("nnn", "onNext: " + eventFavoriteResponses.toString());
                        ArrayList<EventFavoriteResponse> arrayList = (ArrayList<EventFavoriteResponse>) eventFavoriteResponses;
// Log.d("nnn", "onNext: "+arrayList.get(0).getStatus());

                        adapter = new FavoriteAdapter(arrayList, getActivity());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                        mRCycMs.setLayoutManager(linearLayoutManager);
                        mRCycMs.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("nnn", "onError: " + e.getMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onComplete() {
                        dialog.dismiss();
                    }
                });
    }

    private void InitWidget() {
        mRCycMs = view.findViewById(R.id.recyclerviewFavorite);
    }
}