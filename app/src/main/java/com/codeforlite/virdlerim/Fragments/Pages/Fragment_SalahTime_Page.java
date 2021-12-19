package com.codeforlite.virdlerim.Fragments.Pages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.codeforlite.virdlerim.DB_Classes.RoomDB;
import com.codeforlite.virdlerim.ModelClasses.AdressClasses.City;
import com.codeforlite.virdlerim.ModelClasses.AdressClasses.CityWithDistricts;
import com.codeforlite.virdlerim.ModelClasses.AdressClasses.Country;
import com.codeforlite.virdlerim.ModelClasses.AdressClasses.CountryWithCities;
import com.codeforlite.virdlerim.ModelClasses.AdressClasses.District;
import com.codeforlite.virdlerim.ModelClasses.Salah;
import com.codeforlite.virdlerim.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lombok.SneakyThrows;

public class Fragment_SalahTime_Page extends Fragment {

    private RoomDB roomDB;
    private TextView txt_imsak;
    private TextView txt_gunes;
    private TextView txt_ogle;
    private TextView txt_ikindi;
    private TextView txt_aksam;
    private TextView txt_yatsi;
    private TextView txt_kalan;
    private TextView txt_kalansure;
    private Spinner spn_country;
    private Spinner spn_city;
    private Spinner spn_district;
    private CountDownTimer countDownTimer;
    private SharedPreferences locationSelection;
    private String selectedCountry;
    private String selectedCity;
    private String selectedDistrict;


    private Editor sharedEditor;
    private List<Country> allCountries;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_namazvakti,container,false);

        init(view);
        new FetchAllCountriesTask().execute();

        return view;
    }


    public void init(View view){

        roomDB=RoomDB.getInstance(getActivity().getApplicationContext());

        spn_country =view.findViewById(R.id.spn_country);
        spn_city=view.findViewById(R.id.spn_city);
        spn_district=view.findViewById(R.id.spn_district);

        txt_imsak=view.findViewById(R.id.txt_imsak);
        txt_gunes=view.findViewById(R.id.txt_gunes);
        txt_ogle=view.findViewById(R.id.txt_ogle);
        txt_ikindi=view.findViewById(R.id.txt_ikindi);
        txt_aksam=view.findViewById(R.id.txt_aksam);
        txt_yatsi=view.findViewById(R.id.txt_yatsi);
        txt_kalan=view.findViewById(R.id.txt_kalan);
        txt_kalansure=view.findViewById(R.id.txt_kalansure);



    }


    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setTextViews(List<Salah> salahList, String errorMessage)
            throws ParseException,NullPointerException {

        if(salahList!=null) {


            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("HH:mm");

            txt_kalan.setTextSize(60);

            txt_imsak.setText("İmsak"
                    + ": " + salahList.get(0).getSalahDateTime().format(formatter));

            txt_gunes.setText("Güneş"
                    + ": " + salahList.get(1).getSalahDateTime().format(formatter));
            txt_ogle.setText("Öğle"
                    + ": " +  salahList.get(2).getSalahDateTime().format(formatter));
            txt_ikindi.setText("İkindi"
                    + ": " + salahList.get(3).getSalahDateTime().format(formatter));
            txt_aksam.setText("Akşam"
                    + ": " + salahList.get(4).getSalahDateTime().format(formatter));
            txt_yatsi.setText("Yatsı"
                    + ": " + salahList.get(5).getSalahDateTime().format(formatter));
            txt_kalansure.setVisibility(View.VISIBLE);


            long finalRemainingTime = getRemainingTime(salahList);
            if (countDownTimer != null) {
                countDownTimer.cancel();
                countDownTimer = null;
            }

            countDownTimer = new CountDownTimer(finalRemainingTime, 1000) {
                @SuppressLint("DefaultLocale")
                @Override
                public void onTick(long millisUntilFinished) {

                    long totalSec = millisUntilFinished / 1000;
                    long totalMin=totalSec/60;
                    int sec=(int)(totalSec%60);
                    int hour=(int)(totalMin/60);
                    int min=(int)(totalMin%60);

                    txt_kalan.setText(String.format("%02d:%02d:%02d", hour,
                            min, sec));
                }

                @Override
                public void onFinish() {

                }
            };

            countDownTimer.start();
        }
        else{

            txt_kalan.setText(errorMessage);
            txt_kalansure.setVisibility(View.INVISIBLE);
            txt_imsak.setText(errorMessage);
            txt_gunes.setText(errorMessage);
            txt_ogle.setText(errorMessage);
            txt_ikindi.setText(errorMessage);
            txt_aksam.setText(errorMessage);
            txt_yatsi.setText(errorMessage);
            txt_kalan.setTextSize(20);
            countDownTimer.cancel();

        }
    }



    @SuppressLint("DefaultLocale")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private long getRemainingTime(List<Salah> dailySalahList) throws ParseException {

        Salah nextSalah=null;

        for(Salah salah:dailySalahList){

            SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");


            if(salah.getRemainingTime()>=0){
                txt_kalansure.setText(salah.getSalahName()+" Vaktine Kalan Süre:");
                nextSalah=salah;
                break;
            }


        }

       return nextSalah.getRemainingTime();

    }

   /* private class FetchLocationInfosTask extends AsyncTask<String, Country, List<Country>> {


        @Override
        protected List<Country> doInBackground(String... strings) {

            RoomDB roomDB=RoomDB.getInstance(getActivity().getApplicationContext());

            CountryDao countryDao=roomDB.countryDao();

            List<Country> countries=new ArrayList<>();
            List<City> cities = new ArrayList<>();

            Document doc;
            try {
                String url="https://namazvakitleri.diyanet.gov.tr/tr-TR";

                doc = Jsoup.connect(url).get();

                Element region=doc.getElementById("region-selection-wrapper");
                Elements elements=region.getElementsByTag("option");

                Gson gson = new Gson();

                elements.forEach(element -> {

                        Country country=new Country(element.attr("value"),element.text(),null);
                        countryDao.insertCountry(country);
                        countries.add(country);
                        Log.e(country.getCountryName(),country.getCountryName());
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                );


                countries.forEach(country->{

                        String cityUrl="https://namazvakitleri.diyanet.gov.tr/tr-TR/home/GetRegList?ChangeType=country&CountryId="
                                +country.getCountryID()+"&Culture=tr-TR";

                        try {
                            Connection connection =Jsoup.connect(cityUrl).ignoreContentType(true);
                            connection.execute();
                            String text=connection.get().body().text();

                            if (text!=null){

                                JsonObject jsonObject=new JsonParser().parse(text).getAsJsonObject();
                                JsonArray citiesJson=jsonObject.getAsJsonArray("StateList");

                                citiesJson.forEach(jsonElement -> {
                                    JsonObject cityJson=((JsonObject)jsonElement);
                                    String cityName=cityJson.get("SehirAdi").getAsString();
                                    String cityNameEn=cityJson.get("SehirAdiEn").getAsString();
                                    String cityID=cityJson.get("SehirID").getAsString();

                                    City city=new City(cityID,cityName,cityNameEn,country.getCountryID());
                                    countryDao.insertCity(city);
                                    Log.e(city.getCityName(),city.getCityName());
                                    String districtUrl="https://namazvakitleri.diyanet.gov.tr/tr-TR/home/GetRegList?ChangeType=state&CountryId="+country.getCountryID()+"&Culture=tr-TR&StateId="+cityID;

                                    Connection connection2=Jsoup.connect(districtUrl).ignoreContentType(true);
                                    try {
                                        connection2.execute();

                                        String text2=connection2.get().body().text();

                                        if (text2!=null){
                                            JsonObject jsonObject2=new JsonParser().parse(text2).getAsJsonObject();
                                            JsonArray districtsJson=jsonObject2.getAsJsonArray("StateRegionList");

                                            districtsJson.forEach(element->{

                                                JsonObject districtJson=((JsonObject)element);
                                                String districtName=districtJson.get("IlceAdi").getAsString();
                                                String districtNameEn=districtJson.get("IlceAdiEn").getAsString();
                                                String districtID=districtJson.get("IlceID").getAsString();

                                                District district= new District(districtID,districtName,districtNameEn,cityID);

                                                countryDao.insertDistrict(district);
                                                Log.e(district.getDistrictName(),district.getDistrictName());
                                                try {
                                                    Thread.sleep(10);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }

                                            });



                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                    try {
                                        Thread.sleep(10);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                });

                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                );


                int a=5;


            } catch (IOException e) {
                e.printStackTrace();
            }
            return countries;
        }

        @Override
        protected void onPostExecute(List<Country> result) {
            //if you had a ui element, you could display the title




        }
    }*/
    private class FetchAllCountriesTask extends  AsyncTask<Object,Object,Object>{

        @Override
        protected Object doInBackground(Object[] objects) {

            Object object=null;

            try {
                object=roomDB.countryDao().getAllCountries();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return  object;
        }

        @Override
        protected void onPostExecute(Object o) {
            try {
                allCountries=(ArrayList<Country>) o;

                ArrayAdapter adapter=
                        new ArrayAdapter(
                                getActivity(),
                                android.R.layout.simple_spinner_dropdown_item,
                                allCountries);

                spn_country.setAdapter(adapter);

                if(getActivity()!=null) {

                    locationSelection = getActivity().getSharedPreferences("locationSelection", Context.MODE_PRIVATE);
                    selectedCountry = locationSelection.getString("country", "Türkiye");
                    selectedCity = locationSelection.getString("city", "İstanbul");
                    selectedDistrict = locationSelection.getString("district", "İstanbul");
                }

                spn_country.setSelection(
                        adapter.getPosition(
                                new Country("temp",selectedCountry,null)));

                spn_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        ((TextView)(parent.getChildAt(0)))
                                .setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                        String country=parent.getSelectedItem().toString();
                        new FetchCountryWithCitiesTask().execute(country);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {


                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private class FetchCountryWithCitiesTask extends AsyncTask<String,Object,Object>{


        @Override
        protected Object doInBackground(String... strings) {
            List<CountryWithCities> list=null;

            try {
                list=roomDB.countryDao().getCountryWithCitiesByName(strings[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return  list;
        }

        @Override
        protected void onPostExecute(Object o) {

            try {
                CountryWithCities countryWithCities=((List<CountryWithCities>)o).get(0);

                ArrayAdapter adapter2=
                        new ArrayAdapter(
                                getActivity(),
                                android.R.layout.simple_spinner_dropdown_item,
                                countryWithCities.getCities());


                spn_city.setAdapter(adapter2);
                spn_city.setSelection(adapter2.getPosition(
                        new City("temp",selectedCity,null,null)));

                spn_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        ((TextView)(parent.getChildAt(0)))
                                .setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                        Context context;
                        CityWithDistricts city=countryWithCities.getCities().get(position);

                        ArrayAdapter arrayAdapter =
                                new ArrayAdapter(
                                    getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item,
                                            city.getDistricts());

                        spn_district.setAdapter(arrayAdapter);

                        spn_district.setSelection(arrayAdapter.getPosition(
                                new District("temp",selectedDistrict,null,null)));

                        spn_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                ((TextView)(parent.getChildAt(0)))
                                        .setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


                                new FetchSalahTimeTask().execute(city.getDistricts().get(position).getDistrictID(),
                                        city.getDistricts().get(position).getDistrictName(),city.toString(),countryWithCities.toString());

                                locationSelection =
                                        getActivity().getSharedPreferences(
                                                "locationSelection", Context.MODE_PRIVATE);

                                sharedEditor= locationSelection.edit();
                                sharedEditor.putString("country",countryWithCities.getCountry().getCountryName());
                                sharedEditor.putString("city",city.toString());
                                sharedEditor.putString("district",city.getDistricts().get(position).getDistrictName());
                                sharedEditor.commit();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class FetchSalahTimeTask extends AsyncTask<String,Object,Object>{


        @RequiresApi(api = Build.VERSION_CODES.O)
        @SneakyThrows
        @Override
        protected Object doInBackground(String... strings) {

            List<Salah> dailySalahs= null;
            try {
                String districtID=strings[0];

                String url="https://namazvakitleri.diyanet.gov.tr/tr-TR/"+strings[0]+"/"+strings[1]+"-icin-namaz-vakti";
                Element element=Jsoup.connect(url).get().getElementById("today-pray-times-row");
                Element nextFecrElement=Jsoup.connect(url).get().body().getElementsByTag("script").first();

                String strArray[]=nextFecrElement.data().split("\r\n");
                String str=strArray[strArray.length-1];
                String nextFecrTime=str.substring(str.length()-7,str.length()-2);

                String times=element.getElementsByClass("tpt-time").text();
                String salahTimesString[]=times.split(" ");
                List<LocalDateTime> salahDateTimes=new ArrayList<>();
                DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
                DateTimeFormatter formatter1=DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                String date=LocalDate.now().format(formatter);


                for (String salahTime : salahTimesString) {

                    String dateTime=date+" "+salahTime;
                    LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter1);
                    salahDateTimes.add(localDateTime);

                }

                salahDateTimes.add(LocalDateTime.parse(date+" "+nextFecrTime,formatter1).plusDays(1));

                dailySalahs = new ArrayList<>();


                dailySalahs.add(new Salah(
                        "İmsak",
                        strings[2],
                        strings[3],
                        strings[1],
                        salahDateTimes.get(0)));

                dailySalahs.add(new Salah(
                        "Güneş",
                        strings[2],
                        strings[3],
                        strings[1],
                        salahDateTimes.get(1)));

                dailySalahs.add(new Salah(
                        "Öğle",
                        strings[2],
                        strings[3],
                        strings[1],
                        salahDateTimes.get(2)));

                dailySalahs.add(new Salah(
                        "İkindi",
                        strings[2],
                        strings[3],
                        strings[1],
                        salahDateTimes.get(3)));

                dailySalahs.add(new Salah(
                        "Akşam",
                        strings[2],
                        strings[3],
                        strings[1],
                        salahDateTimes.get(4)));

                dailySalahs.add(new Salah(
                        "Yatsı",
                        strings[2],
                        strings[3],
                        strings[1],
                        salahDateTimes.get(5)));

                dailySalahs.add(new Salah(
                        "İmsak",
                        strings[2],
                        strings[3],
                        strings[1],
                        salahDateTimes.get(6)
                        ));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return dailySalahs;

        }

        @SneakyThrows
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(Object o) {

         try {
             List<Salah> dailySalahs=(List<Salah>)o;

             setTextViews(dailySalahs,"Vakit bilgisi alınamıyor.");

         }

         catch (Exception e){}
        }
    }

    //TODO Vakit bilgisi gelene kadar progress bar dönecek

}
