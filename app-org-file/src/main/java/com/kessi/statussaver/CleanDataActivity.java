package com.kessi.statussaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.tabs.TabLayout;
import com.kessi.statussaver.adapter.CleanerPagerAdapter;
import com.kessi.statussaver.util.AdManager;

public class CleanDataActivity extends AppCompatActivity {

    ImageView backIV;
    TabLayout tabLayout;
    ViewPager viewPager;
    String category;
    String receivePath;
    String sentPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean_data);

        this.category = getIntent().getStringExtra("category");
        this.receivePath = getIntent().getStringExtra("receivePath");
        this.sentPath = getIntent().getStringExtra("sentPath");

        Log.e( "category: ", category);
        Log.e( "receivePath: ", receivePath);
        Log.e( "sentPath: ", sentPath);

        backIV = findViewById(R.id.backIV);
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        viewPager =  findViewById(R.id.pagerdiet);
        viewPager.setAdapter(new CleanerPagerAdapter(getSupportFragmentManager(), category, receivePath, sentPath));
        tabLayout =  findViewById(R.id.tab_layoutdiet);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (!AdManager.isloadFbAd) {
                    AdManager.adCounter++;
                    AdManager.showInterAd(CleanDataActivity.this, null,0);
                } else {
                    AdManager.adCounter++;
                    AdManager.showMaxInterstitial(CleanDataActivity.this, null,0);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        LinearLayout adContainer = findViewById(R.id.banner_container);

        if (!AdManager.isloadFbAd) {
            //admob
            AdManager.initAd(CleanDataActivity.this);
            AdManager.loadBannerAd(CleanDataActivity.this, adContainer);
            AdManager.loadInterAd(CleanDataActivity.this);
        } else {
            //MAX + Fb banner Ads
            AdManager.initMAX(CleanDataActivity.this);
            AdManager.maxBanner(CleanDataActivity.this, adContainer);
            AdManager.maxInterstital(CleanDataActivity.this);
        }
    }

    @Override
    public void onBackPressed() {
        AdManager.adCounter++;
        if (AdManager.adCounter == AdManager.adDisplayCounter) {
            if (!AdManager.isloadFbAd) {
                AdManager.showInterAd(CleanDataActivity.this, null,0);
            } else {
                AdManager.showMaxInterstitial(CleanDataActivity.this, null,0);
            }
        } else {
            super.onBackPressed();
        }
    }

    private void setupTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Received File");
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabtwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabtwo.setText("Sent File");
        tabLayout.getTabAt(1).setCustomView(tabtwo);
    }


}
