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
        //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        list = AppDataFactory.getStoreList();

        StoreListAdapter adapter = new StoreListAdapter((FragmentActivity) getContext(), R.layout.item_store, list);
        recyclerView.setAdapter(adapter);
        initStoreMarkers(list);

        final List<StoreItem> finalList = list;

        // long click listener
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
//                Toast.makeText(getActivity(), "1111", Toast.LENGTH_LONG).show();
                StoreItem storeItem = finalList.get(position);
                showChooseDialog(storeItem);

                return false;
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                StoreItem storeItem = finalList.get(position);
                //Set center point coordinates
                //LatLng cenpt = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                LatLng cenpt = new LatLng(storeItem.latitude, storeItem.longitude);

                //Define map status
                MapStatus mMapStatus = new MapStatus.Builder()
                        //Point to move
                        .target(cenpt)
                        //Enlarge the map to 15 times
                        .zoom(15)
                        .build();
                //Define the MapStatusUpdate object to describe the changes that will occur in the map status
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);

                //Change map status
                mBaiDuMap.setMapStatus(mMapStatusUpdate);
            }
            // mBaiDuMap.setMyLocationEnabled(true);
        });
    }

    private void initStoreMarkers(List<StoreItem> storeList) {
        for (StoreItem bean : storeList) {
            //Define Maker coordinates
            LatLng point = new LatLng(bean.latitude, bean.longitude);
            //Build Marker icon points
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.ic_marker);
            //Build MarkerOption to add Marker on the map
            OverlayOptions option = new MarkerOptions()
                    .position(point) //Required parameter
                    .icon(bitmap) //Required parameter
                    .draggable(true)
                    //Set up a flat map, two-finger pull down on the map to see the effect
                    .flat(true)
                    .alpha(0.5f);


            //Add Marker on the map and display
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

        //Define map status
        MapStatus mMapStatus = new MapStatus.Builder()
                //Point to move
                .target(cenpt)
                //Enlarge the map to 8 times
                .zoom(8)
                .build();

        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
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
        //Declare the LocationClient class
        mLocationClient = new LocationClient(getActivity());
        //Register monitor function
        mLocationClient.registerLocationListener(myListener);

        //Set LocationClient related parameters through LocationClientOption
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // Turn on gps
        option.setCoorType("bd09ll"); // Set coordinate type
        option.setScanSpan(1000000);

        //Set locationClientOption
        mLocationClient.setLocOption(option);

        //Register LocationListener
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);

        //Turn on the map location layer
        mLocationClient.start();
    }


    public class MyLocationListener extends BDAbstractLocationListener {
        //Location information callback
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView does not process the newly received position after being destroyed
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    //Set the direction information obtained by the developer here, clockwise 0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiDuMap.setMyLocationData(locData);

            latitude = location.getLatitude();
            longitude = location.getLongitude();

            myLocation = location;
        }
    }


    private void getPermissionMethod() {
        //Authorization list
        List<String> permissionList = new ArrayList<>();

        //Check if the permission is obtained ACCESS_FINE_LOCATION
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (!permissionList.isEmpty()) { //Permission list is not empty
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            //Dynamic permission request
            ActivityCompat.requestPermissions(getActivity(), permissions, 1);
        }
    }

    /**
     * Monitor user authorization
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            //Deny permission
//                            Toast.makeText(this, "All permissions must be unified to use this program", Toast.LENGTH_SHORT).show();
//                            finish();
//                            return;
                        }
                    }
//Initialize positioning after obtaining permission
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

    private void showChooseDialog(final StoreItem storeItem) {
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
        pop.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getActivity().getWindow().getAttributes();
            lp1.alpha = 1f;
            getActivity().getWindow().setAttributes(lp1);
        });
        pop.setAnimationStyle(R.style.main_menu_photo_anim);
        pop.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        View.OnClickListener clickListener = view -> {
            int i = view.getId();
            if (i == R.id.tv_baidu) {
                Uri uri = Uri.parse("baidumap://map/direction?destination=latlng:"+storeItem.latitude+","+ storeItem.longitude+"|name:"+"目的地名称"+"&mode=driving");
                getActivity().startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }

            if (i == R.id.tv_australia) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("URL",storeItem.URL);
                getActivity().startActivity(intent);
            }

            if (i == R.id.tv_cancel) {
                //closePopupWindow();
            }
            closePopupWindow();
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
