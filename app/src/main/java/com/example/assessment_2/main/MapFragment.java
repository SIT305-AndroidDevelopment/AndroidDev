package com.example.assessment_2.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.assessment_2.R;
import com.example.assessment_2.activity.WebViewActivity;
import com.example.assessment_2.adapter.StoreListAdapter;
import com.example.assessment_2.model.AppDataFactory;
import com.example.assessment_2.model.StoreItem;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {
    private MapView mMapView = null;
    private BaiduMap mBaiDuMap;

    private ImageButton btn_loc;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private BDLocation myLocation;
    private double latitude;
    private double longitude;

    private RecyclerView recyclerView;
    private PopupWindow pop;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_map, null);
        mMapView = view.findViewById(R.id.bmapView);
        btn_loc = view.findViewById(R.id.ib_loc);
        recyclerView = view.findViewById(R.id.recyclerView);

        mBaiDuMap = mMapView.getMap();

        initRecyclerView();
        initBtnClickListener();
        getPermissionMethod();
        initLocation();
        return view;
    }

    private void initRecyclerView() {
        List<StoreItem> list = new ArrayList<>();
        //获取layout结构设置 决定布局属性
        //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        list = AppDataFactory.getStoreList();

        StoreListAdapter adapter = new StoreListAdapter((FragmentActivity) getContext(), R.layout.item_store, list);
        //为recyclerview设置适配器
        recyclerView.setAdapter(adapter);
        initStoreMarkers(list);

        final List<StoreItem> finalList = list;

        // long click listener
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
//                Toast.makeText(getActivity(), "1111", Toast.LENGTH_LONG).show();
                StoreItem storeItem = finalList.get(position);
                showChoosePicDialog(storeItem);

                return false;
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                StoreItem storeItem = finalList.get(position);
                //设定中心点坐标
                //LatLng cenpt = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                LatLng cenpt = new LatLng(storeItem.latitude, storeItem.longitude);

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
                mBaiDuMap.setMapStatus(mMapStatusUpdate);
            }
            // mBaiDuMap.setMyLocationEnabled(true);
        });
    }

    private void initStoreMarkers(List<StoreItem> storeList) {
        for (StoreItem bean : storeList) {
            //定义Maker坐标点
            LatLng point = new LatLng(bean.latitude, bean.longitude);
//构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.ic_marker);
//构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point) //必传参数
                    .icon(bitmap) //必传参数
                    .draggable(true)
//设置平贴地图，在地图中双指下拉查看效果
                    .flat(true)
                    .alpha(0.5f);


            //在地图上添加Marker，并显示
            Marker marker = (Marker)mBaiDuMap.addOverlay(option);
            Bundle bundle = new Bundle();
            bundle.putString("storeName",bean.getStoreName());
            marker.setExtraInfo(bundle);

            mBaiDuMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Bundle extraInfo = marker.getExtraInfo();
                    String storeName = extraInfo.getString("storeName");
                    Toast.makeText(getActivity(),"I'm "+storeName,Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }


        LatLng cenpt = new LatLng(storeList.get(0).getLatitude(), storeList.get(0).getLongitude());

        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                //要移动的点
                .target(cenpt)
                //放大地图到20倍
                .zoom(8)
                .build();

        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);

        //改变地图状态
        mBaiDuMap.setMapStatus(mMapStatusUpdate);
    }

    private void initBtnClickListener() {
        btn_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设定中心点坐标
                // LatLng cenpt = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                LatLng cenpt = new LatLng(latitude, longitude);

                //定义地图状态
                MapStatus mMapStatus = new MapStatus.Builder()
                        //要移动的点
                        .target(cenpt)
                        //放大地图到20倍
                        .zoom(15)
                        .build();

                //定义Maker坐标点
                LatLng point = new LatLng(latitude, longitude);
                //构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_honda);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(bitmap);
                //在地图上添加Marker，并显示
                mBaiDuMap.addOverlay(option);

                //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);

                //改变地图状态
                mBaiDuMap.setMapStatus(mMapStatusUpdate);
            }
        });
        mBaiDuMap.setMyLocationEnabled(true);
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
            mBaiDuMap.setMyLocationData(locData);

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
        mBaiDuMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }


    /**
     * 显示修改头像的对话框
     * @param storeItem
     */
    private void showChoosePicDialog(final StoreItem storeItem) {
        View bottomView = View.inflate(getActivity(), R.layout.bottom_dialog, null);
        TextView mAlbum = bottomView.findViewById(R.id.tv_baidu);
        TextView mCamera = bottomView.findViewById(R.id.tv_australia);
        TextView mCancel = bottomView.findViewById(R.id.tv_cancel);

        pop = new PopupWindow(bottomView, -1, -2);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.5f;
        getActivity().getWindow().setAttributes(lp);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        pop.setAnimationStyle(R.style.main_menu_photo_anim);
        pop.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = view.getId();
                if (i == R.id.tv_baidu) {//百度地图
                    Uri uri = Uri.parse("baidumap://map/direction?destination=latlng:"+storeItem.latitude+","+ storeItem.longitude+"|name:"+"目的地名称"+"&mode=driving");
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }

                if (i == R.id.tv_australia) {//拍照
                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("URL",storeItem.URL);
                    getActivity().startActivity(intent);
                }

                if (i == R.id.tv_cancel) {//取消
                    //closePopupWindow();
                }
                closePopupWindow();
            }
        };

        mAlbum.setOnClickListener(clickListener);
        mCamera.setOnClickListener(clickListener);
        mCancel.setOnClickListener(clickListener);
    }

    public void closePopupWindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            pop = null;
        }
    }
}
