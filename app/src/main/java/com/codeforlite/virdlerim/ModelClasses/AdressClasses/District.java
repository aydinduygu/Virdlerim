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
public class District {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "districtID")
    private String districtID;

    @ColumnInfo(name = "districtName")
    private String districtName;

    @ColumnInfo(name ="districtNameEn")
    private String districtNameEn;


    private String parentCityID;


    public District() {
    }

    public District(@NonNull String districtID, String districtName, String districtNameEn, String parentCityID) {
        this.districtID = districtID;
        this.districtName = districtName;
        this.districtNameEn = districtNameEn;
        this.parentCityID = parentCityID;
    }

    @NonNull
    public String getDistrictID() {
        return districtID;
    }

    public void setDistrictID(@NonNull String districtID) {
        this.districtID = districtID;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictNameEn() {
        return districtNameEn;
    }

    public void setDistrictNameEn(String districtNameEn) {
        this.districtNameEn = districtNameEn;
    }

    public String getParentCityID() {
        return parentCityID;
    }

    public void setParentCityID(String parentCityID) {
        this.parentCityID = parentCityID;
    }

    @Override
    public String toString() {
        return districtName;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof District){

            if (((District)o).getDistrictName().equals(this.districtName))return true;
            else{return false;}

        }
        else {return false;}
    }

    @Override
    public int hashCode() {
        return Objects.hash(districtName);
    }
}
