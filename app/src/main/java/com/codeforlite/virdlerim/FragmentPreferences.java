package com.codeforlite.virdlerim;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

public class FragmentPreferences extends Fragment {

    private Context context;
    private CustomSwitchPreference vibrationPreference;
    private CustomSwitchPreference clickSoundPreference;
    private CustomSwitchPreference reminderPreference;
    private SharedPreferences preferenceStates;
    private ImageView gear1;
    private ImageView gear2;

    public FragmentPreferences(Context context) {
        this.context=context;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.fragment_settings,null);

        init(rootView);

        return  rootView;


    }

    private void init(View rootView){

        View childVibrationPref=rootView.findViewById(R.id.pref_vibration);

        View childClickSoundPref=rootView.findViewById(R.id.pref_clicksound);

        View childReminderPref=rootView.findViewById(R.id.pref_reminder);


        vibrationPreference=new CustomSwitchPreference(
                getActivity(),
                childVibrationPref,
                R.id.pref_vibration,
                childVibrationPref.findViewById(R.id.pref_icon),
                childVibrationPref.findViewById(R.id.pref_title),
                childVibrationPref.findViewById(R.id.pref_summary),
                childVibrationPref.findViewById(R.id.pref_switch_widget),
                childVibrationPref.findViewById(R.id.txt_time)
        );

        clickSoundPreference=new CustomSwitchPreference(
                getActivity(),
                childClickSoundPref,
                R.id.pref_clicksound,
                childClickSoundPref.findViewById(R.id.pref_icon),
                childClickSoundPref.findViewById(R.id.pref_title),
                childClickSoundPref.findViewById(R.id.pref_summary),
                childClickSoundPref.findViewById(R.id.pref_switch_widget),
                childVibrationPref.findViewById(R.id.txt_time)
        );


        reminderPreference=new CustomSwitchPreference(
                getActivity(),
                childReminderPref,
                R.id.pref_reminder,
                childReminderPref.findViewById(R.id.pref_icon),
                childReminderPref.findViewById(R.id.pref_title),
                childReminderPref.findViewById(R.id.pref_summary),
                childReminderPref.findViewById(R.id.pref_switch_widget),
                childVibrationPref.findViewById(R.id.txt_time)
        );


        //dişli resimleri
        gear1=rootView.findViewById(R.id.img_gear1);
        gear2=rootView.findViewById(R.id.img_gear2);

        //dişliler için dönme animasyonu
        Animation rotate= AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.gear);
        Animation rotate2= AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.gear2);
        gear1.startAnimation(rotate);
        gear2.startAnimation(rotate2);



    }



}
