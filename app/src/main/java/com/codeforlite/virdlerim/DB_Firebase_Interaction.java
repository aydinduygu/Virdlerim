package com.codeforlite.virdlerim;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import com.codeforlite.virdlerim.Vird_Classes.Ayet;
import com.codeforlite.virdlerim.Vird_Classes.AyetGrubu;
import com.codeforlite.virdlerim.Vird_Classes.Dua;
import com.codeforlite.virdlerim.Vird_Classes.Esma;
import com.codeforlite.virdlerim.Vird_Classes.Salavat;
import com.codeforlite.virdlerim.Vird_Classes.Tesbih;
import com.codeforlite.virdlerim.Vird_Classes.Vird;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DB_Firebase_Interaction {

    private static FirebaseDatabase  mydatabase=FirebaseDatabase.getInstance();
    private DatabaseReference kuran;
    private DatabaseReference esma;
    private DatabaseReference sureler;
    private DatabaseReference tesbihler;
    private DatabaseReference dualar;
    private DatabaseReference salavatlar;
    private DatabaseReference ayet_gruplari;
    private DatabaseReference elmalili_meal;
    private DatabaseReference gunluk_virdlerim;
    private DatabaseReference favourites;

    public DB_Firebase_Interaction() {


        kuran=mydatabase.getReference("kuran");
        esma=mydatabase.getReference("esma");
        sureler=mydatabase.getReference("sureler");
        tesbihler=mydatabase.getReference("tesbihler");
        dualar=mydatabase.getReference("dualar");
        salavatlar=mydatabase.getReference("salavatlar");
        ayet_gruplari=mydatabase.getReference("ayet_gruplari");
        elmalili_meal=mydatabase.getReference("elmalili_meal");
        gunluk_virdlerim=mydatabase.getReference("gunluk_virdlerim");
        favourites=mydatabase.getReference("favourites");
    }

    public void insertToKuranDB(Ayet ayet){

        kuran.push().setValue(ayet);

    }

    public void insertToTesbihler(Tesbih tesbih){

        tesbihler.push().setValue(tesbih);

    }
    public void insertToSalavatlar(Salavat salavat){

        salavatlar.push().setValue(salavat);

    }

    public void insertToDualar(Dua dua){

        dualar.push().setValue(dua);

    }
    public void insertToEsma(Esma esma){

        this.esma.push().setValue(esma);

    }
    public void insertToGunlukVirdlerim(Vird vird){

        gunluk_virdlerim.push().setValue(vird);

    }
    public void insertToFavourites(Vird vird){

        favourites.push().setValue(vird);

    }

    public void insertToAyetGruplari(AyetGrubu ayetGrubu){

        ayet_gruplari.push().setValue(ayetGrubu);

    }

    public void copyFromSqliteToFirebase(){

        SQLiteDatabase sqLiteDatabase=new DB_Interaction(VirdlerimApplication.getAppContext(),VirdlerimApplication.getDbHelper())
                .getMyDatabase();

      /*  Cursor c1= sqLiteDatabase.rawQuery("SELECT * FROM KURAN",null);

        while (c1.moveToNext()){

            Ayet ayet=new Ayet(c1.getInt(c1.getColumnIndex("SURE_NO")),c1.getInt(c1.getColumnIndex("AYET_NO")),c1.getString(c1.getColumnIndex("AYET")),null);
            Cursor c2=sqLiteDatabase.rawQuery("SELECT ISIM FROM SURELER WHERE SIRA="+ayet.getSureNo(),null);
            c2.moveToNext();
            ayet.setSureAdı(c2.getString(c2.getColumnIndex("ISIM")));
            this.insertToKuranDB(ayet);
        }*/

       /* Cursor c =sqLiteDatabase.rawQuery("SELECT * FROM TESBIHLER",null);

        while (c.moveToNext()){

            Tesbih tesbih=new Tesbih(c.getString(c.getColumnIndex("TITLE")),
                    c.getString(c.getColumnIndex("IMAGE_NAME")),
                    c.getString(c.getColumnIndex("ARABIC_TEXT")),
                    c.getString(c.getColumnIndex("TURKISH_TEXT")),
                    c.getString(c.getColumnIndex("MEANING")));

            insertToTesbihler(tesbih);

        }*/


      /* Cursor c=sqLiteDatabase.rawQuery("SELECT * FROM SALAVATLAR",null);

        while (c.moveToNext()){

            Salavat salavat=new Salavat(c.getString(c.getColumnIndex("TITLE")),
                    c.getString(c.getColumnIndex("IMAGE_NAME")),
                    c.getString(c.getColumnIndex("ARABIC_TEXT")),
                    c.getString(c.getColumnIndex("TURKISH_TEXT")),
                    c.getString(c.getColumnIndex("MEANING")));

            insertToSalavatlar(salavat);


        }*/

    /*  Cursor c=sqLiteDatabase.rawQuery("SELECT * FROM AYETGRUPLARIM",null);

      while (c.moveToNext()){

          AyetGrubu  ayetGrubu=new AyetGrubu(c.getString(c.getColumnIndex("TITLE")),
                  c.getString(c.getColumnIndex("ARABIC_TEXT")),
                  c.getString(c.getColumnIndex("TURKISH_TEXT")),
                  c.getString(c.getColumnIndex("MEAL")),
                  c.getString(c.getColumnIndex("IMAGE_NAME")));

          insertToAyetGruplari(ayetGrubu);
      }*/

       /* Cursor c=sqLiteDatabase.rawQuery("SELECT * FROM ESMA",null);

        while (c.moveToNext()){

            Esma esma=new Esma(""+c.getInt(c.getColumnIndex("ID")),
                    c.getString(c.getColumnIndex("IMAGE_NAME")),
                    c.getString(c.getColumnIndex("TURKISH_TEXT")),
                    c.getString(c.getColumnIndex("MEANING")),
                    Integer.parseInt(c.getString(c.getColumnIndex("EBCED"))),
                    c.getString(c.getColumnIndex("ARABIC_TEXT")));

            insertToEsma(esma);


        }*/

    }

}
