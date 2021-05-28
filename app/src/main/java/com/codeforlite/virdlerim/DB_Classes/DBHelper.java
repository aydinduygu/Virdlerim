package com.codeforlite.virdlerim.DB_Classes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper {

    private static String DB_NAME= "virdlerim.db";
    private static String DB_PATH;
    private static int DB_VERSION=1;
    private SQLiteDatabase myDatabase;
    private Context context;


    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context=context;
        DB_PATH=context.getFilesDir().getParent()+"/databases/";
        createDatabase();
    }

    public void createDatabase(){

        boolean dbExists= false;
        try {
            dbExists = checkDatabase();
        } catch (SQLiteCantOpenDatabaseException e) {
            e.printStackTrace();
        }

        myDatabase=getWritableDatabase();
        Cursor c=null;

        try {
            c=myDatabase.rawQuery("SELECT * FROM TESBIHLER",null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        if(c==null||c.getCount()<1){

            try{
                copyDatabase();
                myDatabase=getWritableDatabase();
                myDatabase.close();

            }catch (Exception e){
                Log.e("HATA","VERİ TABANI KOPYALANAMIYOR");
                throw  new Error("Veri Tabanı Kopyalanamıyor");
            }

        }

        else {

            myDatabase.close();
        }

    }

    boolean checkDatabase() throws SQLiteCantOpenDatabaseException {

        SQLiteDatabase checkDB=null;

            String myPath=DB_PATH+DB_NAME;
            checkDB=SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);


            Log.e("HATA","VERİ TABANI AÇILAMADI");


        if(checkDB!=null){
            checkDB.close();
        }
        return checkDB !=null ?true:false;
    }

    void copyDatabase(){

        try{

            InputStream myInput=context.getAssets().open(DB_NAME);
            String outputFileName=DB_PATH+DB_NAME;
            OutputStream myOutput= new FileOutputStream(outputFileName);
            byte[] buffer=new byte[1024];

            int length;

            while((length=myInput.read(buffer))>0){
                myOutput.write(buffer,0,length);
            }

            myOutput.flush();
            myInput.close();
            myOutput.close();

        } catch (Exception e) {
            Log.e("HATA","KOPYA OLUŞTURMA HATASI");
            e.printStackTrace();
        }

    }

    SQLiteDatabase openDatabase(){

        String myPath=DB_PATH+DB_NAME;
        myDatabase=SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
        return myDatabase;

    }

    @Override
    public synchronized void close()
    {
        if (myDatabase != null && myDatabase.isOpen())
            myDatabase.close();

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public SQLiteDatabase getMyDatabase() {
        return myDatabase;
    }

    public void setMyDatabase(SQLiteDatabase myDatabase) {
        this.myDatabase = myDatabase;
    }
}
