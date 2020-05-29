package com.example.assessment_2.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assessment_2.R;
import com.example.assessment_2.adapter.BikeListAdapter;
import com.example.assessment_2.model.BrandItem;
import com.example.assessment_2.model.MotorItem;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BrandDetailActivity extends Activity {

    private ImageView btn_back;
    private RecyclerView bikeRecyclerView;
    private Button btn_sort_by_price;

    private boolean isReverse = false;
    private BrandItem brandItem;
    private ImageView iv_brand;
    private TextView tv_brand_name;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_brand_detail);
        btn_back = findViewById(R.id.iv_go_back);
        btn_sort_by_price = findViewById(R.id.btn_sort_by_price);
        bikeRecyclerView = findViewById(R.id.bike_recyclerView);
        iv_brand = findViewById(R.id.iv_brand);
        tv_brand_name = findViewById(R.id.tv_brandName);

        brandItem = (BrandItem) getIntent().getExtras().getSerializable("brandItem");

        initPageUI();
        initRecyclerView(brandItem.getMotoList());
        initBackClickListener();
        initPriceSortClickListener();
    }

    private void initPageUI() {
        tv_brand_name.setText(brandItem.getBrandName());
        iv_brand.setImageResource(brandItem.getImgRes());
    }


    private void initBackClickListener() {
        btn_back.setOnClickListener(view -> {
            finish();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initPriceSortClickListener() {
        btn_sort_by_price.setOnClickListener(view -> {
            Stream<MotorItem> stream = brandItem.getMotoList().stream();
            List<MotorItem> collect = null;
            if (isReverse) {
                collect = stream.sorted(Comparator.comparing(MotorItem::getPrice)).collect(Collectors.toList());
                isReverse = false;
            } else {
                collect = stream.sorted(Comparator.comparing(MotorItem::getPrice).reversed()).collect(Collectors.toList());
                isReverse = true;
            }
            initRecyclerView(collect);
        });
    }

    private void initRecyclerView(List<MotorItem> motoList) {
//        BikeListAdapter bikeListAdapter = new BikeListAdapter(this, R.layout.item_bike, collect);
        BikeListAdapter bikeListAdapter = new BikeListAdapter(this, R.layout.item_bike, motoList);
        bikeRecyclerView.setAdapter(bikeListAdapter);
        bikeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
