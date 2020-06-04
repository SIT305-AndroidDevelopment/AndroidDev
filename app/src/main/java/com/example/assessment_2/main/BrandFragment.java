package com.example.assessment_2.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assessment_2.R;
import com.example.assessment_2.activity.SearchActivity;
import com.example.assessment_2.adapter.BrandListAdapter;
import com.example.assessment_2.model.AppDataFactory;
import com.example.assessment_2.model.BrandItem;

import java.util.List;

public class BrandFragment extends Fragment {
    private LinearLayout ll_ToSearch;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //返回当前页面容器layout
        View view = inflater.inflate(R.layout.page_brand, null);
        recyclerView = view.findViewById(R.id.recyclerView);
        ll_ToSearch = view.findViewById(R.id.ll_ToSearch);

        initSearchClickListener();

        List<BrandItem> list = AppDataFactory.getBrandList();
        BrandListAdapter adapter = new BrandListAdapter(getActivity(), R.layout.item_brand, list);
        //获取layout结构设置 决定布局属性
        //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //为recyclerview设置适配器
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void initSearchClickListener() {
        ll_ToSearch.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            getActivity().startActivity(intent);
        });
    }
}

