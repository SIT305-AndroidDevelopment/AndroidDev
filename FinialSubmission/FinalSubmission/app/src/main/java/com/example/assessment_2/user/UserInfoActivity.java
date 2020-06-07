package com.example.assessment_2.user;

import android.Manifest;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.assessment_2.R;
import com.example.assessment_2.base.BaseActivity;
import com.example.assessment_2.base.BaseResponse;
import com.example.assessment_2.model.UpdateUserInfoRequest;
import com.example.assessment_2.model.UploadFileResponse;
import com.example.assessment_2.model.UserInfo;
import com.example.assessment_2.util.HttpUtil;
import com.example.assessment_2.util.OkHttpManager;
import com.example.assessment_2.util.SelectorImgPopwindow;
import com.example.assessment_2.util.UserInfoManager;
import com.jph.takephoto.model.TResult;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class UserInfoActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

  private ImageView headerIv;
  private EditText nikeNameEt;
  private String headerPath;
  private UserInfo userInfo;

  protected void initData() {
    super.initData();
    userInfo = UserInfoManager.getInstance().getUserInfo();
    headerPath = userInfo.avatar;
  }

  public void initView(String titleName) {
    super.initView("Basic Information");
    headerIv = this.findViewById(R.id.user_header_iv);
    if (headerPath == null || headerPath.isEmpty()) {
      headerIv.setImageResource(R.mipmap.ic_launcher);
    } else {
      Glide.with(this).load(headerPath).into(headerIv);
    }
    nikeNameEt = this.findViewById(R.id.user_name_et);
    nikeNameEt.setText(userInfo.nickname == null ? "" : userInfo.nickname);
    headerIv.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        requestPermission();
      }
    });
    this.findViewById(R.id.submit_btn).setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        String nickName = nikeNameEt.getText().toString();
        if (nickName.isEmpty()) {
          Toast.makeText(UserInfoActivity.this, "it cannot be blank", Toast.LENGTH_SHORT).show();
          return;
        }
        if (headerPath != null && !headerPath.isEmpty() && !headerPath.startsWith("http")) {
          //Avatar need to be replaced
          uploadFile(headerPath);
        } else {
          updateUserInfo();
        }
      }
    });

  }

  private void updateUserInfo() {
    UpdateUserInfoRequest request = new UpdateUserInfoRequest();
    request.id = userInfo.id;
    request.avatar = headerPath;
    request.nickname = nikeNameEt.getText().toString();
    new OkHttpManager(UserInfoActivity.this, HttpUtil.UPDATE_USER, request, BaseResponse.class, true, new OkHttpManager.ResponseCallback() {
      public void onError(int errorType, int errorCode, String errorMsg) {
        Toast.makeText(UserInfoActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
      }

      public void onSuccess(Object response) {
        if (response != null) {
          //Information updated successfully
          userInfo.avatar = headerPath;
          userInfo.nickname = nikeNameEt.getText().toString();
          UserInfoManager.getInstance().saveUserInfo(userInfo);
          finish();
        }
      }
    }).NetRequest();
  }

  public void takeSuccess(TResult result) {
    super.takeSuccess(result);
    headerPath = result.getImages().get(0).getCompressPath();
    Glide.with(UserInfoActivity.this).load(headerPath).into(headerIv);
  }

  protected int getLayout() {
    return R.layout.activity_user_info;
  }

  /**
   * upload file
   */
  private void uploadFile(String path) {
    new OkHttpManager("file", System.currentTimeMillis() + ".jpg", this, HttpUtil.UPLOAD_FILE,
        new File(path), null, UploadFileResponse.class, true, new OkHttpManager.ResponseCallback() {
      public void onError(int errorType, int errorCode, String errorMsg) {
        Toast.makeText(UserInfoActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
      }

      public void onSuccess(Object response) {
        if (response != null && response instanceof UploadFileResponse) {
          headerPath = ((UploadFileResponse) response).data;
          updateUserInfo();
        }
      }
    }).NetUploadFile();
  }

  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
  }

  private void requestPermission() {
    String[] perms = {
        Manifest.permission.CAMERA,
        android.Manifest.permission.READ_PHONE_STATE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    if (!EasyPermissions.hasPermissions(this, perms)) {
      EasyPermissions.requestPermissions(this, "需要权限", 112, perms);
    } else {
      SelectorImgPopwindow.getInstance().showPop(UserInfoActivity.this, getTakePhoto(),
          true, true, 1024, 800, 800);
    }
  }

  public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
    SelectorImgPopwindow.getInstance().showPop(UserInfoActivity.this, getTakePhoto(),
        true, true, 1024, 600, 600);
  }

  public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

  }
}
