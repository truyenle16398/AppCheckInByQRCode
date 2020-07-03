package com.example.appcheckinbyqrcode.ui.client.fragment;

import android.animation.ArgbEvaluator;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.network.ApiClient;
import com.example.appcheckinbyqrcode.network.response.EventSearchListResponse;
import com.example.appcheckinbyqrcode.network.response.UploadAvatarResponse;
import com.example.appcheckinbyqrcode.sqlite.MyDatabaseHelper;
import com.example.appcheckinbyqrcode.ui.admin.model.FavoriteList;
import com.example.appcheckinbyqrcode.ui.client.EventDetailActivity;
import com.example.appcheckinbyqrcode.ui.client.OnIntent;
import com.example.appcheckinbyqrcode.ui.client.adapter.EventSearchViewAdapter;
import com.example.appcheckinbyqrcode.ui.client.adapter.EventsClientPagerAdapter;
import com.example.appcheckinbyqrcode.ui.client.adapter.fragmentevenstadapter.AdapterViewPagerFavorite;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {
    ArrayList<FavoriteList> favoriteLists;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private MyDatabaseHelper myDatabaseHelper;

    ViewPager pager;
    TabLayout mTabLayoutEvent;
    TabItem firstItem, secondItem, thirdItem;
    EventsClientPagerAdapter adapterPager;
    EventSearchViewAdapter adapterSearch;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<EventSearchListResponse> listResponses;
    private View view;
    Toolbar toolbar;
    TextView search;
    String[] item;

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event, container, false);
        initWidget();

        adapterPager = new EventsClientPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mTabLayoutEvent.getTabCount());
        pager.setAdapter(adapterPager);

        mTabLayoutEvent.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayoutEvent));
        pager.setOffscreenPageLimit(3);
        setHasOptionsMenu(true); // Add this!
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myDatabaseHelper = new MyDatabaseHelper(getContext());
        favoriteLists = (ArrayList<FavoriteList>) myDatabaseHelper.getAllFavo();


//        Integer[] colors_temp = {
//                getResources().getColor(R.color.color1),
//                getResources().getColor(R.color.color2),
//                getResources().getColor(R.color.color3)
//        };
////        colors_temp = favoriteLists
//        colors = colors_temp;
//        toolbar.setBackgroundResource(R.color.colorPrimaryDark);
//        mTabLayoutEvent.setBackgroundResource(R.color.colorPrimaryDark);
//        viewPagerFavo.setBackgroundResource(R.color.colorPrimaryDark);
//        pager.setBackgroundResource(R.color.colorPrimaryDark);


//         change background
//        viewPagerFavo.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                if (position < (adapterViewPagerFavorite.getCount() -1) && position < (colors.length - 1)) {
//                    viewPagerFavo.setBackgroundColor(
//
//                            (Integer) argbEvaluator.evaluate(
//                                    positionOffset,
//                                    colors[position],
//                                    colors[position + 1]
//                            )
//                    );
//                    viewPagerFavo.getBackground().setAlpha(100);
//                    toolbar.setBackgroundColor(
//
//                            (Integer) argbEvaluator.evaluate(
//                                    positionOffset,
//                                    colors[position],
//                                    colors[position + 1]
//                            )
//                    );
//                    toolbar.getBackground().setAlpha(100);
//                    mTabLayoutEvent.setBackgroundColor(
//
//                            (Integer) argbEvaluator.evaluate(
//                                    positionOffset,
//                                    colors[position],
//                                    colors[position + 1]
//                            )
//                    );
//                    mTabLayoutEvent.getBackground().setAlpha(100);
//                }
//
//                else {
//                    viewPagerFavo.setBackgroundColor(colors[colors.length - 1]);
//                }
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

    public void fetchSearch(String key){
        ApiClient.getService().getListSearch(key)
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<EventSearchListResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(List<EventSearchListResponse> listResponses) {
                        adapterSearch = new EventSearchViewAdapter(listResponses, getActivity());
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(adapterSearch);
                        adapterSearch.notifyDataSetChanged();
                    }


                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        adapterSearch.notifyDataSetChanged();
                    }});

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu,inflater);

//        MenuItem item = menu.findItem(R.id.search);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setQueryHint(getResources().getString(R.string.search_something_hint));

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                //Toast.makeText(getActivity(), "xxx" + query, Toast.LENGTH_SHORT).show();
                if (query.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                }else {
                    recyclerView.setVisibility(View.VISIBLE);
                    fetchSearch( query);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //Toast.makeText(getActivity(), "yyy" + newText, Toast.LENGTH_SHORT).show();

                if (newText.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                }else {
                    recyclerView.setVisibility(View.VISIBLE);
                    fetchSearch( newText);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
            case R.id.search:
//                changeInfo();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    private void initWidget() {
        pager = view.findViewById(R.id.viewPagerEvent);
        mTabLayoutEvent = view.findViewById(R.id.tabLayoutEvent);
        firstItem = view.findViewById(R.id.firstItemEvent);
        secondItem = view.findViewById(R.id.secondItemEvent);
        thirdItem = view.findViewById(R.id.thirdItemEvent);
        toolbar = view.findViewById(R.id.toolbarSearchView);
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setTitle("SDC");
        toolbar.setSubtitle(null);
        recyclerView = view.findViewById(R.id.recycleViewSearch);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        fetchSearch( "");
    }


}
