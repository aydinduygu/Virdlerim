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
import com.codeforlite.virdlerim.Vird_Classes.Tesbih;
import com.codeforlite.virdlerim.Vird_Classes.Vird;
import com.codeforlite.virdlerim.VirdlerimApplication;

import java.util.ArrayList;
import java.util.List;

public class Favoriler_Screen extends BaseActivity implements SearchView.OnQueryTextListener {

    private List<Vird> favorilerCardList;
    private Screens_RVAdapter_1column adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar_title.setVisibility(View.GONE);
        toolbar.setTitle("Favoriler");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        DB_Interaction db_interaction=new DB_Interaction(getApplicationContext(), VirdlerimApplication.getDbHelper());
        favorilerCardList=db_interaction.fetchFavourites();

        if (favorilerCardList==null||favorilerCardList.isEmpty()){
            txt_rv_info.setText("Favorilerinize\n ekleme yapmadınız!");
            txt_rv_info.setVisibility(View.VISIBLE);
        }

        adapter=new Screens_RVAdapter_1column(this,favorilerCardList,"Favoriler_Screen");
        recyclerView.setAdapter(adapter);

        button_addVird.setVisibility(View.GONE);

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