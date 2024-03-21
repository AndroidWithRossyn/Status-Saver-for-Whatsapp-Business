package com.kessi.statussaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kessi.statussaver.util.AdManager;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

public class DirectActivity extends AppCompatActivity {

    ImageView back;
    CountryCodePicker ccp;
    EditText edtPhoneNumber, msg_edt;
    LinearLayout wapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        edtPhoneNumber = findViewById(R.id.phone_number_edt);
        ccp.registerPhoneNumberTextView(edtPhoneNumber);
        msg_edt = findViewById(R.id.msg_edt);

        wapp = findViewById(R.id.wapp);
        wapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirect();
            }
        });


        LinearLayout adContainer = findViewById(R.id.banner_container);

        if (!AdManager.isloadFbAd) {
            //admob
            AdManager.initAd(DirectActivity.this);
            AdManager.adptiveBannerAd(DirectActivity.this, adContainer);
            AdManager.loadInterAd(DirectActivity.this);
        } else {
            //MAX + Fb banner Ads
            AdManager.initMAX(DirectActivity.this);
            AdManager.maxBannerAdaptive(DirectActivity.this, adContainer);
            AdManager.maxInterstital(DirectActivity.this);
        }
    }

    @Override
    public void onBackPressed() {
        AdManager.adCounter++;
        if (AdManager.adCounter == AdManager.adDisplayCounter) {
            if (!AdManager.isloadFbAd) {
                AdManager.showInterAd(DirectActivity.this, null,0);
            } else {
                AdManager.showMaxInterstitial(DirectActivity.this, null,0);
            }
        } else {
            super.onBackPressed();
        }
    }

    void redirect(){
        if (TextUtils.isEmpty(edtPhoneNumber.getText().toString())){
            Toast.makeText(DirectActivity.this, "Select country and Enter Mobile Number.",Toast.LENGTH_SHORT ).show();
        }else {
            try {
//                String url = "https://wa.me/whatsappphonenumber" + ccp.getSelectedCountryCode()+edtPhoneNumber.getText().toString() + "/?text=" + msg_edt.getText().toString();
//                Intent i = new Intent("android.intent.action.VIEW");
//                i.setData(Uri.parse(url));
//                startActivity(i);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+ccp.getSelectedCountryCode()+edtPhoneNumber.getText().toString() +"&text="+msg_edt.getText().toString()));
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(DirectActivity.this, "Install WhatsApp First...", Toast.LENGTH_SHORT).show();
            }
        }
    }

}