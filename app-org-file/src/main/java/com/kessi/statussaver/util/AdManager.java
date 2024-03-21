package com.kessi.statussaver.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.kessi.statussaver.R;

public class AdManager {
    public static int adCounter = 1;
    public static int adDisplayCounter = 10;

    public static boolean isloadFbAd = true;


    public static void initAd(Context context) {
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
    }

    static AdView gadView;
    public static void loadBannerAd(Context context, LinearLayout adContainer) {
        gadView = new AdView(context);
        gadView.setAdUnitId(context.getString(R.string.admob_banner_id));
        adContainer.addView(gadView);
        loadBanner(context);
    }

    static void loadBanner(Context context) {
        AdRequest adRequest =
                new AdRequest.Builder().build();

        AdSize adSize = getAdSize((Activity) context);
        gadView.setAdSize(adSize);
        gadView.loadAd(adRequest);
    }

    static AdSize getAdSize(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }

    public static void adptiveBannerAd(Context context, LinearLayout adContainer) {
        AdView adView = new AdView(context);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.setAdSize(AdSize.LARGE_BANNER);
        adView.setAdUnitId(context.getString(R.string.admob_banner_id));
        adView.loadAd(adRequest);
        adContainer.addView(adView);
    }

    static InterstitialAd mInterstitialAd;

    public static void loadInterAd(Context context) {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context,context.getString(R.string.admob_interstitial), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                mInterstitialAd = null;
            }
        });
    }

    public static void showInterAd(final Activity context, final Intent intent, final int requstCode) {
        if (adCounter == adDisplayCounter && mInterstitialAd != null) {
            adCounter = 1;
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                @Override
                public void onAdDismissedFullScreenContent() {
                    // Called when fullscreen content is dismissed.
//                    Log.d("TAG", "The ad was dismissed.");
                    loadInterAd(context);
                    startActivity(context, intent, requstCode);
                }

                @Override
                public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
//                     Called when fullscreen content failed to show.
//                    Log.d("TAG", "The ad failed to show.");
                }


                @Override
                public void onAdShowedFullScreenContent() {
                    // Called when fullscreen content is shown.
                    // Make sure to set your reference to null so you don't
                    // show it a second time.
                    mInterstitialAd = null;
//                    Log.d("TAG", "The ad was shown.");
                }
            });
            mInterstitialAd.show((Activity) context);
        } else {
            if (adCounter == adDisplayCounter){
                adCounter = 1;
            }
            startActivity(context, intent, requstCode);
        }
    }

    public static void showInterAd(final Fragment context, final Intent intent, final int requstCode) {
        if (adCounter == adDisplayCounter && mInterstitialAd != null) {
            adCounter = 1;
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                @Override
                public void onAdDismissedFullScreenContent() {
                    // Called when fullscreen content is dismissed.
//                    Log.d("TAG", "The ad was dismissed.");
                    loadInterAd(context.getActivity());
                    startActivity(context, intent, requstCode);
                }

                @Override
                public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
//                     Called when fullscreen content failed to show.
//                    Log.d("TAG", "The ad failed to show.");
                }


                @Override
                public void onAdShowedFullScreenContent() {
                    // Called when fullscreen content is shown.
                    // Make sure to set your reference to null so you don't
                    // show it a second time.
                    mInterstitialAd = null;
//                    Log.d("TAG", "The ad was shown.");
                }
            });
            mInterstitialAd.show(context.getActivity());
        } else {
            if (adCounter == adDisplayCounter){
                adCounter = 1;
            }
            startActivity(context, intent, requstCode);
        }
    }

    static void startActivity(Activity context, Intent intent, int requestCode) {
        if (intent != null) {
            context.startActivityForResult(intent, requestCode);
        }
    }

    static MaxAdView maxAdView;

    public static void initMAX(Activity activity) {
        AppLovinSdk.getInstance(activity).setMediationProvider("max");
        AppLovinSdk.initializeSdk(activity, configuration -> { });
    }

    public static void maxBanner(Activity activity, LinearLayout linearLayout) {
        maxAdView = new MaxAdView(activity.getResources().getString(R.string.max_banner), activity);

        // Stretch to the width of the screen for banners to be fully functional
        int width = ViewGroup.LayoutParams.MATCH_PARENT;

        // Banner height on phones and tablets is 50 and 90, respectively
        int heightPx = activity.getResources().getDimensionPixelSize(R.dimen.banner_height);

        maxAdView.setLayoutParams(new FrameLayout.LayoutParams(width, heightPx));

        // Set background or background color for banners to be fully functional
        maxAdView.setBackgroundColor(activity.getResources().getColor(R.color.bg_color));

        if (isNetworkConnected(activity)) {
            linearLayout.addView(maxAdView);

            // Load the banner
            if (maxAdView != null) {
                maxAdView.loadAd();
            }
        }

    }

    static MaxAdView maxAdAdaptive;

    public static void maxBannerAdaptive(Activity activity, LinearLayout linearLayout) {
        maxAdAdaptive = new MaxAdView(activity.getResources().getString(R.string.max_banner), activity);

        // Stretch to the width of the screen for banners to be fully functional
        int width = ViewGroup.LayoutParams.MATCH_PARENT;

        // Get the adaptive banner height.
        int heightDp = MaxAdFormat.BANNER.getAdaptiveSize(activity).getHeight();
        int heightPx = AppLovinSdkUtils.dpToPx(activity, heightDp);

        maxAdAdaptive.setLayoutParams(new FrameLayout.LayoutParams(width, heightPx));
        maxAdAdaptive.setExtraParameter("adaptive_banner", "true");

        // Set background or background color for banners to be fully functional
        maxAdAdaptive.setBackgroundColor(activity.getResources().getColor(R.color.bg_color));

        if (isNetworkConnected(activity)) {
            linearLayout.addView(maxAdAdaptive);

            // Load the adaptive
            if (maxAdAdaptive != null) {
                maxAdAdaptive.loadAd();
            }
        }
    }


    static Intent maxIntent;
    static int maxRequstCode;
    static MaxInterstitialAd maxInterstitialAd;

    public static void maxInterstital(Activity activity) {
        maxInterstitialAd = new MaxInterstitialAd(activity.getResources().getString(R.string.max_interstitial), activity);
        maxInterstitialAd.setListener(new MaxAdListener() {
            @Override
            public void onAdLoaded(MaxAd ad) {

            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {
                maxInterstitialAd.loadAd();
                startActivity(activity, maxIntent, maxRequstCode);
            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                if (isNetworkConnected(activity)) {
                    maxInterstitialAd.loadAd();
                }
                startActivity(activity, maxIntent, maxRequstCode);
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                if (isNetworkConnected(activity)) {
                    maxInterstitialAd.loadAd();
                }
                startActivity(activity, maxIntent, maxRequstCode);
            }
        });

        if (isNetworkConnected(activity)) {
            // Load the first ad
            maxInterstitialAd.loadAd();
        }
    }

    public static void showMaxInterstitial(final Activity context, final Intent intent, final int requestCode) {
        maxIntent = intent;
        maxRequstCode = requestCode;
        if (adCounter == adDisplayCounter && maxInterstitialAd != null && maxInterstitialAd.isReady()) {
            adCounter = 1;
            maxInterstitialAd.showAd();
        } else {
            if (adCounter == adDisplayCounter) {
                adCounter = 1;
            }
            startActivity(context, intent, requestCode);
        }
    }

    public static void showMaxInterstitial(final Fragment context, final Intent intent, final int requestCode) {
        maxIntent = intent;
        maxRequstCode = requestCode;
        if (adCounter == adDisplayCounter && maxInterstitialAd != null && maxInterstitialAd.isReady()) {
            adCounter = 1;
            maxInterstitialAd.showAd();
        } else {
            if (adCounter == adDisplayCounter) {
                adCounter = 1;
            }
            startActivity(context, intent, requestCode);
        }
    }

    static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    static void startActivity(Fragment context, Intent intent, int requestCode) {
        if (intent != null) {
            context.startActivityForResult(intent, requestCode);
        }
    }

}
