package com.example.appcheckinbyqrcode.ui.client.fragment.eventsfragment;

import android.animation.ArgbEvaluator;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.network.ApiClient;
import com.example.appcheckinbyqrcode.network.response.EventListResponse;
import com.example.appcheckinbyqrcode.sqlite.MyDatabaseHelper;
import com.example.appcheckinbyqrcode.ui.admin.model.FavoriteList;
import com.example.appcheckinbyqrcode.ui.admin.model.InfoQR;
import com.example.appcheckinbyqrcode.ui.admin.model.Model;
import com.example.appcheckinbyqrcode.ui.client.adapter.fragmentevenstadapter.Adapter;
import com.example.appcheckinbyqrcode.ui.client.adapter.fragmentevenstadapter.AdapterViewPagerFavorite;
import com.example.appcheckinbyqrcode.ui.client.adapter.fragmentevenstadapter.EventAdapterGoingOn;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventGoingOnFragment extends Fragment {
    ViewPager viewPager;
    AdapterViewPagerFavorite adapterViewPagerFavorite;
    ArrayList<FavoriteList> favoriteLists;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    Adapter adapterview;
    List<Model> models;


    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView mRCycMs;
    private static final int NUM_COLUMNS = 2;
    private EventAdapterGoingOn adapter;
    private List<EventListResponse> data;
    private View view;
    TextView tvthongbao;
    private ArrayList<EventListResponse> arrayList = new ArrayList<>();
    private MyDatabaseHelper myDatabaseHelper;


    public EventGoingOnFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event_going_on, container, false);
        InitWidget(view);
        getdata();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        favoriteLists = new ArrayList<>();
//        favoriteLists.add(new FavoriteList(1,123,"Huan Rose","huan giai online","Huan Rose",R.drawable.brochure));
//        favoriteLists.add(new FavoriteList(2,123,"Huan Rose","huan giai online","Huan Rose",R.drawable.sticker));
//        favoriteLists.add(new FavoriteList(3,123,"Huan Rose","huan giai online","Huan Rose",R.drawable.poster));
//        favoriteLists.add(new FavoriteList(4,123,"Huan Rose","huan giai online","Huan Rose",R.drawable.namecard));

        myDatabaseHelper = new MyDatabaseHelper(getContext());
        favoriteLists = (ArrayList<FavoriteList>) myDatabaseHelper.getAllFavo();
        adapterViewPagerFavorite = new AdapterViewPagerFavorite(favoriteLists, getContext());
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapterViewPagerFavorite);
        viewPager.setPadding(50, 0, 50, 0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3)
        };
//        colors_temp = favoriteLists
        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (adapterViewPagerFavorite.getCount() -1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );

                    mRCycMs.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );



                }

                else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getdata() {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("please wait...");
        dialog.show();
        ApiClient.getService().listleventgoingon().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<EventListResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<EventListResponse> eventListResponses) {
                        if (eventListResponses.toString().equals("[]")) {
                            mRCycMs.setVisibility(View.GONE);
                            tvthongbao.setVisibility(View.VISIBLE);
                        } else {
                             arrayList = (ArrayList<EventListResponse>) eventListResponses;
                            mRCycMs.setLayoutManager(new LinearLayoutManager(getActivity()));
                            adapter = new EventAdapterGoingOn( eventListResponses, getActivity());
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
//                        adapter.notifyDataSetChanged();
                    }});
    }

    private void InitWidget(View view) {
        mRCycMs = view.findViewById(R.id.recyclerviewEventOnGoing);
        tvthongbao = view.findViewById(R.id.tvthongbaoEvent);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutEvent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdata();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
