package com.codeforlite.virdlerim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.icu.util.Calendar;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.codeforlite.virdlerim.DB_Classes.DB_Interaction;
import com.codeforlite.virdlerim.Fragments.InsideReadPage.Fragment_ArabicText;
import com.codeforlite.virdlerim.Fragments.InsideReadPage.Fragment_Image;
import com.codeforlite.virdlerim.Fragments.InsideReadPage.Fragment_Meaning;
import com.codeforlite.virdlerim.Fragments.InsideReadPage.Fragment_Turkish_Text;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_DailyPrayers_Page;
import com.codeforlite.virdlerim.ModelClasses.Vird_Classes.AyetGrubu;
import com.codeforlite.virdlerim.ModelClasses.Vird_Classes.Vird;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.Serializable;
import java.util.ArrayList;



/*this activity is to read a text with a counter- it shows arabic and turkish spelling and also meaning
in different fragments*/
public class Activity_Read extends AppCompatActivity implements Serializable {

    private int actualNumber;
    private FragmentManager fragmentManager;
    private Fragment tempFragment;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter viewPagerAdapter;
    private ProgressBar progressBar;
    private Button btn_tıkla;
    private Vird comingVird;
    private ArrayList<Fragment> fragments;
    private TabLayoutMediator tabLayoutMediator;
    private MediaPlayer clickSoundPlayer;
    private MediaPlayer completeSoundPlayer;
    private Vibrator vb;
    private SharedPreferences kalanSayiKayit;
    private SharedPreferences.Editor kalanSayiKayitEditor;
    private SharedPreferences gunlukHedefKayit;
    private SharedPreferences.Editor gunlukHedefKayitEditor;
    private String previousActivity;
    private Calendar calendar;
    private ArrayList<String> headerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        init();

        //check if vibration and sound preferences are on
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(VirdlerimApplication.getAppContext());
        boolean isVibrationOn=sp.getBoolean("pref_vibration",true);
        boolean isClickSoundOn=sp.getBoolean("pref_clicksound",true);


        calendar.setTimeInMillis(System.currentTimeMillis());


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



    private void init(){
        //initialize views
        viewPager2 =findViewById(R.id.viewPager2);
        tabLayout=findViewById(R.id.tablayout);
        progressBar=findViewById(R.id.progressBar);
        btn_tıkla=findViewById(R.id.button_tıkla);
        headerList=new ArrayList<String>();
        fragments=new ArrayList<>();

        calendar=Calendar.getInstance();
        previousActivity=getIntent().getStringExtra("activityName");
        comingVird= (Vird) getIntent().getSerializableExtra("Vird.class");
        //sound at every button click
        adaptTabLayout();
        clickSoundPlayer=MediaPlayer.create(this,R.raw.click);
        //sound will be played when target reached
        completeSoundPlayer=MediaPlayer.create(this,R.raw.complete);
        //vibrator for button click
        vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        actualNumber=comingVird.getTargetNumber();
        //to save the number at the counter
        kalanSayiKayit=getSharedPreferences("kalansayilar",MODE_PRIVATE);
        gunlukHedefKayit=VirdlerimApplication.getAppContext().getSharedPreferences("gunlukvirdler",MODE_PRIVATE);
        kalanSayiKayitEditor=kalanSayiKayit.edit();
        gunlukHedefKayitEditor=gunlukHedefKayit.edit();

    }

    private void adaptTabLayout(){

        adaptFragmentList();

        headerList.add("Arapça");
        headerList.add("Türkçe");
        //eğer gelen vird ayet ise Anlam Sekmesini Meal Sekmesi yap
        if(comingVird.getClass().getSimpleName().equals("AyetGrubu")){headerList.add("Meal");}
        else{headerList.add("Anlamı");}


        viewPagerAdapter=new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        tabLayoutMediator= new TabLayoutMediator(
                tabLayout,
                viewPager2,
                (tab,position)->tab.setText(headerList.get(position))
        );

        tabLayoutMediator.attach();


    }

    private void adaptFragmentList(){



        //coming vird with the intent
        if(getIntent().getSerializableExtra("Vird.class") instanceof AyetGrubu){
            AyetGrubu ayetGrubu=(AyetGrubu)getIntent().getSerializableExtra("Vird.class");
        }

        if (previousActivity.equals("Esma_Screen")){
            try {
                comingVird.setImage_inbyte(
                        new DB_Interaction(
                                VirdlerimApplication.getAppContext(),
                                VirdlerimApplication.getDbHelper()).getEsmaImageByteArray(comingVird.getId())
                );
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
        }

        comingVird.setImage_inbitmap(DB_Interaction.getImageFromInternalStorage(comingVird.getImageName()));

        if (comingVird.getImage_inbitmap()!=null||comingVird.getImage_inbyte()!=null){
            //put the arabic image fragment on the container first
            fragments.add(0,new Fragment_Image(Activity_Read.this,comingVird));
        }
        else {
            //put the arabic text fragment on the container first
            fragments.add(0,new Fragment_ArabicText(Activity_Read.this,comingVird));
        }
        fragments.add(1,new Fragment_Turkish_Text(comingVird.getTurkishText()));
        fragments.add(2,new Fragment_Meaning(comingVird.getMealormeaning()));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (previousActivity.equals("GunlukVirdlerimScreen")) {

            Intent intent = new Intent(Activity_Read.this, Fragment_DailyPrayers_Page.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    private class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity){
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemCount() {
            return fragments.size();
        }
    }

}