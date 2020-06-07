package com.example.assessment_2;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.example.assessment_2.util.UserInfoManager;

public class AssessmentApplication extends Application {

  private static Context mContext;

  @Override
  public void onCreate() {
    super.onCreate();
    mContext = getApplicationContext();

    //Initialize the context information before using the SDK components and pass in the ApplicationContext
    SDKInitializer.initialize(this);
    //Since 4.3.0, all interfaces of Baidu Map SDK support Baidu coordinates and National Bureau of Surveying coordinates. Use this method to set the coordinate type you use.
    //Including BD09LL and GCJ02 coordinates, the default is BD09LL coordinates
    SDKInitializer.setCoordType(CoordType.BD09LL);
//    InitializationConfig config = InitializationConfig.newBuilder(this)
//        .connectionTimeout(30 * 1000)
//        .readTimeout(30 * 1000)
//        .retry(10)
//        .build();
//    NoHttp.initialize(config);
    UserInfoManager.getInstance().init(this);
    initAppData();

    UserInfoManager.getInstance().init(this);
  }

  private void initAppData() {

  }

  /**
   * Provide an application-level global context mContext
   */
  public static Context getContext() {
    return mContext;
  }
}
