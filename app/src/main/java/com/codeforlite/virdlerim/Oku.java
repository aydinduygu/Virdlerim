package com.codeforlite.virdlerim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.codeforlite.virdlerim.Fragments.Fragment_Anlam;
import com.codeforlite.virdlerim.Fragments.Fragment_Arabic;
import com.codeforlite.virdlerim.Fragments.Fragment_Image;
import com.codeforlite.virdlerim.Fragments.Fragment_Turkce_Okunus;
import com.codeforlite.virdlerim.ScreenClasses.Esma_Screen;
import com.codeforlite.virdlerim.ScreenClasses.GunlukVirdlerimScreen;
import com.codeforlite.virdlerim.Vird_Classes.AyetGrubu;
import com.codeforlite.virdlerim.Vird_Classes.Vird;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;


/*this activity is to read a text with a counter- it shows arabic and turkish spelling and also meaning
in different fragments*/
public class Oku extends AppCompatActivity implements Serializable {

    private int actualNumber;
    private FragmentManager fragmentManager;
    private Fragment tempFragment;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout fragmentContainer;
    private ProgressBar progressBar;
    private Button btn_tıkla;
    private Vird comingVird;
    private Fragment arabicTextFragment;
    private Fragment turkishTextFragment;
    private Fragment anlamFragment;
    private Fragment imageFragment;
    private MediaPlayer clickSoundPlayer;
    private MediaPlayer completeSoundPlayer;
    private Vibrator vb;
    private SharedPreferences kalanSayiKayit;
    private SharedPreferences.Editor kalanSayiKayitEditor;
    private SharedPreferences gunlukHedefKayit;
    private SharedPreferences.Editor gunlukHedefKayitEditor;
    private String previousActivity;
    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oku);

        //check if vibration and sound preferences are on
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(VirdlerimApplication.getAppContext());
        boolean isVibrationOn=sp.getBoolean("pref_vibration",true);
        boolean isClickSoundOn=sp.getBoolean("pref_clicksound",true);



        previousActivity=getIntent().getStringExtra("activityName");

        //coming vird with the intent

        if(getIntent().getSerializableExtra("Vird.class") instanceof AyetGrubu){
            AyetGrubu ayetGrubu=(AyetGrubu)getIntent().getSerializableExtra("Vird.class");
        }
        comingVird= (Vird) getIntent().getSerializableExtra("Vird.class");

        if (previousActivity.equals("Esma_Screen")){

            try {
                comingVird.setImage_inbyte(new DB_Interaction(VirdlerimApplication.getAppContext(),VirdlerimApplication.getDbHelper()).getEsmaImageByteArray(comingVird.getId()));
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
        }



        comingVird.setImage_inbitmap(DB_Interaction.getImageFromInternalStorage(comingVird.getImageName()));
        calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());



        //to save the number at the counter
        kalanSayiKayit=getSharedPreferences("kalansayilar",MODE_PRIVATE);
        gunlukHedefKayit=VirdlerimApplication.getAppContext().getSharedPreferences("gunlukvirdler",MODE_PRIVATE);
        kalanSayiKayitEditor=kalanSayiKayit.edit();
        gunlukHedefKayitEditor=gunlukHedefKayit.edit();

        //initialize views
        fragmentContainer=findViewById(R.id.fragment_container);
        bottomNavigationView=findViewById(R.id.bottom_navi);
        progressBar=findViewById(R.id.progressBar);
        btn_tıkla=findViewById(R.id.button_tıkla);

        //sound at every button click
        clickSoundPlayer=MediaPlayer.create(this,R.raw.click);

        //sound will be played when target reached
        completeSoundPlayer=MediaPlayer.create(this,R.raw.complete);

        //vibrator for button click
        vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        actualNumber=comingVird.getTargetNumber();



        if (comingVird.getImage_inbitmap()!=null||comingVird.getImage_inbyte()!=null){

            //put the arabic image fragment on the container first
            imageFragment=new Fragment_Image(Oku.this,comingVird);
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,imageFragment).commit();


        }
        else {

            //put the arabic text fragment on the container first
            arabicTextFragment=new Fragment_Arabic(Oku.this,comingVird);
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,arabicTextFragment).commit();

        }


        //eğer gelen vird ayet ise Anlam Sekmesini Meal Sekmesi yap
        if(comingVird.getClass().getSimpleName().equals("AyetGrubu")){bottomNavigationView.getMenu().getItem(2).setTitle("Meal");}

        setMenuItemsVisibility(bottomNavigationView,comingVird);

        //on navigation item selected:
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //get the selected items id number
                int itemID=item.getItemId();

                //if id number is related to the arabic image fragment, set the temp fragment as arabic image fragment
                if(itemID==R.id.action_oku_arapca){
                    if (comingVird.getImage_inbitmap()!=null||comingVird.getImage_inbyte()!=null){
                    tempFragment=new Fragment_Image(Oku.this,comingVird);
                    }
                    else {
                        tempFragment=new Fragment_Arabic(Oku.this,comingVird);
                    }
                }

                //if id number is related to turkish text fragment, set the related fragment
                else if(itemID==R.id.action_oku_turkce) { tempFragment=new Fragment_Turkce_Okunus(comingVird.getTurkishText());}

                //if id number is related to the meaning set meaning fragment..
                else if(itemID==R.id.action_oku_anlamı){

                    //to change the text on the bottom navigation bar item
                    if(comingVird.getClass().getSimpleName().equals("AyetGrubu")){
                        AyetGrubu ayetGrubu=(AyetGrubu)comingVird;
                        tempFragment=new Fragment_Anlam(ayetGrubu.getMeal());}

                    else{
                        tempFragment=new Fragment_Anlam(comingVird.getMealormeaning());
                    }
                }

                //replace the fragment on the container with the temp fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,tempFragment).commit();

                return true;

                }
            });

        //initiate progress bar with full progress
        progressBar.setProgress(100);

        //show the number of the counter on the button
        btn_tıkla.setText(""+actualNumber);

        //on button click
        btn_tıkla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(actualNumber<=1) {
                    progressBar.setProgress(0);

                    btn_tıkla.setTextSize(15);

                    if (isVibrationOn){ vb.vibrate(500);}
                    if (isClickSoundOn) { completeSoundPlayer.start();}

                    if (!previousActivity.equals("GunlukVirdlerimScreen")){
                        btn_tıkla.setText("Tebrikler\nhedefinize ulaştınız!");
                        kalanSayiKayitEditor.putInt(comingVird.getId(),0);
                        kalanSayiKayitEditor.commit();
                    }
                    else {
                        btn_tıkla.setText("Tebrikler\ngünlük hedefinizi\ntamamladınız!");
                        gunlukHedefKayitEditor.putInt("gunlukhedefkalan_"+comingVird.getId(),0);
                        gunlukHedefKayitEditor.putInt("day",calendar.get(Calendar.DAY_OF_YEAR));
                        gunlukHedefKayitEditor.commit();
                    }


                }

                else{
                    actualNumber--;

                    progressBar.setProgress(100*actualNumber/comingVird.getTargetNumber());
                    //decrease the number and set on the text of the button

                    btn_tıkla.setText("" + actualNumber);

                    if (isClickSoundOn) {
                        clickSoundPlayer.start();
                    }

                    //her button tıklamada kısa titreşim ver
                    if (isVibrationOn) {
                        vb.vibrate(100);
                    }

                    if (!previousActivity.equals("GunlukVirdlerimScreen")) {
                        kalanSayiKayitEditor.putInt(comingVird.getId(),actualNumber);
                        kalanSayiKayitEditor.commit();
                    } else {
                        gunlukHedefKayitEditor.putInt("gunlukhedefkalan_"+comingVird.getId(),actualNumber);
                        gunlukHedefKayitEditor.putInt("day",calendar.get(Calendar.DAY_OF_YEAR));
                        gunlukHedefKayitEditor.commit();
                    }
                }
            }
        });



    }

    public void setMenuItemsVisibility(BottomNavigationView bottomNavigationView,Vird comingVird){

        if (comingVird.getImage_inbyte()==null&&(comingVird.getImage_inbitmap()==null)&&(comingVird.getArabicText()==null||comingVird.getArabicText().isEmpty())){

            bottomNavigationView.getMenu().getItem(0).setVisible(false);
        }
        else {

            bottomNavigationView.getMenu().getItem(0).setVisible(true);
        }
        if (comingVird.getTurkishText()==null||comingVird.getTurkishText().isEmpty()){

            bottomNavigationView.getMenu().getItem(1).setVisible(false);
        }
        else{

            bottomNavigationView.getMenu().getItem(1).setVisible(true);
        }
        if (comingVird.getId().contains("ayetgrubu")){

            if (comingVird.getMealormeaning()==null||comingVird.getMealormeaning().isEmpty()){

                bottomNavigationView.getMenu().getItem(2).setVisible(false);
            }
            else {bottomNavigationView.getMenu().getItem(2).setVisible(true);}
        }
        else{
            if(comingVird.getMealormeaning()==null||comingVird.getMealormeaning().isEmpty()){
                bottomNavigationView.getMenu().getItem(2).setVisible(false);
            }
            else {bottomNavigationView.getMenu().getItem(2).setVisible(true);}

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (previousActivity.equals("GunlukVirdlerimScreen")) {

            Intent intent = new Intent(Oku.this, GunlukVirdlerimScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}