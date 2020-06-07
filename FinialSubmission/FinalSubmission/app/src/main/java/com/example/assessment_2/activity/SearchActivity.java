package com.example.assessment_2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.assessment_2.R;
import com.example.assessment_2.model.AppDataFactory;
import com.example.assessment_2.model.BrandItem;

import java.util.List;

public class SearchActivity extends Activity {
    private SearchView searchView;
    private ListView listView;
    private ImageView btn_back;
    private List<BrandItem> brandItems = AppDataFactory.getBrandList();
    //定义自动完成的列表
    private final String[] mStrings = {"CB500X","Fire Blade","CB650R","Africa Twin","MT09","YZF-R1","V-Storm650","Hayabusa","Ninja400","ZX-10R","Z900","H2"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_search);
        listView = findViewById(R.id.lv);
        btn_back = findViewById(R.id.iv_go_back);
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.item_searchlist,mStrings);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.sv);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Input the Motorcycle you want");

        initOnQueryTextLister(adapter);
        initBackClickListener();
    }

    private void initOnQueryTextLister(ArrayAdapter adapter) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

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
          adapter.getFilter().filter(newText.toString());
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
