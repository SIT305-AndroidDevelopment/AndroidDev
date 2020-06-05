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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.assessment_2.R;
import com.example.assessment_2.util.HttpUtil;
import com.example.assessment_2.util.SharePreferenceUtil;
import com.yanzhenjie.nohttp.Binary;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import static com.luck.picture.lib.tools.PictureFileUtils.isExternalStorageDocument;
import static com.yalantis.ucrop.util.FileUtils.getDataColumn;
import static com.yalantis.ucrop.util.FileUtils.isDownloadsDocument;
import static com.yalantis.ucrop.util.FileUtils.isMediaDocument;

public class UserFragment extends Fragment {

    private static final String TAG = "UserFragment";
    View view;
    Button btn_upload;
    Button text_upload;
    ImageView iv_userBike;
    EditText editText;
    Integer count=0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.page_user_detail, container, false);
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

        initEasterEgg();
        initPermissions(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        initUploadClickListener();
        return view;
    }

    private void initEasterEgg() {
        iv_userBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if (count >10)
                {
                    iv_userBike.setImageResource(R.drawable.easter_egg);
                    count=0;
                }
            }
        });
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

        String postUrl = HttpUtil.HOST + "api/user/text";
        //1.创建一个队列
        RequestQueue queue = NoHttp.newRequestQueue();
        //2.创建消息请求   参数1:String字符串,传网址  参数2:请求方式
        final Request<JSONObject> request = NoHttp.createJsonObjectRequest(postUrl, RequestMethod.POST);
        //3.利用队列去添加消息请求
        //使用request对象添加上传的对象添加键与值,post方式添加上传的数据
        request.add("text", text);

        queue.add(1, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject res = response.get();
                try {
                    if (res.getInt("status") == 0) {
                        Toast.makeText(getContext(), "文字上传成功", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "文件上传失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
            }

            @Override
            public void onFinish(int what) {

            }
        });
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
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            //一些三方的文件浏览器会进入到这个方法中，例如ES
            //QQ文件管理器不在此列

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
     * 上传文件
     */
    private void uploadFile(String path) {
        String postUrl = HttpUtil.HOST + "api/user/upload";
        //1.创建一个队列
        RequestQueue queue = NoHttp.newRequestQueue();
        //2.创建消息请求   参数1:String字符串,传网址  参数2:请求方式
        final Request<JSONObject> request = NoHttp.createJsonObjectRequest(postUrl, RequestMethod.POST);
        //3.利用队列去添加消息请求
        //使用request对象添加上传的对象添加键与值,post方式添加上传的数据
        Binary file = new FileBinary(new File(path));
        request.add("file", file);

        queue.add(1, request, new OnResponseListener<JSONObject>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject res = response.get();
                try {
                    if (res.getInt("status") == 0) {
                        String data = res.getString("data");
                        Glide.with(getActivity())
                                .load(path)
                                .into(iv_userBike);
                        SharePreferenceUtil.setString(getActivity(), "imgUpload", data);
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "文件上传失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
            }

            @Override
            public void onFinish(int what) {

            }
        });
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

//    private static final String TAG = "UserFragment";
//    View view;
//    Button btn_upload;
//    ImageView iv_userBike;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.page_user_detail, container, false);
//        btn_upload = view.findViewById(R.id.btn_upload);
//        iv_userBike = view.findViewById(R.id.iv_userBike);
//
//        String path = SharePreferenceUtil.getString(getActivity(), "imgUpload", "");
//        if (!TextUtils.isEmpty(path)) {
//            Glide.with(getActivity())
//                    .load(path)
//                    .into(iv_userBike);
//        } else {
//            Glide.with(getActivity())
//                    .load(R.drawable.banner4)
//                    .into(iv_userBike);
//        }
//
//
//        initPermissions(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        initUploadClickListener();
//        return view;
//    }
//
//    private void initUploadClickListener() {
//        btn_upload.setOnClickListener(view -> {
//            Intent intent = new Intent(Intent.ACTION_PICK, null);
//            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//            startActivityForResult(intent, 2);
//
//        });
//    }
//
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 2) {
//            // 从相册返回的数据
//            if (data != null) {
//                // 得到图片的全路径
//                Uri uri = data.getData();
////                iv_userBike.setImageURI(uri);
//                Glide.with(getActivity())
//                        .load(uri)
//                        .into(iv_userBike);
//                SharePreferenceUtil.setString(getActivity(), "imgUpload", uri.toString());
//            }
//        }
//    }
//
//    /**
//     * 授权管理,并打开相册或开启相机
//     */
//    public static void initPermissions(Activity ctx, String permissionName) {
//        if (ContextCompat.checkSelfPermission(ctx, permissionName) != PackageManager.PERMISSION_GRANTED) {
//            Log.i(TAG, "需要授权 ");
//            if (ActivityCompat.shouldShowRequestPermissionRationale(ctx, permissionName)) {
//                Log.i(TAG, "拒绝过了");
//            } else {
//                Log.i(TAG, "进行授权");
//                ActivityCompat.requestPermissions(ctx, new String[]{permissionName}, 1000);
//            }
//        } else {
//            Log.i(TAG, "不需要授权 ");
//        }
//    }
}
