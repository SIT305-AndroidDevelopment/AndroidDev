package com.example.assessment_2;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.assessment_2.UI.BrandFragment;
import com.example.assessment_2.UI.HomeFragment;
import com.example.assessment_2.UI.MapFragment;
import com.example.assessment_2.UI.UserFragment;

public class MainActivity extends AppCompatActivity{
    /**
     * 主界面容器
     */
    public FrameLayout frameContainer;

    /**
     * 底部导航栏
     */
    public BottomNavigationBar bottomNavBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameContainer = findViewById(R.id.frame_container);
        bottomNavBar = findViewById(R.id.bottom_nav_bar);



        initTabBottom();
        initTabListener();
    }


    /**
     * 初始化底部导航栏
     */
    public void initTabBottom() {
        bottomNavBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavBar.setBarBackgroundColor(R.color.white);

        BottomNavigationItem bnbItemNotice = new BottomNavigationItem(R.drawable.btn_home, "Home").setInactiveIconResource(R.drawable.btn_home);
        BottomNavigationItem bnbItemStatistic = new BottomNavigationItem(R.drawable.btn_brand, "Brand").setInactiveIconResource(R.drawable.btn_brand);
        BottomNavigationItem bnbItemSchool = new BottomNavigationItem(R.drawable.btn_map, "Map").setInactiveIconResource(R.drawable.btn_map);
        BottomNavigationItem bnbItemMe = new BottomNavigationItem(R.drawable.btn_user, "User").setInactiveIconResource(R.drawable.btn_user);

        bottomNavBar.addItem(bnbItemNotice);
        bottomNavBar.addItem(bnbItemStatistic);
        bottomNavBar.addItem(bnbItemSchool);
        bottomNavBar.addItem(bnbItemMe);
        bottomNavBar.initialise();
    }


    /**
     * 监听导航栏
     */
    public void initTabListener() {
        bottomNavBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                switch (position) {
                    case 0:
                        transaction.replace(R.id.frame_container, new HomeFragment());
                        transaction.commit();
                        break;
                    case 1:
                        transaction.replace(R.id.frame_container, new BrandFragment());
                        transaction.commit();
                        break;
                    case 2:
                        transaction.replace(R.id.frame_container, new MapFragment());
                        transaction.commit();
                        break;
                    case 3:
                        transaction.replace(R.id.frame_container, new UserFragment());
                        transaction.commit();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

        bottomNavBar.selectTab(0);
    }
}