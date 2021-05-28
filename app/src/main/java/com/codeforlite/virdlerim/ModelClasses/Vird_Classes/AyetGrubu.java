package com.codeforlite.virdlerim.ModelClasses.Vird_Classes;

import java.util.ArrayList;

public class AyetGrubu extends Vird  {


    private String meal;
    private ArrayList<Ayet> ayetler;

    public AyetGrubu(String ayetGrubuID,String title, ArrayList<Ayet> ayetler) {

        super("ayetgrubu_"+(ayetGrubuID));
        this.title = title;
        this.ayetler = ayetler;
        String arabicText="";
        String meal="";
        for (int i=0;i<ayetler.size();i++){

            arabicText+=("\n"+(ayetler.get(i).getArabicText()));
            meal+=((ayetler.get(i).getMeal())+"\n");


        }

        this.arabicText=arabicText;
        this.meal=meal;
        this.mealormeaning=meal;

       // this.meal=this.getMeal().substring(4);
    }

    public AyetGrubu(String ayetGrubuID,String title, String arabicText, String turkishText, String meal, String imageName) {
        super("ayetgrubu_"+ayetGrubuID, imageName, turkishText);
        this.meal = meal;
        this.arabicText=arabicText;
        this.title=title;

    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Ayet> getAyetler() {
        return ayetler;
    }

    public void setAyetler(ArrayList<Ayet> ayetler) {
        this.ayetler = ayetler;
    }

    @Override
    public void setId(String id) {
        super.setId("ayetgrubu_"+id);
    }
}




