package com.codeforlite.virdlerim.Vird_Classes;

import android.content.Context;

public class Tesbih extends Vird {


    public Tesbih(String tesbihID,String title,String imageName, String arabicText,String turkishText, String meaning) {
        super("tesbih_"+tesbihID,imageName, turkishText, meaning);
        this.arabicText=arabicText;
        this.title=title;

    }

    @Override
    public void setId(String id) {
        super.setId("tesbih_"+id);
    }
}
