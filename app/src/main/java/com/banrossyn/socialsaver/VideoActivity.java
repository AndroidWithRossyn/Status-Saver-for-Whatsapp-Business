package com.banrossyn.socialsaver;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.banrossyn.socialsaver.util.Utils;


public class VideoActivity extends AppCompatActivity {

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



    }


    @Override
    protected void onResume() {
        super.onResume();
        displayVV.setVideoPath(Utils.mPath);
        displayVV.start();
    }


}
