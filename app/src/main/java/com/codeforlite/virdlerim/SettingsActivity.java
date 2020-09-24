package com.codeforlite.virdlerim;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import com.codeforlite.virdlerim.Receiver_Classes.AlarmReceiver;
import com.codeforlite.virdlerim.Service_Classes.AlarmService;

public class SettingsActivity extends AppCompatActivity {

    private ImageView gear1;
    private ImageView gear2;


    private Calendar calendar;
    private static Intent intent;
    private static PendingIntent pendingIntent;
    private static AlarmManager alarmManager;
    private static TimePickerDialog timePickerDialog;
    private static TextView clock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        //en son ayarlanmış alarm bilgileri
        SharedPreferences sharedPreferences=getSharedPreferences("notification_time",MODE_PRIVATE);
        int alarmhour=sharedPreferences.getInt("hour",13);
        int alarmminute=sharedPreferences.getInt("minute",0);


        //alarm manager
        alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        intent=new Intent(this, AlarmReceiver.class);
        pendingIntent=PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        //dişli resimleri
        gear1=findViewById(R.id.img_gear1);
        gear2=findViewById(R.id.img_gear2);

        clock=findViewById(R.id.txt_time);

        clock.setText(getClockString(alarmhour,alarmminute));

        //preferences fragment ın yerleştirilmesi
        SettingsFragment fragment=new SettingsFragment(SettingsActivity.this);
        getSupportFragmentManager().beginTransaction().replace(R.id.settings, fragment).commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) { actionBar.setDisplayHomeAsUpEnabled(true);}

        //dişliler için dönme animasyonu
        Animation rotate= AnimationUtils.loadAnimation(SettingsActivity.this,R.anim.gear);
        Animation rotate2= AnimationUtils.loadAnimation(SettingsActivity.this,R.anim.gear2);
        gear1.startAnimation(rotate);
        gear2.startAnimation(rotate2);

    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        private SwitchPreference switch_vibration;
        private SwitchPreference switch_clickSound;
        private SwitchPreference switch_notifications;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        private Context context;
        private Calendar calendar;

        public SettingsFragment(Context context) {

            this.context=context;
            calendar=Calendar.getInstance();

        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.settings_layout, rootKey);

            SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(context);

            boolean vibration=sp.getBoolean("pref_vibration",true);
            boolean clickSound=sp.getBoolean("pref_clicksound",true);
            boolean notifications=sp.getBoolean("pref_notification",false);


            switch_vibration=findPreference("pref_vibration");
            switch_clickSound=findPreference("pref_clicksound");
            switch_notifications=findPreference("pref_notification");
            if (!switch_notifications.isChecked()){

                clock.setVisibility(View.INVISIBLE);}

            //en son ayarlanmış alarm bilgileri
            sharedPreferences=context.getSharedPreferences("notification_time",MODE_PRIVATE);
            editor=sharedPreferences.edit();
            int alarmhour=sharedPreferences.getInt("hour",13);
            int alarmminute=sharedPreferences.getInt("minute",0);

            TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    editor.putInt("hour",hourOfDay);
                    editor.putInt("minute",minute);
                    editor.commit();

                    clock.setText(getClockString(hourOfDay,minute));
                    clock.setVisibility(View.VISIBLE);

                    calendar=Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                    calendar.set(Calendar.MINUTE,minute);
                    long interval=24*60*60*1000;

                    try {
                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),interval,pendingIntent);
                        Toast.makeText(context,"Hatırlatıcı ayarlandı.",Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };

            //time picker ayarlandığında sharade pref olarak kaydet
            timePickerDialog=new TimePickerDialog(context, onTimeSetListener,alarmhour,alarmminute,true);

            //tpicker ın positive ve negatif butonları
            timePickerDialog.setButton(TimePickerDialog.BUTTON_POSITIVE,"Ayarla",timePickerDialog);
            timePickerDialog.setButton(TimePickerDialog.BUTTON_NEGATIVE,"İptal",timePickerDialog);

            //Hatırlatıcı pref'e tıklandığında..
            getSwitch_notifications().setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @SuppressLint("WrongConstant")
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    if (!switch_notifications.isChecked()){

                        try {
                            alarmManager.cancel(pendingIntent);
                            Toast.makeText(context,"Hatırlatıcı iptal edildi.",Toast.LENGTH_LONG).show();
                            clock.setVisibility(View.INVISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    else {

                        timePickerDialog.show();
                        clock.setVisibility(View.VISIBLE);
                        timePickerDialog.getButton(TimePickerDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getSwitch_notifications().setChecked(false);
                                clock.setVisibility(View.INVISIBLE);
                                timePickerDialog.dismiss();
                            }
                        });
                    }

                    return false;
                }
            });
            
        }



        public SwitchPreference getSwitch_vibration() {
            return switch_vibration;
        }

        public void setSwitch_vibration(SwitchPreference switch_vibration) {
            this.switch_vibration = switch_vibration;
        }

        public SwitchPreference getSwitch_clickSound() {
            return switch_clickSound;
        }

        public void setSwitch_clickSound(SwitchPreference switch_clickSound) {
            this.switch_clickSound = switch_clickSound;
        }

        public SwitchPreference getSwitch_notifications() {
            return switch_notifications;
        }

        public void setSwitch_notifications(SwitchPreference switch_notifications) {
            this.switch_notifications = switch_notifications;
        }

        @Nullable
        @Override
        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }
    }

    private static String getClockString(int hour, int minute){
        String hourString=""+hour;
        String minuteString=""+minute;

        if (hour<10){hourString="0"+hourString;}
        if (minute<10){minuteString="0"+minuteString;}

        return hourString+":"+minuteString;
    }

}