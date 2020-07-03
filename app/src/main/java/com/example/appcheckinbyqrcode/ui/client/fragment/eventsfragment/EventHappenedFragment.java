package com.example.appcheckinbyqrcode.ui.client.fragment.eventsfragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.network.ApiClient;
import com.example.appcheckinbyqrcode.network.response.EventListResponse;
import com.example.appcheckinbyqrcode.ui.client.adapter.fragmentevenstadapter.EventAdapterHappened;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventHappenedFragment extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView mRCycMs;
    private static final int NUM_COLUMNS = 2;
    private EventAdapterHappened adapter;
    private List<EventListResponse> data;
    private View view;
    TextView tvthongbao, txtEvent3;
    private ArrayList<EventListResponse> arrayList = new ArrayList<>();

    public EventHappenedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_event_happened, container, false);
        InitWidget(view);
        getdata();
        return view;
    }
    private void getdata() {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("please wait...");
        dialog.show();
        ApiClient.getService().listleventhappened().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<EventListResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<EventListResponse> eventListResponses) {
                        if (eventListResponses.toString().equals("[]")) {
                            mRCycMs.setVisibility(View.GONE);
                            txtEvent3.setVisibility(View.GONE);
                            tvthongbao.setVisibility(View.VISIBLE);
                        } else {
                            txtEvent3.setVisibility(View.VISIBLE);
                            arrayList = (ArrayList<EventListResponse>) eventListResponses;
                            mRCycMs.setLayoutManager(new LinearLayoutManager(getActivity()));
                            adapter = new EventAdapterHappened( eventListResponses, getActivity());
                            mRCycMs.setAdapter(adapter);
                            dialog.dismiss();

                        }
                        //Log.d("nnn", "onNext: " + data.get(0).getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("nnn", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        //adapter.notifyDataSetChanged();
                    }
                });
    }

    private void InitWidget(View view) {
        mRCycMs = view.findViewById(R.id.recyclerviewEventUpComing);
        tvthongbao = view.findViewById(R.id.tvthongbaoEvent1);
        txtEvent3 = view.findViewById(R.id.txtEvent3);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutEvent1);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdata();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
