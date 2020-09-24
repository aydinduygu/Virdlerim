package com.codeforlite.virdlerim.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.codeforlite.virdlerim.R;
import com.codeforlite.virdlerim.Vird_Classes.Vird;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class Fragment_Arabic extends Fragment {

    private Context context;
    private TextView textView;

    private String arabicText;
    private Vird vird;


    public Fragment_Arabic(Context context,Vird vird) {
        this.arabicText=vird.getArabicText();
        this.vird=vird;
        this.context=context;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_arabic,container,false);
        textView=view.findViewById(R.id.txt_arabicText_oku);


        textView.setText(""+arabicText);

        return view;
    }
}
