package com.example.assessment_2.util;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.assessment_2.R;
import com.jph.takephoto.app.TakePhoto;


/**
 * Where to get pictures: Take photos, select from album, cancel
 */

public class SelectorImgPopwindow {

  private static volatile SelectorImgPopwindow _instance;

  private PopupWindow popwindow;

  private TakePhotoHelper helper;

  private TakePhoto photo = null;

  private Activity activity;

  private View.OnClickListener clickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      if (v.getId() == R.id.btn_cancel) {
      } else if (v.getId() == R.id.btn_paizhao) {
        helper.setEnableReserveRaw(false);
        helper.onClick(R.id.btn_paizhao, photo);
      } else if (v.getId() == R.id.btn_bendi) {
        helper.setEnableReserveRaw(true);
        helper.onClick(R.id.btn_bendi, photo);
      }
      popwindow.dismiss();
    }
  };

  private SelectorImgPopwindow() {

  }

  public static SelectorImgPopwindow getInstance() {
    if (_instance == null) {
      synchronized (SelectorImgPopwindow.class) {
        if (_instance == null) {
          _instance = new SelectorImgPopwindow();
        }
      }
    }
    return _instance;
  }

  /**
   * @param activity
   * @param takePhoto
   * @param isCrop is cropped
   * @param isCompress is compressed
   * @param compressMaxSize Compression maximum size kb
   * @param width crop ratio
   * @param height crop ratio
   */
  public void showPop(final Activity activity, TakePhoto takePhoto, boolean isCrop,
                      boolean isCompress, int compressMaxSize, int width, int height) {
    init(activity, takePhoto, isCrop, isCompress, compressMaxSize, width, height);

  }

  private void init(Activity activity, TakePhoto takePhoto, boolean isCrop, boolean isCompress, int compressMaxSize, int width, int height) {
    helper = TakePhotoHelper.getHelper(isCrop, isCompress, compressMaxSize, width, height);
    photo = takePhoto;
    LayoutInflater layoutInflater = LayoutInflater.from(activity);
    View popview = layoutInflater.inflate(R.layout.pop_picture_select, null);

    //Local Album
    RelativeLayout btnBendi = (RelativeLayout) popview.findViewById(R.id.btn_bendi);
    btnBendi.setOnClickListener(clickListener);
    //Cancel
    RelativeLayout btnCancel = (RelativeLayout) popview.findViewById(R.id.btn_cancel);
    btnCancel.setOnClickListener(clickListener);
    //Take Photo
    RelativeLayout btnCapture = (RelativeLayout) popview.findViewById(R.id.btn_paizhao);
    btnCapture.setOnClickListener(clickListener);

    popwindow = new PopupWindow(popview, LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.MATCH_PARENT);
    popwindow.setBackgroundDrawable(new ColorDrawable(0x80000000));
    popwindow.setAnimationStyle(R.style.AnimBottom);
    popwindow.setTouchable(true);
    popwindow.setOutsideTouchable(true);
    popwindow.setFocusable(true);
    popwindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    if (!popwindow.isShowing()) {
      popwindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }
  }

}
