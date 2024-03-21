package com.kessi.statussaver;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import com.kessi.statussaver.fragments.Utils;
import com.kessi.statussaver.util.AdManager;
import com.kessi.statussaver.util.SharedPrefs;

import java.io.File;

import static android.os.Build.VERSION.SDK_INT;

public class CleanOptionActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView backIV;
    RelativeLayout imgBtn, videoBtn, docBtn, audioBtn, stickerBtn, wallBtn, gifBtn;
    getSizeAsync async;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean_option);
        init();

        LinearLayout adContainer = findViewById(R.id.banner_container);

        if (!AdManager.isloadFbAd) {
            //admob
            AdManager.initAd(CleanOptionActivity.this);
            AdManager.loadBannerAd(CleanOptionActivity.this, adContainer);
            AdManager.loadInterAd(CleanOptionActivity.this);
        } else {
            //MAX + Fb banner Ads
            AdManager.initMAX(CleanOptionActivity.this);
            AdManager.maxBanner(CleanOptionActivity.this, adContainer);
            AdManager.maxInterstital(CleanOptionActivity.this);
        }
    }

    void init() {
        backIV = findViewById(R.id.backIV);
        backIV.setOnClickListener(this);

        imgBtn = findViewById(R.id.imgBtn);
        imgBtn.setOnClickListener(this);

        videoBtn = findViewById(R.id.videoBtn);
        videoBtn.setOnClickListener(this);

        docBtn = findViewById(R.id.docBtn);
        docBtn.setOnClickListener(this);

        audioBtn = findViewById(R.id.audioBtn);
        audioBtn.setOnClickListener(this);

        stickerBtn = findViewById(R.id.stickerBtn);
        stickerBtn.setOnClickListener(this);

        wallBtn = findViewById(R.id.wallBtn);
        wallBtn.setOnClickListener(this);

        gifBtn = findViewById(R.id.gifBtn);
        gifBtn.setOnClickListener(this);

        async = new getSizeAsync();
        async.execute();
    }

    @Override
    public void onBackPressed() {
        AdManager.adCounter++;
        if (AdManager.adCounter == AdManager.adDisplayCounter) {
            if (!AdManager.isloadFbAd) {
                AdManager.showInterAd(CleanOptionActivity.this, null, 0);
            } else {
                AdManager.showMaxInterstitial(CleanOptionActivity.this, null, 0);
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        async = new getSizeAsync();
        async.execute();
    }

    class getSizeAsync extends AsyncTask<Void, Void, Void> {
        String txt1 = "0B", txt2 = "0B", txt3 = "0B", txt4 = "0B",
                txt5 = "0B", txt6 = "0B", txt7 = "0B";

        @Override
        protected Void doInBackground(Void... voids) {
            if (Utils.iswApp) {

                //Images
                if (!SharedPrefs.getWAImgTree(CleanOptionActivity.this).equals("") &&
                        !SharedPrefs.getWAImgSendTree(CleanOptionActivity.this).equals("")) {
                    txt1 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWAImgTree(CleanOptionActivity.this))) +
                                    folderSize(getFiles(SharedPrefs.getWAImgSendTree(CleanOptionActivity.this))));
                } else if (!SharedPrefs.getWAImgTree(CleanOptionActivity.this).equals("") &&
                        SharedPrefs.getWAImgSendTree(CleanOptionActivity.this).equals("")) {
                    txt1 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWAImgTree(CleanOptionActivity.this))));
                } else if (SharedPrefs.getWAImgTree(CleanOptionActivity.this).equals("") &&
                        !SharedPrefs.getWAImgSendTree(CleanOptionActivity.this).equals("")) {
                    txt1 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWAImgSendTree(CleanOptionActivity.this))));
                }

                //Videos
                if (!SharedPrefs.getWAVideoTree(CleanOptionActivity.this).equals("") &&
                        !SharedPrefs.getWAVideoSendTree(CleanOptionActivity.this).equals("")) {
                    txt2 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWAVideoTree(CleanOptionActivity.this))) +
                                    folderSize(getFiles(SharedPrefs.getWAVideoSendTree(CleanOptionActivity.this))));
                } else if (!SharedPrefs.getWAVideoTree(CleanOptionActivity.this).equals("") &&
                        SharedPrefs.getWAVideoSendTree(CleanOptionActivity.this).equals("")) {
                    txt2 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWAVideoTree(CleanOptionActivity.this))));
                } else if (SharedPrefs.getWAVideoTree(CleanOptionActivity.this).equals("") &&
                        !SharedPrefs.getWAVideoSendTree(CleanOptionActivity.this).equals("")) {
                    txt2 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWAVideoSendTree(CleanOptionActivity.this))));
                }

                //Documents
                if (!SharedPrefs.getWADocTree(CleanOptionActivity.this).equals("") &&
                        !SharedPrefs.getWADocSendTree(CleanOptionActivity.this).equals("")) {
                    txt3 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWADocTree(CleanOptionActivity.this))) +
                                    folderSize(getFiles(SharedPrefs.getWADocSendTree(CleanOptionActivity.this))));
                } else if (!SharedPrefs.getWADocTree(CleanOptionActivity.this).equals("") &&
                        SharedPrefs.getWADocSendTree(CleanOptionActivity.this).equals("")) {
                    txt3 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWADocTree(CleanOptionActivity.this))));
                } else if (SharedPrefs.getWADocTree(CleanOptionActivity.this).equals("") &&
                        !SharedPrefs.getWADocSendTree(CleanOptionActivity.this).equals("")) {
                    txt3 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWADocSendTree(CleanOptionActivity.this))));
                }

                //Audios
                if (!SharedPrefs.getWAAudioTree(CleanOptionActivity.this).equals("") &&
                        !SharedPrefs.getWAAudioSendTree(CleanOptionActivity.this).equals("")) {
                    txt4 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWAAudioTree(CleanOptionActivity.this))) +
                                    folderSize(getFiles(SharedPrefs.getWAAudioSendTree(CleanOptionActivity.this))));
                } else if (!SharedPrefs.getWAAudioTree(CleanOptionActivity.this).equals("") &&
                        SharedPrefs.getWAAudioSendTree(CleanOptionActivity.this).equals("")) {
                    txt4 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWAAudioTree(CleanOptionActivity.this))));
                } else if (SharedPrefs.getWAAudioTree(CleanOptionActivity.this).equals("") &&
                        !SharedPrefs.getWAAudioSendTree(CleanOptionActivity.this).equals("")) {
                    txt4 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWAAudioSendTree(CleanOptionActivity.this))));
                }

                //Sticker
                if (!SharedPrefs.getWAStickerTree(CleanOptionActivity.this).equals("")) {
                    txt5 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWAStickerTree(CleanOptionActivity.this))));
                }

                //Wallpaper
                if (!SharedPrefs.getWAWallTree(CleanOptionActivity.this).equals("")) {
                    txt6 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWAWallTree(CleanOptionActivity.this))));
                }

                //Gif
                if (!SharedPrefs.getWAGifTree(CleanOptionActivity.this).equals("") &&
                        !SharedPrefs.getWAGifSendTree(CleanOptionActivity.this).equals("")) {
                    txt7 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWAGifTree(CleanOptionActivity.this))) +
                                    folderSize(getFiles(SharedPrefs.getWAGifSendTree(CleanOptionActivity.this))));
                } else if (!SharedPrefs.getWAGifTree(CleanOptionActivity.this).equals("") &&
                        SharedPrefs.getWAGifSendTree(CleanOptionActivity.this).equals("")) {
                    txt7 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWAGifTree(CleanOptionActivity.this))));
                } else if (SharedPrefs.getWAGifTree(CleanOptionActivity.this).equals("") &&
                        !SharedPrefs.getWAGifSendTree(CleanOptionActivity.this).equals("")) {
                    txt7 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWAGifSendTree(CleanOptionActivity.this))));
                }

            } else {

                //Images
                if (!SharedPrefs.getWBImgTree(CleanOptionActivity.this).equals("") &&
                        !SharedPrefs.getWBImgSendTree(CleanOptionActivity.this).equals("")) {
                    txt1 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWBImgTree(CleanOptionActivity.this))) +
                                    folderSize(getFiles(SharedPrefs.getWBImgSendTree(CleanOptionActivity.this))));
                } else if (!SharedPrefs.getWBImgTree(CleanOptionActivity.this).equals("") &&
                        SharedPrefs.getWBImgSendTree(CleanOptionActivity.this).equals("")) {
                    txt1 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWBImgTree(CleanOptionActivity.this))));
                } else if (SharedPrefs.getWBImgTree(CleanOptionActivity.this).equals("") &&
                        !SharedPrefs.getWBImgSendTree(CleanOptionActivity.this).equals("")) {
                    txt1 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWBImgSendTree(CleanOptionActivity.this))));
                }

                //Videos
                if (!SharedPrefs.getWBVideoTree(CleanOptionActivity.this).equals("") &&
                        !SharedPrefs.getWBVideoSendTree(CleanOptionActivity.this).equals("")) {
                    txt2 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWBVideoTree(CleanOptionActivity.this))) +
                                    folderSize(getFiles(SharedPrefs.getWBVideoSendTree(CleanOptionActivity.this))));
                } else if (!SharedPrefs.getWBVideoTree(CleanOptionActivity.this).equals("") &&
                        SharedPrefs.getWBVideoSendTree(CleanOptionActivity.this).equals("")) {
                    txt2 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWBVideoTree(CleanOptionActivity.this))));
                } else if (SharedPrefs.getWBVideoTree(CleanOptionActivity.this).equals("") &&
                        !SharedPrefs.getWBVideoSendTree(CleanOptionActivity.this).equals("")) {
                    txt2 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWBVideoSendTree(CleanOptionActivity.this))));
                }

                //Documents
                if (!SharedPrefs.getWBDocTree(CleanOptionActivity.this).equals("") &&
                        !SharedPrefs.getWBDocSendTree(CleanOptionActivity.this).equals("")) {
                    txt3 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWBDocTree(CleanOptionActivity.this))) +
                                    folderSize(getFiles(SharedPrefs.getWBDocSendTree(CleanOptionActivity.this))));
                } else if (!SharedPrefs.getWBDocTree(CleanOptionActivity.this).equals("") &&
                        SharedPrefs.getWBDocSendTree(CleanOptionActivity.this).equals("")) {
                    txt3 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWBDocTree(CleanOptionActivity.this))));
                } else if (SharedPrefs.getWBDocTree(CleanOptionActivity.this).equals("") &&
                        !SharedPrefs.getWBDocSendTree(CleanOptionActivity.this).equals("")) {
                    txt3 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWBDocSendTree(CleanOptionActivity.this))));
                }

                //Audios
                if (!SharedPrefs.getWBAudioTree(CleanOptionActivity.this).equals("") &&
                        !SharedPrefs.getWBAudioSendTree(CleanOptionActivity.this).equals("")) {
                    txt4 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWBAudioTree(CleanOptionActivity.this))) +
                                    folderSize(getFiles(SharedPrefs.getWBAudioSendTree(CleanOptionActivity.this))));
                } else if (!SharedPrefs.getWBAudioTree(CleanOptionActivity.this).equals("") &&
                        SharedPrefs.getWBAudioSendTree(CleanOptionActivity.this).equals("")) {
                    txt4 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWBAudioTree(CleanOptionActivity.this))));
                } else if (SharedPrefs.getWBAudioTree(CleanOptionActivity.this).equals("") &&
                        !SharedPrefs.getWBAudioSendTree(CleanOptionActivity.this).equals("")) {
                    txt4 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWBAudioSendTree(CleanOptionActivity.this))));
                }

                //Sticker
                if (!SharedPrefs.getWBStickerTree(CleanOptionActivity.this).equals("")) {
                    txt5 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWBStickerTree(CleanOptionActivity.this))));
                }

                //Wallpaper
                if (!SharedPrefs.getWBWallTree(CleanOptionActivity.this).equals("")) {
                    txt6 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWBWallTree(CleanOptionActivity.this))));
                }

                //Gif
                if (!SharedPrefs.getWBGifTree(CleanOptionActivity.this).equals("") &&
                        !SharedPrefs.getWBGifSendTree(CleanOptionActivity.this).equals("")) {
                    txt7 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWBGifTree(CleanOptionActivity.this))) +
                                    folderSize(getFiles(SharedPrefs.getWBGifSendTree(CleanOptionActivity.this))));
                } else if (!SharedPrefs.getWBGifTree(CleanOptionActivity.this).equals("") &&
                        SharedPrefs.getWBGifSendTree(CleanOptionActivity.this).equals("")) {
                    txt7 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWBGifTree(CleanOptionActivity.this))));
                } else if (SharedPrefs.getWBGifTree(CleanOptionActivity.this).equals("") &&
                        !SharedPrefs.getWBGifSendTree(CleanOptionActivity.this).equals("")) {
                    txt7 = Formatter.formatShortFileSize(CleanOptionActivity.this,
                            folderSize(getFiles(SharedPrefs.getWBGifSendTree(CleanOptionActivity.this))));
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            ((TextView) findViewById(R.id.txt1)).setText(txt1);
            ((TextView) findViewById(R.id.txt2)).setText(txt2);
            ((TextView) findViewById(R.id.txt3)).setText(txt3);
            ((TextView) findViewById(R.id.txt4)).setText(txt4);
            ((TextView) findViewById(R.id.txt5)).setText(txt5);
            ((TextView) findViewById(R.id.txt6)).setText(txt6);
            ((TextView) findViewById(R.id.txt7)).setText(txt7);
        }
    }

    public DocumentFile[] getFiles(String treeUri) {
        DocumentFile fromTreeUri = DocumentFile.fromTreeUri(getApplicationContext(), Uri.parse(treeUri));
        if (fromTreeUri != null && fromTreeUri.exists() && fromTreeUri.isDirectory()
                && fromTreeUri.canRead() && fromTreeUri.canWrite()) {

            return fromTreeUri.listFiles();
        } else {
            return null;
        }
    }

    public static long folderSize(DocumentFile[] files) {
        long j = 0;
        long length = 0;
        if (files == null) {
            return length;
        } else {
            for (DocumentFile file : files) {
                if (file.isFile()) {
                    j = file.length();
                }
                length += j;
            }
            return length;
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.backIV) {
            onBackPressed();
        } else if (id == R.id.imgBtn) {
            if (Utils.iswApp) {

                onTapBtn(Utils.IMAGE, getWhatsupFolder("WhatsApp Images"),
                        getWhatsupFolder("WhatsApp Images%2FSent"));

            } else {

                onTapBtn(Utils.IMAGE, getWhatsupBusFolder("WhatsApp Business Images"),
                        getWhatsupBusFolder("WhatsApp Business Images%2FSent"));

            }
        } else if (id == R.id.videoBtn) {
            if (Utils.iswApp) {

                onTapBtn(Utils.VIDEO, getWhatsupFolder("WhatsApp Video"),
                        getWhatsupFolder("WhatsApp Video%2FSent"));

            } else {

                onTapBtn(Utils.VIDEO, getWhatsupBusFolder("WhatsApp Business Video"),
                        getWhatsupBusFolder("WhatsApp Business Video%2FSent"));

            }
        } else if (id == R.id.docBtn) {
            if (Utils.iswApp) {

                onTapBtn(Utils.DOCUMENT, getWhatsupFolder("WhatsApp Documents"),
                        getWhatsupFolder("WhatsApp Documents%2FSent"));

            } else {

                onTapBtn(Utils.DOCUMENT, getWhatsupBusFolder("WhatsApp Business Documents"),
                        getWhatsupBusFolder("WhatsApp Business Documents%2FSent"));

            }
        } else if (id == R.id.audioBtn) {
            if (Utils.iswApp) {

                onTapBtn(Utils.AUDIO, getWhatsupFolder("WhatsApp Audio"),
                        getWhatsupFolder("WhatsApp Audio%2FSent"));

            } else {

                onTapBtn(Utils.AUDIO, getWhatsupBusFolder("WhatsApp Business Audio"),
                        getWhatsupBusFolder("WhatsApp Business Audio%2FSent"));

            }
        } else if (id == R.id.stickerBtn) {
            if (Utils.iswApp) {

                onTapOther(Utils.STICKER, getWhatsupFolder("WhatsApp Stickers"));

            } else {

                onTapOther(Utils.STICKER, getWhatsupBusFolder("WhatsApp Business Stickers"));

            }
        } else if (id == R.id.wallBtn) {
            if (Utils.iswApp) {

                onTapOther(Utils.WALLPAPER, getWhatsupFolder("WallPaper"));

            } else {

                onTapOther(Utils.WALLPAPER, getWhatsupBusFolder("WallPaper"));

            }
        } else if (id == R.id.gifBtn) {
            if (Utils.iswApp) {

                onTapBtn(Utils.GIF, getWhatsupFolder("WhatsApp Animated Gifs"),
                        getWhatsupFolder("WhatsApp Animated Gifs%2FSent"));

            } else {

                onTapBtn(Utils.GIF, getWhatsupBusFolder("WhatsApp Business Animated Gifs"),
                        getWhatsupBusFolder("WhatsApp Business Animated Gifs%2FSent"));

            }
        }
    }

    void onTapBtn(String category, String receivePath, String sentPath) {
        if (Utils.iswApp) {
            if (com.kessi.statussaver.util.Utils.appInstalledOrNot(CleanOptionActivity.this, "com.whatsapp")) {
                onTTap(category, receivePath, sentPath);
            } else {
                Toast.makeText(CleanOptionActivity.this, "Please Install WhatsApp For Download Status!!!!!", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (com.kessi.statussaver.util.Utils.appInstalledOrNot(CleanOptionActivity.this, "com.whatsapp.w4b")) {
                onTTap(category, receivePath, sentPath);
            } else {
                Toast.makeText(CleanOptionActivity.this, "Please Install WhatsApp Business For Download Status!!!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void onTTap(String category, String receivePath, String sentPath) {
        Intent intent = new Intent(this, CleanDataActivity.class);
        intent.putExtra("category", category);
        intent.putExtra("receivePath", receivePath);
        intent.putExtra("sentPath", sentPath);
        startActivityes(intent);
    }

    void onTapOther(String category, String path) {
        if (Utils.iswApp) {
            if (com.kessi.statussaver.util.Utils.appInstalledOrNot(CleanOptionActivity.this, "com.whatsapp")) {
                oTOther(category, path);
            } else {
                Toast.makeText(CleanOptionActivity.this, "Please Install WhatsApp For Download Status!!!!!", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (com.kessi.statussaver.util.Utils.appInstalledOrNot(CleanOptionActivity.this, "com.whatsapp.w4b")) {
                oTOther(category, path);
            } else {
                Toast.makeText(CleanOptionActivity.this, "Please Install WhatsApp Business For Download Status!!!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void oTOther(String category, String path) {
        Intent intent = new Intent(this, WallCleanerActivity.class);
        intent.putExtra("category", category);
        intent.putExtra("folderPath", path);
        startActivityes(intent);
    }

    void startActivityes(Intent intent) {
        destroyAsyc();
        if (!AdManager.isloadFbAd) {
            AdManager.adCounter++;
            AdManager.showInterAd(CleanOptionActivity.this, intent, 0);
        } else {
            AdManager.adCounter++;
            AdManager.showMaxInterstitial(CleanOptionActivity.this, intent, 0);
        }
    }

    void destroyAsyc(){
        if (async != null) {
            async.cancel(true);
        }
    }

    public String getWhatsupFolder(String folder) {
        if (new File(Environment.getExternalStorageDirectory() + File.separator + "Android/media/com.whatsapp/WhatsApp" + File.separator + "Media").isDirectory()) {
            return "Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F" + folder;
        } else {
            return "WhatsApp%2FMedia%2F" + folder;
        }
    }

    public String getWhatsupBusFolder(String folder) {
        if (new File(Environment.getExternalStorageDirectory() + File.separator + "Android/media/com.whatsapp.w4b/WhatsApp Business" + File.separator + "Media").isDirectory()) {
            return "Android%2Fmedia%2Fcom.whatsapp.w4b%2FWhatsApp Business%2FMedia%2F" + folder;
        } else {
            return "WhatsApp Business%2FMedia%2F" + folder;
        }
    }
}
