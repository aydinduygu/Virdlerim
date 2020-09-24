package com.codeforlite.virdlerim.Popup_Classes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.codeforlite.virdlerim.DB_Interaction;
import com.codeforlite.virdlerim.Vird_Classes.Esma;
import com.codeforlite.virdlerim.Oku;
import com.codeforlite.virdlerim.R;
import com.codeforlite.virdlerim.Vird_Classes.Vird;
import com.codeforlite.virdlerim.VirdlerimApplication;

import java.io.Serializable;
import java.util.List;

public class Popup_SayiBelirle extends PopupWindow {

    private Button btn_hedefSayiGir;
    private CheckBox checkBox_ebcedSayisi;
    private EditText editText_sayigir;


    public Popup_SayiBelirle(Context context, Vird actualVird,boolean isGunlukVird) {

        super(LayoutInflater.from(context).inflate(R.layout.popup_sayi_belirle,null,false),700,700,true);
        setAnimationStyle(R.style.animationforpopup);
        showAtLocation(new LinearLayout(context), Gravity.CENTER,0,0);
        editText_sayigir=getContentView().findViewById(R.id.editText_hedefsayi_gir);
        checkBox_ebcedSayisi=getContentView().findViewById(R.id.checkBox_ebced);
        btn_hedefSayiGir=getContentView().findViewById(R.id.btn_hedefsayi_gir);


        if (!actualVird.getClass().getSimpleName().equals("Esma")){
            checkBox_ebcedSayisi.setVisibility(View.GONE);
        }

        else {
            checkBox_ebcedSayisi.setVisibility(View.VISIBLE);
        }

        if(isGunlukVird){

            editText_sayigir.setHint("Günlük Hedef");
            btn_hedefSayiGir.setText("Tamam");
        }


        checkBox_ebcedSayisi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editText_sayigir.setText(""+((Esma) actualVird).getEbced());
                }
                else{
                    editText_sayigir.setText("");
                }
            }
        });

        btn_hedefSayiGir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Oku.class);
                intent.putExtra("activityName","");
                String editTextContent=editText_sayigir.getText().toString();

                if(!editTextContent.isEmpty()){

                    boolean isDigitAll=true;

                    for (int i=0;i<editTextContent.length();i++){

                        if(!Character.isDigit(editTextContent.charAt(i))){

                            Toast.makeText(context,"Hedef sayı giriniz!",Toast.LENGTH_SHORT).show();
                            isDigitAll=false;
                            break;
                        }
                    }

                    if(isDigitAll) {

                        int sayi = Integer.parseInt(editText_sayigir.getText().toString());

                        if (!isGunlukVird){


                            actualVird.setTargetNumber(sayi);
                            intent.putExtra("Vird.class", (Serializable) actualVird);
                            if (actualVird instanceof Esma){intent.putExtra("activityName","Esma_Screen");}
                            dismiss();
                            context.startActivity(intent);
                        }
                        else {

                                actualVird.setGunlukHedef(sayi);
                                if (new DB_Interaction(context, VirdlerimApplication.getDbHelper()).insertToGunlukVird(actualVird)){

                                    SharedPreferences sharedPreferences=context.getSharedPreferences("gunlukvirdler",Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    editor.putInt("gunlukhedefkalan_"+actualVird.getId(),actualVird.getGunlukHedef());
                                    editor.commit();
                                    Toast.makeText(VirdlerimApplication.getAppContext(),"Günlük Virdlerinize eklendi",Toast.LENGTH_SHORT).show();
                                    dismiss();

                                }

                                else {

                                    Toast.makeText(VirdlerimApplication.getAppContext(),"Hata! Günlük virdlerinize eklenemedi",Toast.LENGTH_LONG).show();
                                    dismiss();

                                }

                        }
                    }
                }

                else {
                    Toast.makeText(context,"Lütfen hedef sayı giriniz.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    }

