package com.codeforlite.virdlerim.Fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.codeforlite.virdlerim.BaseActivity;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_AyahGroups_Page;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_Dua_Page;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_Esma_Page;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_SalahTime_Page;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_Salavat_Page;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_Tesbih_Page;
import com.codeforlite.virdlerim.ModelClasses.Folder;
import com.codeforlite.virdlerim.R;
import com.codeforlite.virdlerim.folderColorList_Interface;

import java.util.ArrayList;

public class Fragment_Dashboard extends Fragment implements folderColorList_Interface {

    private ArrayList<Folder> folderList;
    private LinearLayout folder_ayetler;
    private LinearLayout folder_salavat;
    private LinearLayout folder_esma;
    private LinearLayout folder_dua;
    private LinearLayout folder_salah;
    private LinearLayout folder_tesbih;
    private FrameLayout base_container;
    private Activity activity;
    public Fragment_Dashboard(FrameLayout base_container){
        this.base_container=base_container;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_dashboard,null);
        activity=getActivity();
        init(view);

        setListeners();

        //Görüntülenecek klasörleri listeye ekle
        folderList = new ArrayList<>();
        folderList.add(new Folder("AYET\nGRUPLARI", "Ayetler"));
        folderList.add(new Folder("SALAVAT-I\nŞERİFE", "Salavat"));
        folderList.add(new Folder("ESMAÜL\nHÜSNA", "Esma"));
        folderList.add(new Folder("TESBİHLER", "Tesbih"));
        folderList.add(new Folder("DUALAR", "Dua"));
        folderList.add(new Folder("FAVORİLER", "Vird"));

        return view;
    }

    private void init(View view){

        folder_ayetler=view.findViewById(R.id.lyt_ayetler);
        folder_salah=view.findViewById(R.id.lyt_salah);
        folder_dua=view.findViewById(R.id.şyt_dua);
        folder_tesbih=view.findViewById(R.id.lyt_tesbih);
        folder_esma=view.findViewById(R.id.lyt_esma);
        folder_salavat=view.findViewById(R.id.lyt_salavat);
    }

    private void setListeners(){


        folder_ayetler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 getActivity().getSupportFragmentManager()
                        .beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.fade_out)
                        .replace(R.id.base_container, new Fragment_AyahGroups_Page())
                        .commit();

                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Ayetler");
                ((BaseActivity)getActivity()). animateBgColor(base_container,

                        Color.argb(255,248,245,245),
                        Color.argb(255,52,160,164));

                ((BaseActivity)activity).findViewById(R.id.search).setVisibility(View.VISIBLE);

            }
        });

        folder_salavat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.fade_out)
                        .replace(R.id.base_container, new Fragment_Salavat_Page())
                        .commit();

                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Salavat-ı Şerife");
                ((BaseActivity)getActivity()). animateBgColor(base_container,

                        Color.argb(255,248,245,245),
                        Color.argb(255,52,160,164)

                );

                ((BaseActivity)activity).findViewById(R.id.search).setVisibility(View.VISIBLE);
            }
        });

        folder_esma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.fade_out)
                        .replace(R.id.base_container, new Fragment_Esma_Page())
                        .commit();

                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Esmaül Hüsna");
                ((BaseActivity)getActivity()). animateBgColor(base_container,

                        Color.argb(255,248,245,245),
                        Color.argb(255,52,160,164)

                );
                ((BaseActivity)activity).findViewById(R.id.search).setVisibility(View.VISIBLE);
            }

        });

        folder_tesbih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.fade_out)
                        .replace(R.id.base_container, new Fragment_Tesbih_Page())
                        .commit();

                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Tesbihler");
                ((BaseActivity)getActivity()). animateBgColor(base_container,

                        Color.argb(255,248,245,245),
                        Color.argb(255,52,160,164)

                );
                ((BaseActivity)activity).findViewById(R.id.search).setVisibility(View.VISIBLE);
            }
        });

        folder_dua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.fade_out)
                        .replace(R.id.base_container, new Fragment_Dua_Page())
                        .commit();

                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Dualar");
                ((BaseActivity)getActivity()). animateBgColor(base_container,

                        Color.argb(255,248,245,245),
                        Color.argb(255,52,160,164)

                );

                ((BaseActivity)activity).findViewById(R.id.search).setVisibility(View.VISIBLE);
            }
        });

        folder_salah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager()
                        .beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.fade_out)
                        .replace(R.id.base_container, new Fragment_SalahTime_Page())
                        .commit();

                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Namaz Vakitleri");
                ((BaseActivity)getActivity()). animateBgColor(base_container,

                        Color.argb(255,248,245,245),
                        Color.argb(255,52,160,164)

                );
                ((BaseActivity)activity).findViewById(R.id.search).setVisibility(View.GONE);
            }
        });



    }

}