package com.example.assessment_2.util;


public class HttpUtil {
  public static final String HOST = "http://49.235.23.118:8889/";
  public static final String LOGIN =  HOST + "api/user/login";
  public static final String BRAND_LIST =  HOST + "api/motor/brandList";
  public static final String MOTOR_LIST =  HOST + "api/motor/list?brandId=";
  public static final String MOTOR_DETAIL =  HOST + "api/motor/detail";
  public static final String COLLECT_LIST =  HOST + "api/motor/collectList?userId=";
  public static final String FOOT_LIST =  HOST + "api/motor/footList?userId=";
  public static final String UPLOAD_FILE =  HOST + "api/user/upload";
  public static final String UPDATE_USER =  HOST + "api/user/updateUser";
  public static final String ADD_COLLECT =  HOST + "api/motor/addCollect";
  public static final String DELETE_COLLECT =  HOST + "api/motor/removeCollect";
  public static final String REGISTER =  HOST + "api/user/register";
  public static final String COLLECT_BRAND_LIST =  HOST + "api/motor/collectBrandList?userId=";
  public static final String TEXT =  HOST + "api/user/text";
}
