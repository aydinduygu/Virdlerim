package com.codeforlite.virdlerim.Vird_Classes;

import android.content.Context;

import com.codeforlite.virdlerim.Vird_Classes.Vird;

public class Dua extends Vird {


    public Dua(String duaID,String title,String imageName, String arabicText,String turkishText, String meaning) {
        super("dua_"+duaID,imageName, turkishText, meaning);
        this.arabicText=arabicText;
        this.title=title;


    }

    @Override
    public void setId(String id) {
        super.setId("dua_"+id);
    }
}
