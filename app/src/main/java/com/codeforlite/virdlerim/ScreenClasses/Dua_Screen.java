package com.codeforlite.virdlerim.ScreenClasses;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.codeforlite.virdlerim.DB_Interaction;
import com.codeforlite.virdlerim.RV_Adapters.Screens_RVAdapter_1column;
import com.codeforlite.virdlerim.Vird_Classes.Dua;
import com.codeforlite.virdlerim.Vird_Classes.Tesbih;
import com.codeforlite.virdlerim.Vird_Classes.Vird;
import com.codeforlite.virdlerim.VirdlerimApplication;

import java.util.ArrayList;
import java.util.List;

public class Dua_Screen extends BaseActivity implements SearchView.OnQueryTextListener {

    private ArrayList<Vird> duaCardList;
    private Screens_RVAdapter_1column adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar_title.setVisibility(View.GONE);
        toolbar.setTitle("Dualar");

        duaCardList=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        DB_Interaction db_interaction=new DB_Interaction(getApplicationContext(), VirdlerimApplication.getDbHelper());

        List<Vird> fetchedList=db_interaction.fetch_All("Dua");

        if(!fetchedList.isEmpty()){

            for (Vird dua:fetchedList){

                duaCardList.add((Dua) dua);
            }
        }

        adapter=new Screens_RVAdapter_1column(this,duaCardList,"Dua_Screen");
        recyclerView.setAdapter(adapter);

        button_addVird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(Dua_Screen.this,AddVird_Secreen.class);
              intent.putExtra("class","Dua");
              startActivity(intent);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        adapter.getFilter().filter(newText);

        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!drawer.isOpen())finish();
    }
}