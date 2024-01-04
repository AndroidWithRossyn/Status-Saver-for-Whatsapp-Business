package com.banrossyn.socialsaver.util;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.documentfile.provider.DocumentFile;

import com.banrossyn.socialsaver.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {

    public static boolean iswApp = true;
    public static String mPath;
    public static final String IMAGE = "image";
    public static final String DOCUMENT = "document";
    public static final String VIDEO = "video";
    public static final String AUDIO = "audio";
    public static final String GIF = "gif";
    public static final String WALLPAPER = "wallpaper";
    public static final String VOICE = "status";
    public static final String STICKER = "sticker";

    public static final String imagesReceivedPath = Environment.getExternalStorageDirectory().toString() + "/WhatsApp/Media/WhatsApp Images";
    public static final String imagesSentPath = Environment.getExternalStorageDirectory().toString() + "/WhatsApp/Media/WhatsApp Images/Sent";

    public static final String documentsReceivedPath = Environment.getExternalStorageDirectory().toString() + "/WhatsApp/Media/WhatsApp Documents";
    public static final String documentsSentPath = Environment.getExternalStorageDirectory().toString() + "/WhatsApp/Media/WhatsApp Documents/Sent";

    public static final String videosReceivedPath = Environment.getExternalStorageDirectory().toString() + "/WhatsApp/Media/WhatsApp Video";
    public static final String videosSentPath = Environment.getExternalStorageDirectory().toString() + "/WhatsApp/Media/WhatsApp Video/Sent";

    public static final String audiosReceivedPath = Environment.getExternalStorageDirectory().toString() + "/WhatsApp/Media/WhatsApp Audio";
    public static final String audiosSentPath = Environment.getExternalStorageDirectory().toString() + "/WhatsApp/Media/WhatsApp Audio/Sent";

    public static final String gifReceivedPath = Environment.getExternalStorageDirectory().toString() + "/WhatsApp/Media/WhatsApp Animated Gifs";
    public static final String gifSentPath = Environment.getExternalStorageDirectory().toString() + "/WhatsApp/Media/WhatsApp Animated Gifs/Sent";

    public static final String wallReceivedPath = Environment.getExternalStorageDirectory().toString() + "/WhatsApp/Media/WallPaper";

    public static final String voiceReceivedPath = Environment.getExternalStorageDirectory().toString() + "/WhatsApp/Media/WhatsApp Voice Notes";
    public static boolean isVoice = false;



    /*wb paths*/

    public static final String wbImagesReceivedPath = Environment.getExternalStorageDirectory().toString() + "/WhatsApp Business/Media/WhatsApp Business Images";
    public static final String wbImagesSentPath = Environment.getExternalStorageDirectory().toString() + "/WhatsApp Business/Media/WhatsApp Business Images/Sent";

    public static final String wbDocumentsReceivedPath = Environment.getExternalStorageDirectory().toString() + "/WhatsApp Business/Media/WhatsApp Business Documents";
    public static final String wbDocumentsSentPath = Environment.getExternalStorageDirectory().toString() + "/WhatsApp Business/Media/WhatsApp Business Documents/Sent";

    public static final String wbVideosReceivedPath = Environment.getExternalStorageDirectory().toString() + "/WhatsApp Business/Media/WhatsApp Business Video";
    public static final String wbVideosSentPath = Environment.getExternalStorageDirectory().toString() + "/WhatsApp Business/Media/WhatsApp Business Video/Sent";

    public static final String wbAudiosReceivedPath = Environment.getExternalStorageDirectory().toString() + "/WhatsApp Business/Media/WhatsApp Business Audio";
    public static final String wbAudiosSentPath = Environment.getExternalStorageDirectory().toString() + "/WhatsApp Business/Media/WhatsApp Business Audio/Sent";

    public static final String wbGifReceivedPath = Environment.getExternalStorageDirectory().toString() + "/WhatsApp Business/Media/WhatsApp Business Animated Gifs";
    public static final String wbGifSentPath = Environment.getExternalStorageDirectory().toString() + "/WhatsApp Business/Media/WhatsApp Business Animated Gifs/Sent";

    public static final String wbWallReceivedPath = Environment.getExternalStorageDirectory().toString() + "/WhatsApp Business/Media/WallPaper";

    public static final String wbVoiceReceivedPath = Environment.getExternalStorageDirectory().toString() + "/WhatsApp Business/Media/WhatsApp Business Voice Notes";
    public static boolean wbIsVoice = false;


    /*whapp paths andr 11*/
    public static final String imagesReceivedPath11 = Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Images";
    public static final String imagesSentPath11 = Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Images/Sent";

    public static final String documentsReceivedPath11 = Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Documents";
    public static final String documentsSentPath11 = Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Documents/Sent";

    public static final String videosReceivedPath11 = Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Video";
    public static final String videosSentPath11 = Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Video/Sent";

    public static final String audiosReceivedPath11 = Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Audio";
    public static final String audiosSentPath11 = Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Audio/Sent";

    public static final String gifReceivedPath11 = Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Animated Gifs";
    public static final String gifSentPath11 = Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Animated Gifs/Sent";

    public static final String wallReceivedPath11 = Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp/WhatsApp/Media/WallPaper";

    public static final String voiceReceivedPath11 = Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Voice Notes";




    /*wb paths andr 11*/

    public static final String wbImagesReceivedPath11 = Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/WhatsApp Business Images";
    public static final String wbImagesSentPath11 = Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/WhatsApp Business Images/Sent";

    public static final String wbDocumentsReceivedPath11 = Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/WhatsApp Business Documents";
    public static final String wbDocumentsSentPath11 = Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/WhatsApp Business Documents/Sent";

    public static final String wbVideosReceivedPath11 = Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/WhatsApp Business Video";
    public static final String wbVideosSentPath11 = Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/WhatsApp Business Video/Sent";

    public static final String wbAudiosReceivedPath11 = Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/WhatsApp Business Audio";
    public static final String wbAudiosSentPath11 = Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/WhatsApp Business Audio/Sent";

    public static final String wbGifReceivedPath11 = Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/WhatsApp Business Animated Gifs";
    public static final String wbGifSentPath11 = Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/WhatsApp Business Animated Gifs/Sent";

    public static final String wbWallReceivedPath11 = Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/WallPaper";

    public static final String wbVoiceReceivedPath11 = Environment.getExternalStorageDirectory().toString() + "/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/WhatsApp Business Voice Notes";


    public static void mediaScanner(Context context, String newFilePath, String oldFilePath, String fileType) {
        try {
                MediaScannerConnection.scanFile(context, new String[]{newFilePath + new File(oldFilePath).getName()}, new String[]{fileType},
                        new MediaScannerConnection.MediaScannerConnectionClient() {
                            public void onMediaScannerConnected() {
                            }

                            public void onScanCompleted(String path, Uri uri) {
                            }
                        });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getBack(String paramString1, String paramString2) {
        Matcher localMatcher = Pattern.compile(paramString2).matcher(paramString1);
        if (localMatcher.find()) {
            return localMatcher.group(1);
        }
        return "";
    }


    public static boolean download(Context context, String sourceFile) {
        if (copyFileInSavedDir(context, sourceFile)) {
            return true;
        } else {
            return false;
        }
    }

    static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }

//    static boolean isVideoFile(String path) {
//        String mimeType = URLConnection.guessContentTypeFromName(path);
//        return mimeType != null && mimeType.startsWith("video");
//    }

    public static boolean isVideoFile(Context context, String path) {
        if (path.startsWith("content")) {
            DocumentFile fromTreeUri = DocumentFile.fromSingleUri(context, Uri.parse(path));
            String mimeType = fromTreeUri.getType();
            return mimeType != null && mimeType.startsWith("video");
        } else {
            String mimeType = URLConnection.guessContentTypeFromName(path);
            return mimeType != null && mimeType.startsWith("video");
        }
    }



    public static boolean copyFileInSavedDir(Context context, String sourceFile) {

        String finalPath;
        if (isVideoFile(context, sourceFile)) {
            finalPath = getDir(context, "Videos").getAbsolutePath();
        } else {
            finalPath = getDir(context, "Images").getAbsolutePath();
        }

        String pathWithName = finalPath + File.separator + new File(sourceFile).getName();
        Uri destUri = Uri.fromFile(new File(pathWithName));

        InputStream is = null;
        OutputStream os = null;
        try {
            Uri uri= Uri.parse(sourceFile);
            is = context.getContentResolver().openInputStream(uri);
            os = context.getContentResolver().openOutputStream(destUri, "w");

            byte[] buffer = new byte[1024];

            int length;
            while ((length = is.read(buffer)) > 0)
                os.write(buffer, 0, length);

            is.close();
            os.flush();
            os.close();

            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(destUri);
            context.sendBroadcast(intent);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    static File getDir(Context context, String folder) {

        File rootFile = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "Download" + File.separator + context.getResources().getString(R.string.app_name) + File.separator + folder);
        rootFile.mkdirs();

        return rootFile;

    }

    public static boolean appInstalledOrNot(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void shareFile(Context context, boolean isVideo, String path) {
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        if (isVideo)
            share.setType("Video/*");
        else
            share.setType("image/*");

        Uri uri;
        if (path.startsWith("content")) {
            uri = Uri.parse(path);
        } else {
            uri = FileProvider.getUriForFile(context,
                    context.getApplicationContext().getPackageName() + ".provider", new File(path));
        }

        share.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(share);
    }

    public static void repostWhatsApp(Context context, boolean isVideo, String path) {
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        if (isVideo)
            share.setType("Video/*");
        else
            share.setType("image/*");

        Uri uri;
        if (path.startsWith("content")) {
            uri = Uri.parse(path);
        } else {
            uri = FileProvider.getUriForFile(context,
                    context.getApplicationContext().getPackageName() + ".provider", new File(path));
        }
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.setPackage("com.whatsapp");
        context.startActivity(share);
    }


    static AlertDialog alertDialog;

    public static AlertDialog loadingPopup(Context context) {
        alertDialog = null;
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        View view = layoutInflaterAndroid.inflate(R.layout.dialog_loader, null);
        AlertDialog.Builder alert = null;
        alert = new AlertDialog.Builder(context);
        alert.setView(view);

        alertDialog = alert.create();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.setCancelable(false);
        return alertDialog;
    }
}
