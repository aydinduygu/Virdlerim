package com.codeforlite.virdlerim.Vird_Classes;

import android.content.Context;

import java.io.Serializable;

public class Ayet implements Serializable {


    private int sureNo;
    private int ayetNo;
    private String sureAdı;
    private String meal;
    private String arabicText;

    public Ayet(int sureNo, int ayetNo, String arabicText, String meal) {

        this.sureNo = sureNo;
        this.ayetNo = ayetNo;
        this.meal = meal;
        this.arabicText=arabicText;
    }


    public int getSureNo() {
        return sureNo;
    }

    public void setSureNo(int sureNo) {
        this.sureNo = sureNo;
    }

    public int getAyetNo() {
        return ayetNo;
    }

    public void setAyetNo(int ayetNo) {
        this.ayetNo = ayetNo;
    }

    public String getSureAdı() {
        return sureAdı;
    }

    public void setSureAdı(String sureAdı) {
        this.sureAdı = sureAdı;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getArabicText() {
        return arabicText;
    }

    public void setArabicText(String arabicText) {
        this.arabicText = arabicText;
    }
}
