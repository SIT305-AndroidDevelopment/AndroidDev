package com.example.assessment_2.adapter;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.assessment_2.R;
import com.example.assessment_2.model.StoreItem;

import java.util.List;

public class StoreListAdapter extends BaseQuickAdapter<StoreItem, BaseViewHolder> {

    private FragmentActivity activity;

    public StoreListAdapter(FragmentActivity activity, int layoutResId, @Nullable List<StoreItem> data) {
        super(layoutResId, data);
        this.activity = activity;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final StoreItem item) {
        final LinearLayout llBrand = helper.getView(R.id.ll_brand);
        TextView itemName = helper.getView(R.id.tv_item_name);
        TextView itemDescription = helper.getView(R.id.tv_item_description);
        ImageView itemBrand = helper.getView(R.id.iv_brand);

        itemName.setText(item.storeName);
        itemDescription.setText(item.locationDescription);
        itemBrand.setImageResource(item.storeImage);
    }
}
