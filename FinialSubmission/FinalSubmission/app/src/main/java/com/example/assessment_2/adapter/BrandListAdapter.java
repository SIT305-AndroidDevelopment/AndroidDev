package com.example.assessment_2.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.assessment_2.R;
import com.example.assessment_2.base.IRecyclerViewItemClickListener;
import com.example.assessment_2.model.MotorcycleBrandDto;

import java.util.List;

public class BrandListAdapter extends BaseQuickAdapter<MotorcycleBrandDto, BaseViewHolder> {

  private Context mContext;
  private IRecyclerViewItemClickListener itemClickListener;

  public void setItemClickListener(IRecyclerViewItemClickListener itemClickListener) {
    this.itemClickListener = itemClickListener;
  }

  public BrandListAdapter(Context mContext, int layoutResId, @Nullable List<MotorcycleBrandDto> data) {
    super(layoutResId, data);
    this.mContext = mContext;
  }

  protected void convert(final BaseViewHolder helper, final MotorcycleBrandDto item) {
    LinearLayout llBrand = helper.getView(R.id.ll_brand);
    TextView tvItemName = helper.getView(R.id.tv_item_name);
    ImageView ivItemName = helper.getView(R.id.img_brand);
    tvItemName.setText(item.name);
    Glide.with(mContext).load(item.img).into(ivItemName);
    if (itemClickListener != null) {
      helper.itemView.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
          itemClickListener.onItemClickListener(helper);
        }
      });
    }
//
//    llBrand.setOnClickListener(view -> {
//      Intent intent = new Intent(activity, BrandDetailActivity.class);
//      Bundle bundle = new Bundle();
//      bundle.putSerializable("brandItem", item);
//      intent.putExtras(bundle);
//      activity.startActivity(intent);
//    });
  }
}
