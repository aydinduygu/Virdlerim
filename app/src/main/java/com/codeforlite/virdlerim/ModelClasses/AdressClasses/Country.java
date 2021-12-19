package com.codeforlite.virdlerim.ModelClasses.AdressClasses;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(tableName = "Countries")
public class Country {


    @PrimaryKey(autoGenerate = false )
    @ColumnInfo(name = "countryID")
    @NonNull
    private String countryID;

    @ColumnInfo(name = "countryName")
    private String countryName;

    @ColumnInfo(name="countryNameEn")
    private String countryNameEn;

    public Country() {
    }

    public Country(@NonNull String countryID, String countryName, String countryNameEn) {
        this.countryID = countryID;
        this.countryName = countryName;
        this.countryNameEn = countryNameEn;
    }

    @NonNull
    public String getCountryID() {
        return countryID;
    }

    public void setCountryID(@NonNull String countryID) {
        this.countryID = countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryNameEn() {
        return countryNameEn;
    }

    public void setCountryNameEn(String countryNameEn) {
        this.countryNameEn = countryNameEn;
    }

    @Override
    public String toString() {
        return countryName;
    }

    @Override
    public boolean equals(Object o) {

        boolean returnValue=false;
        if(o instanceof Country){

            returnValue= this.getCountryName().equals(((Country)o).getCountryName());

        }
        else if(o instanceof String){

            returnValue= this.getCountryName().equals((String)o);
        }

        return returnValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryName);
    }
}
