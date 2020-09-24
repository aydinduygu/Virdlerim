package com.codeforlite.virdlerim.Popup_Classes;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.codeforlite.virdlerim.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Popup_SetTitle extends PopupWindow {

    private EditText editText_Title;
    private FloatingActionButton button_setTitle;

    public Popup_SetTitle(Context context) {
        super(LayoutInflater.from(context).inflate(R.layout.popup_set_ayetgrouptitle,null,false),930,300,true);
        setAnimationStyle(R.style.animationforpopup);
        showAtLocation(new LinearLayout(context), Gravity.CENTER,0,0);
        editText_Title=getContentView().findViewById(R.id.editText_title);
        button_setTitle=getContentView().findViewById(R.id.button_setTitle);
    }

    public EditText getEditText_Title() {
        return editText_Title;
    }

    public FloatingActionButton getButton_setTitle() {
        return button_setTitle;
    }


}
