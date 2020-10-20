package com.codeforlite.virdlerim;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.codeforlite.virdlerim.Vird_Classes.Ayet;
import com.codeforlite.virdlerim.Vird_Classes.AyetGrubu;
import com.codeforlite.virdlerim.Vird_Classes.Dua;
import com.codeforlite.virdlerim.Vird_Classes.Esma;
import com.codeforlite.virdlerim.Vird_Classes.Salavat;
import com.codeforlite.virdlerim.Vird_Classes.Tesbih;
import com.codeforlite.virdlerim.Vird_Classes.Vird;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DB_Interaction {


    private DBHelper dbHelper;
    private Context context;

    public DB_Interaction(Context Context, DBHelper dbHelper) {
        this.dbHelper = dbHelper;
        this.context=Context;
    }

    public List<Vird> fetch_All(String type){

        if (!dbHelper.getMyDatabase().isOpen()){dbHelper.setMyDatabase(dbHelper.openDatabase());}

        List<Vird> virdList=new ArrayList<>();
        switch (type.toLowerCase()){

            case "vird":{}
            case "esma":{

                ArrayList<Vird> esmaList=new ArrayList<>();

                Cursor c=dbHelper.getMyDatabase().rawQuery("SELECT * FROM ESMA",null);

                while (c.moveToNext()){

                    Esma esma=new Esma(""+c.getInt(c.getColumnIndex("ID")),
                            c.getString(c.getColumnIndex("IMAGE_NAME")),
                            c.getString(c.getColumnIndex("TURKISH_TEXT")),
                            c.getString(c.getColumnIndex("MEANING")),
                            Integer.parseInt(c.getString(c.getColumnIndex("EBCED"))),
                            c.getString(c.getColumnIndex("ARABIC_TEXT")));


                    try {
                        byte[] image_inByte=c.getBlob(c.getColumnIndex("IMAGE"));
                        //Bitmap image= BitmapFactory.decodeByteArray(c.getBlob(c.getColumnIndex("IMAGE")),0,c.getBlob(c.getColumnIndex("IMAGE")).length);
                        esma.setImage_inbyte(image_inByte);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    esmaList.add(esma);
                }

                dbHelper.getMyDatabase().close();

                return esmaList;
            }

            case "tesbih":{

                if (!dbHelper.getMyDatabase().isOpen()){dbHelper.setMyDatabase(dbHelper.openDatabase());}

                Cursor c=dbHelper.getMyDatabase().rawQuery("SELECT * FROM TESBIHLER",null);

                while (c.moveToNext()){

                    Tesbih tesbih=new Tesbih(""+c.getInt(c.getColumnIndex("ID")),
                            c.getString(c.getColumnIndex("TITLE")),
                            c.getString(c.getColumnIndex("IMAGE_NAME")),
                            c.getString(c.getColumnIndex("ARABIC_TEXT")),
                            c.getString(c.getColumnIndex("TURKISH_TEXT")),
                            c.getString(c.getColumnIndex("MEANING")));

                    Bitmap image= null;
                    try {
                        byte[] image_inByte=c.getBlob(c.getColumnIndex("IMAGE"));
                        tesbih.setImage_inbyte(image_inByte);
                       // image = BitmapFactory.decodeByteArray(image_inByte,0,image_inByte.length);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    virdList.add(tesbih);

                }
                dbHelper.getMyDatabase().close();
                return virdList;
            }
            case "dua":{

                if (!dbHelper.getMyDatabase().isOpen()){dbHelper.setMyDatabase(dbHelper.openDatabase());}

                Cursor c=dbHelper.getMyDatabase().rawQuery("SELECT * FROM DUALAR",null);

                while (c.moveToNext()){

                    Dua dua=new Dua(""+c.getInt(c.getColumnIndex("ID")),c.getString(c.getColumnIndex("TITLE")),
                            c.getString(c.getColumnIndex("IMAGE_NAME")),
                            c.getString(c.getColumnIndex("ARABIC_TEXT")),
                            c.getString(c.getColumnIndex("TURKISH_TEXT")),
                            c.getString(c.getColumnIndex("MEANING")));

                    Bitmap image= null;
                    try {
                        byte[] image_inByte=c.getBlob(c.getColumnIndex("IMAGE"));
                        dua.setImage_inbyte(image_inByte);
                        // image = BitmapFactory.decodeByteArray(image_inByte,0,image_inByte.length);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    virdList.add(dua);

                }
                dbHelper.getMyDatabase().close();
                return virdList;
            }
            case "salavat":{

                if (!dbHelper.getMyDatabase().isOpen()){dbHelper.setMyDatabase(dbHelper.openDatabase());}

                Cursor c=dbHelper.getMyDatabase().rawQuery("SELECT * FROM SALAVATLAR",null);

                while (c.moveToNext()){

                    Salavat salavat=new Salavat(""+c.getInt(c.getColumnIndex("ID")),c.getString(c.getColumnIndex("TITLE")),
                            c.getString(c.getColumnIndex("IMAGE_NAME")),
                            c.getString(c.getColumnIndex("ARABIC_TEXT")),
                            c.getString(c.getColumnIndex("TURKISH_TEXT")),
                            c.getString(c.getColumnIndex("MEANING")));

                    Bitmap image= null;
                    try {
                        byte[] image_inByte=c.getBlob(c.getColumnIndex("IMAGE"));
                        salavat.setImage_inbyte(image_inByte);
                        // image = BitmapFactory.decodeByteArray(image_inByte,0,image_inByte.length);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    virdList.add(salavat);

                }
                dbHelper.getMyDatabase().close();
                return virdList;


            }
            case "ayetgrubu":{

                if (!dbHelper.getMyDatabase().isOpen()){dbHelper.setMyDatabase(dbHelper.openDatabase());}

                List<AyetGrubu> ayetGrubuListList=new ArrayList<>();
                //Cursor c3=myDatabase.rawQuery("SELECT * FROM AYETGRUPLARIM",null);

                //while (c3.moveToNext()){

                    AyetGrubu ayetGrubu=null;
                  //  byte [] ayetGrubuBinary=c3.getBlob(c3.getColumnIndex("CONTENT"));

                    //ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(ayetGrubuBinary);
                    //try {
                        Cursor c=dbHelper.getMyDatabase().rawQuery("SELECT * FROM AYETGRUPLARIM",null);

                        while (c.moveToNext()){

                            ayetGrubu=new AyetGrubu(""+c.getInt(c.getColumnIndex("ID")),c.getString(c.getColumnIndex("TITLE")),
                                    c.getString(c.getColumnIndex("ARABIC_TEXT")),
                                    c.getString(c.getColumnIndex("TURKISH_TEXT")),
                                    c.getString(c.getColumnIndex("MEAL")),
                                    c.getString(c.getColumnIndex("IMAGE_NAME")));

                            ayetGrubuListList.add(ayetGrubu);

                        }
                      //  ObjectInputStream objectInputStream=new ObjectInputStream(byteArrayInputStream);
                       // try {
                        //    ayetGrubu=(AyetGrubu)objectInputStream.readObject();
                        //} catch (ClassNotFoundException e) {
                         //   e.printStackTrace();
                        //}
                    //} catch (Exception e) {
                      //  e.printStackTrace();
                    //}
                    return (List<Vird>)(List<?>)ayetGrubuListList;
                }

            }

       // }

        dbHelper.getMyDatabase().close();
        return virdList;
    }

    public void insertData(Vird vird){

        if (!dbHelper.getMyDatabase().isOpen()){dbHelper.setMyDatabase(dbHelper.openDatabase());}


        String type=vird.getClass().getSimpleName();

        switch (type){

            case "Tesbih":{

                try{
                    Tesbih tesbih=(Tesbih)vird;

                    String sql_createTable="CREATE TABLE IF NOT EXISTS TESBIHLER (ID INTEGER PRIMARY KEY AUTOINCREMENT,ARABIC_TEXT TEXT,TURKISH_TEXT TEXT,MEANING TEXT,SOUND BLOB,IMAGE BLOB,IMAGE_NAME TEXT,TITLE TEXT);";
                    SQLiteStatement sqLiteStatement_createTable=dbHelper.getMyDatabase().compileStatement(sql_createTable);
                    sqLiteStatement_createTable.execute();

                    ContentValues values=new ContentValues();

                    values.put("ARABIC_TEXT",tesbih.getArabicText());
                    values.put("TURKISH_TEXT",tesbih.getTurkishText());
                    values.put("MEANING",tesbih.getMealormeaning());
                    values.put("TITLE",tesbih.getTitle());

                    dbHelper.getMyDatabase().insert("TESBIHLER",null,values);
                    Cursor c=dbHelper.getMyDatabase().rawQuery("SELECT * FROM TESBIHLER",null);
                    c.moveToLast();
                    tesbih.setId(""+c.getInt(c.getColumnIndex("ID")));

                    if (vird.getImage_inbitmap()!=null){
                        if(vird.getImageName().equals("tesbih_null_image.jpg")){
                            vird.setImageName(vird.getId());
                        }
                        saveImageOnInternalStorage(tesbih);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    dbHelper.getMyDatabase().close();
                }

                break;

            }
            case "Dua":{

                if (!dbHelper.getMyDatabase().isOpen()){dbHelper.setMyDatabase(dbHelper.openDatabase());}

                try{
                    Dua dua=(Dua)vird;

                    String sql_createTable="CREATE TABLE IF NOT EXISTS DUALAR (ID INTEGER PRIMARY KEY AUTOINCREMENT,ARABIC_TEXT TEXT,TURKISH_TEXT TEXT,MEANING TEXT,SOUND BLOB,IMAGE BLOB,IMAGE_NAME TEXT,TITLE TEXT);";
                    SQLiteStatement sqLiteStatement_createTable=dbHelper.getMyDatabase().compileStatement(sql_createTable);
                    sqLiteStatement_createTable.execute();

                    ContentValues values=new ContentValues();

                    values.put("ARABIC_TEXT",dua.getArabicText());
                    values.put("TURKISH_TEXT",dua.getTurkishText());
                    values.put("MEANING",dua.getMealormeaning());
                    values.put("TITLE",dua.getTitle());
                    dbHelper.getMyDatabase().insert("DUALAR",null,values);
                    Cursor c=dbHelper.getMyDatabase().rawQuery("SELECT * FROM DUALAR",null);
                    c.moveToLast();
                    dua.setId(""+c.getInt(c.getColumnIndex("ID")));

                    if (vird.getImage_inbitmap()!=null){
                        if(vird.getImageName().equals("dua_null_image.jpg")){
                            vird.setImageName(vird.getId());
                        }
                        saveImageOnInternalStorage(dua);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                   dbHelper.getMyDatabase().close();
                }

                break;
            }
            case "Salavat":{

                if (!dbHelper.getMyDatabase().isOpen()){dbHelper.setMyDatabase(dbHelper.openDatabase());}

                Salavat salavat=(Salavat)vird;

                try{
                    String sql_createTable="CREATE TABLE IF NOT EXISTS SALAVATLAR (ID INTEGER PRIMARY KEY AUTOINCREMENT,ARABIC_TEXT TEXT,TURKISH_TEXT TEXT,MEANING TEXT,SOUND BLOB,IMAGE BLOB,IMAGE_NAME TEXT,TITLE TEXT);";
                    SQLiteStatement sqLiteStatement_createTable=dbHelper.getMyDatabase().compileStatement(sql_createTable);
                    sqLiteStatement_createTable.execute();

                    ContentValues values=new ContentValues();

                    values.put("ARABIC_TEXT",salavat.getArabicText());
                    values.put("TURKISH_TEXT",salavat.getTurkishText());
                    values.put("MEANING",salavat.getMealormeaning());
                    values.put("TITLE",salavat.getTitle());
                    dbHelper.getMyDatabase().insert("SALAVATLAR",null,values);
                    Cursor c=dbHelper.getMyDatabase().rawQuery("SELECT * FROM SALAVATLAR",null);
                    c.moveToLast();
                    salavat.setId(""+c.getInt(c.getColumnIndex("ID")));

                    if (vird.getImage_inbitmap()!=null){
                        if(vird.getImageName().equals("salavat_null_image.jpg")){
                            vird.setImageName(vird.getId());
                        }
                        saveImageOnInternalStorage(salavat);
                    }
                 } catch (Exception e) {
                     e.printStackTrace();
                 } finally {
                    dbHelper.getMyDatabase().close();
                 }

                 break;
            }
            case "AyetGrubu":{

                if (!dbHelper.getMyDatabase().isOpen()){dbHelper.setMyDatabase(dbHelper.openDatabase());}

                AyetGrubu ayetGrubu=(AyetGrubu)vird;
                // ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                //byte[] ayetGrubu_inBinary=null;

                try {

                    //ObjectOutputStream objectOutputStream=new ObjectOutputStream(byteArrayOutputStream);
                    //objectOutputStream.writeObject(ayetGrubu);
                    //objectOutputStream.flush();
                    //ayetGrubu_inBinary=byteArrayOutputStream.toByteArray();
                    String sql_createTable="CREATE TABLE IF NOT EXISTS AYETGRUPLARIM (ID INTEGER PRIMARY KEY AUTOINCREMENT,ARABIC_TEXT TEXT,TURKISH_TEXT TEXT,MEAL TEXT,SOUND BLOB,IMAGE BLOB,IMAGE_NAME TEXT,TITLE TEXT);";
                    SQLiteStatement sqLiteStatement_createTable=dbHelper.getMyDatabase().compileStatement(sql_createTable);
                    sqLiteStatement_createTable.execute();

                    ContentValues values=new ContentValues();
                    values.put("ARABIC_TEXT",ayetGrubu.getArabicText());
                    values.put("TURKISH_TEXT",ayetGrubu.getTurkishText());
                    values.put("MEAL",ayetGrubu.getMeal());
                    values.put("TITLE",ayetGrubu.getTitle());
                    dbHelper.getMyDatabase().insert("AYETGRUPLARIM",null,values);
                    Cursor c=dbHelper.getMyDatabase().rawQuery("SELECT * FROM AYETGRUPLARIM",null);
                    c.moveToLast();
                    ayetGrubu.setId(""+c.getInt(c.getColumnIndex("ID")));

                    if (vird.getImage_inbitmap()!=null){
                        if(vird.getImageName().equals("ayetgrubu_null_image.jpg")){
                            vird.setImageName(vird.getId());
                        }
                        saveImageOnInternalStorage(ayetGrubu);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    dbHelper.getMyDatabase().close();
                }
                break;
            }
        }
    }

    public void removeData(Vird vird){
        if (!dbHelper.getMyDatabase().isOpen()){dbHelper.setMyDatabase(dbHelper.openDatabase());}

        switch (vird.getClass().getSimpleName()){

            case "AyetGrubu":{

                AyetGrubu ayetGrubu=(AyetGrubu)vird;
                String ID=ayetGrubu.getId();
                int ayetGrubuID=Integer.parseInt(ID.substring(ID.indexOf("_")+1));
                dbHelper.getMyDatabase().delete("AYETGRUPLARIM","ID=?",new String[]{String.valueOf(ayetGrubuID)});
                try {
                    deleteImageFromInternalStorage(vird.getImageName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case "Tesbih":{

                Tesbih tesbih=(Tesbih) vird;
                String ID=tesbih.getId();
                int tesbihID=Integer.parseInt(ID.substring(ID.indexOf("_")+1));
                dbHelper.getMyDatabase().delete("TESBIHLER","ID=?",new String[]{String.valueOf(tesbihID)});
                try {
                    deleteImageFromInternalStorage(vird.getImageName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case "Dua":{
                Dua dua=(Dua)vird;
                String ID=dua.getId();
                int duaID=Integer.parseInt(ID.substring(ID.indexOf("_")+1));
                dbHelper.getMyDatabase().delete("DUALAR","ID=?",new String[]{String.valueOf(duaID)});
                try {
                    deleteImageFromInternalStorage(vird.getImageName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case "Salavat":{
                Salavat salavat=(Salavat) vird;
                String ID=salavat.getId();
                int salavatID=Integer.parseInt(ID.substring(ID.indexOf("_")+1));
                dbHelper.getMyDatabase().delete("SALAVATLAR","ID=?",new String[]{String.valueOf(salavatID)});
                try {
                    deleteImageFromInternalStorage(vird.getImageName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            default:
        }

        dbHelper.getMyDatabase().close();
    }

    public Ayet fetchAyet(int sureNo, int ayetNo){

        if (!dbHelper.getMyDatabase().isOpen()){dbHelper.setMyDatabase(dbHelper.openDatabase());}

        Cursor c1=dbHelper.getMyDatabase().rawQuery("SELECT * FROM KURAN WHERE AYET_NO="+ayetNo+" AND SURE_NO="+sureNo ,null);
        Cursor c2=dbHelper.getMyDatabase().rawQuery("SELECT * FROM ELMALILI_MEAL WHERE AYET_NO="+ayetNo+" AND SURE_NO="+sureNo ,null);

        c1.moveToFirst();
        c2.moveToFirst();
        Ayet ayet=new Ayet(c1.getInt(c1.getColumnIndex("SURE_NO")),c1.getInt(c1.getColumnIndex("AYET_NO")),c1.getString(c1.getColumnIndex("AYET")),c2.getString(c2.getColumnIndex("AYET")));
        dbHelper.getMyDatabase().close();
        return ayet;
    }

    public AyetGrubu fetchAyet(int sureNo){
        if (!dbHelper.getMyDatabase().isOpen()){dbHelper.setMyDatabase(dbHelper.openDatabase());}


        Cursor c1=dbHelper.getMyDatabase().rawQuery("SELECT * FROM KURAN WHERE SURE_NO="+sureNo ,null);
        Cursor c2=dbHelper.getMyDatabase().rawQuery("SELECT * FROM ELMALILI_MEAL WHERE SURE_NO="+sureNo ,null);

        ArrayList<Ayet> ayetler=new ArrayList<>();
        while (c1.moveToNext()&&c2.moveToNext()) {
            Ayet ayet = new Ayet(c1.getInt(c1.getColumnIndex("SURE_NO")), c1.getInt(c1.getColumnIndex("AYET_NO")), c1.getString(c1.getColumnIndex("AYET")), c2.getString(c2.getColumnIndex("AYET")));
            ayetler.add(ayet);
        }

        AyetGrubu ayetGrubu=new AyetGrubu(null,null,ayetler);

        dbHelper.getMyDatabase().close();

        return ayetGrubu;
    }

    public AyetGrubu fetchAyetGrubu( int sureNo,int fromAyet, int toAyet){
        if (!dbHelper.getMyDatabase().isOpen()){dbHelper.setMyDatabase(dbHelper.openDatabase());}

        Cursor c1=dbHelper.getMyDatabase().rawQuery("SELECT * FROM KURAN WHERE SURE_NO="+sureNo ,null);
        Cursor c2=dbHelper.getMyDatabase().rawQuery("SELECT * FROM ELMALILI_MEAL WHERE SURE_NO="+sureNo ,null);

        c1.moveToFirst();
        c2.moveToFirst();

        ArrayList<Ayet> ayetler=new ArrayList<>();
        while (c1.moveToNext()&&c2.moveToNext()) {

            Ayet ayet = new Ayet(c1.getInt(c1.getColumnIndex("SURE_NO")),
                    c1.getInt(c1.getColumnIndex("AYET_NO")),
                    c1.getString(c1.getColumnIndex("AYET")),
                    c2.getString(c2.getColumnIndex("AYET")));

            if(ayet.getAyetNo()>=fromAyet&&ayet.getAyetNo()<=toAyet) {
                ayetler.add(ayet);
            }
        }

        AyetGrubu ayetGrubu=new AyetGrubu(null,null,ayetler);

        dbHelper.getMyDatabase().close();

        return ayetGrubu;
    }

    public byte[] getEsmaImageByteArray(String id) throws SQLiteException {
        if (!dbHelper.getMyDatabase().isOpen()){dbHelper.setMyDatabase(dbHelper.openDatabase());}

        int esmaID=Integer.parseInt(id.substring(id.indexOf("_")+1));
        Cursor c=getMyDatabase().rawQuery("SELECT * FROM ESMA WHERE ID=?",new String[]{String.valueOf(esmaID)});
        c.moveToFirst();
        byte [] image_inbyte= null;
        try {
            image_inbyte = c.getBlob(c.getColumnIndex("IMAGE"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image_inbyte;

    }

    public ArrayList<String> getSureNames_fromDB(){

        if (!dbHelper.getMyDatabase().isOpen()){dbHelper.setMyDatabase(dbHelper.openDatabase());}

        ArrayList<String> sureNames=new ArrayList<>();
        Cursor c=dbHelper.getMyDatabase().rawQuery("SELECT * FROM SURELER",null);

        while (c.moveToNext()){

            String sureName=c.getString(c.getColumnIndex("ISIM"));
            sureNames.add(sureName);

        }

        dbHelper.getMyDatabase().close();

        return sureNames;
    }

    public ArrayList<Integer> getAyetSayilari_fromDB(){

        if (!dbHelper.getMyDatabase().isOpen()){dbHelper.setMyDatabase(dbHelper.openDatabase());}

        ArrayList<Integer> ayetSayilari=new ArrayList<>();

        Cursor c=dbHelper.getMyDatabase().rawQuery("SELECT * FROM SURELER",null);

        while (c.moveToNext()){

            int ayetsayısı=c.getInt(c.getColumnIndex("AYET_SAYISI"));
            ayetSayilari.add(ayetsayısı);

        }

        return ayetSayilari;
    }

    public boolean saveImageOnInternalStorage(Vird vird){

        try {
            if(vird.getImage_inbitmap()!=null){

                FileOutputStream fos=VirdlerimApplication.getAppContext().openFileOutput(vird.getImageName(),Context.MODE_PRIVATE);

                vird.getImage_inbitmap().compress(Bitmap.CompressFormat.PNG,100,fos);

                fos.close();
                return true;
            }
            else {return false;}

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("HATA","RESİM KAYIT EDİLEMEDİ");
            Log.e("HATA","RESİM KAYIT EDİLEMEDİ");
            Log.e("HATA","RESİM KAYIT EDİLEMEDİ");
            Log.e("HATA","RESİM KAYIT EDİLEMEDİ");
            Log.e("HATA","RESİM KAYIT EDİLEMEDİ");
            Log.e("HATA","RESİM KAYIT EDİLEMEDİ");
            return false;
        }
    }

    public boolean deleteImageFromInternalStorage(String imageName) throws Exception {

        ContextWrapper cw=new ContextWrapper(VirdlerimApplication.getAppContext());
        File filesDir=cw.getFilesDir();
        File file=new File(filesDir,imageName);
        if (!file.exists()){throw new FileNotFoundException();}
        else {
            if (file.delete()){return true;}
            else {
                throw new Exception("DOSYA SİLİNEMEDİ");

            }
        }

    }

    public static Bitmap getImageFromInternalStorage(String imageFileName){

        byte[] image=null;

        try {

            ContextWrapper cw=new ContextWrapper(VirdlerimApplication.getAppContext());

            File filesDir=cw.getFilesDir();

            File imageFile=new File(filesDir,imageFileName);

            if (imageFile.exists()){

                FileInputStream fis=new FileInputStream(imageFile);

                Bitmap b= BitmapFactory.decodeStream(fis);

                return b;
            }


        } catch (Exception e) {
            e.printStackTrace();

            Log.e("HATA","RESİM GETİRİLEMEDİ");
            Log.e("HATA","RESİM GETİRİLEMEDİ");
            Log.e("HATA","RESİM GETİRİLEMEDİ");
            Log.e("HATA","RESİM GETİRİLEMEDİ");
            Log.e("HATA","RESİM GETİRİLEMEDİ");
            Log.e("HATA","RESİM GETİRİLEMEDİ");
            Log.e("HATA","RESİM GETİRİLEMEDİ");
        }

        return null;
    }

    public boolean insertToGunlukVird(Vird comingVird) {

        if (!dbHelper.getMyDatabase().isOpen()){dbHelper.setMyDatabase(dbHelper.openDatabase());}

        ByteArrayOutputStream byteOutStream=new ByteArrayOutputStream();
        byte[] virdInBinary=null;

        try {
            ObjectOutputStream outputStream=new ObjectOutputStream(byteOutStream);
            outputStream.writeObject(comingVird);
            outputStream.flush();
            virdInBinary=byteOutStream.toByteArray();
            byteOutStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ContentValues cv=new ContentValues();

        if (comingVird.getId()!=null&&virdInBinary!=null) {
            cv.put("ID",comingVird.getId());
            cv.put("CONTENT",virdInBinary);
        }

        try {
            getMyDatabase().insert("GUNLUKVIRDLERIM",null,cv);
            getMyDatabase().close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("HATA","GÜNLÜK VİRDLERİM TABLOSUNA EKLENEMEDİ");
            Log.e("HATA","GÜNLÜK VİRDLERİM TABLOSUNA EKLENEMEDİ");
            Log.e("HATA","GÜNLÜK VİRDLERİM TABLOSUNA EKLENEMEDİ");
            Log.e("HATA","GÜNLÜK VİRDLERİM TABLOSUNA EKLENEMEDİ");
            Log.e("HATA","GÜNLÜK VİRDLERİM TABLOSUNA EKLENEMEDİ");
            getMyDatabase().close();
            return false;

        }


    }
    
    public boolean removeFromGunlukVird(Vird comingVird){

        if (!dbHelper.getMyDatabase().isOpen()){dbHelper.setMyDatabase(dbHelper.openDatabase());}

        try {
            getMyDatabase().delete("GUNLUKVIRDLERIM","ID=?",new String[]{comingVird.getId()});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("HATA","GÜNLÜK VİRDLERDEN SİLİNEMEDİ");
            Log.e("HATA","GÜNLÜK VİRDLERDEN SİLİNEMEDİ");
            Log.e("HATA","GÜNLÜK VİRDLERDEN SİLİNEMEDİ");
            Log.e("HATA","GÜNLÜK VİRDLERDEN SİLİNEMEDİ");
            Log.e("HATA","GÜNLÜK VİRDLERDEN SİLİNEMEDİ");
            return false;
        }
    }

    public List<Vird> fetchGunlukVirdlerim() {

        Vird gunlukVird=null;

        if (!dbHelper.getMyDatabase().isOpen()){dbHelper.setMyDatabase(dbHelper.openDatabase());}

        List<Vird> gunlukVirdlerim=new ArrayList<>();

        Cursor c=getMyDatabase().rawQuery("SELECT * FROM GUNLUKVIRDLERIM",null);

        while (c.moveToNext()) {

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(c.getBlob(c.getColumnIndex("CONTENT")));
            try {
                ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream);
                gunlukVird = (Vird) inputStream.readObject();

            } catch (Exception e) {
                e.printStackTrace();
            }

            if(gunlukVird!=null){
                gunlukVird.setImage_inbitmap(getImageFromInternalStorage(gunlukVird.getImageName()));}

            gunlukVirdlerim.add(gunlukVird);

        }

        return gunlukVirdlerim;
    }

    public boolean insertToFavourites(Vird comingVird){

        if (!dbHelper.getMyDatabase().isOpen()){dbHelper.setMyDatabase(dbHelper.openDatabase());}

        ByteArrayOutputStream byteOutStream=new ByteArrayOutputStream();
        byte[] virdInBinary=null;

        try {
            ObjectOutputStream outputStream=new ObjectOutputStream(byteOutStream);
            outputStream.writeObject(comingVird);
            outputStream.flush();
            virdInBinary=byteOutStream.toByteArray();
            byteOutStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ContentValues cv=new ContentValues();

        if (comingVird.getId()!=null&&virdInBinary!=null) {
            cv.put("ID",comingVird.getId());
            cv.put("CONTENT",virdInBinary);
        }

        try {
            getMyDatabase().insert("FAVORILER",null,cv);
            getMyDatabase().close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("HATA","FAVORİLER TABLOSUNA EKLENEMEDİ");
            Log.e("HATA","FAVORİLER TABLOSUNA EKLENEMEDİ");
            Log.e("HATA","FAVORİLER TABLOSUNA EKLENEMEDİ");
            Log.e("HATA","FAVORİLER TABLOSUNA EKLENEMEDİ");
            Log.e("HATA","FAVORİLER TABLOSUNA EKLENEMEDİ");
            getMyDatabase().close();
            return false;


        }

    }

    public boolean removeFromFavourites(Vird comingVird){

        if (!dbHelper.getMyDatabase().isOpen()){dbHelper.setMyDatabase(dbHelper.openDatabase());}

        try {
            getMyDatabase().delete("FAVORILER","ID=?",new String[]{comingVird.getId()});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("HATA","FAVORİLERDEN SİLİNEMEDİ");
            Log.e("HATA","FAVORİLERDEN SİLİNEMEDİ");
            Log.e("HATA","FAVORİLERDEN SİLİNEMEDİ");
            Log.e("HATA","FAVORİLERDEN SİLİNEMEDİ");
            Log.e("HATA","FAVORİLERDEN SİLİNEMEDİ");
            return false;
        }
    }

    public List<Vird> fetchFavourites() {

        Vird favouriteVird=null;

        if (!dbHelper.getMyDatabase().isOpen()){dbHelper.setMyDatabase(dbHelper.openDatabase());}

        List<Vird> favourites=new ArrayList<>();

        Cursor c=getMyDatabase().rawQuery("SELECT * FROM FAVORILER",null);

        while (c.moveToNext()) {

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(c.getBlob(c.getColumnIndex("CONTENT")));
            try {
                ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream);
                favouriteVird = (Vird) inputStream.readObject();

            } catch (Exception e) {
                e.printStackTrace();
            }

            if(favouriteVird!=null){
                favouriteVird.setImage_inbitmap(getImageFromInternalStorage(favouriteVird.getImageName()));}

            favourites.add(favouriteVird);

        }

        return favourites;
    }

    public SQLiteDatabase getMyDatabase() {
        return dbHelper.getMyDatabase();
    }

    public void setMyDatabase(SQLiteDatabase myDatabase) {
        this.dbHelper.setMyDatabase(myDatabase);
    }

    public DBHelper getDbHelper() {
        return dbHelper;
    }

    public void setDbHelper(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
}
