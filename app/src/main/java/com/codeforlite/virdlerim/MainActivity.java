package com.codeforlite.virdlerim;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.codeforlite.virdlerim.DB_Classes.DB_Firebase_Interaction;
import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    private Timer openingPassTimer;
    private TimerTask openingTimerTask;


    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
        } catch (Exception e) {
            e.printStackTrace();
        }


        CircularFillableLoaders openingLogo=findViewById(R.id.openingprogressbar);
        TextView openingTextView=findViewById(R.id.txt_opening);

        new DB_Firebase_Interaction().copyFromSqliteToFirebase();

        openingPassTimer=new Timer();

        final Intent passToMainActivity=new Intent(getApplicationContext(), BaseActivity.class);

        openingTimerTask=new TimerTask() {
            @Override
            public void run() {
                if (!isTaskRoot()
                        && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                        && getIntent().getAction() != null
                        && getIntent().getAction().equals(Intent.ACTION_MAIN)) {

                    finish();
                    return;
                }
                startActivity(passToMainActivity);

            }
        };

        openingPassTimer.schedule(openingTimerTask,2000);

    }


}
