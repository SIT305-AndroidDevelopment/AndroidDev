package com.example.assessment_2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.assessment_2.R;

public class SearchActivity extends Activity {
    private SearchView searchView;
    private ListView listView;
    private Button btn_back;
    //定义自动完成的列表
    private final String[] mStrings = {"CB500x","CB1100","CB650R","Africa Twin","MT09","RZF-R1","V-Storm650","Hayabusa","Ninja400","ZX-10R","Z900","H2"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_search);
        listView = findViewById(R.id.lv);
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
