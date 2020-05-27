package com.example.assessment_2.UI;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.assessment_2.R;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {
    private MapView mMapView = null;
    private BaiduMap mBaiduMap;


    private ImageButton btn_loc;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private BDLocation myLocation;
    private double latitude;
    private double longitude;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_map, null);

        mMapView = view.findViewById(R.id.bmapView);
        btn_loc = view.findViewById(R.id.ib_loc);
        mBaiduMap = mMapView.getMap();

        btn_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设定中心点坐标
//                LatLng cenpt = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                LatLng cenpt = new LatLng(latitude, longitude);

                //定义地图状态
                MapStatus mMapStatus = new MapStatus.Builder()
                        //要移动的点
                        .target(cenpt)
                        //放大地图到20倍
                        .zoom(15)
                        .build();

                //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);

                //改变地图状态
                mBaiduMap.setMapStatus(mMapStatusUpdate);
            }
        });



        mBaiduMap.setMyLocationEnabled(true);

        //获得定位权限
        getPermissionMethod();
        initLocation();
        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initLocation() {
        //声明LocationClient类
        mLocationClient = new LocationClient(getActivity());
        //注册监听函数
        mLocationClient.registerLocationListener(myListener);

        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000000);

        //设置locationClientOption
        mLocationClient.setLocOption(option);

        //注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);

//
//        //开启前台定位服务：
//
//        Notification.Builder builder = new Notification.Builder (MainActivity.this.getApplicationContext());
////获取一个Notification构造器
//
//        Intent nfIntent = new Intent(MainActivity.this.getApplicationContext(), MainActivity.class);
//        builder.setContentIntent(PendingIntent.getActivity(MainActivity.this, 0, nfIntent, 0)) // 设置PendingIntent
//                .setContentTitle("正在进行后台定位") // 设置下拉列表里的标题
//                .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
//                .setContentText("后台定位通知") // 设置上下文内容
//                .setAutoCancel(true)
//                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间
//        Notification notification = null;
//        notification = builder.build();
//        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
//        mLocationClient.enableLocInForeground(1001, notification);// 调起前台定位

        //开启地图定位图层
        mLocationClient.start();
    }


    public class MyLocationListener extends BDAbstractLocationListener {
        //定位信息回调
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);

            latitude = location.getLatitude();
            longitude = location.getLongitude();

            myLocation = location;
        }
    }


    private void getPermissionMethod() {
        //授权列表
        List<String> permissionList = new ArrayList<>();

        //检查是否获取该权限 ACCESS_FINE_LOCATION
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (!permissionList.isEmpty()) { //权限列表不是空
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            //动态申请权限的请求
            ActivityCompat.requestPermissions(getActivity(), permissions, 1);
        }
    }

    /**
     * 监听用户是否授权
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            //拒绝获取权限
//                            Toast.makeText(this, "必须统一所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
//                            finish();
//                            return;
                        }
                    }
//获得权限后初始化定位
                    initLocation();
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

}
