package com.example.appcheckinbyqrcode.ui.client.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.appcheckinbyqrcode.ui.client.fragment.eventsfragment.EventGoingOnFragment;
import com.example.appcheckinbyqrcode.ui.client.fragment.eventsfragment.EventHappenedFragment;
import com.example.appcheckinbyqrcode.ui.client.fragment.eventsfragment.EventGoingOnHappenFragment;

public class EventsClientPagerAdapter extends FragmentPagerAdapter {
    private int tabsNumber;

    public EventsClientPagerAdapter(@NonNull FragmentManager fm, int behavior, int tabs) {
        super(fm, behavior);
        this.tabsNumber = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new EventGoingOnFragment();
            case 1:
                return new EventGoingOnHappenFragment();
            case 2:
                return new EventHappenedFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabsNumber;
    }
}