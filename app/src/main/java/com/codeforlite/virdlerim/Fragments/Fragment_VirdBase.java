package com.codeforlite.virdlerim.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.codeforlite.virdlerim.AddVird_Secreen;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_AyahGroups_Page;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_Dua_Page;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_Salavat_Page;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_Tesbih_Page;
import com.codeforlite.virdlerim.ModelClasses.Vird_Classes.Vird;
import com.codeforlite.virdlerim.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Fragment_VirdBase extends Fragment {

    protected final static int RESULT_CODE_ADDVIRD=101;
    protected final static int RESULT_CODE_ADDAYAH=102;
    protected RecyclerView recyclerView;
    protected FloatingActionButton button_addVird;
    protected TextView txt_rv_info;
    protected RecyclerView.Adapter<?> adapter;
    protected List<Vird> virdList;
    protected Intent addVirdIntent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_vird_main,null,false);

        init(view);
        recyclerView.setHasFixedSize(true);

        return view;
    }

    private void init(View view) {

        virdList=new ArrayList<>();
        button_addVird=view.findViewById(R.id.button_add_vird);
        recyclerView=view.findViewById(R.id.recyleView_virdScreen);
        txt_rv_info=view.findViewById(R.id.txt_rv_information);
        addVirdIntent=new Intent(getActivity(), AddVird_Secreen.class);
        addVirdIntent.putExtra("adapter", (Serializable) adapter);

        button_addVird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(addVirdIntent,RESULT_CODE_ADDVIRD);
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Fragment fragment=null;

        switch (requestCode){

            case (RESULT_CODE_ADDVIRD):{
                
                String fragmentInfo=data.getStringExtra("fragment");

                switch (fragmentInfo){

                    case("Salavat"):{fragment=new Fragment_Salavat_Page(); break;}
                    case("Tesbih"):{fragment=new Fragment_Tesbih_Page();break;}
                    case("Dua"):{fragment=new Fragment_Dua_Page();break;}

                }

                break;

            }

            case(RESULT_CODE_ADDAYAH):{

                fragment=new Fragment_AyahGroups_Page();break;
            }

        }
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.base_container,fragment).commit();

    }

    public RecyclerView.Adapter<?> getAdapter() {
        return adapter;
    }


}
