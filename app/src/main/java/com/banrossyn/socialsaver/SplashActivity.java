package com.banrossyn.socialsaver;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
public class SplashActivity extends AppCompatActivity {
    ImageView icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }, 1500);
            
    }

}


