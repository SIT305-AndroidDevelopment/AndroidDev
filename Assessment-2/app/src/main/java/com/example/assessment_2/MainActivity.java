package com.example.assessment_2;

//import android.app.Activity;
//import android.graphics.BitmapFactory;
//import android.graphics.Matrix;
//import android.os.Bundle;
//import android.util.DisplayMetrics;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.TranslateAnimation;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.viewpager.widget.PagerAdapter;
//import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private ViewPager viewPage;
    private ArrayList arrayList;
    private View view_video, view_brand, view_map, view_login;

    @Override
    protected void onCreate(Bundle savedInstanceStat) {
        super.onCreate(savedInstanceStat);
        setContentView(R.layout.activity_main);
        viewPage = findViewById(R.id.viewPager);
        LayoutInflater layoutInflater = getLayoutInflater();
        view_video = layoutInflater.inflate(R.layout.page_video,null);
        view_brand = layoutInflater.inflate(R.layout.page_brand,null);
        view_map = layoutInflater.inflate(R.layout.page_map,null);
        view_login = layoutInflater.inflate(R.layout.page_login,null);
        List<View> viewList = new ArrayList<View>();
        viewList.add(view_video);
        viewList.add(view_brand);
        viewList.add(view_map);
        viewList.add(view_login);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(viewList);
        viewPage.setAdapter(myPagerAdapter);
    }
}