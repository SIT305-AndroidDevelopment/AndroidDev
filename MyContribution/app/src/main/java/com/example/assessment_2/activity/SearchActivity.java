package com.example.assessment_2.activity;

import android.app.Activity;
<<<<<<< HEAD
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
=======
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
>>>>>>> 882321405689a782c1a8835c5cca03894cc57371
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.assessment_2.R;
<<<<<<< HEAD
import com.example.assessment_2.model.AppDataFactory;
import com.example.assessment_2.model.BrandItem;

import java.util.List;
=======
>>>>>>> 882321405689a782c1a8835c5cca03894cc57371

public class SearchActivity extends Activity {
    private SearchView searchView;
    private ListView listView;
<<<<<<< HEAD
    private ImageView btn_back;
    private List<BrandItem> brandItems = AppDataFactory.getBrandList();
    //定义自动完成的列表
    private final String[] mStrings = {"CB500X","Fire Blade","CB650R","Africa Twin","MT09","YZF-R1","V-Storm650","Hayabusa","Ninja400","ZX-10R","Z900","H2"};
=======
    private Button btn_back;
    //定义自动完成的列表
    private final String[] mStrings = {"CB500x","CB1100","CB650R","Africa Twin","MT09","RZF-R1","V-Storm650","Hayabusa","Ninja400","ZX-10R","Z900","H2"};
>>>>>>> 882321405689a782c1a8835c5cca03894cc57371
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_search);
        listView = findViewById(R.id.lv);
<<<<<<< HEAD
        btn_back = findViewById(R.id.iv_go_back);
=======
>>>>>>> 882321405689a782c1a8835c5cca03894cc57371
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.item_searchlist,mStrings);
        listView.setAdapter(adapter);
        //为ListView启动过滤
        listView.setTextFilterEnabled(true);
        searchView = (SearchView) findViewById(R.id.sv);
        //设置SearchView自动缩小为图标
        searchView.setIconifiedByDefault(false);//设为true则搜索栏 缩小成俄日一个图标点击展开
        //设置该SearchView显示搜索按钮
        searchView.setSubmitButtonEnabled(true);
        //设置默认提示文字
        searchView.setQueryHint("Input the Motorcycle you want");
        //配置监听器

        initOnQueryTextLister(adapter);
        initBackClickListener();
    }

    private void initOnQueryTextLister(ArrayAdapter adapter) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //点击搜索按钮时触发
            @Override
            public boolean onQueryTextSubmit(String query) {
                //此处添加查询开始后的具体时间和方法
<<<<<<< HEAD

                Intent intent = new Intent(SearchActivity.this, BikeDetailActivity.class);
                Bundle bundle = new Bundle();

                switch(query) {
                    case "CB500X":
                        bundle.putSerializable("MotorItem", brandItems.get(0).getMotoList().get(0));
                        break;
                    case "Africa Twin":
                        bundle.putSerializable("MotorItem", brandItems.get(0).getMotoList().get(1));
                        break;
                    case "Fire Blade":
                        bundle.putSerializable("MotorItem", brandItems.get(0).getMotoList().get(2));
                        break;
                    case "CB650R":
                        bundle.putSerializable("MotorItem", brandItems.get(0).getMotoList().get(3));
                        break;
                    case "MT09":
                        bundle.putSerializable("MotorItem", brandItems.get(1).getMotoList().get(1));
                        break;
                    case "YZF-R1":
                        bundle.putSerializable("MotorItem", brandItems.get(1).getMotoList().get(0));
                        break;
                    case "V-Storm650":
                        bundle.putSerializable("MotorItem", brandItems.get(2).getMotoList().get(0));
                        break;
                    case "Hayabusa":
                        bundle.putSerializable("MotorItem", brandItems.get(2).getMotoList().get(1));
                        break;
                    case "Ninja400":
                        bundle.putSerializable("MotorItem", brandItems.get(3).getMotoList().get(0));
                        break;
                    case "ZX-10R":
                        bundle.putSerializable("MotorItem", brandItems.get(3).getMotoList().get(1));
                        break;
                    case "Z900":
                        bundle.putSerializable("MotorItem", brandItems.get(3).getMotoList().get(2));
                        break;
                    case "H2":
                        bundle.putSerializable("MotorItem", brandItems.get(3).getMotoList().get(3));
                        break;
                }

                intent.putExtras(bundle);
                SearchActivity.this.startActivity(intent);

=======
>>>>>>> 882321405689a782c1a8835c5cca03894cc57371
                Toast.makeText(SearchActivity.this,"you choose:" + query,Toast.LENGTH_SHORT).show();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //如果newText长度不为0
                if (TextUtils.isEmpty(newText)){
                    listView.clearTextFilter();
                }else{
                    //listView.setFilterText(newText);
          adapter.getFilter().filter(newText.toString());//替换成本句后消失黑框！！！
                }
                return true;
            }
        });
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Object string = adapter.getItem(position);
            searchView.setQuery(string.toString(),true);
        });
    }

    private void initBackClickListener() {
        btn_back.setOnClickListener(view -> {
            finish();
        });
    }
}
