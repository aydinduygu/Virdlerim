package com.codeforlite.virdlerim.DB_Classes.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.codeforlite.virdlerim.ModelClasses.AdressClasses.City;
import com.codeforlite.virdlerim.ModelClasses.AdressClasses.Country;
import com.codeforlite.virdlerim.ModelClasses.AdressClasses.CountryWithCities;
import com.codeforlite.virdlerim.ModelClasses.AdressClasses.District;

import java.util.List;

@Dao
public interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCountry(Country country);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCity(City city);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDistrict(District district);

    @Transaction
    @Query("SELECT * FROM Countries WHERE countryID = :countryID")
    List<CountryWithCities> getCountryWithCitiesByID(String countryID);

    @Transaction
    @Query("SELECT * FROM Countries")
    List<Country> getAllCountries();

    @Transaction
    @Query("SELECT * FROM Countries WHERE countryName = :countryName")
    List<CountryWithCities> getCountryWithCitiesByName(String countryName);

}
