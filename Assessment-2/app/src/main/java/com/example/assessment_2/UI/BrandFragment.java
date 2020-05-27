package com.example.assessment_2.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.assessment_2.R;
import com.example.assessment_2.SideBar;

import java.util.ArrayList;
import java.util.List;

public class BrandFragment extends Fragment {

    private int contentLayoutId;
    private SideBar indexBar;
    private TextView textViewDialog;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //返回当前页面容器layout
        View view = inflater.inflate(R.layout.page_brand, null);
        recyclerView = view.findViewById(R.id.recyclerView);


        List<String> list = new ArrayList<>();

        list.add("SJ1hao");
        list.add("SJ2hao");
        list.add("SJ3hao");
        list.add("SJ4hao");
        list.add("SJ5hao");
        list.add("SJ6hao");
        list.add("SJ7hao");
        list.add("SJ8hao");

        DemoAdapter adapter = new DemoAdapter(R.layout.item_brand,list);

        //获取layout结构设置 决定布局属性
//        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        //为recyclerview设置适配器
        recyclerView.setAdapter(adapter);

        return view;
    }

    public class DemoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public DemoAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            TextView tvItemName = helper.getView(R.id.tv_item_name);
            tvItemName.setText(item);
        }


        /**
         @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.page_brand, null);
         View recyclerView = view.findViewById(R.id.recyclerView);
         initRecyclerView();
         return view;
         }

         private void initRecyclerView() {
         //create list
         ArrayList<BrandBean> newsList = getList();
         //create adapter
         GeneralAdapter adapter = new GeneralAdapter(getActivity(), newsList);
         //create layoutManager
         LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
         //config recyclerView
         recyclerView.setLayoutManager(layoutManager);
         recyclerView.setAdapter(adapter);
         }
         //set brand
         private ArrayList<BrandBean> getList() {
         ArrayList<BrandBean> brandList = new ArrayList<>();
         BrandBean Honda = new BrandBean();
         Honda.setImgResource(R.drawable.ic_honda);
         Honda.setBrandTitle("Honda");
         Honda.fragmentType = FragmentType.Honda;

         BrandBean Yamaha = new BrandBean();
         Honda.setImgResource(R.drawable.ic_yamaha);
         Honda.setBrandTitle("Honda");
         Honda.fragmentType = FragmentType.Yamaha;

         BrandBean Suzuki = new BrandBean();
         Honda.setImgResource(R.drawable.ic_suzuki);
         Honda.setBrandTitle("Honda");
         Honda.fragmentType = FragmentType.Suzuki;

         BrandBean Kawasaki = new BrandBean();
         Honda.setImgResource(R.drawable.ic_kawasaki);
         Honda.setBrandTitle("Honda");
         Honda.fragmentType = FragmentType.Kawasaki;

         brandList.add(Honda);
         brandList.add(Yamaha);
         brandList.add(Suzuki);
         brandList.add(Kawasaki);

         return brandList;
         }

         public class GeneralAdapter extends RecyclerView.Adapter<GeneralAdapter.MyViewHolder> {

         Context context;
         List<BrandBean> datas;

         public GeneralAdapter(Context context, List<BrandBean> datas) {
         this.context = context;
         this.datas = datas;
         }

         @NonNull
         @Override public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

         View v = View.inflate(context, R.layout.item_brand, null);
         return new MyViewHolder(v);
         }

         @Override public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
         holder.textView.setText(datas.get(position).getBrandTitle());
         holder.imageView.setImageResource(datas.get(position).getImgResource());

         holder.textView.setOnClickListener(new View.OnClickListener() {
         @Override public void onClick(View view) {
         int pos = holder.getLayoutPosition();
         if(pos==0){initGoListener_Honda();}//Honda
         else if(pos==1){initGoListener_Yamaha();}//Yamaha
         else if(pos==2){initGoListener_Suzuki();}//Suzuki
         else if(pos==3){initGoListener_Kawasaki();}//Kawasaki
         }
         });
         }

         @Override public int getItemCount() {
         return datas.size();
         }

         class MyViewHolder extends RecyclerView.ViewHolder {
         TextView textView;
         ImageView imageView;

         public MyViewHolder(View itemView) {
         super(itemView);
         textView = itemView.findViewById(R.id.text);
         imageView = itemView.findViewById(R.id.image);
         }
         }
         }

         public class BrandBean {
         public int imgResource;
         public String brandTitle;
         public FragmentType fragmentType;

         public int getImgResource() {
         return imgResource;
         }

         public void setImgResource(int imgResource) {
         this.imgResource = imgResource;
         }

         public String getBrandTitle() {
         return brandTitle;
         }

         public void setBrandTitle(String brandTitle) {
         this.brandTitle = brandTitle;
         }

         }

         public void initGoListener_Honda() {
         FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

         transaction.replace(contentLayoutId, new FragmentHonda(contentLayoutId));
         transaction.commit();
         }

         public void initGoListener_Yamaha() {
         FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

         transaction.replace(contentLayoutId, new FragmentYamaha(contentLayoutId));
         transaction.commit();
         }

         public void initGoListener_Suzuki() {
         FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

         transaction.replace(contentLayoutId, new FragmentSuzuki(contentLayoutId));
         transaction.commit();
         }

         public void initGoListener_Kawasaki() {
         FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

         transaction.replace(contentLayoutId, new FragmentKawasaki(contentLayoutId));
         transaction.commit();
         }

         public enum FragmentType {
         Honda,
         Yamaha,
         Suzuki,
         Kawasaki
         }*/
    }
}
