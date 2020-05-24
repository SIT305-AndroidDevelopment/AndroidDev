package com.example.assessment_2.UI;

import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.assessment_2.R;
import com.example.assessment_2.SideBar;

public class BrandFragment extends Fragment {

    private int contentLayoutId;
    private SideBar indexBar;
    private TextView textViewDialog;
    private RecyclerView recyclerView;

    public class DemoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        /**
         * 构造方法，此示例中，在实例化Adapter时就传入了一个List。
         * 如果后期设置数据，不需要传入初始List，直接调用 super(layoutResId); 即可
         */
        public DemoAdapter(list List<String>) {
            super(R.layout.page_brand, list);
        }

        /**
         * 在此方法中设置item数据
         */
        @Override
        protected void convert(@NotNull BaseViewHolder helper, @NotNull String item) {
            helper.setText(R.id.recyclerView, "This is an Item, pos: " + (helper.getAdapterPosition() - getHeaderLayoutCount()));
        }
    }
    /**
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View v = View.inflate(context, R.layout.item_brand, null);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
            holder.textView.setText(datas.get(position).getBrandTitle());
            holder.imageView.setImageResource(datas.get(position).getImgResource());

            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    if(pos==0){initGoListener_Honda();}//Honda
                    else if(pos==1){initGoListener_Yamaha();}//Yamaha
                    else if(pos==2){initGoListener_Suzuki();}//Suzuki
                    else if(pos==3){initGoListener_Kawasaki();}//Kawasaki
                }
            });
        }

        @Override
        public int getItemCount() {
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
