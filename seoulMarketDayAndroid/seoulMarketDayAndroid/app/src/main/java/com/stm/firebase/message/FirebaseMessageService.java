package com.stm.firebase.message;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.stm.R;
import com.stm.dialog.notification.activity.NotificationDialogActivity;
import com.stm.main.base.activity.MainActivity;
import com.stm.repository.local.SharedPrefersManager;

/**
 * Created by Dev-0 on 2017-06-21.
 */

public class FirebaseMessageService  extends FirebaseMessagingService{

    @Override
    public void onCreate() {
        FirebaseInstanceId.getInstance().getToken();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String dataMessage = remoteMessage.getData().get("body");

        if(dataMessage != null){
            SharedPrefersManager sharedPrefersManager = new SharedPrefersManager(getApplicationContext());
            boolean isAllowed =  sharedPrefersManager.isAllowedForNotification();
            if(isAllowed) {
                sendDataMessage(dataMessage);
            }
        }else{
            String notificationMessage = remoteMessage.getNotification().getBody();
            Intent intent = new Intent(this, NotificationDialogActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("notificationMessage", notificationMessage);
            startActivity(intent);
        }
    }

    private void sendDataMessage(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("서울장날")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }


}
