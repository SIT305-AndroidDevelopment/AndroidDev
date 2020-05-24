package com.example.assessment_2.UI;

import android.app.Application;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.example.assessment_2.R;

public class MapFragment extends Fragment {
    View view;
    TextView title;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.page_map, container, false);
        title = view.findViewById(R.id.txt_map);
        //title.setText("我是" + getResources().getString(R.string.txt_nav_home));
        return view;
    }

    public class Map extends Application {
        @Override
        public void onCreate() {
            super.onCreate();
            //在使用SDK各组件之前初始化context信息，传入ApplicationContext
            SDKInitializer.initialize(this);
            //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
            //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
            SDKInitializer.setCoordType(CoordType.BD09LL);
        }
    }

}
