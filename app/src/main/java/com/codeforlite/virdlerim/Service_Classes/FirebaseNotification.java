package com.codeforlite.virdlerim.Service_Classes;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.codeforlite.virdlerim.R;
import com.codeforlite.virdlerim.ScreenClasses.GunlukVirdlerimScreen;
import com.codeforlite.virdlerim.VirdlerimApplication;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseNotification extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        String title=remoteMessage.getNotification().getTitle();
        String content=remoteMessage.getNotification().getBody();
        generateNotification(this,title,content);

    }

    @SuppressLint("ResourceAsColor")
    private void generateNotification(Context context, String title, String content){

        Intent intent=new Intent(context, GunlukVirdlerimScreen.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,2,intent,0);

        NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder=null;

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            String channelId="virdlerim";
            String channelName="virdlerimchannel";
            String channelDef="virdlerimchanneldef";
            final int channelpriority= NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel=notificationManager.getNotificationChannel(channelId);

            if (channel==null){

                channel=new NotificationChannel(channelId,channelName,channelpriority);
                channel.setDescription(channelDef);
                notificationManager.createNotificationChannel(channel);
            }
            builder=new NotificationCompat.Builder(context,channelId);
            builder.setContentTitle(title);
            builder.setSmallIcon(R.mipmap.ic_launcher_foreground);
            builder.setContentText(content);
            builder.setAutoCancel(true);
           // builder.setContentIntent(pendingIntent);

        }
        else{
            builder=new NotificationCompat.Builder(context);
            builder.setContentTitle(title);
            builder.setContentText(content);
            builder.setAutoCancel(true);
            builder.setSmallIcon(R.mipmap.ic_launcher_foreground);
           // builder.setContentIntent(pendingIntent);
            builder.setPriority(NotificationManager.IMPORTANCE_HIGH);
        }
        notificationManager.notify(1,builder.build());
    }

}
