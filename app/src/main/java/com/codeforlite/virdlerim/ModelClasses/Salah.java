package com.codeforlite.virdlerim.ModelClasses;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;

@Getter
public class Salah {

    private String salahName;
    private Date salahDateTime;
    private String country;
    private String city;
    private String district;

    public Salah(String salahName,String country,String city,String district,
                 Date date, String time) throws ParseException {

       this.salahName=salahName;

       @SuppressLint("SimpleDateFormat")
       String dateString=new SimpleDateFormat("dd-MM-yyyy").format(date);

       this.salahDateTime= date;

       String[] hourMinute=time.split(":");
       date.setHours(Integer.parseInt(hourMinute[0]));
       date.setMinutes(Integer.parseInt(hourMinute[1]));


       this.country=country;
       this.city=city;
       this.district=district;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public long getRemainingTime(){

        long difference=new Date().getTime()- salahDateTime.getTime();
        return difference;

    }

    @Override
    public String toString() {
        return  salahName;
    }
}
