package com.example.assessment_2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.assessment_2.R;
import com.example.assessment_2.constant.AppConstant;
import com.example.assessment_2.main.BrandFragment;
import com.example.assessment_2.view.SideBar;

import java.util.ArrayList;
import java.util.List;

public class FragmentSample extends Fragment {

    private int contentLayoutId;
    private SideBar indexBar;
    private TextView textViewDialog;
    private RecyclerView recyclerView;
    private Adapter clickAdapter;
    private ImageView btn_back;

    public class BikeItem {
        String bikeName;
        int imgRes;
        int Price;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //返回当前页面容器layout
        View view = inflater.inflate(R.layout.page_brand_detail, null);
        recyclerView = view.findViewById(R.id.recyclerView);
        //btn_back = view.findViewById(R.id.iv_back);
        List<BikeItem> list = getList();

        BrandAdapter adapter = new BrandAdapter(R.layout.item_brand,list);

        //获取layout结构设置 决定布局属性
        //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        //为recyclerview设置适配器
        recyclerView.setAdapter(adapter);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getContext(), BrandFragment.class);
                    startActivity(intent);
            }
        });

        return view;
    }

    private ArrayList<BikeItem> getList(){
        ArrayList<BikeItem> brandList = new ArrayList<>();

        BikeItem Honda = new BikeItem();
        Honda.imgRes = R.drawable.ic_honda;
        Honda.bikeName = AppConstant.BRAND_HONDA;

        BikeItem Yamaha = new BikeItem();
        Yamaha.imgRes = R.drawable.ic_yamaha;
        Yamaha.bikeName = AppConstant.BRAND_YAMAHA;

        BikeItem Suzuki = new BikeItem();
        Suzuki.imgRes = R.drawable.ic_suzuki;
        Suzuki.bikeName = AppConstant.BRAND_SUZUKI;

        BikeItem Kawasaki = new BikeItem();
        Kawasaki.imgRes = R.drawable.ic_kawasaki;
        Kawasaki.bikeName = AppConstant.BRAND_KAWASAKI;

        brandList.add(Honda);
        brandList.add(Yamaha);
        brandList.add(Suzuki);
        brandList.add(Kawasaki);

        return brandList;
    }

    public class BrandAdapter extends BaseQuickAdapter<BikeItem, BaseViewHolder> {
        public BrandAdapter(int layoutResId, @Nullable List<BikeItem> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, BikeItem item) {
            TextView tvItemName = helper.getView(R.id.tv_item_name);
            ImageView ivItemName = helper.getView(R.id.img_brand);
            tvItemName.setText(item.bikeName);
            ivItemName.setImageResource(item.imgRes);
        }
    }
}

