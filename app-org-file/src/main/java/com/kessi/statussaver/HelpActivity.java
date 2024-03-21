package com.kessi.statussaver;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.kessi.statussaver.util.AdManager;


public class HelpActivity extends AppCompatActivity {

    ImageView back;
    ImageView help1, help2, help3, help4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        help1 = findViewById(R.id.help1);
        help2 = findViewById(R.id.help2);
        help3 = findViewById(R.id.help3);
        help4 = findViewById(R.id.help4);

        Glide.with(this)
                .load(R.drawable.step1)
                .into(help1);

        Glide.with(this)
                .load(R.drawable.step2)
                .into(help2);

        Glide.with(this)
                .load(R.drawable.step3)
                .into(help3);

        Glide.with(this)
                .load(R.drawable.step4)
                .into(help4);

        LinearLayout adContainer = findViewById(R.id.banner_container);

        if (!AdManager.isloadFbAd) {
            //admob
            AdManager.initAd(HelpActivity.this);
            AdManager.loadBannerAd(HelpActivity.this, adContainer);
            AdManager.loadInterAd(HelpActivity.this);
        } else {
            //MAX + Fb banner Ads
            AdManager.initMAX(HelpActivity.this);
            AdManager.maxBanner(HelpActivity.this, adContainer);
            AdManager.maxInterstital(HelpActivity.this);
        }

    }

    @Override
    public void onBackPressed() {
        AdManager.adCounter++;
        if (AdManager.adCounter == AdManager.adDisplayCounter) {
            if (!AdManager.isloadFbAd) {
                AdManager.showInterAd(HelpActivity.this, null,0);
            } else {
                AdManager.showMaxInterstitial(HelpActivity.this, null,0);
            }
        } else {
            super.onBackPressed();
        }
    }
}
