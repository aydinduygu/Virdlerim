package com.codeforlite.virdlerim.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.codeforlite.virdlerim.R;

public class Fragment_Turkce_Okunus extends Fragment {

    private TextView textView;
    private String turkishText;

    public Fragment_Turkce_Okunus(String turkishText) {
        this.turkishText = turkishText;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       View view= inflater.inflate(R.layout.fragment_turkce_okunus,container,false);

       textView=view.findViewById(R.id.fragment_txt_turkish);

       textView.setText(turkishText);

       return view;
    }
}
