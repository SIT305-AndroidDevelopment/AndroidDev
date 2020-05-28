package com.example.assessment_2;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Banner加载器
 */

public class GlideImageLoaderHomeBanner extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context)
                .load(path)
//                .crossFade()
                .thumbnail(0.1f)
                .into(imageView);
    }
}
