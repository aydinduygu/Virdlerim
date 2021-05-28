package com.codeforlite.virdlerim;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.icu.util.Calendar;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.codeforlite.virdlerim.Receiver_Classes.AlarmReceiver;

import lombok.Data;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;


@Data
public class CustomSwitchPreference {

    private static PendingIntent pendingIntent;
    private static AlarmManager alarmManager;
    private int id;
    private ImageView imageView;
    private TextView title;
    private TextView summary;
    private Switch switchWidget;
    private boolean switchState;
    private TextView clockText;
    private View layout;
    private Context context;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    public CustomSwitchPreference(Context context, View layout,
                                  int id,
                                  ImageView imageView,
                                  TextView title,
                                  TextView summary,
                                  Switch switchWidget,
                                  TextView clockText) {

        this.context = context;
        this.id = id;
        this.imageView = imageView;
        this.title = title;
        this.summary = summary;
        this.switchWidget = switchWidget;
        this.clockText = clockText;
        this.layout = layout;

        switchState = Boolean.parseBoolean(
                context.getSharedPreferences("preferenceStates", Context.MODE_PRIVATE)
                        .getString("" + id, "false"));

        switchWidget.setChecked(switchState);

        String text = (switchState ? " acık" : " kapalı");

        switch (id) {

            case R.id.pref_vibration: {

                this.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_vibration_24));
                this.title.setText("Titreşim");
                this.summary.setText("Titreşim" + text);
                break;

            }
            case R.id.pref_clicksound: {
                this.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_music_note_24));
                this.title.setText("Buton Sesi");

                this.summary.setText("Buton sesi" + text);
                break;
            }
            case R.id.pref_reminder: {

                this.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_vibration_24));
                this.title.setText("Hatırlatıcı");

                this.summary.setText("Hatırlatıcı" + text);
                break;
            }
        }

        int[] attrs = new int[]{R.attr.selectableItemBackground};
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        int backgroundResource = typedArray.getResourceId(0, 0);
        layout.setBackgroundResource(backgroundResource);


        //alarm manager
        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context.getApplicationContext(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);


        layout.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseSwitchCompatOrMaterialCode")
            @Override
            public void onClick(View v) {


                boolean isChecked = switchWidget.isChecked();
                context.getSharedPreferences("preferenceStates", Context.MODE_PRIVATE)
                        .edit()
                        .putString("" + id, "" + !switchWidget.isChecked())
                        .commit();


                String text = (!isChecked ? " acık" : " kapalı");

                summary.setText(title.getText().toString() + text);



                switch (id) {

                    case R.id.pref_vibration: {
                        PreferenceManager.getDefaultSharedPreferences(context)
                                .edit()
                                .putBoolean("pref_vibration",!isChecked).commit();
                        switchWidget.setChecked(!switchWidget.isChecked());

                        break;
                    }
                    case R.id.pref_clicksound: {
                        PreferenceManager.getDefaultSharedPreferences(context)
                                .edit()
                                .putBoolean("pref_clicksound",!isChecked).commit();
                        switchWidget.setChecked(!switchWidget.isChecked());

                        break;
                    }
                    case R.id.pref_reminder: {

                        if (isChecked) {
                            cancelNotifier();
                        } else {
                            SetUpNotifier();
                        }
                        break;
                    }


                }
            }
        });


    }

    private static String getClockString(int hour, int minute) {
        String hourString = "" + hour;
        String minuteString = "" + minute;

        if (hour < 10) {
            hourString = "0" + hourString;
        }
        if (minute < 10) {
            minuteString = "0" + minuteString;
        }

        return hourString + ":" + minuteString;
    }

    private void SetUpNotifier() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("notification_time", MODE_PRIVATE);

        //en son ayarlanmış alarm bilgileri
        int alarmhour = sharedPreferences.getInt("hour", 13);
        int alarmminute = sharedPreferences.getInt("minute", 0);

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("UseSwitchCompatOrMaterialCode")
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                switchWidget.setChecked(!switchWidget.isChecked());

                Calendar calendar = null;
                SharedPreferences.Editor editor =
                        context.getSharedPreferences("notification_time", MODE_PRIVATE).edit();

                editor.putInt("hour", hourOfDay).putInt("minute", minute);
                editor.commit();

                clockText.setText(getClockString(hourOfDay, minute));
                clockText.setVisibility(View.VISIBLE);

                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                long interval = 24 * 60 * 60 * 1000;

                try {
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
                    Toast.makeText(context, "Hatırlatıcı ayarlandı.", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        //time picker ayarlandığında sharade pref olarak kaydet
        @SuppressLint("UseSwitchCompatOrMaterialCode") TimePickerDialog timePickerDialog = new TimePickerDialog(context, onTimeSetListener, alarmhour, alarmminute, true);
        //tpicker ın positive ve negatif butonları
        timePickerDialog.setButton(TimePickerDialog.BUTTON_POSITIVE, "Ayarla", timePickerDialog);
        timePickerDialog.setButton(TimePickerDialog.BUTTON_NEGATIVE, "İptal", timePickerDialog);
        timePickerDialog.show();
    }

    private void cancelNotifier() {
        try {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(context, "Hatırlatıcı iptal edildi.", Toast.LENGTH_LONG).show();
            clockText.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
