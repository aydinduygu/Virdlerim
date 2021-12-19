package com.codeforlite.virdlerim.ModelClasses.AdressClasses;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class CountryWithCities {

    @Embedded
    private Country country;

    @Relation(

            parentColumn ="countryID",
            entityColumn = "parentCountryID",
            entity = City.class
    )

    private List<CityWithDistricts> cities;

    public CountryWithCities() {
    }

    public CountryWithCities(Country country, List<CityWithDistricts> cities) {
        this.country = country;
        this.cities = cities;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<CityWithDistricts> getCities() {
        return cities;
    }

    public void setCities(List<CityWithDistricts> cities) {
        this.cities = cities;
    }
}
