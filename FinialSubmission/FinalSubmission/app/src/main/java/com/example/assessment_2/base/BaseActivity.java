package com.example.assessment_2.base;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.assessment_2.R;
import com.jph.takephoto.app.TakePhotoFragmentActivity;

public abstract class BaseActivity extends TakePhotoFragmentActivity {

  protected TextView titleNameTv;

  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayout());
    initData();
    View titleView = this.findViewById(R.id.title_constraint);
    if (titleView != null) {
      titleNameTv = titleView.findViewById(R.id.title_name_tv);
      titleView.findViewById(R.id.back_iv).setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
          finish();
        }
      });
    }
    initView("");
  }

  protected void initData(){}

  protected abstract int getLayout();

  public void initView(String titleName) {
    if (titleNameTv != null) {
      titleNameTv.setText(titleName);
    }
  }
}
