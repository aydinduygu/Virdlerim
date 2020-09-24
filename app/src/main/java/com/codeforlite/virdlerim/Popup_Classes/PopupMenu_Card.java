package com.codeforlite.virdlerim.Popup_Classes;

import android.content.Context;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;

import com.codeforlite.virdlerim.R;


public class PopupMenu_Card extends PopupMenu {

    private MenuInflater menuInflater;



    public PopupMenu_Card(@NonNull Context context, @NonNull View anchor) {
        super(context, anchor);
        menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_card_vird,getMenu());
        this.show();

    }



}
