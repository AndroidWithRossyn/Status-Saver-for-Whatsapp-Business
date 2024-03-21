package com.kessi.statussaver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.storage.StorageManager;
import android.text.format.Formatter;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kessi.statussaver.adapter.WallCleanerAdapter;
import com.kessi.statussaver.fragments.Utils;
import com.kessi.statussaver.model.CleanerFileModel;
import com.kessi.statussaver.util.AdManager;
import com.kessi.statussaver.util.SharedPrefs;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WallCleanerActivity extends AppCompatActivity implements WallCleanerAdapter.OnCheckboxListener {

    ImageView backIV;
    LinearLayout delete;
    TextView txt;
    TextView emptyTxt;
    File file;
    ArrayList<CleanerFileModel> filesToDelete = new ArrayList<>();
    WallCleanerAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView recyclerView;
    ArrayList<CleanerFileModel> statusImageList = new ArrayList<>();
    String folderPath;
    String category;
    CheckBox selectAll;

    RelativeLayout loaderLay, emptyLay;
    LinearLayout sAccessBtn;
    int REQUEST_ACTION_OPEN_DOCUMENT_TREE = 1010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_cleaner);

        category = getIntent().getStringExtra("category");
        folderPath = getIntent().getStringExtra("folderPath");

        loaderLay = findViewById(R.id.loaderLay);
        emptyLay = findViewById(R.id.emptyLay);

        backIV = findViewById(R.id.backIV);
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        emptyTxt = findViewById(R.id.emptyTxt);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);


        txt = findViewById(R.id.txt);
        delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!AdManager.isloadFbAd) {
                    AdManager.adCounter++;
                    AdManager.showInterAd(WallCleanerActivity.this, null, 0);
                } else {
                    AdManager.adCounter++;
                    AdManager.showMaxInterstitial(WallCleanerActivity.this, null, 0);
                }
                if (!filesToDelete.isEmpty()) {
                    new AlertDialog.Builder(WallCleanerActivity.this)
                            .setMessage("Are you sure , You want to delete selected files?")
                            .setCancelable(true)
                            .setNegativeButton("Yes", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    int success = -1;
                                    ArrayList<CleanerFileModel> deletedFiles = new ArrayList<>();

                                    for (CleanerFileModel details : filesToDelete) {
                                        DocumentFile fromTreeUri = DocumentFile.fromSingleUri(WallCleanerActivity.this, Uri.parse(details.getFilePath()));
                                        if (fromTreeUri.exists()) {
                                            if (fromTreeUri.delete()) {
                                                deletedFiles.add(details);
                                                if (success == 0) {
                                                    return;
                                                }
                                                success = 1;
                                            } else {
                                                success = 0;
                                            }
                                        } else {
                                            success = 0;
                                        }
                                    }

                                    filesToDelete.clear();
                                    for (CleanerFileModel deletedFile : deletedFiles) {
                                        statusImageList.remove(deletedFile);
                                    }
                                    mAdapter.notifyDataSetChanged();
                                    if (success == 0) {
                                        Toast.makeText(WallCleanerActivity.this, "Couldn't delete some files", Toast.LENGTH_SHORT).show();
                                    } else if (success == 1) {
                                        Toast.makeText(WallCleanerActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    txt.setText(R.string.delete_items_blank);
                                    txt.setTextColor(getResources().getColor(R.color.h_btn_text_color));
                                    selectAll.setChecked(false);
                                }
                            })
                            .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).create().show();
                }
            }
        });

        selectAll = findViewById(R.id.selectAll);
        selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (!AdManager.isloadFbAd) {
                    AdManager.adCounter++;
                    AdManager.showInterAd(WallCleanerActivity.this, null, 0);
                } else {
                    AdManager.adCounter++;
                    AdManager.showMaxInterstitial(WallCleanerActivity.this, null, 0);
                }

                if (!compoundButton.isPressed()) {
                    return;
                }

                filesToDelete.clear();

                for (int i = 0; i < statusImageList.size(); i++) {
                    if (!statusImageList.get(i).selected) {
                        b = true;
                        break;
                    }
                }

                if (b) {
                    for (int i = 0; i < statusImageList.size(); i++) {
                        statusImageList.get(i).selected = true;
                        filesToDelete.add(statusImageList.get(i));
                    }
                    selectAll.setChecked(true);
                } else {
                    for (int i = 0; i < statusImageList.size(); i++) {
                        statusImageList.get(i).selected = false;
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });

        sAccessBtn = findViewById(R.id.sAccessBtn);
        sAccessBtn.setOnClickListener(v -> {


            StorageManager sm = (StorageManager) getSystemService(Context.STORAGE_SERVICE);

            String statusDir = folderPath;

            Intent intent = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                intent = sm.getPrimaryStorageVolume().createOpenDocumentTreeIntent();
                Uri uri = intent.getParcelableExtra("android.provider.extra.INITIAL_URI");

                String scheme = uri.toString();

                scheme = scheme.replace("/root/", "/document/");

                scheme += "%3A" + statusDir;

                uri = Uri.parse(scheme);

                intent.putExtra("android.provider.extra.INITIAL_URI", uri);
            } else {
                intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                intent.putExtra("android.provider.extra.INITIAL_URI", Uri.parse("content://com.android.externalstorage.documents/document/primary%3A" + statusDir));
            }


            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);

            startActivityForResult(intent, REQUEST_ACTION_OPEN_DOCUMENT_TREE);

        });

        if (Utils.iswApp && this.category.equals(Utils.WALLPAPER)) {
            if (!SharedPrefs.getWAWallTree(WallCleanerActivity.this).equals("")) {
                populateGrid();
            }
        } else if (Utils.iswApp && this.category.equals(Utils.STICKER)) {
            if (!SharedPrefs.getWAStickerTree(WallCleanerActivity.this).equals("")) {
                populateGrid();
            }
        }

        LinearLayout adContainer = findViewById(R.id.banner_container);

        if (!AdManager.isloadFbAd) {
            //admob
            AdManager.initAd(WallCleanerActivity.this);
            AdManager.loadBannerAd(WallCleanerActivity.this, adContainer);
            AdManager.loadInterAd(WallCleanerActivity.this);
        } else {
            //MAX + Fb banner Ads
            AdManager.initMAX(WallCleanerActivity.this);
            AdManager.maxBanner(WallCleanerActivity.this, adContainer);
            AdManager.maxInterstital(WallCleanerActivity.this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (async != null) {
            async.cancel(true);
        }
    }

    @Override
    public void onBackPressed() {
        AdManager.adCounter++;
        if (AdManager.adCounter == AdManager.adDisplayCounter) {
            if (!AdManager.isloadFbAd) {
                AdManager.showInterAd(WallCleanerActivity.this, null, 0);
            } else {
                AdManager.showMaxInterstitial(WallCleanerActivity.this, null, 0);
            }
        } else {
            super.onBackPressed();
        }
    }

    loadDataAsync async;

    public void populateGrid() {
        async = new loadDataAsync();
        async.execute();
    }

    class loadDataAsync extends AsyncTask<Void, Void, Void> {
        DocumentFile[] allFiles;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loaderLay.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            sAccessBtn.setVisibility(View.GONE);
            emptyLay.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            allFiles = null;
            statusImageList = new ArrayList<>();
            allFiles = getFromSdcard();
            for (int i = 0; i < allFiles.length; i++) {
                if (!allFiles[i].getUri().toString().contains(".nomedia") && !allFiles[i].isDirectory()) {
                    statusImageList.add(new CleanerFileModel(allFiles[i].getUri().toString(),
                            allFiles[i].getName(), String.valueOf(allFiles[i].length())));
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            new Handler().postDelayed(() -> {
//                Collections.reverse(statusImageList);
                mAdapter = new WallCleanerAdapter(WallCleanerActivity.this, statusImageList, Utils.WALLPAPER, WallCleanerActivity.this);
                recyclerView.setAdapter(mAdapter);
                loaderLay.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                if (statusImageList == null || statusImageList.size() == 0) {
                    emptyLay.setVisibility(View.VISIBLE);
                } else {
                    emptyLay.setVisibility(View.GONE);
                }

            }, 300);
        }
    }

    private DocumentFile[] getFromSdcard() {
        String treeUri = "";

        if (Utils.iswApp && this.category.equals(Utils.WALLPAPER)) {

            treeUri = SharedPrefs.getWAWallTree(WallCleanerActivity.this);

        } else if (!Utils.iswApp && this.category.equals(Utils.WALLPAPER)) {

            treeUri = SharedPrefs.getWBWallTree(WallCleanerActivity.this);

        } else if (Utils.iswApp && this.category.equals(Utils.STICKER)) {

            treeUri = SharedPrefs.getWAStickerTree(WallCleanerActivity.this);

        } else if (!Utils.iswApp && this.category.equals(Utils.STICKER)) {

            treeUri = SharedPrefs.getWBStickerTree(WallCleanerActivity.this);

        }

        DocumentFile fromTreeUri = DocumentFile.fromTreeUri(getApplicationContext(), Uri.parse(treeUri));
        if (fromTreeUri != null && fromTreeUri.exists() && fromTreeUri.isDirectory()
                && fromTreeUri.canRead() && fromTreeUri.canWrite()) {

            return fromTreeUri.listFiles();
        } else {
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ACTION_OPEN_DOCUMENT_TREE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
//            Log.e("onActivityResult: ", "" + data.getData());
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    getContentResolver()
                            .takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (Utils.iswApp && this.category.equals(Utils.WALLPAPER)) {

                SharedPrefs.setWAWallTree(WallCleanerActivity.this, uri.toString());

            } else if (!Utils.iswApp && this.category.equals(Utils.WALLPAPER)) {

                SharedPrefs.setWBWallTree(WallCleanerActivity.this, uri.toString());

            } else if (Utils.iswApp && this.category.equals(Utils.STICKER)) {

                SharedPrefs.setWAStickerTree(WallCleanerActivity.this, uri.toString());

            } else if (!Utils.iswApp && this.category.equals(Utils.STICKER)) {

                SharedPrefs.setWBStickerTree(WallCleanerActivity.this, uri.toString());

            }

            populateGrid();
        }
    }

    @Override
    public void onCheckboxListener(View view, List<CleanerFileModel> updatedFiles) {
        filesToDelete.clear();
        for (CleanerFileModel details : updatedFiles) {
            if (details.isSelected()) {
                filesToDelete.add(details);
            }
        }
        if (filesToDelete.size() == statusImageList.size()) {
            selectAll.setChecked(true);
        }
        if (!filesToDelete.isEmpty()) {
            long totalFileSize = 0;

            for (CleanerFileModel details : filesToDelete) {
//                totalFileSize += new File(details.getFilePath()).length();
//                totalFileSize++;
                totalFileSize += Integer.parseInt(details.getSize());
            }
            String size = Formatter.formatShortFileSize(this, totalFileSize);
            txt.setText("Delete Selected Items (" + size + ")");
            txt.setTextColor(Color.parseColor("#5AA061"));
            return;
        }
        txt.setText(R.string.delete_items_blank);
        txt.setTextColor(getResources().getColor(R.color.h_btn_text_color));
        selectAll.setChecked(false);
    }
}
