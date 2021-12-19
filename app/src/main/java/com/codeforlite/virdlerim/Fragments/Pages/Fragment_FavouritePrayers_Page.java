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
import com.codeforlite.virdlerim.RV_Adapters.Screens_RVAdapter_1column;
import com.codeforlite.virdlerim.VirdlerimApplication;

public class Fragment_FavouritePrayers_Page extends Fragment_VirdBase {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= super.onCreateView(inflater, container, savedInstanceState);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        DB_Interaction db_interaction=new DB_Interaction(getActivity().getApplicationContext(), VirdlerimApplication.getDbHelper());
        virdList=db_interaction.fetchFavourites();

        if (virdList==null||virdList.isEmpty()){
            txt_rv_info.setText("Favorilerinize\n ekleme yapmadınız!");
            txt_rv_info.setVisibility(View.VISIBLE);
        }

        adapter=new Screens_RVAdapter_1column(getActivity(),virdList,"Favoriler_Screen");
        recyclerView.setAdapter(adapter);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        button_addVird.setVisibility(View.GONE);

        return view;
    }
}