package com.example.assessment_2.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.assessment_2.R;
import com.example.assessment_2.activity.BrandDetailActivity;
import com.example.assessment_2.model.BrandItem;

import java.util.List;

public class BrandListAdapter extends BaseQuickAdapter<BrandItem, BaseViewHolder> {

    private FragmentActivity activity;

    public BrandListAdapter(FragmentActivity activity, int layoutResId, @Nullable List<BrandItem> data) {
        super(layoutResId, data);
        this.activity = activity;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final BrandItem item) {
        LinearLayout llBrand = helper.getView(R.id.ll_brand);
        TextView tvItemName = helper.getView(R.id.tv_item_name);
        ImageView ivItemName = helper.getView(R.id.img_brand);
        tvItemName.setText(item.brandName);
        ivItemName.setImageResource(item.imgRes);

        llBrand.setOnClickListener(view -> {
            Intent intent = new Intent(activity, BrandDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("brandItem",item);
            intent.putExtras(bundle);
            activity.startActivity(intent);
        });
    }
}
