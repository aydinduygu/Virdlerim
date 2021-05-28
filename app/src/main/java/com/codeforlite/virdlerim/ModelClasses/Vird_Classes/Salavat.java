package com.codeforlite.virdlerim.ModelClasses.Vird_Classes;

public class Salavat extends Vird {


    public Salavat(String salavatID,String title,String imageName, String arabicText,String turkishText, String meaning) {

        super("salavat_"+salavatID,imageName, turkishText, meaning);
        this.arabicText=arabicText;
        this.title=title;

    }
    @Override
    public void setId(String id) {
        super.setId("salavat_"+id);
    }

}
