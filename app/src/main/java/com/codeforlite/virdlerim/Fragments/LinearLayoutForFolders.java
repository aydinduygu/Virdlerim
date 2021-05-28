package com.codeforlite.virdlerim.Fragments;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class LinearLayoutForFolders extends LinearLayout {

    public LinearLayoutForFolders(Context context) {
        super(context);
    }

    public LinearLayoutForFolders(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearLayoutForFolders(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LinearLayoutForFolders(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
    }
}
