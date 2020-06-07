package com.example.assessment_2.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.assessment_2.R;
import com.example.assessment_2.adapter.AdapterChannelPager;
import com.example.assessment_2.constant.AppConstant;
import com.example.assessment_2.fragments.HomeChannelFragment;
import com.example.assessment_2.util.GlideImageLoaderHomeBanner;
import com.google.android.material.tabs.TabLayout;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    View view;
    TextView title;
    TabLayout tabLayout;
//    private List<String> bannerPicList = new ArrayList();
    private List<Integer> bannerPicList = new ArrayList();
    private List<String> tabList = new ArrayList<>();
    private ViewPager viewPager;
    private AdapterChannelPager adapterChannelPager;

    /**
     * init tablist
     */
    {
        tabList.add(AppConstant.BRAND_HONDA);
        tabList.add(AppConstant.BRAND_YAMAHA);
        tabList.add(AppConstant.BRAND_SUZUKI);
        tabList.add(AppConstant.BRAND_KAWASAKI);
    }

    private Banner banner;

    /**
     * Fragment
     */
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.page_home, container, false);
        banner = view.findViewById(R.id.banner);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        initBanner();
        initTabLayout();
        initViewPager();
        return view;
    }

    private void initBanner() {
        bannerPicList.add(R.drawable.banner1);
        bannerPicList.add(R.drawable.banner2);
        bannerPicList.add(R.drawable.banner3);
        bannerPicList.add(R.drawable.banner4);

        banner.setImageLoader(new GlideImageLoaderHomeBanner());
        banner.setImages(bannerPicList);
        banner.start();
    }

    private void initTabLayout() {
        for (int i = 0; i < tabList.size(); i++) {
            String brandName = tabList.get(i);
            tabLayout.addTab(tabLayout.newTab().setText(brandName));
            fragmentList.add(new HomeChannelFragment(brandName));
        }
    }

    private void initViewPager() {
        FragmentManager childFragMan = getChildFragmentManager();
        childFragMan.beginTransaction();
        adapterChannelPager = new AdapterChannelPager(childFragMan, fragmentList, tabList);
        viewPager.setAdapter(adapterChannelPager);
        tabLayout.setupWithViewPager(viewPager);
    }
}
