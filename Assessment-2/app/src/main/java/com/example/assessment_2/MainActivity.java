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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPage;
    private ArrayList arrayList;
    private View view_video, view_brand, view_map, view_login;

    @Override
    protected void onCreate(Bundle savedInstanceStat) {
        super.onCreate(savedInstanceStat);
        setContentView(R.layout.activity_main);
        viewPage = findViewById(R.id.viewPager);

        LayoutInflater layoutInflater = getLayoutInflater();
        view_video = layoutInflater.inflate(R.layout.page_video, null);
        view_brand = layoutInflater.inflate(R.layout.page_brand, null);
        view_map = layoutInflater.inflate(R.layout.page_map, null);
        view_login = layoutInflater.inflate(R.layout.page_login, null);

        List<View> viewList = new ArrayList<View>();
        viewList.add(view_video);
        viewList.add(view_brand);
        viewList.add(view_map);
        viewList.add(view_login);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(viewList);

        //set viewpager adapter
        viewPage.setAdapter(myPagerAdapter);

        RadioGroup mainBtnContainer = (RadioGroup) findViewById(R.id.rg_main_button);

        RadioButton btn_video = (RadioButton) findViewById(R.id.rb_video);
        RadioButton btn_brand = (RadioButton) findViewById(R.id.rb_brand);
        RadioButton btn_map = (RadioButton) findViewById(R.id.rb_map);
        RadioButton btn_login = (RadioButton) findViewById(R.id.rb_login);
    }

    private void initView() {
        //通过findViewById()来找到我们需要的RadioGroup
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg_main_button);
        //设置状态改变的事件
        radioGroup.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) this);

        viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            /**
             * 重写的状态改变的事件的方法
             *
             * @param //group     单选组合框
             * @param //checkedId 其中的每个RadioButton的Id
             */

            //界面改变监听
            //界面改变监听
            //当前选中页面
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        viewPage.check(R.id.rb_video);
                        break;
                    case 1:
                        viewPage.check(R.id.rb_brand);
                        break;
                    case 2:
                        viewPage.check(R.id.rb_map);
                        break;
                    case 3:
                        viewPage.check(R.id.rb_login);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}