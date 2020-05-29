package com.example.assessment_2.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * 用于TabLayout中放置FragmentList抽取的适配器
 */
public class CAdapterChannelPager extends FragmentStatePagerAdapter {
    /**
     * 顶部分类名称
     */
    private List<String> tabNameList;

    /**
     * Fragment容器
     */
    private List<Fragment> tabFragmentList;


    public CAdapterChannelPager(FragmentManager fm, List<Fragment> tabFragmentList, List<String> tabNameList) {
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
