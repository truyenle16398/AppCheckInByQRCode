package com.example.appcheckinbyqrcode.ui.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.appcheckinbyqrcode.R;
import com.example.appcheckinbyqrcode.ui.client.adapter.ClientPagerAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class HomeClientActivity extends AppCompatActivity implements OnIntent {
    boolean doubleBackToExitPressedOnce = false;
    DrawerLayout drawerLayout;
    ViewPager pager;
    TabLayout mTabLayout;
    TabItem firstItem, secondItem, thirdItem;
    ClientPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_client);
        pager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tablayout);

        firstItem = findViewById(R.id.firstItem);
        secondItem = findViewById(R.id.secondItem);
        thirdItem = findViewById(R.id.thirdItem);

        drawerLayout = findViewById(R.id.drawer);


        adapter = new ClientPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mTabLayout.getTabCount());
        mTabLayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(android.R.color.holo_purple), PorterDuff.Mode.SRC_IN);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
//                switch (tab.getPosition()){
//                    case 0:
//                        mTabLayout.getTabAt(tab.getPosition()).setIcon(getResources().getDrawable(R.drawable.ic_call_to_action_black_24dp));
//                        mTabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.ic_event_available_while_24dp));
//                        mTabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.ic_person_white_24dp));
//                        break;
//                    case 1:
//                        mTabLayout.getTabAt(tab.getPosition()).setIcon(getResources().getDrawable(R.drawable.ic_arrow_forward_black_24dp));
//                        break;
//                    case 2:
//                        mTabLayout.getTabAt(tab.getPosition()).setIcon(getResources().getDrawable(R.drawable.ic_chevron_right_black_24dp));
//                        break;
//                }
                //mTabLayout.getTabAt(tab.getPosition()).setIcon(getResources().getDrawable(R.drawable.ic_call_to_action_black_24dp));
                mTabLayout.getTabAt(tab.getPosition()).getIcon().setColorFilter(getResources().getColor(android.R.color.holo_purple), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //mTabLayout.getTabAt(tab.getPosition()).setIcon(getResources().getDrawable(R.drawable.ic_person_white_24dp));
                mTabLayout.getTabAt(tab.getPosition()).getIcon().setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_IN);
//                switch (tab.getPosition()){
//                    case 0:
//                        mTabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.ic_home_whitte_24dp));
//                        mTabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.ic_person_white_24dp));
//                        break;
//                    case 1:
//                        mTabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.ic_home_whitte_24dp));
//                        mTabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.ic_person_white_24dp));
//                        break;
//                    case 2:
//                        mTabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.ic_home_whitte_24dp));
//                        mTabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.ic_event_available_while_24dp));
//                        break;
//                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(adapter);

//        mTabLayout.getTabAt(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
//        mTabLayout.getTabAt(1).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
//        mTabLayout.getTabAt(2).getIcon().setColorFilter(Color .WHITE, PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void intents() {
        pager.setCurrentItem(1);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
//            Toast.makeText(this, "thoát rrrrrrrrrrr", Toast.LENGTH_SHORT).show();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Chạm lần nữa để thoát!", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
