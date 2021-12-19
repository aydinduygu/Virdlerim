package com.codeforlite.virdlerim.ModelClasses.AdressClasses;


import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CityWithDistricts {

    @Embedded
    private City city;


    @Relation(
            parentColumn = "cityID",
            entityColumn = "parentCityID",
            entity = District.class

    )
    private List<District> districts;

    public CityWithDistricts() {
    }

    public CityWithDistricts(City city, List<District> districts) {
        this.city = city;
        this.districts = districts;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    @Override
    public String toString() {
        return city.getCityName();
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

        if(cityName.equals(this.getCity().getCityName()))return true;
        else {return false;}
    }

    @Override
    public int hashCode() {
        return Objects.hash(city.getCityName());
    }
}
