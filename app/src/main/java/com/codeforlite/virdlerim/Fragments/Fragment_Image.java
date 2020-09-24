package com.codeforlite.virdlerim.Fragments;

import android.content.Context;
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
import com.jsibbold.zoomage.ZoomageView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class Fragment_Image extends Fragment {

    private Context context;
    private ZoomageView imageView;
    private Vird vird;

    public Fragment_Image(Context context, Vird vird) {
        this.vird=vird;
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_image,container,false);

        imageView=view.findViewById(R.id.imageView_arabictext_fragment);

        if (vird.getImage_inbyte()!=null||vird.getImage_inbitmap()!=null) {

            if (vird.getImage_inbyte()!=null){
                vird.setImage_inbitmap(BitmapFactory.decodeByteArray(vird.getImage_inbyte(),0,vird.getImage_inbyte().length));
            }
            imageView.setImageBitmap(vird.getImage_inbitmap());
            imageView.getLayoutParams().width=MATCH_PARENT;
            imageView.getLayoutParams().height=WRAP_CONTENT;
        }

        if (imageView.getDrawable()==null){

            imageView.setVisibility(View.GONE);


        }
        else{

            imageView.setVisibility(View.VISIBLE);
        }
        return view;
    }
}
