package com.banrossyn.socialsaver;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;
import androidx.viewpager.widget.ViewPager;


import com.banrossyn.socialsaver.adapter.FullscreenImageAdapter;
import com.banrossyn.socialsaver.model.StatusModel;

import com.banrossyn.socialsaver.util.Utils;

import java.io.File;
import java.util.ArrayList;

public class PreviewActivity extends AppCompatActivity {

    ViewPager viewPager;
    ArrayList<StatusModel> imageList;
    int position;

    LinearLayout downloadIV, shareIV, deleteIV, wAppIV;
    FullscreenImageAdapter fullscreenImageAdapter;
    String statusdownload;
    ImageView backIV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_preview);


        backIV = findViewById(R.id.backIV);


        viewPager = findViewById(R.id.viewPager);

        shareIV = findViewById(R.id.shareIV);

        downloadIV = findViewById(R.id.downloadIV);

        deleteIV = findViewById(R.id.deleteIV);
        wAppIV = findViewById(R.id.wAppIV);


        imageList = getIntent().getParcelableArrayListExtra("images");
        position = getIntent().getIntExtra("position", 0);
        statusdownload = getIntent().getStringExtra("statusdownload");

        if (statusdownload.equals("download")) {
            downloadIV.setVisibility(View.GONE);
        } else {
            downloadIV.setVisibility(View.VISIBLE);
        }

        fullscreenImageAdapter = new FullscreenImageAdapter(PreviewActivity.this, imageList);
        viewPager.setAdapter(fullscreenImageAdapter);
        viewPager.setCurrentItem(position);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        downloadIV.setOnClickListener(clickListener);
        shareIV.setOnClickListener(clickListener);
        deleteIV.setOnClickListener(clickListener);
        backIV.setOnClickListener(clickListener);
        wAppIV.setOnClickListener(clickListener);



    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.backIV) {
                onBackPressed();
            } else if (id == R.id.downloadIV) {
                if (imageList.size() > 0) {


                    try {
                        Utils.download(PreviewActivity.this, imageList.get(viewPager.getCurrentItem()).getFilePath());
                        Toast.makeText(PreviewActivity.this, "Status saved successfully", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(PreviewActivity.this, "Sorry we can't move file.try with other file.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    finish();
                }
            } else if (id == R.id.shareIV) {
                if (imageList.size() > 0) {
                    Utils.shareFile(PreviewActivity.this, Utils.isVideoFile(PreviewActivity.this, imageList.get(viewPager.getCurrentItem()).getFilePath()), imageList.get(viewPager.getCurrentItem()).getFilePath());
                } else {
                    finish();
                }
            } else if (id == R.id.deleteIV) {
                if (imageList.size() > 0) {


                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(PreviewActivity.this);
                    alertDialog.setTitle("Confirm Delete....");
                    alertDialog.setMessage("Are you sure, You Want To Delete This Status?");
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            int currentItem = 0;

                            if (statusdownload.equals("download")) {
                                File file = new File(imageList.get(viewPager.getCurrentItem()).getFilePath());
                                if (file.exists()) {
                                    boolean del = file.delete();
                                    delete(currentItem);
                                }
                            } else {
                                DocumentFile fromTreeUri = DocumentFile.fromSingleUri(PreviewActivity.this, Uri.parse(imageList.get(viewPager.getCurrentItem()).getFilePath()));
                                if (fromTreeUri.exists()) {
                                    boolean del = fromTreeUri.delete();
                                    delete(currentItem);
                                }
                            }
                        }
                    });
                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog.show();
                } else {
                    finish();
                }
            } else if (id == R.id.wAppIV) {
                Utils.repostWhatsApp(PreviewActivity.this, Utils.isVideoFile(PreviewActivity.this, imageList.get(viewPager.getCurrentItem()).getFilePath()), imageList.get(viewPager.getCurrentItem()).getFilePath());
            }
        }
    };

    void delete(int currentItem){
        if (imageList.size() > 0 && viewPager.getCurrentItem() < imageList.size()) {
            currentItem = viewPager.getCurrentItem();
        }
        imageList.remove(viewPager.getCurrentItem());
        fullscreenImageAdapter = new FullscreenImageAdapter(PreviewActivity.this, imageList);
        viewPager.setAdapter(fullscreenImageAdapter);

        Intent intent = new Intent();
        setResult(10, intent);

        if (imageList.size() > 0) {
            viewPager.setCurrentItem(currentItem);
        } else {
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
