package com.codeforlite.virdlerim.Fragments.InsideReadPage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.codeforlite.virdlerim.ModelClasses.Vird_Classes.Vird;
import com.codeforlite.virdlerim.R;

public class Fragment_ArabicText extends Fragment {

    private Context context;
    private TextView textView;

    private String arabicText;
    private Vird vird;


    public Fragment_ArabicText(Context context, Vird vird) {
        this.arabicText=vird.getArabicText();
        this.vird=vird;
        this.context=context;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_arabic,container,false);
        textView=view.findViewById(R.id.txt_arabicText_oku);

        if (arabicText.length()<30){

            textView.setTextSize(textView.getTextSize()+4);

        }

        textView.setText(""+arabicText);

        return view;
    }
}
