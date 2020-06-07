package com.example.assessment_2.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Adapter for place FragmentList to a TabLayout
 */
public class AdapterChannelPager extends FragmentStatePagerAdapter {
    /**
     * Name List
     */
    private List<String> tabNameList;

    /**
     * Fragment Container
     */
    private List<Fragment> tabFragmentList;


    public AdapterChannelPager(FragmentManager fm, List<Fragment> tabFragmentList, List<String> tabNameList) {
        super(fm);
        this.tabFragmentList = tabFragmentList;
        this.tabNameList = tabNameList;
    }

    @Override
    public Fragment getItem(int position) {
        return tabFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return tabFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabNameList.get(position);
    }
}
