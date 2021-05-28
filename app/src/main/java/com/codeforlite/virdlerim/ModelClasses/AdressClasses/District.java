package com.codeforlite.virdlerim.ModelClasses.AdressClasses;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
