package com.codeforlite.virdlerim.ModelClasses.Vird_Classes;

public class Esma extends Vird {

    private int ebced;

    public Esma(String esmaID, String imageName, String turkishText, String meaning, int ebced, String arabicText){

        super("esma_"+esmaID);
        this.imageName=imageName;
        this.turkishText=turkishText;
        this.mealormeaning =meaning;
        this.ebced=ebced;
        this.arabicText=arabicText;

    }

    public int getEbced() {
        return ebced;
    }

    public void setEbced(int ebced) {
        this.ebced = ebced;
    }

    @Override
    public void setId(String id) {
        super.setId("esma_"+id);
    }
}
