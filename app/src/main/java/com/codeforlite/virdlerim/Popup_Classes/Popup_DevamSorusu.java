package com.codeforlite.virdlerim.Popup_Classes;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.codeforlite.virdlerim.Oku;
import com.codeforlite.virdlerim.R;
import com.codeforlite.virdlerim.Vird_Classes.Vird;

import java.io.Serializable;

public class Popup_DevamSorusu extends PopupWindow {

    private TextView textView;
    private TextView txt_kalansayi;
    private TextView textView2;
    private RadioGroup radioGroup;
    private RadioButton rb_evet;
    private RadioButton rb_hayır;



    public Popup_DevamSorusu(Context context, int kalansayi, Vird actualVird) {

        super(LayoutInflater.from(context).inflate(R.layout.popup_devamsorusu,null,false), 600, 800, true);
        setAnimationStyle(R.style.animationforpopup);
        showAtLocation(new LinearLayout(context), Gravity.CENTER,0,0);
        textView=getContentView().findViewById(R.id.txt_devamsorusu);
        textView2=getContentView().findViewById(R.id.textView2);
        txt_kalansayi=getContentView().findViewById(R.id.txt_kalansayi);
        radioGroup=getContentView().findViewById(R.id.radiogroup_devamsorusu);
        rb_evet=getContentView().findViewById(R.id.rb_devamsorusu_evet);
        rb_hayır=getContentView().findViewById(R.id.rb_devamsorusu_hayır);
        textView.setText("Bu virdde hedefinize ulaşmak için kalan sayı:");
        txt_kalansayi.setText(""+kalansayi);
        textView2.setText("Devam etmek istiyor musunuz?");


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){

                    case R.id.rb_devamsorusu_evet:{

                        actualVird.setTargetNumber(kalansayi);
                        Intent intent =new Intent(context, Oku.class);
                        intent.putExtra("Vird.class", (Serializable) actualVird);
                        intent.putExtra("activityName","");
                        context.startActivity(intent);
                        dismiss();
                        break;

                    }

                    case R.id.rb_devamsorusu_hayır:{

                        new Popup_SayiBelirle(context,actualVird,false);
                        dismiss();
                        break;

                    }


                }
            }
        });




    }

    public TextView getTextView() {
        return textView;
    }

    public RadioButton getRb_evet() {
        return rb_evet;
    }

    public RadioButton getRb_hayır() {
        return rb_hayır;
    }


}
