package com.example.assessment_2.unuse;

import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import com.example.assessment_2.MainActivity;

import java.util.List;

class ContentAdapter extends PagerAdapter {

    private final FragmentManager supportFragmentManager;
    private MainActivity act;
    private List<View> views;

    public ContentAdapter(MainActivity mainActivity, List<View> views) {
        this.views = views;
        this.act = mainActivity;
        supportFragmentManager = act.getSupportFragmentManager();
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        container.addView(view);
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

}

//public class MyPagerAdapter extends PagerAdapter {
//
//    private List<View> viewList = new ArrayList<View>();
//
//    public MyPagerAdapter(List<View> viewList) {
//        this.viewList = viewList;
//    }
//
//    @NonNull
//    @Override
//    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        container.addView(viewList.get(position));
//        return viewList.get(position);
//    }
//
//    @Override
//    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        container.removeView(viewList.get(position));
//    }
//
//    @Override
//    public int getCount() {
//        return viewList.size();
//    }
//
//    @Override
//    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//        return view == object;
//    }
//}



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
 * Honda.setBrandTitle(AppConstant.BRAND_HONDA);
 * Honda.fragmentType = FragmentType.Honda;
 * <p>
 * BrandBean Yamaha = new BrandBean();
 * Honda.setImgResource(R.drawable.ic_yamaha);
 * Honda.setBrandTitle(AppConstant.BRAND_HONDA);
 * Honda.fragmentType = FragmentType.Yamaha;
 * <p>
 * BrandBean Suzuki = new BrandBean();
 * Honda.setImgResource(R.drawable.ic_suzuki);
 * Honda.setBrandTitle(AppConstant.BRAND_HONDA);
 * Honda.fragmentType = FragmentType.Suzuki;
 * <p>
 * BrandBean Kawasaki = new BrandBean();
 * Honda.setImgResource(R.drawable.ic_kawasaki);
 * Honda.setBrandTitle(AppConstant.BRAND_HONDA);
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

//        adapter.notifyDataSetChanged();

//        Gson gson = new Gson();
//        StoreBeanList jsonBean = gson.fromJson(AppData.STORE_INFO, StoreBeanList.class);
//        List<StoreBean> storeBeanList = jsonBean.getStoreBeanList();
//        StoreBean storeBean = storeBeanList.get(3);