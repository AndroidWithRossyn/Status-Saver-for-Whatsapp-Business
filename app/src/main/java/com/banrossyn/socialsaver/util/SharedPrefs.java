package com.banrossyn.socialsaver.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class SharedPrefs {

    public static final String PREF_NIGHT_MODE = "night_mode";
    private static SharedPreferences mPreferences;
    public static final String WA_TREE_URI = "wa_tree_uri";
    public static final String WB_TREE_URI = "wb_tree_uri";

    private static SharedPreferences getInstance(Context context) {
        if (mPreferences == null) {
            mPreferences = context.getApplicationContext()
                    .getSharedPreferences("stat_data", Context.MODE_PRIVATE);
        }
        return mPreferences;
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return getInstance(context).getInt(key, defaultValue);
    }

    public static void setInt(Context context, String key, int value) {
        getInstance(context).edit().putInt(key, value).apply();
    }

    public static void clearPrefs(Context context) {
        getInstance(context).edit().clear().apply();
    }

    public static int getAppNightDayMode(Context context) {
        return getInt(context, PREF_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_NO);
    }

    public static void setWATree(Context context, String value) {
        getInstance(context).edit().putString(WA_TREE_URI, value).apply();
    }

    public static String getWATree(Context context) {
        return getInstance(context).getString(WA_TREE_URI, "");
    }

    public static void setWBTree(Context context, String value) {
        getInstance(context).edit().putString(WB_TREE_URI, value).apply();
    }

    public static String getWBTree(Context context) {
        return getInstance(context).getString(WB_TREE_URI, "");
    }


    //    Image whatsapp
    public static void setWAImgTree(Context context, String value) {
        getInstance(context).edit().putString("wa_img_tree_uri", value).apply();
    }

    public static String getWAImgTree(Context context) {
        return getInstance(context).getString("wa_img_tree_uri", "");
    }

    public static void setWAImgSendTree(Context context, String value) {
        getInstance(context).edit().putString("wa_img_send_tree_uri", value).apply();
    }

    public static String getWAImgSendTree(Context context) {
        return getInstance(context).getString("wa_img_send_tree_uri", "");
    }

    //    Video whatsapp
    public static void setWAVideoTree(Context context, String value) {
        getInstance(context).edit().putString("wa_video_tree_uri", value).apply();
    }

    public static String getWAVideoTree(Context context) {
        return getInstance(context).getString("wa_video_tree_uri", "");
    }

    public static void setWAVideoSendTree(Context context, String value) {
        getInstance(context).edit().putString("wa_video_send_tree_uri", value).apply();
    }

    public static String getWAVideoSendTree(Context context) {
        return getInstance(context).getString("wa_video_send_tree_uri", "");
    }

    //    Document whatsapp
    public static void setWADocTree(Context context, String value) {
        getInstance(context).edit().putString("wa_doc_tree_uri", value).apply();
    }

    public static String getWADocTree(Context context) {
        return getInstance(context).getString("wa_doc_tree_uri", "");
    }

    public static void setWADocSendTree(Context context, String value) {
        getInstance(context).edit().putString("wa_doc_send_tree_uri", value).apply();
    }

    public static String getWADocSendTree(Context context) {
        return getInstance(context).getString("wa_doc_send_tree_uri", "");
    }

    //    Audio whatsapp
    public static void setWAAudioTree(Context context, String value) {
        getInstance(context).edit().putString("wa_audio_tree_uri", value).apply();
    }

    public static String getWAAudioTree(Context context) {
        return getInstance(context).getString("wa_audio_tree_uri", "");
    }

    public static void setWAAudioSendTree(Context context, String value) {
        getInstance(context).edit().putString("wa_audio_send_tree_uri", value).apply();
    }

    public static String getWAAudioSendTree(Context context) {
        return getInstance(context).getString("wa_audio_send_tree_uri", "");
    }

    //    Sticker whatsapp
    public static void setWAStickerTree(Context context, String value) {
        getInstance(context).edit().putString("wa_sticker_tree_uri", value).apply();
    }

    public static String getWAStickerTree(Context context) {
        return getInstance(context).getString("wa_sticker_tree_uri", "");
    }


    //    GIF whatsapp
    public static void setWAGifTree(Context context, String value) {
        getInstance(context).edit().putString("wa_Gif_tree_uri", value).apply();
    }

    public static String getWAGifTree(Context context) {
        return getInstance(context).getString("wa_Gif_tree_uri", "");
    }

    public static void setWAGifSendTree(Context context, String value) {
        getInstance(context).edit().putString("wa_Gif_send_tree_uri", value).apply();
    }

    public static String getWAGifSendTree(Context context) {
        return getInstance(context).getString("wa_Gif_send_tree_uri", "");
    }

    //    Wallpaper whatsapp
    public static void setWAWallTree(Context context, String value) {
        getInstance(context).edit().putString("wa_wall_tree_uri", value).apply();
    }

    public static String getWAWallTree(Context context) {
        return getInstance(context).getString("wa_wall_tree_uri", "");
    }









    //    Image whatsapp business
    public static void setWBImgTree(Context context, String value) {
        getInstance(context).edit().putString("wb_img_tree_uri", value).apply();
    }

    public static String getWBImgTree(Context context) {
        return getInstance(context).getString("wb_img_tree_uri", "");
    }

    public static void setWBImgSendTree(Context context, String value) {
        getInstance(context).edit().putString("wb_img_send_tree_uri", value).apply();
    }

    public static String getWBImgSendTree(Context context) {
        return getInstance(context).getString("wb_img_send_tree_uri", "");
    }

    //    Video whatsapp business
    public static void setWBVideoTree(Context context, String value) {
        getInstance(context).edit().putString("wb_video_tree_uri", value).apply();
    }

    public static String getWBVideoTree(Context context) {
        return getInstance(context).getString("wb_video_tree_uri", "");
    }

    public static void setWBVideoSendTree(Context context, String value) {
        getInstance(context).edit().putString("wb_video_send_tree_uri", value).apply();
    }

    public static String getWBVideoSendTree(Context context) {
        return getInstance(context).getString("wb_video_send_tree_uri", "");
    }

    //    Document whatsapp business
    public static void setWBDocTree(Context context, String value) {
        getInstance(context).edit().putString("wb_doc_tree_uri", value).apply();
    }

    public static String getWBDocTree(Context context) {
        return getInstance(context).getString("wb_doc_tree_uri", "");
    }

    public static void setWBDocSendTree(Context context, String value) {
        getInstance(context).edit().putString("wb_doc_send_tree_uri", value).apply();
    }

    public static String getWBDocSendTree(Context context) {
        return getInstance(context).getString("wb_doc_send_tree_uri", "");
    }

    //    Audio whatsapp business
    public static void setWBAudioTree(Context context, String value) {
        getInstance(context).edit().putString("wb_audio_tree_uri", value).apply();
    }

    public static String getWBAudioTree(Context context) {
        return getInstance(context).getString("wb_audio_tree_uri", "");
    }

    public static void setWBAudioSendTree(Context context, String value) {
        getInstance(context).edit().putString("wb_audio_send_tree_uri", value).apply();
    }

    public static String getWBAudioSendTree(Context context) {
        return getInstance(context).getString("wb_audio_send_tree_uri", "");
    }

    //    Sticker whatsapp business
    public static void setWBStickerTree(Context context, String value) {
        getInstance(context).edit().putString("wb_sticker_tree_uri", value).apply();
    }

    public static String getWBStickerTree(Context context) {
        return getInstance(context).getString("wb_sticker_tree_uri", "");
    }


    //    GIF whatsapp business
    public static void setWBGifTree(Context context, String value) {
        getInstance(context).edit().putString("wb_Gif_tree_uri", value).apply();
    }

    public static String getWBGifTree(Context context) {
        return getInstance(context).getString("wb_Gif_tree_uri", "");
    }

    public static void setWBGifSendTree(Context context, String value) {
        getInstance(context).edit().putString("wb_Gif_send_tree_uri", value).apply();
    }

    public static String getWBGifSendTree(Context context) {
        return getInstance(context).getString("wb_Gif_send_tree_uri", "");
    }

    //    Wallpaper whatsapp business
    public static void setWBWallTree(Context context, String value) {
        getInstance(context).edit().putString("wb_wall_tree_uri", value).apply();
    }

    public static String getWBWallTree(Context context) {
        return getInstance(context).getString("wb_wall_tree_uri", "");
    }


}
