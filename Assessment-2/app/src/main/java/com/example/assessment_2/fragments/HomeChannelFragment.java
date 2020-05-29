package com.example.assessment_2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.assessment_2.R;
import com.example.assessment_2.model.AppDataFactory;

import cn.jzvd.JZVideoPlayerStandard;

public class HomeChannelFragment extends Fragment {
    private String brandName;
    private View view;
    private JZVideoPlayerStandard videoPlayer;

    public HomeChannelFragment(String brandName) {
        this.brandName = brandName;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.page_home_channel, container, false);
        videoPlayer = view.findViewById(R.id.videoPlayer);

        initVideo(brandName);
        return view;
    }

    private void initVideo(String brandName) {
            String mp4Url = AppDataFactory.getVideoUrl(brandName);
            Glide.with(getActivity())
                    .load("")
                    .into(videoPlayer.thumbImageView);
            videoPlayer.setUp(mp4Url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
            videoPlayer.startVideo();
    }
}
