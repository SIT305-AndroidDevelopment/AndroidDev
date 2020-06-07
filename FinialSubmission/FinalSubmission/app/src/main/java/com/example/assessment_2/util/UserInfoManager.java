package com.example.assessment_2.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.assessment_2.model.UserInfo;
import com.google.gson.Gson;

public class UserInfoManager {
  public volatile static UserInfoManager instance;
  private SharedPreferences sharedPreferences;
  private UserInfo userInfo;
  private Gson mGson;

  public static UserInfoManager getInstance() {
    if (instance == null) {
      synchronized (UserInfoManager.class) {
        if (instance == null) {
          instance = new UserInfoManager();
        }
      }
    }
    return instance;
  }

  private UserInfoManager() {
    mGson = new Gson();
  }

  public void init(Context mCtx) {
    sharedPreferences = mCtx.getSharedPreferences("user_info", Context.MODE_PRIVATE);
    String s = sharedPreferences.getString("userInfo", null);
    if (s != null && !s.isEmpty()) {
      userInfo = mGson.fromJson(s, UserInfo.class);
    }
  }

  public void saveUserInfo(UserInfo userInfo) {
    this.userInfo = userInfo;
    sharedPreferences.edit().putString("userInfo", mGson.toJson(userInfo)).apply();
  }

  public UserInfo getUserInfo() {
    return this.userInfo;
  }
}
