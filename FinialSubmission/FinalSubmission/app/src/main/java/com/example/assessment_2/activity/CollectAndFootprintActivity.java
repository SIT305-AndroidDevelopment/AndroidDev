package com.example.assessment_2.activity;

import android.content.Intent;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assessment_2.R;
import com.example.assessment_2.adapter.BikeListAdapter;
import com.example.assessment_2.base.BaseActivity;
import com.example.assessment_2.base.IRecyclerViewItemClickListener;
import com.example.assessment_2.model.MotorItemDto;
import com.example.assessment_2.model.MotorListResponse;
import com.example.assessment_2.util.HttpUtil;
import com.example.assessment_2.util.OkHttpManager;
import com.example.assessment_2.util.UserInfoManager;

import java.util.ArrayList;
import java.util.List;

public class CollectAndFootprintActivity extends BaseActivity {

  //to sign where it jump from, 0-collection  1-footprint
  private int type;
  private String titleStr;
  private BikeListAdapter bikeListAdapter;
  private List<MotorItemDto> data = new ArrayList<>();

  protected void initData() {
    super.initData();
    type = getIntent().getIntExtra("TYPE", -1);
    titleStr = type == 0 ? "Collection" : "Track";
  }

  public void initView(String titleName) {
    super.initView(titleStr);
    initRecyclerView();
  }

  private void initRecyclerView() {
    bikeListAdapter = new BikeListAdapter(this, R.layout.item_bike, data);
    RecyclerView recyclerView = this.findViewById(R.id.recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    recyclerView.setAdapter(bikeListAdapter);
    bikeListAdapter.setItemClickListener(new IRecyclerViewItemClickListener() {
      public void onItemClickListener(RecyclerView.ViewHolder holder) {
        MotorItemDto motorItemDto = data.get(holder.getLayoutPosition());
        Intent intent = new Intent(CollectAndFootprintActivity.this, BikeDetailActivity.class);
        intent.putExtra("NAME", motorItemDto.name);
        intent.putExtra("ID", motorItemDto.id);
        startActivity(intent);
      }
    });
  }

  private void getList() {
    new OkHttpManager(this,
        type == 0 ? HttpUtil.COLLECT_LIST + UserInfoManager.getInstance().getUserInfo().id
            : HttpUtil.FOOT_LIST + UserInfoManager.getInstance().getUserInfo().id,
        null,
        MotorListResponse.class, true, new OkHttpManager.ResponseCallback() {
      public void onError(int errorType, int errorCode, String errorMsg) {
        Toast.makeText(CollectAndFootprintActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
      }

      public void onSuccess(Object response) {
        if (response != null && response instanceof MotorListResponse) {
          MotorListResponse resp = (MotorListResponse) response;
          data.clear();
          data.addAll(resp.data);
          bikeListAdapter.notifyDataSetChanged();
          if (data.size() == 0) {
            Toast.makeText(CollectAndFootprintActivity.this, "Nothing here", Toast.LENGTH_SHORT).show();
          }
        }
      }
    }).NetRequest();
  }

  protected void onResume() {
    super.onResume();
    getList();
  }

  protected int getLayout() {
    return R.layout.activity_coolect_and_footprint;
  }
}
