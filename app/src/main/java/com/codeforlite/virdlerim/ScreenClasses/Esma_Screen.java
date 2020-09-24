package com.codeforlite.virdlerim.ScreenClasses;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SearchView;

import com.codeforlite.virdlerim.DBHelper;
import com.codeforlite.virdlerim.DB_Interaction;
import com.codeforlite.virdlerim.R;
import com.codeforlite.virdlerim.RV_Adapters.EsmaScreen_RVAdapter;
import com.codeforlite.virdlerim.Vird_Classes.Vird;
import com.codeforlite.virdlerim.VirdlerimApplication;

import java.io.Serializable;
import java.util.ArrayList;

public class Esma_Screen extends BaseActivity implements SearchView.OnQueryTextListener, androidx.appcompat.widget.SearchView.OnQueryTextListener, Serializable {

    private ArrayList<Vird> esmaListesi;
    private EsmaScreen_RVAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar_title.setText("Esma-ül Hüsna");

        esmaListesi= (ArrayList<Vird>) new DB_Interaction(getApplicationContext(), VirdlerimApplication.getDbHelper()).fetch_All("esma");
        adapter=new EsmaScreen_RVAdapter(this,esmaListesi);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
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
    public void onPointerCaptureChanged(boolean hasCapture) { }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!drawer.isOpen())finish();
    }
}