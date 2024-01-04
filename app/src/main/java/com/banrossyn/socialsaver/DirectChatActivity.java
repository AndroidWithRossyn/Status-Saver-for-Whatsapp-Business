package com.banrossyn.socialsaver;

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


import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

public class DirectChatActivity extends AppCompatActivity {

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



    }

    @Override
    public void onBackPressed() {

            super.onBackPressed();

    }

    void redirect(){
        if (TextUtils.isEmpty(edtPhoneNumber.getText().toString())){
            Toast.makeText(DirectChatActivity.this, "Select country and Enter Mobile Number.",Toast.LENGTH_SHORT ).show();
        }else {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+ccp.getSelectedCountryCode()+edtPhoneNumber.getText().toString() +"&text="+msg_edt.getText().toString()));
                startActivity(intent);
            }catch (Exception e){
                Toast.makeText(DirectChatActivity.this, "Install WhatsApp First...", Toast.LENGTH_SHORT).show();
            }
        }
    }

}