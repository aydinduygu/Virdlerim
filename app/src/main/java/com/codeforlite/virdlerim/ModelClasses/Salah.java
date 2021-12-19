package com.codeforlite.virdlerim.ModelClasses;
import android.os.Build;
import androidx.annotation.RequiresApi;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;


public class Salah {

    private String salahName;
    private LocalDateTime salahDateTime;
    private String country;
    private String city;
    private String district;

    public Salah(String salahName, String country, String city, String district,
                 LocalDateTime dateTime) throws ParseException {

       this.salahName=salahName;
       this.country=country;
       this.city=city;
       this.district=district;
       this.salahDateTime= dateTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public long getRemainingTime(){
        long salahTimeMili = this.salahDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long nowMili=LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return salahTimeMili-nowMili;

    }

    public String getSalahName() {
        return salahName;
    }

    public LocalDateTime getSalahDateTime() {
        return salahDateTime;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    @Override
    public String toString() {
        return  salahName;
    }
}
