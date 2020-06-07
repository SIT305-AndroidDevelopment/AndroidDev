package com.example.assessment_2.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assessment_2.R;
import com.example.assessment_2.base.BaseActivity;
import com.example.assessment_2.base.BaseResponse;
import com.example.assessment_2.model.AddCollectRequest;
import com.example.assessment_2.model.MotorDetailRequest;
import com.example.assessment_2.model.MotorDetailResponse;
import com.example.assessment_2.model.MotorItemDto;
import com.example.assessment_2.model.UserInfo;
import com.example.assessment_2.util.HttpUtil;
import com.example.assessment_2.util.OkHttpManager;
import com.example.assessment_2.util.UserInfoManager;

public class BikeDetailActivity extends BaseActivity {
  private ImageView bikePic;
  private RecyclerView bikeRecyclerView;
  private Button btn_sort_by_price;
  private int position;
  private String motorId;
  private String motorName;

  private boolean isReverse = false;
  private ImageView iv_bike;
  private TextView tv_bike_name;

  private TextView tvHorsePowerValue;
  private TextView tvTorqueValue;
  private TextView tvWeightValue;
  private TextView tvDisplacementValue;
  private TextView tvTankVolumeValue;
  private ImageView collectBtn;
  private UserInfo userInfo;
  private boolean isCollect;

  protected void initData() {
    super.initData();
    motorName = getIntent().getStringExtra("NAME");
    motorId = getIntent().getStringExtra("ID");
    userInfo = UserInfoManager.getInstance().getUserInfo();

  }

  public void initView(String titleName) {
    super.initView(motorName);
    iv_bike = findViewById(R.id.iv_bike);
    tv_bike_name = findViewById(R.id.tv_bike_name);
    collectBtn = this.findViewById(R.id.collect_btn);
    collectBtn.setVisibility(userInfo != null && userInfo.id != null && !userInfo.id.isEmpty() ? View.VISIBLE : View.GONE);
    collectBtn.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        collect();
      }
    });
    tvHorsePowerValue = findViewById(R.id.tv_value_horsePower);
    tvTorqueValue = findViewById(R.id.tv_value_torque);
    tvWeightValue = findViewById(R.id.tv_value_weight);
    tvDisplacementValue = findViewById(R.id.tv_value_displacement);
    tvTankVolumeValue = findViewById(R.id.tv_value_tankVolume);

    MotorDetailRequest request = new MotorDetailRequest();
    request.motorId = motorId;

    if (UserInfoManager.getInstance().getUserInfo() != null) {
      request.userId = UserInfoManager.getInstance().getUserInfo().id + "";
    }
    new OkHttpManager(this, HttpUtil.MOTOR_DETAIL, request, MotorDetailResponse.class, true,
        new OkHttpManager.ResponseCallback() {
          public void onError(int errorType, int errorCode, String errorMsg) {
            Toast.makeText(BikeDetailActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
          }

          public void onSuccess(Object response) {
            if (response != null && response instanceof MotorDetailResponse) {
              setData(((MotorDetailResponse) response).data);
            }

          }
        }).NetRequest();
  }

  //like/unlike
  private void collect() {
    AddCollectRequest request = new AddCollectRequest();
    request.userId = userInfo.id;
    if (isCollect) {
      request.collectId = motorId;
    } else {
      request.motorId = motorId;
    }
    new OkHttpManager(this, isCollect ? HttpUtil.DELETE_COLLECT : HttpUtil.ADD_COLLECT, request, BaseResponse.class, true,
        new OkHttpManager.ResponseCallback() {
          public void onError(int errorType, int errorCode, String errorMsg) {
            Toast.makeText(BikeDetailActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
          }

          public void onSuccess(Object response) {
            isCollect = !isCollect;
            Toast.makeText(BikeDetailActivity.this, isCollect ? "Collected successfully" : "cancel to collect", Toast.LENGTH_SHORT).show();
            collectBtn.setImageResource(isCollect ? R.mipmap.ic_collect : R.mipmap.ic_un_collect);
          }
        }).NetRequest();
  }

  private void setData(MotorItemDto motorItem) {
    tvHorsePowerValue.setText("" + motorItem.horsePower);
    tvTorqueValue.setText("" + motorItem.torque);
    tvWeightValue.setText("" + motorItem.weight);
    tvDisplacementValue.setText("" + motorItem.displacement);
    tvTankVolumeValue.setText("" + motorItem.tankVolume);
    Glide.with(BikeDetailActivity.this).load(motorItem.picRes).into(iv_bike);
    tv_bike_name.setText(motorItem.name);
    isCollect = motorItem.collected;
    collectBtn.setImageResource(isCollect ? R.mipmap.ic_collect : R.mipmap.ic_un_collect);
  }

  protected int getLayout() {
    return R.layout.page_bike_detail;
  }


}
