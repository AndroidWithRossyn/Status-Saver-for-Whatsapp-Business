package com.kessi.statussaver;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.kessi.statussaver.fragments.Utils;
import com.kessi.statussaver.util.AdManager;


public class VideoPreviewActivity extends AppCompatActivity {

    VideoView displayVV;
    ImageView backIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_preview);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        backIV = findViewById(R.id.backIV);
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        displayVV = (VideoView) findViewById(R.id.displayVV);

        displayVV.setVideoPath(Utils.mPath);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(displayVV);

        displayVV.setMediaController(mediaController);

        displayVV.start();


        LinearLayout adContainer = findViewById(R.id.banner_container);

        if (!AdManager.isloadFbAd) {
            //admob
            AdManager.initAd(VideoPreviewActivity.this);
            AdManager.loadBannerAd(VideoPreviewActivity.this, adContainer);
        } else {
            //MAX + Fb banner Ads
            AdManager.initMAX(VideoPreviewActivity.this);
            AdManager.maxBanner(VideoPreviewActivity.this, adContainer);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        displayVV.setVideoPath(Utils.mPath);
        displayVV.start();
    }


}
