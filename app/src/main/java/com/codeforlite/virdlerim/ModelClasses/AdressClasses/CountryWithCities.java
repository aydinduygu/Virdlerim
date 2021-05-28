package com.codeforlite.virdlerim.ModelClasses.AdressClasses;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CountryWithCities {

    @Embedded
    private Country country;

    @Relation(

            parentColumn ="countryID",
            entityColumn = "parentCountryID",
            entity = City.class
    )

    private List<CityWithDistricts> cities;


}
