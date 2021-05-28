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
