package com.codeforlite.virdlerim.ModelClasses.AdressClasses;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
public class City {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "cityID",defaultValue = "000")
    private String cityID;

    @ColumnInfo(name="cityName")
    private String cityName;

    @ColumnInfo(name="cityNameEn")
    private String cityNameEn;


    private String parentCountryID;

    public City() {
    }

    public City(@NonNull String cityID, String cityName, String cityNameEn, String parentCountryID) {
        this.cityID = cityID;
        this.cityName = cityName;
        this.cityNameEn = cityNameEn;
        this.parentCountryID = parentCountryID;
    }

    @NonNull
    public String getCityID() {
        return cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCityNameEn() {
        return cityNameEn;
    }

    public String getParentCountryID() {
        return parentCountryID;
    }

    public void setCityID(@NonNull String cityID) {
        this.cityID = cityID;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setCityNameEn(String cityNameEn) {
        this.cityNameEn = cityNameEn;
    }

    public void setParentCountryID(String parentCountryID) {
        this.parentCountryID = parentCountryID;
    }

    @Override
    public String toString() {
        return this.cityName;
    }

    @Override
    public boolean equals(Object o) {
        String cityName=null;

        if (o instanceof City){
            cityName=((City)o).getCityName();
        }

        else if(o instanceof CityWithDistricts) {
            cityName=((CityWithDistricts)o).getCity().getCityName();
        }

        else{ return false;}

        if(cityName.equals(this.getCityName()))return true;
        else {return false;}
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityID, cityName, cityNameEn, parentCountryID);
    }
}
