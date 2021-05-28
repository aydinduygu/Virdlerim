package com.codeforlite.virdlerim.Popup_Classes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.codeforlite.virdlerim.Activity_Read;
import com.codeforlite.virdlerim.DB_Classes.DB_Interaction;
import com.codeforlite.virdlerim.ModelClasses.Vird_Classes.Esma;
import com.codeforlite.virdlerim.ModelClasses.Vird_Classes.Vird;
import com.codeforlite.virdlerim.R;
import com.codeforlite.virdlerim.VirdlerimApplication;

import java.io.Serializable;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class AlertView_SayiBelirle extends AlertDialog.Builder {

    private Button btn_hedefSayiGir;
    private CheckBox checkBox_ebcedSayisi;
    private EditText editText_sayigir;
    private Vird actualVird;
    private boolean isGunlukVird;
    public boolean isAddedToList;
    private AlertDialog alertDialog;
    private View dialogView;
    private boolean isGunlukHedefSet;


    public AlertView_SayiBelirle(Context context, Vird comingVird, boolean is_GunlukVird) {

        super(context);
        dialogView = ((Activity) context).getLayoutInflater().inflate(R.layout.alertview_selectnumber, null);
        setView(dialogView);

        alertDialog = create();

        isAddedToList = false;

        editText_sayigir = dialogView.findViewById(R.id.editText_hedefsayi_gir);
        checkBox_ebcedSayisi = dialogView.findViewById(R.id.checkBox_ebced);
        btn_hedefSayiGir = dialogView.findViewById(R.id.btn_hedefsayi_gir);
        this.actualVird = comingVird;
        this.isGunlukVird = is_GunlukVird;

        isGunlukHedefSet = false;

        if (!actualVird.getClass().getSimpleName().equals("Esma")) {
            checkBox_ebcedSayisi.setVisibility(View.GONE);
        } else {
            checkBox_ebcedSayisi.setVisibility(View.VISIBLE);
        }

        if (isGunlukVird) {

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
                Intent intent=new Intent(context, Activity_Read.class);
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
                            alertDialog.dismiss();
                            context.startActivity(intent);
                        }
                        else {

                                actualVird.setGunlukHedef(sayi);
                                if ((new DB_Interaction(context, VirdlerimApplication.getDbHelper()).insertToGunlukVird(actualVird))){
                                    isGunlukHedefSet = true;
                                    SharedPreferences sharedPreferences=context.getSharedPreferences("gunlukvirdler",Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    editor.putInt("gunlukhedefkalan_"+actualVird.getId(),actualVird.getGunlukHedef());
                                    editor.commit();
                                    Toast.makeText(VirdlerimApplication.getAppContext(),"Günlük Virdlerinize eklendi",Toast.LENGTH_SHORT).show();

                                    SharedPreferences.Editor buttonSPEditor=context.getSharedPreferences("likeButtons",Context.MODE_PRIVATE).edit();
                                    buttonSPEditor.putBoolean("gunluk"+comingVird.getId(),true);
                                    buttonSPEditor.commit();


                                    alertDialog.dismiss();


                                }


                                else {

                                    Toast.makeText(VirdlerimApplication.getAppContext(), "Hata! Günlük virdlerinize eklenemedi", Toast.LENGTH_LONG).show();
                                    alertDialog.dismiss();

                                }

                        }
                    }
                } else {
                    Toast.makeText(context, "Lütfen hedef sayı giriniz.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.four_corner_radius10);

        alertDialog.show();
        alertDialog.getWindow().setLayout(750, WRAP_CONTENT);
    }

    public Button getBtn_hedefSayiGir() {
        return btn_hedefSayiGir;
    }

    public void setBtn_hedefSayiGir(Button btn_hedefSayiGir) {
        this.btn_hedefSayiGir = btn_hedefSayiGir;
    }

    public CheckBox getCheckBox_ebcedSayisi() {
        return checkBox_ebcedSayisi;
    }

    public void setCheckBox_ebcedSayisi(CheckBox checkBox_ebcedSayisi) {
        this.checkBox_ebcedSayisi = checkBox_ebcedSayisi;
    }

    public EditText getEditText_sayigir() {
        return editText_sayigir;
    }

    public void setEditText_sayigir(EditText editText_sayigir) {
        this.editText_sayigir = editText_sayigir;
    }

    public Vird getActualVird() {
        return actualVird;
    }

    public void setActualVird(Vird actualVird) {
        this.actualVird = actualVird;
    }

    public boolean isGunlukVird() {
        return isGunlukVird;
    }

    public void setGunlukVird(boolean gunlukVird) {
        isGunlukVird = gunlukVird;
    }

    public boolean isAddedToList() {
        return isAddedToList;
    }

    public void setAddedToList(boolean addedToList) {
        isAddedToList = addedToList;
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

    public boolean isGunlukHedefSet() {
        return isGunlukHedefSet;
    }

    public void setGunlukHedefSet(boolean gunlukHedefSet) {
        isGunlukHedefSet = gunlukHedefSet;
    }
}

