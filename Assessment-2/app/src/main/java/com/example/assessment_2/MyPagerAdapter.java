package com.example.assessment_2;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

class ContentAdapter extends PagerAdapter {

    private List<View> views;

    public ContentAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

}

//public class MyPagerAdapter extends PagerAdapter {
//
//    private List<View> viewList = new ArrayList<View>();
//
//    public MyPagerAdapter(List<View> viewList) {
//        this.viewList = viewList;
//    }
//
//    @NonNull
//    @Override
//    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        container.addView(viewList.get(position));
//        return viewList.get(position);
//    }
//
//    @Override
//    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        container.removeView(viewList.get(position));
//    }
//
//    @Override
//    public int getCount() {
//        return viewList.size();
//    }
//
//    @Override
//    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//        return view == object;
//    }
//}
