package com.codeforlite.virdlerim.ModelClasses.Vird_Classes;

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
