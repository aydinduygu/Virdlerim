package com.codeforlite.virdlerim;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

public class VirdlerimApplication extends Application {

    private static Context context;
    private static DBHelper dbHelper;
    private static Activity activity;

    public void onCreate(){

        super.onCreate();
        VirdlerimApplication.context=getApplicationContext();
        VirdlerimApplication.activity=getActivity();
        prepareDatabase();

    }

    public static Context getAppContext(){

        return VirdlerimApplication.context;

    }
    private static void prepareDatabase(){
        dbHelper=new DBHelper(VirdlerimApplication.getAppContext());
    }
    public static DBHelper getDbHelper() {

        return dbHelper;
    }

    public static Activity getActivity() {
        return activity;
    }

    public static void setActivity(Activity activity) {
        VirdlerimApplication.activity = activity;
    }
}
