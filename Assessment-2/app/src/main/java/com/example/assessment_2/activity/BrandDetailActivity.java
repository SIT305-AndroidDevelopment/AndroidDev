package com.example.assessment_2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.assessment_2.MainActivity;
import com.example.assessment_2.R;

public class BrandDetailActivity extends Activity {

    private String brandName;
    private ImageView btn_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_brand_detail);

        brandName = getIntent().getStringExtra("brandName");
        btn_back = findViewById(R.id.iv_go_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BrandDetailActivity.this , MainActivity.class);
                startActivityForResult(intent, 0);
                finish();
            }
        });
    }
}
