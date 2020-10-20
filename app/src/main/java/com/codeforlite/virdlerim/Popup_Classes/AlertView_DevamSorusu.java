package com.codeforlite.virdlerim.Popup_Classes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.codeforlite.virdlerim.Oku;
import com.codeforlite.virdlerim.R;
import com.codeforlite.virdlerim.Vird_Classes.Vird;

import java.io.Serializable;

public class AlertView_DevamSorusu extends AlertDialog.Builder {

    private TextView textView;
    private TextView txt_kalansayi;
    private TextView textView2;
    private RadioGroup radioGroup;
    private RadioButton rb_evet;
    private RadioButton rb_hayır;
    private AlertDialog alertDialog;
    private View dialogView;


    public AlertView_DevamSorusu(Context context, int kalansayi, Vird actualVird) {

        super(context);

        dialogView = ((Activity) context).getLayoutInflater().inflate(R.layout.alertview_devamsorusu, null);
        setView(dialogView);
        alertDialog = create();


        textView = dialogView.findViewById(R.id.txt_devamsorusu);
        textView2 = dialogView.findViewById(R.id.textView2);
        txt_kalansayi = dialogView.findViewById(R.id.txt_kalansayi);
        radioGroup = dialogView.findViewById(R.id.radiogroup_devamsorusu);
        rb_evet = dialogView.findViewById(R.id.rb_devamsorusu_evet);
        rb_hayır = dialogView.findViewById(R.id.rb_devamsorusu_hayır);
        textView.setText("Bu virdde hedefinize ulaşmak için kalan sayı:");
        txt_kalansayi.setText("" + kalansayi);
        textView2.setText("Devam etmek istiyor musunuz?");


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.rb_devamsorusu_evet:{

                        actualVird.setTargetNumber(kalansayi);
                        Intent intent =new Intent(context, Oku.class);
                        intent.putExtra("Vird.class", (Serializable) actualVird);
                        intent.putExtra("activityName","");
                        context.startActivity(intent);
                        alertDialog.dismiss();
                        break;

                    }

                    case R.id.rb_devamsorusu_hayır: {

                        new AlertView_SayiBelirle(context, actualVird, false);
                        alertDialog.dismiss();
                        break;

                    }


                }
            }
        });

        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.coloredborder_7);
        alertDialog.show();

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

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public TextView getTxt_kalansayi() {
        return txt_kalansayi;
    }

    public void setTxt_kalansayi(TextView txt_kalansayi) {
        this.txt_kalansayi = txt_kalansayi;
    }

    public TextView getTextView2() {
        return textView2;
    }

    public void setTextView2(TextView textView2) {
        this.textView2 = textView2;
    }

    public RadioGroup getRadioGroup() {
        return radioGroup;
    }

    public void setRadioGroup(RadioGroup radioGroup) {
        this.radioGroup = radioGroup;
    }

    public void setRb_evet(RadioButton rb_evet) {
        this.rb_evet = rb_evet;
    }

    public void setRb_hayır(RadioButton rb_hayır) {
        this.rb_hayır = rb_hayır;
    }

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }

    public void setAlertDialog(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }

    public View getDialogView() {
        return dialogView;
    }

    public void setDialogView(View dialogView) {
        this.dialogView = dialogView;
    }
}
