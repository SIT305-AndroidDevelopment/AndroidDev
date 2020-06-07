package com.example.assessment_2.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.assessment_2.R;
import com.example.assessment_2.constant.AppConstant;

public class HomeChannelFragment extends Fragment {
    private String brandName;
    private View view;
    //    private JZVideoPlayerStandard videoPlayer;
    private VideoView videoView;

    public HomeChannelFragment(String brandName) {
        this.brandName = brandName;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.page_home_channel, container, false);
//        videoPlayer = view.findViewById(R.id.videoPlayer);
        videoView = view.findViewById(R.id.videoView);
        initVideo(brandName);
        return view;
    }

    private void initVideo(String brandName) {

        Uri uri = null;

        switch (brandName) {
            case AppConstant.BRAND_HONDA:
                uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.honda);
                break;
            case AppConstant.BRAND_YAMAHA:
                uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.yamaha);
                break;
            case AppConstant.BRAND_SUZUKI:
                uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.suzuki);
                break;
            case AppConstant.BRAND_KAWASAKI:
                uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.kawasaki);
                break;
        }
        videoView.setVideoURI(uri);
        videoView.start();
    }
}
