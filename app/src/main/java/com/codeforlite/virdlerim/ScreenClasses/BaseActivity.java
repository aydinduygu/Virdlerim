package com.codeforlite.virdlerim.ScreenClasses;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.codeforlite.virdlerim.R;
import com.codeforlite.virdlerim.SettingsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class BaseActivity extends AppCompatActivity implements androidx.appcompat.widget.SearchView.OnQueryTextListener {

    public Toolbar toolbar;
    public DrawerLayout drawer;
    public NavigationView navigationView;
    public RecyclerView recyclerView;
    public FloatingActionButton button_addVird;
    public TextView txt_rv_info;
    public TextView toolbar_title;
    androidx.appcompat.widget.SearchView searchView;
    public ConstraintLayout baseLayout;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        baseLayout=findViewById(R.id.base_screen_layout);


        button_addVird=findViewById(R.id.button_add_vird);
        recyclerView=findViewById(R.id.recyleView_virdScreen);
        toolbar=findViewById(R.id.toolbar);

        drawer=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.drawer_nav);
        txt_rv_info=findViewById(R.id.txt_rv_information);
        toolbar_title=findViewById(R.id.txt_toolbar_title);
        toolbar_title.setVisibility(View.GONE);

        //toolbarı özelleştir
        toolbar.setPadding(15,15,15,15);
        toolbar.setTitle("Virdlerim");
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,0,0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //nav view de ikonların orijinal renkleri ile görülmesi için
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.action_gunlukvirdlerim:{

                        Intent intent=new Intent(getApplicationContext(),GunlukVirdlerimScreen.class);
                        startActivity(intent);
                        drawer.closeDrawer(GravityCompat.START);

                        return true;

                    }

                    case R.id.action_drawer_favoriler:{

                        Intent intent=new Intent(getApplicationContext(),Favoriler_Screen.class);
                        startActivity(intent);
                        drawer.closeDrawer(GravityCompat.START);

                        return true;
                    }
                    case R.id.action_drawer_ayetgrupları:{

                        Intent intent=new Intent(getApplicationContext(),AyetGrubu_Screen.class);
                        startActivity(intent);
                        drawer.closeDrawer(GravityCompat.START);

                        return true;
                    }

                    case R.id.action_drawer_salavat:{

                        Intent intent=new Intent(getApplicationContext(),Salavat_Screen.class);
                        startActivity(intent);
                        drawer.closeDrawer(GravityCompat.START);

                        return true;
                    }

                    case R.id.action_drawer_tesbih:{

                        Intent intent=new Intent(getApplicationContext(),Tesbih_Screen.class);
                        startActivity(intent);
                        drawer.closeDrawer(GravityCompat.START);

                        return true;
                    }

                    case R.id.action_drawer_dua:{

                        Intent intent=new Intent(getApplicationContext(),Dua_Screen.class);
                        startActivity(intent);
                        drawer.closeDrawer(GravityCompat.START);

                        return true;
                    }
                    case R.id.action_drawer_esma:{

                        Intent intent=new Intent(getApplicationContext(),Esma_Screen.class);
                        startActivity(intent);
                        drawer.closeDrawer(GravityCompat.START);

                        return true;
                    }

                    case R.id.action_close:{

                        finishAffinity();
                        System.exit(0);
                        return true;
                    }

                    case R.id.action_settings:{

                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));

                    }
                }
                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        String activityName=this.getClass().getSimpleName();


        if (activityName.equals("Folder_Screen")||activityName.equals("SettingsActivity")){
            getMenuInflater().inflate(R.menu.toolbar_menu_without_search,menu);


        }
        else{

            getMenuInflater().inflate(R.menu.search_menu,menu);
            MenuItem item=menu.findItem(R.id.search);
            searchView= (androidx.appcompat.widget.SearchView) new CustomSearchView(getBaseContext(),item).getActionView();
            searchView.setOnQueryTextListener(this);
        }

        //MenuItem item=menu.findItem(R.id.search);
       // searchView= (androidx.appcompat.widget.SearchView) new CustomSearchView(getBaseContext(),item).getActionView();
        //searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_toolbar_gunlukvird:{

                startActivity(new Intent(this,GunlukVirdlerimScreen.class));
                break;

            }
            case R.id.action_toolbar_favoriler:{

                startActivity(new Intent(this,Favoriler_Screen.class));
                break;
            }


        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {


        return false;
    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    class CustomSearchView extends SearchView {

        private MenuItem item;

        public CustomSearchView(@NonNull Context context, MenuItem item) {
            super(context);
            this.item=item;
        }

        public View getActionView(){

            return item.getActionView();
        }


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (drawer.isOpen()){
            drawer.close();
        }
        else {
            super.onBackPressed();
        }

    }
}


