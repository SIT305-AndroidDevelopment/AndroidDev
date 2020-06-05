package com.example.assessment_2.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.assessment_2.R;

public class UserDetailActivity extends Activity {

    private Button btn_upload;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_user_detail);
        btn_upload=findViewById(R.id.btn_upload);

        initUploadClickListen();
    }

    private void initUploadClickListen() {
        btn_upload.setOnClickListener(view -> {
            finish();
        });
    }

}
