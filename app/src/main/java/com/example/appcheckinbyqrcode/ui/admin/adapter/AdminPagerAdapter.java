package com.example.appcheckinbyqrcode.ui.admin.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.appcheckinbyqrcode.ui.admin.fragment.AdminUserFragment;
import com.example.appcheckinbyqrcode.ui.admin.fragment.HistoryCheckInFragment;
import com.example.appcheckinbyqrcode.ui.admin.fragment.ScannerFragment;

public class AdminPagerAdapter extends FragmentPagerAdapter {
    private int tabsNumber;

    public AdminPagerAdapter(@NonNull FragmentManager fm, int behavior, int tabs) {
        super(fm, behavior);
        this.tabsNumber = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ScannerFragment();
            case 1:
                return new HistoryCheckInFragment();
            case 2:
                return new AdminUserFragment();

            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabsNumber;
    }
}
