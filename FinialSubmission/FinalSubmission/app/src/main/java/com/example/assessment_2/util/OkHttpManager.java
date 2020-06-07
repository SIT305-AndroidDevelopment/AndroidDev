package com.example.assessment_2.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.example.assessment_2.base.BaseResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.util.Map;

import okhttp3.Call;

public class OkHttpManager {
  /*=======================Model=================*/
  private boolean isCancelNormal = false;
  private Context mContext;
  /**
   * Set timeout
   */
  private long connTimeOut = 1000 * 30;
  /**
   * Whether to display the loading dialog
   */
  private boolean isShowLoading = false;
  /**
   * OkHttp instance for each request
   */
  private RequestCall call = null;
  /**
   * The data object to be returned when the parsing is successful
   */
  private Class responseClass;
  /**
   * Requested entity class
   */
  private ResponseCallback mResponseCallback;
  /**
   * Entity class requested successfully when downloading the file
   */
  private DownLoadFileRespCallback mDownloadFileRespCallBack;
  /**
   * Url passed
   */
  private String url = "";
  private String postJson = "";
  private File file = null;
  /**
   * Path when adding pictures
   */
  private String profileImage = "";
  /**
   * File name when uploading file
   */
  private String fileName = "";
  /**
   * Loading dialog
   */
  private ProgressDialog mLoadingDialog = null;

  /**
   * Common interface request
   *
   * @param mContext          Current application context
   * @param url               Example of the interface url to request: /web-app/createActivity/viewActInfo
   *                          The default parameter type is: application/json; charset=UTF-8
   * @param request           The corresponding request entity class, when it is null, means get request
   * @param responseClass     Corresponding response entity class model
   * @param isShowLoading     Whether to display "Loading..." dialog box during request
   * @param mResponseCallback Return result of operation success and failure
   */
  public OkHttpManager(Context mContext, String url, Object request, Class responseClass,
                       boolean isShowLoading, ResponseCallback mResponseCallback) {
    this.mContext = mContext;
    this.url = url;
    this.responseClass = responseClass;
    this.isShowLoading = isShowLoading;
    this.mResponseCallback = mResponseCallback;
    if (request != null) {
      postJson = new Gson().toJson(request);
    }
  }

  /**
   * Upload pictures and files
   * Interface request
   *
   * @param profileImage      The file key when passed to the server
   * @param mContext
   * @param url
   * @param file
   * @param responseClass
   * @param isShowLoading
   * @param mResponseCallback
   */
  public OkHttpManager(String profileImage, String fileName, Context mContext, String url, File file, Object request,
                       Class responseClass, boolean isShowLoading, ResponseCallback mResponseCallback) {
    this.profileImage = profileImage;
    this.fileName = fileName;
    this.mContext = mContext;
    this.url = url;
    this.file = file;
    this.responseClass = responseClass;
    this.isShowLoading = isShowLoading;
    this.mResponseCallback = mResponseCallback;
    if (request != null) {
      postJson = new Gson().toJson(request);
    }
  }

  /**
   * Start request
   */
  public void NetRequest() {
    if (!this.check(mContext)) {  //Judging the network
      if (mResponseCallback != null) {//Interface exception
        mResponseCallback.onError(-1, 0, "Connection Broken");
      }
      return;
    }
    if (isShowLoading) {
      showNormalLoading();
    }

    try {
      if (postJson == null || TextUtils.isEmpty(postJson)) {
        call = OkHttpUtils
                .get()
                .url(url)
                .addHeader("Content_Type", "application/x-www-form-urlencoded;charset=utf-8")
                .build()
                .connTimeOut(connTimeOut);
      } else {
        /* convert Json to Map<String, String> */
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        Map<String, String> map = g.fromJson(postJson, new TypeToken<Map<String, String>>() {
        }
                .getType());
        /* Set okhttp */

        call = OkHttpUtils
                .post()
                .url(url)
                .params(map)
                .addHeader("Content_Type", "application/x-www-form-urlencoded; charset=utf-8")
                .build()
                .connTimeOut(connTimeOut);
      }
    } catch (Exception e) {
      if (mLoadingDialog != null) {
        mLoadingDialog.dismiss();
        mLoadingDialog = null;
      }
      if (mResponseCallback != null) {
        Log.e("TAG", "==okHttp error " + e.toString());
        mResponseCallback.onError(3, 0, "Unusual parameter request");//数据解析异常
      }
      return;
    }

    Log.i("TAG", "==okHttp request \n url :" + url + "\npostJson :" + postJson);
    call.execute(new StringCallback() {
      @Override
      public void onError(Call call, Exception e, int id) {
        if (mLoadingDialog != null) {
          mLoadingDialog.dismiss();
          mLoadingDialog = null;
        }
        if (isCancelNormal) {//Manually disconnect
          return;
        }
        if (e.toString().contains("SocketTimeoutException")) {
          if (mResponseCallback != null) {//Interface exception
            mResponseCallback.onError(0, id, "Connection timed out, please try again later");
          }
        } else if (e.toString().contains("UnknownHostException")) {
//                    Toast.showToast("Disconnected from the network", JJBToast.WARNING);
          if (mResponseCallback != null) {//Interface exception
            mResponseCallback.onError(-1, id, "Connection broken, please check internet");
          }
        } else {
          if (mResponseCallback != null) {//Interface exception
            mResponseCallback.onError(1, id, "No response of server");
          }
        }

      }

      @Override
      public void onResponse(String response, int id) {
        if (mLoadingDialog != null) {
          mLoadingDialog.dismiss();
          mLoadingDialog = null;
        }

        Object object = null;
        try {
          Log.i("TAG", "==okHttp responseJson " + url + ": \n" + response);

          object = new Gson().fromJson(response, responseClass);
        } catch (Exception e) {
          e.printStackTrace();
          if (mResponseCallback != null) {
            Log.e("TAG", "==okHttp error " + e.toString());
            mResponseCallback.onError(2, 0, "Data parsing exception");//Data parsing exception
          }
        }
        if (object instanceof BaseResponse) {
          BaseResponse baseResponse = (BaseResponse) object;
          if (baseResponse.status != 0) {
            mResponseCallback.onError(-1, 0, baseResponse.msg);
          } else {
            if (mResponseCallback != null && object != null) {
              mResponseCallback.onSuccess(object);
            }
          }
        } else {
          mResponseCallback.onError(2, 0, "Data parsing exception");
        }

      }
    });
  }

  /**
   * Upload file request
   */
  public void NetUploadFile() {
    if (!this.check(mContext)) {
//            JJBToast.showToast("Disconnected from the network", JJBToast.WARNING);
      if (mResponseCallback != null) {//Interface exception
        mResponseCallback.onError(-1, 0, "Internet connection broken");
      }
      return;
    }
    if (this.file == null) {
      if (mResponseCallback != null) {//Interface exception
        mResponseCallback.onError(-1, 0, "Upload failed");
      }
      return;
    }
    if (isShowLoading) {
      showNormalLoading();
    }
    try {
      GsonBuilder gb = new GsonBuilder();
      Gson g = gb.create();
      Map<String, String> map = g.fromJson(postJson, new TypeToken<Map<String, String>>() {
      }
              .getType());
      call = OkHttpUtils.post()//
              .params(map)
              .addFile(profileImage, fileName, file)
              .url(url)
//                    .params(params)//
              .addHeader("Content_Type", "application/x-www-form-urlencoded; charset=utf-8")
              .build();
    } catch (Exception e) {
      if (mLoadingDialog != null) {
        mLoadingDialog.dismiss();
        mLoadingDialog = null;
      }
      if (mResponseCallback != null) {
        Log.e("TAG", "==okHttp error " + e.toString());
        mResponseCallback.onError(3, 0, "Unusual parameter request");
      }
      return;
    }
    Log.i("TAG", "==okHttp request \n url :" + url + "\npostJson :" + postJson);
    call.execute(new StringCallback() {
      @Override
      public void onError(Call call, Exception e, int id) {
        if (mLoadingDialog != null) {
          mLoadingDialog.dismiss();
          mLoadingDialog = null;
        }
        if (isCancelNormal) {//Manually disconnect
          return;
        }
        if (e.toString().contains("SocketTimeoutException")) {//network timeout
          if (mResponseCallback != null) {//Interface exception
            mResponseCallback.onError(0, id, "Connection timed out, please try again later");
          }
        } else if (e.toString().contains("UnknownHostException")) {//Disconnected from the network
//                    Toast.showToast("Disconnected from the network", JJBToast.WARNING);
          if (mResponseCallback != null) {//Interface exception
            mResponseCallback.onError(-1, id, "The network is disconnected, please check the network connection");
          }
        } else {
          if (mResponseCallback != null) {//Interface exception
            mResponseCallback.onError(1, id, "Failed to connect to the server, please contact the administrator");
          }
        }

      }

      @Override
      public void onResponse(String response, int id) {
        if (mLoadingDialog != null) {
          mLoadingDialog.dismiss();
          mLoadingDialog = null;
        }

        Object object = null;
        try {
          Log.i("TAG", "==okHttp responseJson " + url + ": \n" + response);

          object = new Gson().fromJson(response, responseClass);
        } catch (Exception e) {
          e.printStackTrace();
          if (mResponseCallback != null) {
            Log.e("TAG", "==okHttp error " + e.toString());
            mResponseCallback.onError(2, 0, "Data parsing exception");//Data parsing exception
          }
        }
        if (mResponseCallback != null && object != null) {
          mResponseCallback.onSuccess(object);
        }
      }
    });
  }


  /**
   * Cancel current interface request
   */
  public void cancel() {
    if (mLoadingDialog != null) {
      mLoadingDialog.dismiss();
      mLoadingDialog = null;
    }
    if (call != null) {
      isCancelNormal = true;
      call.cancel();
    }
  }

  /**
   * Response operation interface
   */
  public interface ResponseCallback {
    /**
     * Reason for failure
     *
     * @param errorType -1: Network disconnected 0: Timeout 1: Network abnormality, http code is not 200 　　 2: Parsing abnormality based on the response bean data 3: Parameter abnormality requested
     * @param errorCode 　Network request error code
     * @param errorMsg specific exception information (usually not available)
     */
    void onError(int errorType, int errorCode, String errorMsg);

    /**
     * Successful request
     *
     * The successful entity class corresponding to @param response
     */
    void onSuccess(Object response);

  }

  /**
   * Operation interface that responds when downloading files
   */
  public interface DownLoadFileRespCallback {
    /**
     * Reason for failure
     *
     * @param errorType -1: Network disconnected 0: Timeout 1: Network abnormality, http code is not 200 　　 2: Parsing abnormality based on the response bean data 3: Parameter abnormality requested
     * @param errorCode 　Network request error code
     * @param errorMsg specific exception information (usually not available)
     */
    void onError(int errorType, int errorCode, String errorMsg);

    /**
     * Successful request
     *
     * @param file The file after successful download
     */
    void onSuccess(File file);

    /**
     * Successful request
     *
     * @param progress The current download progress
     */
    void onDownloadProgress(float progress);

  }

  public void showNormalLoading() {
    Log.i("TAG", "showNormalLoading");
    if (mLoadingDialog != null) {
      mLoadingDialog.dismiss();
      mLoadingDialog = null;
    }
    /*Display and turn off less than 1 second, then delay 1 second to turn off*/
    mLoadingDialog = ProgressDialog.show(mContext, null, "Loading...");
    mLoadingDialog.setCancelable(false);
    mLoadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
      @Override
      public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_BACK) {
          if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
          }
        }
        return false;
      }
    });
    //Control display often
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
//                if (System.currentTimeMillis() - startTime > 1000 * 1) {//This request is greater than one second, a request box needs to pop up
        if (mLoadingDialog != null) {
          mLoadingDialog.show();
        }
//                }
      }
    }, 1000 * 0);

  }

  /**
   * check net state
   * Check if the network is connected and the network status
   *
   * @param context
   * @return
   */
  private Boolean check(Context context) {
    boolean isNet = true;
    ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectMgr != null) {
      NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
      NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
      if (mobNetInfo == null && wifiNetInfo != null) {
        //Not connected to mobile or wifi
        if (!wifiNetInfo.isConnected()) {
          isNet = false;
        }
      } else if (mobNetInfo != null && wifiNetInfo == null) {
        if (!mobNetInfo.isConnected()) {
          isNet = false;
        }
      } else if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
        //Not connected to mobile or wifi
        isNet = false;
      }
    }
    return isNet;
  }
}
