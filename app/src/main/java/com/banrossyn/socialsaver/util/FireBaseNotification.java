package com.banrossyn.socialsaver.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.banrossyn.socialsaver.HomeActivity;
import com.banrossyn.socialsaver.R;


import java.util.Map;

public class FireBaseNotification extends FirebaseMessagingService {

    private static final String TAG = "FireBaseNotification";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        Map<String, String> params = remoteMessage.getData();
        if (params.size() > 0) {
            sendNotification(params.get("title"), params.get("message"));
            broadcastNewNotification();
        }else {
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }

    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
    }


    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Push Notification", title);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(getResources().getString(R.string.app_name),
                    getResources().getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
            mChannel.enableLights(true);
            mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(mChannel);
        }
//       try{
//            Uri notification= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            Ringtone r=RingtoneManager.getRingtone(getApplicationContext(),notification);
//            r.play();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
        Uri notification= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder;
        notificationBuilder = new NotificationCompat.Builder(this, getResources().getString(R.string.app_name))
                .setAutoCancel(true)
                .setSound(notification)
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
             //   .setColor(getResources().getColor(R.color.transparent))
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                        R.mipmap.ic_launcher_foreground))
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setChannelId(getResources().getString(R.string.app_name))
                .setFullScreenIntent(pendingIntent, true);
        notificationManager.notify(1, notificationBuilder.build());
    }

    private void broadcastNewNotification() {
        Intent intent = new Intent("new_notification");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
