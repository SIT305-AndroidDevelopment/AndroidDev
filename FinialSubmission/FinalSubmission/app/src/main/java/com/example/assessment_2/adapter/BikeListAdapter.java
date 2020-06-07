package com.example.assessment_2.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.assessment_2.R;
import com.example.assessment_2.base.IRecyclerViewItemClickListener;
import com.example.assessment_2.model.MotorItemDto;

import java.util.List;

public class BikeListAdapter extends BaseQuickAdapter<MotorItemDto, BaseViewHolder> {

  private Context context;
  private IRecyclerViewItemClickListener itemClickListener;

  public void setItemClickListener(IRecyclerViewItemClickListener itemClickListener) {
    this.itemClickListener = itemClickListener;
  }

  public BikeListAdapter(Context context, int layoutResId, @Nullable List<MotorItemDto> data) {
    super(layoutResId, data);
    this.context = context;
  }

  protected void convert(final BaseViewHolder helper, final MotorItemDto item) {
    LinearLayout ll_BikeItem = helper.getView(R.id.ll_BikeItem);
    ImageView ivImg = helper.getView(R.id.img_motor);
    TextView tvName = helper.getView(R.id.tv_motor_name);
    TextView tvPrice = helper.getView(R.id.tv_price);

    tvName.setText(item.name);
    Glide.with(context).load(item.picRes).into(ivImg);
    tvPrice.setText("￥" + item.price);

    ll_BikeItem.setOnClickListener(view -> {
      itemClickListener.onItemClickListener(helper);
    });
  }
}
