package com.example.assessment_2.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.assessment_2.R;
import com.example.assessment_2.activity.BikeDetailActivity;
import com.example.assessment_2.model.MotorItem;

import java.util.List;

public class BikeListAdapter extends BaseQuickAdapter<MotorItem, BaseViewHolder> {

    private Activity activity;

    public BikeListAdapter(Activity activity, int layoutResId, @Nullable List<MotorItem> data) {
        super(layoutResId, data);
        this.activity = activity;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MotorItem item) {
        LinearLayout ll_BikeItem = helper.getView(R.id.ll_BikeItem);
        ImageView ivImg = helper.getView(R.id.img_motor);
        TextView tvName = helper.getView(R.id.tv_motor_name);
        TextView tvPrice = helper.getView(R.id.tv_price);

        tvName.setText(item.getName());
        ivImg.setImageResource(item.getPicRes());
        tvPrice.setText("￥" + item.getPrice());

        ll_BikeItem.setOnClickListener(view -> {
            Intent intent = new Intent(activity, BikeDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("MotorItem",item);
            intent.putExtras(bundle);
            activity.startActivity(intent);
        });
    }
}
