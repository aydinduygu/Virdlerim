package com.codeforlite.virdlerim.Popup_Classes;

import android.content.Context;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;

import com.codeforlite.virdlerim.R;

public class Popup_Card_GunlukVird extends PopupMenu {

    private MenuInflater menuInflater;

    public Popup_Card_GunlukVird(@NonNull Context context, @NonNull View anchor) {
        super(context, anchor);

        menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.gunlukvirddencikar,getMenu());
        this.show();

    }
}
