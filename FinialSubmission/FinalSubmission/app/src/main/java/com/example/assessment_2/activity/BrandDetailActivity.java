package com.example.assessment_2.activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assessment_2.R;
import com.example.assessment_2.adapter.BikeListAdapter;
import com.example.assessment_2.base.BaseActivity;
import com.example.assessment_2.model.MotorItemDto;
import com.example.assessment_2.model.MotorListResponse;
import com.example.assessment_2.model.MotorcycleBrandDto;
import com.example.assessment_2.util.HttpUtil;
import com.example.assessment_2.util.OkHttpManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BrandDetailActivity extends BaseActivity {
  private Button btn_sort_by_price;
  private List<MotorItemDto> data = new ArrayList<>();
  private MotorcycleBrandDto brandItem;
  private ImageView iv_brand;
  private TextView tv_brand_name;
  private BikeListAdapter bikeListAdapter;
  private boolean isDesc = true; //排序

  protected void initData() {
    super.initData();
    brandItem = (MotorcycleBrandDto) getIntent().getSerializableExtra("BRAND");

  }

  public void initView(String titleName) {
    super.initView(brandItem.name);
    btn_sort_by_price = findViewById(R.id.btn_sort_by_price);
    iv_brand = findViewById(R.id.iv_brand);
    tv_brand_name = findViewById(R.id.tv_brandName);
    initPageUI();
    initRecyclerView();
    getMotorList();

    btn_sort_by_price.setOnClickListener(view -> {
      //sort by price
      if (isDesc) {
        Collections.sort(data, Collections.reverseOrder());
        isDesc = false;
      } else {
        Collections.sort(data);
        isDesc = true;
      }
      bikeListAdapter.notifyDataSetChanged();
    });
  }

  private void getMotorList() {
    new OkHttpManager(this, HttpUtil.MOTOR_LIST + brandItem.id, null,
        MotorListResponse.class, false, new OkHttpManager.ResponseCallback() {
      public void onError(int errorType, int errorCode, String errorMsg) {
        Toast.makeText(BrandDetailActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
      }

      public void onSuccess(Object response) {
        if (response != null && response instanceof MotorListResponse) {
          MotorListResponse resp = (MotorListResponse) response;
          data.clear();
          data.addAll(resp.data);
          bikeListAdapter.notifyDataSetChanged();
        }
      }
    }).NetRequest();
  }

  protected int getLayout() {
    return R.layout.page_brand_detail;
  }

  private void initPageUI() {
    tv_brand_name.setText(brandItem.name);
    Glide.with(this).load(brandItem.img).into(iv_brand);
  }

  private void initRecyclerView() {
//        BikeListAdapter bikeListAdapter = new BikeListAdapter(this, R.layout.item_bike, collect);
    RecyclerView bikeRecyclerView = findViewById(R.id.bike_recyclerView);
    bikeListAdapter = new BikeListAdapter(this, R.layout.item_bike, data);
    bikeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    bikeRecyclerView.setAdapter(bikeListAdapter);
    bikeListAdapter.setItemClickListener(holder -> {
      MotorItemDto motorItemDto = data.get(holder.getLayoutPosition());
      Intent intent = new Intent(BrandDetailActivity.this, BikeDetailActivity.class);
      intent.putExtra("NAME", motorItemDto.name);
      intent.putExtra("ID", motorItemDto.id);
      startActivity(intent);
    });
  }
}
