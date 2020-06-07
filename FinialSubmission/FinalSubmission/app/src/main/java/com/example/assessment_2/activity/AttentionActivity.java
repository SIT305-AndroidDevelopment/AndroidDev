package com.example.assessment_2.activity;

import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assessment_2.R;
import com.example.assessment_2.adapter.BrandListAdapter;
import com.example.assessment_2.base.BaseActivity;
import com.example.assessment_2.base.IRecyclerViewItemClickListener;
import com.example.assessment_2.model.MotorcycleBrandDto;
import com.example.assessment_2.model.MotorcycleBrandListResponse;
import com.example.assessment_2.util.HttpUtil;
import com.example.assessment_2.util.OkHttpManager;
import com.example.assessment_2.util.UserInfoManager;

import java.util.ArrayList;
import java.util.List;

public class AttentionActivity extends BaseActivity {
  private BrandListAdapter adapter;
  private List<MotorcycleBrandDto> list = new ArrayList<>();

  public void initView(String titleName) {
    super.initView("My Attention");
    initRecyclerView();
    getAttentionList();
  }

  //get list attention
  private void getAttentionList() {
    new OkHttpManager(this, HttpUtil.COLLECT_BRAND_LIST + UserInfoManager.getInstance().getUserInfo().id,
        null, MotorcycleBrandListResponse.class, true, new OkHttpManager.ResponseCallback() {
      public void onError(int errorType, int errorCode, String errorMsg) {
        Toast.makeText(AttentionActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
      }

      public void onSuccess(Object response) {
        if (response != null && response instanceof MotorcycleBrandListResponse) {
          MotorcycleBrandListResponse resp = (MotorcycleBrandListResponse) response;
          list.clear();
          list.addAll(resp.data);
          adapter.notifyDataSetChanged();
        }
      }
    }).NetRequest();
  }

  private void initRecyclerView() {
    adapter = new BrandListAdapter(this, R.layout.item_brand, list);
    RecyclerView recyclerView = this.findViewById(R.id.recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);
    adapter.setItemClickListener(new IRecyclerViewItemClickListener() {
      public void onItemClickListener(RecyclerView.ViewHolder holder) {
        MotorcycleBrandDto model = list.get(holder.getAdapterPosition());
        if (model.name.contains("honda")) {
          String url = "http://www.honda.com.cn/motor/";
          Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
          startActivity(browserIntent);
        } else if (model.name.contains("suzuki")) {
          String url = "http://www.qssuzuki.com.cn/";
          Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
          startActivity(browserIntent);
        } else if (model.name.contains("kawasaki")) {
          String url = "http://www.kawasaki-motors.cn/kawasakimotors/";
          Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
          startActivity(browserIntent);
        } else if (model.name.contains("yamaha")) {
          String url = "https://www.yamaha-motor.com.cn/mc/";
          Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
          startActivity(browserIntent);
        }
      }
    });

  }

  protected int getLayout() {
    return R.layout.activity_like;
  }
}
