package com.example.assessment_2.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assessment_2.R;
import com.example.assessment_2.base.BaseActivity;
import com.example.assessment_2.base.BaseResponse;
import com.example.assessment_2.model.RegisterRequest;
import com.example.assessment_2.util.HttpUtil;
import com.example.assessment_2.util.OkHttpManager;

public class RegisterActivity extends BaseActivity {
  private EditText userNameEt;
  private EditText passwordEt;
  private EditText passwordAgainEt;

  public void initView(String titleName) {
    super.initView("Register");
    userNameEt = this.findViewById(R.id.user_name_et);
    passwordEt = this.findViewById(R.id.user_password_et);
    passwordAgainEt = this.findViewById(R.id.user_password_again);
    this.findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        String userName = userNameEt.getText().toString();
        String password = passwordEt.getText().toString();
        String password2 = passwordAgainEt.getText().toString();
        if (userName.isEmpty()) {
          Toast.makeText(RegisterActivity.this, "Input username", Toast.LENGTH_SHORT).show();
          return;
        }
        if (password.isEmpty()) {
          Toast.makeText(RegisterActivity.this, "Input password", Toast.LENGTH_SHORT).show();
          return;
        }
        if (password2.isEmpty()) {
          Toast.makeText(RegisterActivity.this, "Input password again", Toast.LENGTH_SHORT).show();
          return;
        }
        if (!password.equals(password2)) {
          Toast.makeText(RegisterActivity.this, "The password is name same", Toast.LENGTH_SHORT).show();
          return;
        }
        register(userName, password);
      }
    });
  }

  private void register(String userName, String password) {
    RegisterRequest request = new RegisterRequest();
    request.username = userName;
    request.password = password;
    new OkHttpManager(this, HttpUtil.REGISTER, request, BaseResponse.class, true, new OkHttpManager.ResponseCallback() {
      public void onError(int errorType, int errorCode, String errorMsg) {
        Toast.makeText(RegisterActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
      }

      public void onSuccess(Object response) {
        Toast.makeText(RegisterActivity.this, "Register successfully", Toast.LENGTH_SHORT).show();
        finish();
      }
    }).NetRequest();
  }

  protected int getLayout() {
    return R.layout.activity_register;
  }
}
