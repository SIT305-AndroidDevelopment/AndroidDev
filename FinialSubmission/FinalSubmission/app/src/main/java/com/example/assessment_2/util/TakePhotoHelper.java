package com.example.assessment_2.util;

import android.net.Uri;
import android.os.Environment;

import com.example.assessment_2.R;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TakePhotoOptions;

import java.io.File;

public class TakePhotoHelper {
  /**
   * Whether to crop
   */
  private boolean isCrop = false;
  /**
   * Whether to compress
   */
  private boolean isCompress = false;
  /**
   * Compression size does not exceed
   * Unit: B
   */
  private int compressMaxSize = 100000;

  /**
   * Crop ratio Width
   */
  private int width = 1;
  /**
   * Crop ratio height
   */
  private int height = 1;
  /**
   * Whether to keep the original picture
   */
  private boolean enableReserveRaw = true;

  /**
   * TakePahoto management tool
   *
   * @param isCrop is cropped
   * @param isCompress is compressed
   * @param compressMaxSize Compression maximum size
   * @param width crop ratio width
   * @param height crop ratio high
   * @return
   */
  public static TakePhotoHelper getHelper(boolean isCrop, boolean isCompress, int compressMaxSize,
                                          int width, int height) {
    return new TakePhotoHelper(isCrop, isCompress, compressMaxSize, width, height);
  }

  private TakePhotoHelper(boolean isCrop, boolean isCompress, int compressMaxSize, int width, int height) {
    this.isCrop = isCrop;
    this.isCompress = isCompress;
    this.compressMaxSize = compressMaxSize * 1000;
    this.width = width;
    this.height = height;
  }

  public void setEnableReserveRaw(boolean enableReserveRaw) {
    this.enableReserveRaw = enableReserveRaw;
  }

  /**
   * Take a picture
   *
   * @param takePhoto
   */
  public void onClick(int viewID, TakePhoto takePhoto) {
    File file = new File(Environment.getExternalStorageDirectory(), "/images/" + System.currentTimeMillis() + ".jpg");
    if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
    Uri imageUri = Uri.fromFile(file);
    configCompress(takePhoto);
    configTakePhotoOption(takePhoto);
    if (viewID == R.id.btn_paizhao) {
      /* 拍照 */
      if (isCrop) {
        takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
      } else {
        takePhoto.onPickFromCapture(imageUri);
      }
    } else if (viewID == R.id.btn_bendi) {
      if (isCrop) {
        takePhoto.onPickMultipleWithCrop(1, getCropOptions());
      } else {
        takePhoto.onPickMultiple(1);
      }
    }

  }

  /**
   * Configuration
   *
   * @param takePhoto
   */
  private void configTakePhotoOption(TakePhoto takePhoto) {
    TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
    //是否使用TakePhoto的相册
//        builder.setWithOwnGallery(true);
    /* 拍摄的照片是否纠正照片角度 */
    builder.setCorrectImage(true);
    takePhoto.setTakePhotoOptions(builder.create());

  }

  /**
   * Compression related configuration
   *
   * @param takePhoto
   */
  private void configCompress(TakePhoto takePhoto) {
    if (!isCompress) {
      /* Whether to compress */
      takePhoto.onEnableCompress(null, false);
      return;
    }
    int compressWidth = 1000;
    int compressHeight = 800;
    CompressConfig config;
    config = new CompressConfig.Builder()
        .setMaxSize(compressMaxSize)
        .setMaxPixel(compressWidth >= compressHeight ? compressWidth : compressHeight)
        .enableReserveRaw(enableReserveRaw)//Whether to keep the original picture
        .create();
    takePhoto.onEnableCompress(config, false);
  }

  /**
   * Clipping related configuration
   *
   * @return
   */
  private CropOptions getCropOptions() {
    /* Whether to use a third-party cropping tool */
    boolean withWonCrop = true;

    CropOptions.Builder builder = new CropOptions.Builder();
    builder.setAspectX(width).setAspectY(height);

    builder.setWithOwnCrop(withWonCrop);
    return builder.create();
  }

}
