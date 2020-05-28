package com.example.assessment_2.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.assessment_2.R;
import com.example.assessment_2.SideBar;
import com.example.assessment_2.activity.BrandDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class BrandFragment extends Fragment {

    private int contentLayoutId;
    private SideBar indexBar;
    private TextView textViewDialog;
    private RecyclerView recyclerView;
    private Adapter clickAdapter;

    public class BrandItem {
        String brandName;
        int imgRes;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //返回当前页面容器layout
        View view = inflater.inflate(R.layout.page_brand, null);
        recyclerView = view.findViewById(R.id.recyclerView);

        List<BrandItem> list = getList();

        BrandAdapter adapter = new BrandAdapter(getActivity(), R.layout.item_brand, list);

        //获取layout结构设置 决定布局属性
        //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //为recyclerview设置适配器
        recyclerView.setAdapter(adapter);

//        adapter.notifyDataSetChanged();



//        Gson gson = new Gson();
//        StoreBeanList jsonBean = gson.fromJson(AppData.STORE_INFO, StoreBeanList.class);
//        List<StoreBean> storeBeanList = jsonBean.getStoreBeanList();
//        StoreBean storeBean = storeBeanList.get(3);


        return view;
    }

    private ArrayList<BrandItem> getList() {
        ArrayList<BrandItem> brandList = new ArrayList<>();

        BrandItem Honda = new BrandItem();
        Honda.imgRes = R.drawable.ic_honda;
        Honda.brandName = "Honda";

        BrandItem Yamaha = new BrandItem();
        Yamaha.imgRes = R.drawable.ic_yamaha;
        Yamaha.brandName = "Yamaha";

        BrandItem Suzuki = new BrandItem();
        Suzuki.imgRes = R.drawable.ic_suzuki;
        Suzuki.brandName = "Suzuki";

        BrandItem Kawasaki = new BrandItem();
        Kawasaki.imgRes = R.drawable.ic_kawasaki;
        Kawasaki.brandName = "Kawasaki";

        brandList.add(Honda);
        brandList.add(Yamaha);
        brandList.add(Suzuki);
        brandList.add(Kawasaki);

        return brandList;
    }

    public class BrandAdapter extends BaseQuickAdapter<BrandItem, BaseViewHolder> {

        private FragmentActivity activity;

        public BrandAdapter(FragmentActivity activity, int layoutResId, @Nullable List<BrandItem> data) {
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

            llBrand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), BrandDetailActivity.class);
                    intent.putExtra("brandName", item.brandName);
                    activity.startActivity(intent);
                }
            });
        }
    }
}


/**
 * @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
 * View view = inflater.inflate(R.layout.page_brand, null);
 * View recyclerView = view.findViewById(R.id.recyclerView);
 * initRecyclerView();
 * return view;
 * }
 * <p>
 * private void initRecyclerView() {
 * //create list
 * ArrayList<BrandBean> newsList = getList();
 * //create adapter
 * GeneralAdapter adapter = new GeneralAdapter(getActivity(), newsList);
 * //create layoutManager
 * LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
 * //config recyclerView
 * recyclerView.setLayoutManager(layoutManager);
 * recyclerView.setAdapter(adapter);
 * }
 * //set brand
 * private ArrayList<BrandBean> getList() {
 * ArrayList<BrandBean> brandList = new ArrayList<>();
 * BrandBean Honda = new BrandBean();
 * Honda.setImgResource(R.drawable.ic_honda);
 * Honda.setBrandTitle("Honda");
 * Honda.fragmentType = FragmentType.Honda;
 * <p>
 * BrandBean Yamaha = new BrandBean();
 * Honda.setImgResource(R.drawable.ic_yamaha);
 * Honda.setBrandTitle("Honda");
 * Honda.fragmentType = FragmentType.Yamaha;
 * <p>
 * BrandBean Suzuki = new BrandBean();
 * Honda.setImgResource(R.drawable.ic_suzuki);
 * Honda.setBrandTitle("Honda");
 * Honda.fragmentType = FragmentType.Suzuki;
 * <p>
 * BrandBean Kawasaki = new BrandBean();
 * Honda.setImgResource(R.drawable.ic_kawasaki);
 * Honda.setBrandTitle("Honda");
 * Honda.fragmentType = FragmentType.Kawasaki;
 * <p>
 * brandList.add(Honda);
 * brandList.add(Yamaha);
 * brandList.add(Suzuki);
 * brandList.add(Kawasaki);
 * <p>
 * return brandList;
 * }
 * <p>
 * public class GeneralAdapter extends RecyclerView.Adapter<GeneralAdapter.MyViewHolder> {
 * <p>
 * Context context;
 * List<BrandBean> datas;
 * <p>
 * public GeneralAdapter(Context context, List<BrandBean> datas) {
 * this.context = context;
 * this.datas = datas;
 * }
 * @NonNull
 * @Override public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
 * <p>
 * View v = View.inflate(context, R.layout.item_brand, null);
 * return new MyViewHolder(v);
 * }
 * @Override public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
 * holder.textView.setText(datas.get(position).getBrandTitle());
 * holder.imageView.setImageResource(datas.get(position).getImgResource());
 * <p>
 * holder.textView.setOnClickListener(new View.OnClickListener() {
 * @Override public void onClick(View view) {
 * int pos = holder.getLayoutPosition();
 * if(pos==0){initGoListener_Honda();}//Honda
 * else if(pos==1){initGoListener_Yamaha();}//Yamaha
 * else if(pos==2){initGoListener_Suzuki();}//Suzuki
 * else if(pos==3){initGoListener_Kawasaki();}//Kawasaki
 * }
 * });
 * }
 * @Override public int getItemCount() {
 * return datas.size();
 * }
 * <p>
 * class MyViewHolder extends RecyclerView.ViewHolder {
 * TextView textView;
 * ImageView imageView;
 * <p>
 * public MyViewHolder(View itemView) {
 * super(itemView);
 * textView = itemView.findViewById(R.id.text);
 * imageView = itemView.findViewById(R.id.image);
 * }
 * }
 * }
 * <p>
 * public class BrandBean {
 * public int imgResource;
 * public String brandTitle;
 * public FragmentType fragmentType;
 * <p>
 * public int getImgResource() {
 * return imgResource;
 * }
 * <p>
 * public void setImgResource(int imgResource) {
 * this.imgResource = imgResource;
 * }
 * <p>
 * public String getBrandTitle() {
 * return brandTitle;
 * }
 * <p>
 * public void setBrandTitle(String brandTitle) {
 * this.brandTitle = brandTitle;
 * }
 * <p>
 * }
 * <p>
 * public void initGoListener_Honda() {
 * FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
 * <p>
 * transaction.replace(contentLayoutId, new FragmentHonda(contentLayoutId));
 * transaction.commit();
 * }
 * <p>
 * public void initGoListener_Yamaha() {
 * FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
 * <p>
 * transaction.replace(contentLayoutId, new FragmentYamaha(contentLayoutId));
 * transaction.commit();
 * }
 * <p>
 * public void initGoListener_Suzuki() {
 * FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
 * <p>
 * transaction.replace(contentLayoutId, new FragmentSuzuki(contentLayoutId));
 * transaction.commit();
 * }
 * <p>
 * public void initGoListener_Kawasaki() {
 * FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
 * <p>
 * transaction.replace(contentLayoutId, new FragmentKawasaki(contentLayoutId));
 * transaction.commit();
 * }
 * <p>
 * public enum FragmentType {
 * Honda,
 * Yamaha,
 * Suzuki,
 * Kawasaki
 * }
 */
