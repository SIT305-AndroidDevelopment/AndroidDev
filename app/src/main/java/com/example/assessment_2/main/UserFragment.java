package com.example.assessment_2.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.assessment_2.R;
import com.example.assessment_2.util.SharePreferenceUtil;

public class UserFragment extends Fragment {
    private static final String TAG = "UserFragment";
    View view;
    Button btn_upload;
    ImageView iv_userBike;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.page_user_detail, container, false);
        btn_upload = view.findViewById(R.id.btn_upload);
        iv_userBike = view.findViewById(R.id.iv_userBike);

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


        initPermissions(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        initUploadClickListener();
        return view;
    }

    private void initUploadClickListener() {
        btn_upload.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, 2);

        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
//                iv_userBike.setImageURI(uri);
                Glide.with(getActivity())
                        .load(uri)
                        .into(iv_userBike);
                SharePreferenceUtil.setString(getActivity(), "imgUpload", uri.toString());
            }
        }
    }

    /**
     * 授权管理,并打开相册或开启相机
     */
    public static void initPermissions(Activity ctx, String permissionName) {
        if (ContextCompat.checkSelfPermission(ctx, permissionName) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "需要授权 ");
            if (ActivityCompat.shouldShowRequestPermissionRationale(ctx, permissionName)) {
                Log.i(TAG, "拒绝过了");
            } else {
                Log.i(TAG, "进行授权");
                ActivityCompat.requestPermissions(ctx, new String[]{permissionName}, 1000);
            }
        } else {
            Log.i(TAG, "不需要授权 ");
        }
    }
}
