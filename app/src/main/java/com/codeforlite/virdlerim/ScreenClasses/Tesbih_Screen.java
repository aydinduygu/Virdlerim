package com.codeforlite.virdlerim.ScreenClasses;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.codeforlite.virdlerim.DB_Interaction;
import com.codeforlite.virdlerim.R;
import com.codeforlite.virdlerim.RV_Adapters.Screens_RVAdapter_1column;
import com.codeforlite.virdlerim.Vird_Classes.Tesbih;
import com.codeforlite.virdlerim.Vird_Classes.Vird;
import com.codeforlite.virdlerim.VirdlerimApplication;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Tesbih_Screen extends BaseActivity implements SearchView.OnQueryTextListener {

    private ArrayList<Vird> tesbihCardList;
    private Screens_RVAdapter_1column adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar_title.setVisibility(View.GONE);
        toolbar.setTitle("Tesbihler");

        tesbihCardList=new ArrayList<>();
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        DB_Interaction db_interaction=new DB_Interaction(getApplicationContext(), VirdlerimApplication.getDbHelper());

        List<Vird> fetchedList=db_interaction.fetch_All("Tesbih");

        if(!fetchedList.isEmpty()){

            for (Vird tesbih:fetchedList){

                tesbihCardList.add((Tesbih) tesbih);
            }
        }

        adapter=new Screens_RVAdapter_1column(this,tesbihCardList,"Tesbih_Screen");
        recyclerView.setAdapter(adapter);

        button_addVird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(Tesbih_Screen.this,AddVird_Secreen.class);
              intent.putExtra("class","Tesbih");
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