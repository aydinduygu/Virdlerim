package com.codeforlite.virdlerim.Service_Classes;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;

import com.codeforlite.virdlerim.Fragments.Pages.Fragment_DailyPrayers_Page;
import com.codeforlite.virdlerim.R;

public class AlarmService extends JobIntentService {

    public static final int JOB_ID = 1;

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, AlarmService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        buildNotification(getApplicationContext());
    }

    @SuppressLint("WrongConstant")
    public void buildNotification(Context context){

        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder=null;

        Intent intent=new Intent(context, Fragment_DailyPrayers_Page.class);

        PendingIntent pendingIntent=PendingIntent.getActivity(context,1,intent,0);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            String channelId="virdlerim";
            String channelName="virdlerimchannel";
            String channelDef="virdlerimchanneldef";
            final int channelpriority=NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel=notificationManager.getNotificationChannel(channelId);

            if (channel==null){

                channel=new NotificationChannel(channelId,channelName,channelpriority);
                channel.setDescription(channelDef);
                notificationManager.createNotificationChannel(channel);
            }
            builder=new NotificationCompat.Builder(context,channelId);
            builder.setContentTitle("\"Günlük Virdlerinizi Okudunuz Mu?\"");
            //builder.setContentText("içerik");
            builder.setSmallIcon(R.mipmap.ic_launcher_foreground);
            //builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher_foreground));
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent);

        }
        else{
            builder=new NotificationCompat.Builder(context);
            builder.setContentTitle("Günlük Virdlerinizi Okudunuz Mu?");
            //builder.setContentText("içerik");
            builder.setSmallIcon(R.mipmap.ic_launcher_foreground);
           // builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher_foreground));
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent);
            builder.setPriority(NotificationManager.IMPORTANCE_HIGH);
        }

        notificationManager.notify(1,builder.build());

    }
}
