package com.codeforlite.virdlerim;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SwitchPreference;

public class MyPreference extends SwitchPreference {

    public MyPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setWidgetLayoutResource(R.layout.custom_switch_preference);
    }

    public MyPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWidgetLayoutResource(R.layout.custom_switch_preference);
    }

    public MyPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWidgetLayoutResource(R.layout.custom_switch_preference);
    }

    public MyPreference(Context context) {

        super(context);
        setWidgetLayoutResource(R.layout.custom_switch_preference);
    }



}
