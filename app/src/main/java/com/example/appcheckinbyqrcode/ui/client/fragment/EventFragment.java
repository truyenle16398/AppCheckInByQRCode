package com.example.appcheckinbyqrcode.ui.client.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.network.ApiClient;
import com.example.appcheckinbyqrcode.network.response.EventListResponse;
import com.example.appcheckinbyqrcode.ui.client.adapter.EventAdapter;
import com.example.appcheckinbyqrcode.ui.client.model.Event;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    RecyclerView mRCycMs;
    private static final int NUM_COLUMNS = 2;
    private EventAdapter adapter;
    private List<EventListResponse> data;
    private View view;

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event, container, false);
        InitWidget();
        data = new ArrayList<>();
        getdata();
        return view;
    }

    private void getdata() {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("please wait...");
        dialog.show();
        ApiClient.getService().listlevents().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<EventListResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<EventListResponse> eventListResponses) {
                        ArrayList<EventListResponse> arrayList = (ArrayList<EventListResponse>) eventListResponses;
////                        Log.d("nnn", "onNext: "+arrayList.get(0).getStatus());
                        adapter = new EventAdapter(arrayList, getActivity());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                        mRCycMs.setLayoutManager(linearLayoutManager);
                        mRCycMs.setAdapter(adapter);
                        dialog.dismiss();
                        Log.d("nnn", "onNext: " + arrayList.get(0).getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("nnn", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void InitWidget() {
        mRCycMs = view.findViewById(R.id.recyclerviewEvent);
        mRCycMs.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
