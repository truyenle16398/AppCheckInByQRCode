package com.example.appcheckinbyqrcode.ui.client.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.ui.client.OnIntent;
import com.example.appcheckinbyqrcode.ui.client.adapter.EventsClientPagerAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment implements OnIntent {
    ViewPager pager;
    TabLayout mTabLayoutEvent;
    TabItem firstItem, secondItem, thirdItem;
    EventsClientPagerAdapter adapter;
    private View view;
    SearchView search_view;

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_event, container, false);
        InitWidget();

        adapter = new EventsClientPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mTabLayoutEvent.getTabCount());
        pager.setAdapter(adapter);

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
        return view;
    }




    private void InitWidget() {
        pager = view.findViewById(R.id.viewPagerEvent);
        mTabLayoutEvent = view.findViewById(R.id.tabLayoutEvent);

        firstItem = view.findViewById(R.id.firstItemEvent);
        secondItem = view.findViewById(R.id.secondItemEvent);
        thirdItem = view.findViewById(R.id.thirdItemEvent);
        search_view = view.findViewById(R.id.search_view);
    }

    @Override
    public void intents() {

    }
}
