package com.codeforlite.virdlerim.Receiver_Classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.legacy.content.WakefulBroadcastReceiver;

import com.codeforlite.virdlerim.Service_Classes.AlarmService;

import static com.google.firebase.iid.WakeLockHolder.startWakefulService;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent alarmServiceIntent=new Intent(context,AlarmService.class);

        AlarmService.enqueueWork(context,new Intent());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
