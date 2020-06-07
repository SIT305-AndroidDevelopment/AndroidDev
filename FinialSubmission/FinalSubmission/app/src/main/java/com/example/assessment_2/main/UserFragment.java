package com.example.assessment_2.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.assessment_2.R;
import com.example.assessment_2.activity.AttentionActivity;
import com.example.assessment_2.activity.CollectAndFootprintActivity;
import com.example.assessment_2.base.BaseResponse;
import com.example.assessment_2.login.LoginActivity;
import com.example.assessment_2.model.TextRequest;
import com.example.assessment_2.model.UploadFileResponse;
import com.example.assessment_2.model.UserInfo;
import com.example.assessment_2.user.UserInfoActivity;
import com.example.assessment_2.util.HttpUtil;
import com.example.assessment_2.util.OkHttpManager;
import com.example.assessment_2.util.SharePreferenceUtil;
import com.example.assessment_2.util.UserInfoManager;
import com.example.assessment_2.view.CircleImageView;

import java.io.File;

import static com.luck.picture.lib.tools.PictureFileUtils.getDataColumn;
import static com.luck.picture.lib.tools.PictureFileUtils.isDownloadsDocument;
import static com.luck.picture.lib.tools.PictureFileUtils.isExternalStorageDocument;
import static com.luck.picture.lib.tools.PictureFileUtils.isMediaDocument;

public class UserFragment extends Fragment {
  private static final String TAG = "UserFragment";
  private TextView mBtnLogin;
  private View userInfoView;
  private CircleImageView userHeaderIv;
  private TextView userNameTv;
  private TextView editUserInfoBtn;
  private Button logoutBtn;
  Button btn_upload;
  Button text_upload;
  ImageView iv_userBike;
  EditText editText;

  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.page_user_detail, container, false);

    btn_upload = view.findViewById(R.id.btn_upload);
    text_upload = view.findViewById(R.id.text_upload);
    iv_userBike = view.findViewById(R.id.iv_userBike);
    editText = view.findViewById(R.id.edit_content);
    String path = SharePreferenceUtil.getString(getActivity(), "imgUpload", "");
    if (!TextUtils.isEmpty(path)) {
      Glide.with(getActivity())
          .load(path)
          .into(iv_userBike);
    } else {
      Glide.with(getActivity())
          .load(R.drawable.banner4)
          .into(iv_userBike);
    }

    userHeaderIv = view.findViewById(R.id.user_header_iv);
    userNameTv = view.findViewById(R.id.user_name_tv);
    editUserInfoBtn = view.findViewById(R.id.edit_user_tv);
    editUserInfoBtn.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent mIntent = new Intent(getActivity(), UserInfoActivity.class);
        startActivity(mIntent);
      }
    });
    logoutBtn = view.findViewById(R.id.logout_btn);
    logoutBtn.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        UserInfoManager.getInstance().saveUserInfo(null);
        changeUi();
      }
    });
    view.findViewById(R.id.attention_constraint).setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        if (!isLogin()) {
          Toast.makeText(getActivity(), "Please Log in first", Toast.LENGTH_SHORT).show();
          return;
        }
        Intent mIntent = new Intent(getActivity(), AttentionActivity.class);
        getActivity().startActivity(mIntent);
      }
    });
    userInfoView = view.findViewById(R.id.user_info_constraint);
    userInfoView.setVisibility(View.GONE);
    mBtnLogin = view.findViewById(R.id.login_tv);
    mBtnLogin.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent mIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(mIntent);
      }
    });

    view.findViewById(R.id.collect_constraint).setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        if (!isLogin()) {
          Toast.makeText(getActivity(), "Please Log in first", Toast.LENGTH_SHORT).show();
          return;
        }
        Intent mIntent = new Intent(getActivity(), CollectAndFootprintActivity.class);
        mIntent.putExtra("TYPE", 0);
        getActivity().startActivity(mIntent);
      }
    });

    view.findViewById(R.id.footprint_constraint).setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        if (!isLogin()) {
          Toast.makeText(getActivity(), "Please Log in first", Toast.LENGTH_SHORT).show();
          return;
        }
        Intent mIntent = new Intent(getActivity(), CollectAndFootprintActivity.class);
        mIntent.putExtra("TYPE", 1);
        getActivity().startActivity(mIntent);
      }
    });
    initPermissions(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
    initUploadClickListener();
    return view;
  }

  private boolean isLogin() {
    if (UserInfoManager.getInstance().getUserInfo() != null && UserInfoManager.getInstance().getUserInfo().id != null) {
      return true;
    }
    return false;
  }

  public void onResume() {
    super.onResume();
    changeUi();
  }

  private void changeUi() {
    UserInfo userInfo = UserInfoManager.getInstance().getUserInfo();
    userInfoView.setVisibility(userInfo == null ? View.GONE : View.VISIBLE);
    logoutBtn.setVisibility(userInfo == null ? View.GONE : View.VISIBLE);
    mBtnLogin.setVisibility(userInfo == null ? View.VISIBLE : View.GONE);
    if (userInfo != null && userInfo.id != null) {
      userNameTv.setText(userInfo.nickname);
      if (userInfo.avatar != null) {
        Glide.with(getActivity()).load(userInfo.avatar).into(userHeaderIv);
      } else {
        userHeaderIv.setImageResource(R.drawable.ic_avatar);
      }
    } else {
      userHeaderIv.setImageResource(R.drawable.ic_avatar);
    }
  }

  private void initUploadClickListener() {
    btn_upload.setOnClickListener(view -> {
      Intent intent = new Intent(Intent.ACTION_PICK, null);
      intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
      startActivityForResult(intent, 2);

    });
    text_upload.setOnClickListener(view -> {
      uploadContent(editText.getText().toString());
    });
  }

  private void uploadContent(String text) {
    if (!isLogin()) {
      Toast.makeText(getActivity(), "Please Log in first", Toast.LENGTH_SHORT).show();
      return;
    }
    TextRequest request = new TextRequest();
    request.text = text;
    new OkHttpManager(getContext(), HttpUtil.TEXT, request, BaseResponse.class, false, new OkHttpManager.ResponseCallback() {
      public void onError(int errorType, int errorCode, String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
      }

      public void onSuccess(Object response) {
        Toast.makeText(getActivity(), "Upload successful", Toast.LENGTH_SHORT).show();
      }
    }).NetRequest();
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == 2) {
      // 从相册返回的数据
      if (data != null) {
        // 得到图片的全路径
        Uri uri = data.getData();
//                iv_userBike.setImageURI(uri);
        String path = getPath(getActivity(), uri);
        uploadFile(path);
      }
    }
  }

  /**
   * Obtain absolute file path from Uri designed for Android4.4
   */
  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  public static String getPath(final Context context, final Uri uri) {

    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

      if (isExternalStorageDocument(uri)) {
        final String docId = DocumentsContract.getDocumentId(uri);
        final String[] split = docId.split(":");
        final String type = split[0];
        if ("primary".equalsIgnoreCase(type)) {
          return Environment.getExternalStorageDirectory() + "/" + split[1];
        }
      }
      // DownloadsProvider
      else if (isDownloadsDocument(uri)) {
      }
      // MediaProvider
      else if (isMediaDocument(uri)) {
      }
    } else if ("content".equalsIgnoreCase(uri.getScheme())) {// MediaStore (and general)
      return getDataColumn(context, uri, null, null);
    } else if ("file".equalsIgnoreCase(uri.getScheme())) {// File
    }
    return null;
  }

  /**
   * Upload
   */
  private void uploadFile(String path) {
    if (!isLogin()) {
      Toast.makeText(getActivity(), "Please Log in first", Toast.LENGTH_SHORT).show();
      return;
    }
    new OkHttpManager("file", System.currentTimeMillis() + ".jpg", getContext(), HttpUtil.UPLOAD_FILE,
        new File(path), null, UploadFileResponse.class, true, new OkHttpManager.ResponseCallback() {
      public void onError(int errorType, int errorCode, String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
      }

      public void onSuccess(Object response) {
        if (response != null && response instanceof UploadFileResponse) {
          Toast.makeText(getActivity(), "Upload Successfully", Toast.LENGTH_SHORT).show();
        }
      }
    }).NetUploadFile();
  }

  /**
   * Authorized management, and open the album or turn on the camera
   */
  public static void initPermissions(Activity ctx, String permissionName) {
    if (ContextCompat.checkSelfPermission(ctx, permissionName) != PackageManager.PERMISSION_GRANTED) {
      Log.i(TAG, "Authorization required ");
      if (ActivityCompat.shouldShowRequestPermissionRationale(ctx, permissionName)) {
        Log.i(TAG, "Rejected");
      } else {
        Log.i(TAG, "Authorize");
        ActivityCompat.requestPermissions(ctx, new String[]{permissionName}, 1000);
      }
    } else {
      Log.i(TAG, "No authorization required ");
    }
  }
}
