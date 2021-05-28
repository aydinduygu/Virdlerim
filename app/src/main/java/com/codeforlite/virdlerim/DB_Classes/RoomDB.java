package com.codeforlite.virdlerim.DB_Classes;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.codeforlite.virdlerim.DB_Classes.Dao.CountryDao;
import com.codeforlite.virdlerim.ModelClasses.AdressClasses.City;
import com.codeforlite.virdlerim.ModelClasses.AdressClasses.Country;
import com.codeforlite.virdlerim.ModelClasses.AdressClasses.District;

@Database(entities = {Country.class, City.class, District.class},version = 1)
public abstract class RoomDB extends RoomDatabase {

    private static RoomDB INSTANCE;

    public static RoomDB getInstance(Context context){

        if (INSTANCE==null){


            INSTANCE= Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, "Sample.db")
                    .createFromAsset("VIRDLERIM_ROOMDB")
                    .build();
        }

        return INSTANCE;

    }

    public abstract CountryDao countryDao();


}
