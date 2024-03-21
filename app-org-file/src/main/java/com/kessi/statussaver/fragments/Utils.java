package com.kessi.statussaver.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;

import com.kessi.statussaver.R;

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


}
