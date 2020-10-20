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
import android.view.animation.Animation;
import android.widget.Toast;

import com.codeforlite.virdlerim.DB_Interaction;
import com.codeforlite.virdlerim.Popup_Classes.Popup_SetTitle;
import com.codeforlite.virdlerim.R;
import com.codeforlite.virdlerim.RV_Adapters.Screens_RVAdapter_1column;
import com.codeforlite.virdlerim.Vird_Classes.AyetGrubu;
import com.codeforlite.virdlerim.Vird_Classes.Vird;
import com.codeforlite.virdlerim.VirdlerimApplication;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AyetGrubu_Screen extends BaseActivity implements SearchView.OnQueryTextListener {


    private ArrayList<Vird> ayetlercardList;
    private Screens_RVAdapter_1column adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar_title.setVisibility(View.GONE);
        toolbar.setTitle("Ayet Grupları");

        ayetlercardList=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        DB_Interaction db_interaction=new DB_Interaction(getApplicationContext(), VirdlerimApplication.getDbHelper());

        List<AyetGrubu> fetchedList=(List<AyetGrubu>)(List<?>) db_interaction.fetch_All("AyetGrubu");

        if(!fetchedList.isEmpty()){

            for (AyetGrubu ayetGrubu:fetchedList){

                ayetlercardList.add(ayetGrubu);
            }

        }
        adapter=new Screens_RVAdapter_1column(this,ayetlercardList,"AyetGrubu_Screen");
        recyclerView.setAdapter(adapter);

        button_addVird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Popup_SetTitle popup_setTitle=new Popup_SetTitle(AyetGrubu_Screen.this);

                popup_setTitle.getButton_setTitle().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title=popup_setTitle.getEditText_Title().getText().toString();

                        if(!title.isEmpty()){

                            Intent intent=new Intent(getApplicationContext(), Add_AyetGroup_Screen.class);
                            intent.putExtra("title",title);
                            startActivity(intent);
                            popup_setTitle.dismiss();

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Öncelikle başlık girmelisiniz",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {



        return super.onCreateOptionsMenu(menu);

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