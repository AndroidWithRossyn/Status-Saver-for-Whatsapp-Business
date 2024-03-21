package com.banrossyn.socialsaver;

import static android.Manifest.permission.POST_NOTIFICATIONS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import com.banrossyn.socialsaver.adapter.SliderAdapter;

import com.banrossyn.socialsaver.util.SharedPrefs;
import com.banrossyn.socialsaver.util.Utils;
import com.banrossyn.socialsaver.waweb.WAWebActivity;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;
import com.google.android.ump.ConsentForm;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    String[] permissionsList = new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};
    String[] permissionNotification = {POST_NOTIFICATIONS};
    private boolean permission_post_notification = false;
    private boolean isPermissionGrantedToAccessStorage = true;
    ImageView wappBtn, modeIV,rateBtn,shareBtn, policyBtn, moreBtn;
    LinearLayout   helpBtn;
    RelativeLayout recentBtn, statusBtn, cleanBtn, wbCleanBtn, directBtn, wbWebBtn;
    Animation blink;
    Dialog dialog;
    AppUpdateManager appUpdateManager;
    Context update;

    int[] images = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six, R.drawable.seven, R.drawable.eight, R.drawable.nine};

    private String deviceInfo;
    private ReviewInfo reviewInfo;
    private ReviewManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        update = HomeActivity.this;
        appUpdateManager = AppUpdateManagerFactory.create(update);

        wappBtn = findViewById(R.id.wappBtn);
        wappBtn.setOnClickListener(this);

        blink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        wappBtn.startAnimation(blink);


        recentBtn = findViewById(R.id.recentBtn);
        recentBtn.setOnClickListener(this);

        statusBtn = findViewById(R.id.statusBtn);
        statusBtn.setOnClickListener(this);

        cleanBtn = findViewById(R.id.cleanBtn);
        cleanBtn.setOnClickListener(this);

        wbCleanBtn = findViewById(R.id.wbCleanBtn);
        wbCleanBtn.setOnClickListener(this);

        directBtn = findViewById(R.id.directBtn);
        directBtn.setOnClickListener(this);

        wbWebBtn = findViewById(R.id.wbWebBtn);
        wbWebBtn.setOnClickListener(this);

        rateBtn = findViewById(R.id.rateBtn);
        rateBtn.setOnClickListener(this);

        shareBtn = findViewById(R.id.shareBtn);
        shareBtn.setOnClickListener(this);

        policyBtn = findViewById(R.id.policyBtn);
        policyBtn.setOnClickListener(this);

        moreBtn = findViewById(R.id.moreBtn);
        moreBtn.setOnClickListener(this);

        helpBtn = findViewById(R.id.helpBtn);
        helpBtn.setOnClickListener(this);

        modeIV = findViewById(R.id.modeIV);
        modeIV.setOnClickListener(this);

        int mode = SharedPrefs.getAppNightDayMode(this);
        if (mode == AppCompatDelegate.MODE_NIGHT_YES) {
            modeIV.setImageResource(R.drawable.dark_mode);
        } else {
            modeIV.setImageResource(R.drawable.light_mode);
        }

        wAppAlert();
        activateReviewInfo();
//        UpdateApp();
        GDPRMessage();

        SliderAdapter sliderAdapter = new SliderAdapter(images);
        SliderView imageSlider= findViewById(R.id.image_slider);
        imageSlider.setSliderAdapter(sliderAdapter);
        imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        imageSlider.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        imageSlider.startAutoCycle();


        // for feedback
        deviceInfo = "App Info:";
        deviceInfo += "\n App Version: " + "V " + BuildConfig.VERSION_NAME;
        deviceInfo += "\n API Level: " + android.os.Build.VERSION.SDK_INT;
        deviceInfo += "\n Model: " + android.os.Build.MODEL;

        if (!permission_post_notification && Build.VERSION.SDK_INT >= 33) {
            requestPermissionNotification();
        }

        requestPermission();
    }
    public void requestPermissionNotification() {
        if (ActivityCompat.checkSelfPermission(this, permissionNotification[0]) == PackageManager.PERMISSION_GRANTED) {
            permission_post_notification = true;
        } else {
            if (shouldShowRequestPermissionRationale(POST_NOTIFICATIONS)) {
                Log.d("checknotification","notification allow at first");
//                first time not allow
            } else {
                Log.d("checknotification","notification allow at second");
//                second time not allow
            }
            requestPermissionLauncherNotificatoin.launch(permissionNotification[0]);
        }

    }

    private ActivityResultLauncher<String> requestPermissionLauncherNotificatoin = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            permission_post_notification = true;
        } else {
            permission_post_notification = false;
            showPermissionDialog();

        }
    });

    public void showPermissionDialog() {
        new AlertDialog.Builder(HomeActivity.this)
                .setTitle("Notification Permission")
                .setMessage("This App requires NOTIFICATIONS_PERMISSIONS for Particular features to work as expected")
                .setCancelable(true)
                .setPositiveButton("Settings", (dialog, which) -> {
                    Intent permi= new Intent();
                    permi.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    permi.setData(uri);
                    startActivity(permi);
                    dialog.dismiss();
                })
                .setNegativeButton("Exit", (dialog, which) -> dialog.dismiss())
                .show();
    }


    void wAppAlert() {
        dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.popup_lay);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        RelativeLayout btnWapp = dialog.findViewById(R.id.btn_wapp);
        RelativeLayout btnWappBus = dialog.findViewById(R.id.btn_wapp_bus);

        btnWapp.setOnClickListener(arg0 -> {
            try {
                startActivity(getPackageManager().getLaunchIntentForPackage("com.whatsapp"));
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(HomeActivity.this, "Please Install WhatsApp For Download Status!!!!!", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();

        });

        btnWappBus.setOnClickListener(arg0 -> {
            try {
                startActivity(getPackageManager().getLaunchIntentForPackage("com.whatsapp.w4b"));
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(HomeActivity.this, "Please Install WhatsApp Business For Download Status!!!!!", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });

    }

//    public static boolean checkPermissions(Context context, String... permissions) {
//        if (context != null && permissions != null) {
//            for (String permission : permissions) {
//                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

    void requestPermission() {
        ActivityCompat.requestPermissions(HomeActivity.this, permissionsList, 30);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  @NotNull String [] permissions, @NotNull int [] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 30) {
            if (grantResults.length > 0) {
                boolean reader = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean writer = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (reader && writer) {
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        isPermissionGrantedToAccessStorage = true;
                    }
                } else {
                    isPermissionGrantedToAccessStorage = false;
                }

            } else {
                isPermissionGrantedToAccessStorage = false;
            }
        }


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.wappBtn) {
            dialog.show();
        } else if (id == R.id.recentBtn) {
            startActivity(new Intent(HomeActivity.this, RecentStatusActivity.class));
        } else if (id == R.id.statusBtn) {
            startActivity(new Intent(HomeActivity.this, DownloadStatusActivity.class));
        } else if (id == R.id.directBtn) {
            startActivity(new Intent(HomeActivity.this, DirectChatActivity.class));
        } else if (id == R.id.wbWebBtn) {
            startActivity(new Intent(HomeActivity.this, WAWebActivity.class));
        } else if (id == R.id.cleanBtn) {
            Utils.iswApp = true;
            startActivity(new Intent(HomeActivity.this, CleanOptionActivity.class));
        } else if (id == R.id.wbCleanBtn) {
            Utils.iswApp = false;
            startActivity(new Intent(HomeActivity.this, CleanOptionActivity.class));
        } else if (id == R.id.rateBtn) {
            startReviewFlow();
        } else if (id == R.id.shareBtn) {
            Intent myapp = new Intent(Intent.ACTION_SEND);
            myapp.setType("text/plain");
            myapp.putExtra(Intent.EXTRA_TEXT, "Social Saver \n \n Hello Let me Recommend you this App\n Download easy Whatsapp Status (Photos & Videos).\n https://play.google.com/store/apps/details?id=" + getPackageName() + " \n");
            startActivity(myapp);
        } else if (id == R.id.policyBtn) {
            startActivity(new Intent(HomeActivity.this, PrivacyActivity.class));
        } else if (id == R.id.moreBtn) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=5306067645342206751")));
        } else if (id == R.id.helpBtn) {
            startActivity(new Intent(HomeActivity.this, HelpActivity.class));
        } else if (id == R.id.modeIV) {
            int mode = SharedPrefs.getAppNightDayMode(this);
            if (mode == AppCompatDelegate.MODE_NIGHT_NO) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                SharedPrefs.setInt(this, SharedPrefs.PREF_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                SharedPrefs.setInt(this, SharedPrefs.PREF_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exitAlert();
    }

    void exitAlert() {
        final Dialog exitDialog = new Dialog(HomeActivity.this);
        exitDialog.setContentView(R.layout.exit_popup_lay);

        exitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        TextView noBtn = exitDialog.findViewById(R.id.noBtn);
        TextView yesBtn = exitDialog.findViewById(R.id.yesBtn);

        noBtn.setOnClickListener(arg0 -> exitDialog.dismiss());

        yesBtn.setOnClickListener(arg0 -> {
            exitDialog.dismiss();
            HomeActivity.super.onBackPressed();
        });
        exitDialog.show();
    }

    void activateReviewInfo() {
        manager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> managerInfoTask = manager.requestReviewFlow();
        managerInfoTask.addOnCompleteListener((task) -> {
            if (task.isSuccessful()) {
                reviewInfo = task.getResult();
            } else {
                Toast.makeText(this, "Review failed to start", Toast.LENGTH_SHORT).show();
            }
        });


    }

    void startReviewFlow() {
        if (reviewInfo != null) {
            Task<Void> flow = manager.launchReviewFlow(this, reviewInfo);
            flow.addOnCompleteListener(task ->
            {
                Toast.makeText(this, "Rating is completed", Toast.LENGTH_SHORT).show();
                HomeActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id="+BuildConfig.APPLICATION_ID)));

            });
        }
    }

    public void UpdateApp() {
        try {
            Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
            appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                    try {
                        appUpdateManager.startUpdateFlowForResult(
                                appUpdateInfo, AppUpdateType.FLEXIBLE, HomeActivity.this, 101);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }).addOnFailureListener(e -> {
                e.printStackTrace();

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        appUpdateManager.registerListener(listener);
    }

    InstallStateUpdatedListener listener = installState -> {
        if (installState.installStatus() == InstallStatus.DOWNLOADED) {
            popUp();
        }

    };

    private void popUp() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "App Update Almost Done", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Reload", v -> appUpdateManager.completeUpdate());
        snackbar.setTextColor(Color.parseColor("#FF0000"));
        snackbar.show();
    }


    private ConsentInformation consentInformation;
    private final AtomicBoolean isMobileAdsInitializeCalled = new AtomicBoolean(false);


    public void GDPRMessage() {
//        ConsentDebugSettings debugSettings = new ConsentDebugSettings.Builder(this)
//                .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
//                .addTestDeviceHashedId("4F45E161A3144238FA2A0869C6BB2EE1")
//                .build();
        // Set tag for under age of consent. false means users are not under age
        // of consent.
        ConsentRequestParameters params = new ConsentRequestParameters
                .Builder()
//                .setConsentDebugSettings(debugSettings)
                .setTagForUnderAgeOfConsent(false)
                .build();

        consentInformation = UserMessagingPlatform.getConsentInformation(this);
        consentInformation.requestConsentInfoUpdate(
                this,
                params,
                (ConsentInformation.OnConsentInfoUpdateSuccessListener) () -> {
                    UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                            this,
                            (ConsentForm.OnConsentFormDismissedListener) loadAndShowError -> {
                                if (loadAndShowError != null) {
                                    // Consent gathering failed.
                                    Log.w("TAG", String.format("%s: %s",
                                            loadAndShowError.getErrorCode(),
                                            loadAndShowError.getMessage()));
                                }

                                // Consent has been gathered.
                                if (consentInformation.canRequestAds()) {
                                    initializeMobileAdsSdk();
                                }
                            }
                    );
                },
                (ConsentInformation.OnConsentInfoUpdateFailureListener) requestConsentError -> {
                    // Consent gathering failed.
                    Log.w("TAG", String.format("%s: %s",
                            requestConsentError.getErrorCode(),
                            requestConsentError.getMessage()));
                });

        // Check if you can initialize the Google Mobile Ads SDK in parallel
        // while checking for new consent information. Consent obtained in
        // the previous session can be used to request ads.
        if (consentInformation.canRequestAds()) {
            initializeMobileAdsSdk();
        }
    }


    private void initializeMobileAdsSdk() {
        if (isMobileAdsInitializeCalled.getAndSet(true)) {
            return;
        }
        // Initialize the Google Mobile Ads SDK.
        MobileAds.initialize(this);
    }
}
