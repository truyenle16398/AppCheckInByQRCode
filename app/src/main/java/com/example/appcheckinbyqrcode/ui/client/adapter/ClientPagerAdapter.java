package com.example.appcheckinbyqrcode.ui.client.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.appcheckinbyqrcode.ui.client.fragment.ClientUserFragment;
import com.example.appcheckinbyqrcode.ui.client.fragment.EventFragment;
import com.example.appcheckinbyqrcode.ui.client.fragment.FavoriteEventFragment;


public class ClientPagerAdapter extends FragmentPagerAdapter {
    private int tabsNumber;

    public ClientPagerAdapter(@NonNull FragmentManager fm, int behavior, int tabs) {
        super(fm, behavior);
        this.tabsNumber = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                return new EventFragment();
            case 1:
                return new FavoriteEventFragment();
            case 2:
                return new ClientUserFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabsNumber;
    }
}