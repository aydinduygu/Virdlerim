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
import com.codeforlite.virdlerim.ModelClasses.Vird_Classes.Vird;
import com.codeforlite.virdlerim.RV_Adapters.EsmaScreen_RVAdapter;
import com.codeforlite.virdlerim.VirdlerimApplication;

import java.io.Serializable;
import java.util.ArrayList;

public class Fragment_Esma_Page extends Fragment_VirdBase implements Serializable {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=super.onCreateView(inflater, container, savedInstanceState);

        virdList= (ArrayList<Vird>) new DB_Interaction(getActivity().getApplicationContext(), VirdlerimApplication.getDbHelper()).fetch_All("esma");
        adapter=new EsmaScreen_RVAdapter(getActivity(),virdList);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        button_addVird.setVisibility(View.GONE);


        return view;
    }

}