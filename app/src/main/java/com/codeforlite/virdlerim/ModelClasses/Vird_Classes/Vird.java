package com.codeforlite.virdlerim.ModelClasses.Vird_Classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Vird implements Serializable, Parcelable {

    protected String id;
    protected String imageName;
    protected String turkishText;
    protected String mealormeaning;
    protected int targetNumber;
    protected boolean isClicked;
    protected String arabicText;
    protected int kalanSayi;
    protected String title;
    protected byte[] image_inbyte;
    protected transient Bitmap image_inbitmap;
    protected transient Context context;
    protected int gunlukHedef;
    protected boolean isGunlukVird;


    //default constructor
    public Vird(String id) {

        this.id=id;

    }

    public Vird(String id, String turkishText) {
        this.id=id;
        this.turkishText = turkishText;

    }

    public Vird(String id, String imageName, String turkishText) {
        this.id=id;

        this.imageName=(imageName==null? (id+"_image.jpg"):(imageName+"_image.jpg"));
        this.turkishText = turkishText;
    }

    public Vird(String id,String imageName, String turkishText, String mealormeaning) {
        this.id=id;
        this.imageName=(imageName==null? (id+"_image.jpg"):(imageName+"_image.jpg"));
        this.turkishText = turkishText;
        this.mealormeaning = mealormeaning;

    }

    public Vird(String id, String imageName, String turkishText, String mealormeaning, int targetNumber) {
        this.id=id;
        this.imageName=(imageName==null? (id+"_image.jpg"):(imageName+"_image.jpg"));
        this.turkishText = turkishText;
        this.mealormeaning = mealormeaning;
        this.targetNumber = targetNumber;

    }

    protected Vird(Parcel in) {
        id = in.readString();
        imageName = in.readString();
        turkishText = in.readString();
        mealormeaning = in.readString();
        targetNumber = in.readInt();
        isClicked = in.readByte() != 0;
        arabicText = in.readString();
        kalanSayi = in.readInt();
        title = in.readString();
        gunlukHedef = in.readInt();
        isGunlukVird = in.readByte() != 0;
    }

    public static final Creator<Vird> CREATOR = new Creator<Vird>() {
        @Override
        public Vird createFromParcel(Parcel in) {
            return new Vird(in);
        }

        @Override
        public Vird[] newArray(int size) {
            return new Vird[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }
    public void setImageName(String imageName) {

            this.imageName=imageName+"_image.jpg";
    }

    public String getTurkishText() {
        return turkishText;
    }
    public void setTurkishText(String turkishText) {
        this.turkishText = turkishText;
    }

    public String getMealormeaning() {
        return mealormeaning;
    }
    public void setMealormeaning(String mealormeaning) {
        this.mealormeaning = mealormeaning;
    }

    public int getTargetNumber() {
        return targetNumber;
    }
    public void setTargetNumber(int sayi) {
        this.targetNumber = sayi;
    }

    public boolean isClicked() {
        return isClicked;
    }
    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public String getArabicText() {
        return arabicText;
    }
    public void setArabicText(String arabicText) {this.arabicText = arabicText;}

    public int getKalanSayi() {
        return kalanSayi;
    }

    public void setKalanSayi(int kalanSayi) {
        this.kalanSayi = kalanSayi;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getImage_inbyte() {
        return image_inbyte;
    }

    public void setImage_inbyte(byte[] image_inbyte) {

        this.image_inbyte = image_inbyte;

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Bitmap getImage_inbitmap() {
        return image_inbitmap;
    }

    public void setImage_inbitmap(Bitmap image_inbitmap) {
        this.image_inbitmap = image_inbitmap;
    }

    public int getGunlukHedef() {
        return gunlukHedef;
    }

    public void setGunlukHedef(int gunlukHedef) {
        this.gunlukHedef = gunlukHedef;
    }

    public boolean isGunlukVird() {
        return isGunlukVird;
    }

    public void setGunlukVird(boolean gunlukVird) {
        isGunlukVird = gunlukVird;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(imageName);
        dest.writeString(turkishText);
        dest.writeString(mealormeaning);
        dest.writeInt(targetNumber);
        dest.writeByte((byte) (isClicked ? 1 : 0));
        dest.writeString(arabicText);
        dest.writeInt(kalanSayi);
        dest.writeString(title);
        dest.writeInt(gunlukHedef);
        dest.writeByte((byte) (isGunlukVird ? 1 : 0));
    }
}

