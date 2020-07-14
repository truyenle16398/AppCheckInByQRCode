package com.example.appcheckinbyqrcode.ui.client.fragment;

import android.animation.ArgbEvaluator;
import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.appcheckinbyqrcode.CheckValidate;
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

    boolean doubleBackToExitPressedOnce = false;
    private MyDatabaseHelper myDatabaseHelper;
    EditText mEdtSearch;
    ViewPager pager;
    ImageView mImgSeacr;
    TabLayout mTabLayoutEvent;
    TabItem firstItem, secondItem, thirdItem;
    EventsClientPagerAdapter adapterPager;
    EventSearchViewAdapter adapterSearch;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<EventSearchListResponse> listSearch;
    private View view;
    EditText edtSearchView;

    public EventFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
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
        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    fetchSearch(s.toString());
                }

            }
        });
        mEdtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Log.d("focus", "focus loosed");
                    CheckValidate.hideKeyboard(v,getActivity());
                } else {
                    Log.d("focus", "focused");
                }
            }
        });
        mImgSeacr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdtSearch.setFocusableInTouchMode(true);
                mEdtSearch.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mEdtSearch, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myDatabaseHelper = new MyDatabaseHelper(getContext());
        favoriteLists = (ArrayList<FavoriteList>) myDatabaseHelper.getAllFavo();
//        edtSearchView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                recyclerView.setVisibility(View.GONE);
//                filter((String) s);
//            }
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//            @Override
//            public void afterTextChanged(Editable s) {
//                recyclerView.setVisibility(View.VISIBLE);
//                filter(String.valueOf(s));
//                Log.d(TAG, "afterTextChanged: "+ s.toString());
//            }
//        });

    }

    private void filter(String text) {
        if (text.isEmpty()) {
            Toast.makeText(getContext(), "No data search", Toast.LENGTH_SHORT).show();
        } else {
            for (EventSearchListResponse item : listSearch) {
                if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                    fetchSearch(text);
                }
            }
            adapterSearch.filterList(listSearch);
            Log.d(TAG, "filter: " + text.toString());
        }

    }

    public void fetchSearch(String key) {
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
                        Log.d(TAG, "onNext: " + listResponses.toString());
                    }


                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        adapterSearch.notifyDataSetChanged();
                    }
                });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu, inflater);

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
                if (query.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    fetchSearch(query);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //Toast.makeText(getActivity(), "yyy" + newText, Toast.LENGTH_SHORT).show();

                if (newText.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    fetchSearch(newText);
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


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initWidget() {
        pager = view.findViewById(R.id.viewPagerEvent);
        mImgSeacr = view.findViewById(R.id.mImgSeacr);
        mEdtSearch = view.findViewById(R.id.mEdtSearch);
        mTabLayoutEvent = view.findViewById(R.id.tabLayoutEvent);
        firstItem = view.findViewById(R.id.firstItemEvent);
        secondItem = view.findViewById(R.id.secondItemEvent);
        thirdItem = view.findViewById(R.id.thirdItemEvent);
        recyclerView = view.findViewById(R.id.recycleViewSearch);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        fetchSearch("");
    }
}
