package com.example.assessment_2;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private TextView txt_title;

    // 底部菜单4个Linearlayout
    private LinearLayout ll_home;
    private LinearLayout ll_address;
    private LinearLayout ll_friend;
    private LinearLayout ll_setting;

    // 底部菜单4个ImageView
    private ImageView img_home;
    private ImageView img_news;
    private ImageView img_friend;
    private ImageView img_user;

    // 底部菜单4个菜单标题
    private TextView txt_home;
    private TextView txt_news;
    private TextView txt_friend;
    private TextView txt_user;

    // 中间内容区域
    private ViewPager viewPager;

    // ViewPager适配器ContentAdapter
    private ContentAdapter adapter;
    private List<View> views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();// 初始化控件
        initEvent();// 设置按钮监听

        //txt_title.setText(R.string.txt_nav_home);
        txt_home.setTextColor(0xff1B940A);
    }

    private void initEvent() {
        ll_home.setOnClickListener(this);
        ll_address.setOnClickListener(this);
        ll_friend.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
        viewPager.setOnPageChangeListener(this);
    }

    private void initView() {
        txt_title = findViewById(R.id.title);

        // 底部菜单4个Linearlayout
        ll_home = findViewById(R.id.ll_home);
        ll_address = findViewById(R.id.ll_brand);
        ll_friend = findViewById(R.id.ll_map);
        ll_setting = findViewById(R.id.ll_user);

        // 底部菜单4个ImageView
        img_home = findViewById(R.id.img_home);
        img_news = findViewById(R.id.img_brand);
        img_friend = findViewById(R.id.img_map);
        img_user = findViewById(R.id.img_user);

        // 底部菜单4个菜单标题
        txt_home = findViewById(R.id.txt_home);
        txt_news = findViewById(R.id.txt_brand);
        txt_friend = findViewById(R.id.txt_map);
        txt_user = findViewById(R.id.txt_user);

        // 中间内容区域ViewPager
        viewPager = findViewById(R.id.vp_content);

        // 适配器
        View page_home = View.inflate(MainActivity.this, R.layout.page_home, null);
        View page_news = View.inflate(MainActivity.this, R.layout.page_brand, null);
        View page_friend = View.inflate(MainActivity.this, R.layout.page_map, null);
        View page_user = View.inflate(MainActivity.this, R.layout.page_user, null);

        views = new ArrayList<View>();
        views.add(page_home);
        views.add(page_news);
        views.add(page_friend);
        views.add(page_user);
        adapter = new ContentAdapter(views);
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home:
                restartBotton();
                //txt_title.setText(R.string.txt_nav_home);
                txt_home.setTextColor(0xff1B940A);
                viewPager.setCurrentItem(0);
                break;
            case R.id.ll_brand:
                restartBotton();
                //txt_title.setText(R.string.txt_nav_news);
                txt_news.setTextColor(0xff1B940A);
                viewPager.setCurrentItem(1);
                break;
            case R.id.ll_map:
                restartBotton();
                //txt_title.setText(R.string.txt_nav_friend);
                txt_friend.setTextColor(0xff1B940A);
                viewPager.setCurrentItem(2);
                break;
            case R.id.ll_user:
                restartBotton();
                //txt_title.setText(R.string.txt_nav_user);
                txt_user.setTextColor(0xff1B940A);
                viewPager.setCurrentItem(3);
                break;

            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        restartBotton();
        switch (position) {
            case 0:
                //txt_title.setText(R.string.txt_nav_home);
                txt_home.setTextColor(0xff1B940A);
                break;
            case 1:
                //txt_title.setText(R.string.txt_nav_news);
                txt_news.setTextColor(0xff1B940A);
                break;
            case 2:
                //txt_title.setText(R.string.txt_nav_friend);
                txt_friend.setTextColor(0xff1B940A);
                break;
            case 3:
                //txt_title.setText(R.string.txt_nav_user);
                txt_user.setTextColor(0xff1B940A);
                break;

            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //重置链接样式
    private void restartBotton() {
        txt_home.setTextColor(Color.parseColor("#888888"));
        txt_news.setTextColor(Color.parseColor("#888888"));
        txt_friend.setTextColor(Color.parseColor("#888888"));
        txt_user.setTextColor(Color.parseColor("#888888"));
    }
}