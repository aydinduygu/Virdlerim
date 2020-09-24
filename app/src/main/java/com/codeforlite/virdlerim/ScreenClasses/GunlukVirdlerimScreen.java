package com.codeforlite.virdlerim.ScreenClasses;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.codeforlite.virdlerim.DB_Interaction;
import com.codeforlite.virdlerim.RV_Adapters.Screens_RVAdapter_1column;
import com.codeforlite.virdlerim.Vird_Classes.Vird;
import com.codeforlite.virdlerim.VirdlerimApplication;

import java.util.ArrayList;
import java.util.List;


public class GunlukVirdlerimScreen extends BaseActivity {

    private List<Vird> gunlukVirdList;
    private Screens_RVAdapter_1column adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar_title.setText("Günlük Virdlerim");
        toolbar.setTitleMarginStart(170);

        gunlukVirdList=new ArrayList<>();

        DB_Interaction db_interaction=new DB_Interaction(getApplicationContext(), VirdlerimApplication.getDbHelper());
        gunlukVirdList=db_interaction.fetchGunlukVirdlerim();

       if (gunlukVirdList.isEmpty()){
            txt_rv_info.setText("Günlük virdlerinize\n ekleme yapmadınız!");
            txt_rv_info.setTextSize(20);
            txt_rv_info.setVisibility(View.VISIBLE);
        }

        adapter=new Screens_RVAdapter_1column(getApplicationContext(),gunlukVirdList,"GunlukVirdlerimScreen");

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

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

        if (!drawer.isOpen()) {
            Intent intent = new Intent(GunlukVirdlerimScreen.this, Folder_Screen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }

}