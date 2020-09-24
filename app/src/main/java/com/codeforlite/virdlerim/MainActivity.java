package com.codeforlite.virdlerim;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.codeforlite.virdlerim.ScreenClasses.Folder_Screen;
import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    private Timer openingPassTimer;
    private TimerTask openingTimerTask;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CircularFillableLoaders openingLogo=findViewById(R.id.openingprogressbar);
        TextView openingTextView=findViewById(R.id.txt_opening);

        new DB_Firebase_Interaction().copyFromSqliteToFirebase();

        openingPassTimer=new Timer();

        final Intent passToMainActivity=new Intent(getApplicationContext(), Folder_Screen.class);

        openingTimerTask=new TimerTask() {
            @Override
            public void run() {
                finish();
                startActivity(passToMainActivity);

            }
        };

        openingPassTimer.schedule(openingTimerTask,3000);

    }


}
