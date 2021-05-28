package com.codeforlite.virdlerim.Fragments.Pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.codeforlite.virdlerim.DB_Classes.DB_Interaction;
import com.codeforlite.virdlerim.Fragments.Fragment_VirdBase;
import com.codeforlite.virdlerim.ModelClasses.Vird_Classes.Tesbih;
import com.codeforlite.virdlerim.ModelClasses.Vird_Classes.Vird;
import com.codeforlite.virdlerim.RV_Adapters.Screens_RVAdapter_1column;
import com.codeforlite.virdlerim.VirdlerimApplication;

import java.util.List;

public class Fragment_Tesbih_Page extends Fragment_VirdBase {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= super.onCreateView(inflater, container, savedInstanceState);


        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        DB_Interaction db_interaction=new DB_Interaction(getActivity().getApplicationContext(), VirdlerimApplication.getDbHelper());

        List<Vird> fetchedList=db_interaction.fetch_All("Tesbih");

        if(!fetchedList.isEmpty()){

            for (Vird tesbih:fetchedList){

                virdList.add((Tesbih) tesbih);
            }
        }

        adapter=new Screens_RVAdapter_1column(getActivity(),virdList,"Tesbih_Screen");
        recyclerView.setAdapter(adapter);
        addVirdIntent.putExtra("class","Tesbih");


        return view;
    }
}