package com.kelvinlee.appchallenge_java;

import androidx.annotation.ContentView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.kelvinlee.appchallenge_java.fragment.ContentViewFragment;
import com.kelvinlee.appchallenge_java.helper.APIHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String[] teamList = new String[] {"rangers","elastic","dynamo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        APIHelper.context = this;

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabLayout);

        mTabLayout.setSelectedTabIndicatorColor(Color.parseColor("red"));

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager, false);

        mTabLayout.addTab(mTabLayout.newTab());

        for(int i=0;i<teamList.length;i++){
            mTabLayout.getTabAt(i).setText(teamList[i]);
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for(int i=0;i<teamList.length;i++){
            adapter.add(teamList[i]);
        }
        viewPager.setAdapter(adapter);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragmentList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void add(String team) {
            Fragment fragment = ContentViewFragment.newInstance(team);
            mFragmentList.add(fragment);
        }
    }
}
