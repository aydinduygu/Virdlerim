package com.codeforlite.virdlerim.Fragments.InsideReadPage;

import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.codeforlite.virdlerim.R;

public class Fragment_Meaning extends Fragment {

    private ConstraintLayout layout;
    private EditText textview;
    private String Anlam;

    public Fragment_Meaning(String anlam) {
        Anlam = anlam;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_meaning,container,false);

        textview=view.findViewById(R.id.txt_fragment_anlam);

        layout=view.findViewById(R.id.fragment_anlam);


        textview.setText(Anlam);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textview.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        view.setBackgroundColor(getResources().getColor(R.color.icons));
    }
}
