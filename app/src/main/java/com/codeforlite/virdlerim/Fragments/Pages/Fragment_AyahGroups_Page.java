package com.codeforlite.virdlerim.Fragments.Pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.codeforlite.virdlerim.Add_AyetGroup_Screen;
import com.codeforlite.virdlerim.DB_Classes.DB_Interaction;
import com.codeforlite.virdlerim.Fragments.Fragment_VirdBase;
import com.codeforlite.virdlerim.ModelClasses.Vird_Classes.AyetGrubu;
import com.codeforlite.virdlerim.RV_Adapters.Screens_RVAdapter_1column;
import com.codeforlite.virdlerim.VirdlerimApplication;

import java.util.List;

import lombok.SneakyThrows;

public class Fragment_AyahGroups_Page extends Fragment_VirdBase {


    @SneakyThrows
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=super.onCreateView(inflater,container,savedInstanceState);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        DB_Interaction db_interaction=new DB_Interaction(getActivity().getApplicationContext(), VirdlerimApplication.getDbHelper());

        List<AyetGrubu> fetchedList=(List<AyetGrubu>)(List<?>) db_interaction.fetch_All("AyetGrubu");

        if(!fetchedList.isEmpty()){

            for (AyetGrubu ayetGrubu:fetchedList){

                virdList.add(ayetGrubu);
            }

        }
        adapter=new Screens_RVAdapter_1column(getActivity(),virdList,"AyetGrubu_Screen");
        recyclerView.setAdapter(adapter);

        button_addVird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(getActivity().getApplicationContext(), Add_AyetGroup_Screen.class);
                    startActivityForResult(intent,102);

            }
        });



        return view;
    }



}