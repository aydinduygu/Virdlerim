package com.codeforlite.virdlerim;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.codeforlite.virdlerim.Fragments.Fragment_Dashboard;
import com.codeforlite.virdlerim.Fragments.Fragment_VirdBase;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_AyahGroups_Page;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_DailyPrayers_Page;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_Dua_Page;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_Esma_Page;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_FavouritePrayers_Page;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_SalahTime_Page;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_Salavat_Page;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_Tesbih_Page;
import com.codeforlite.virdlerim.RV_Adapters.MyRVAdapter;
import com.google.android.material.navigation.NavigationView;
import com.scwang.wave.MultiWaveHeader;

import lombok.SneakyThrows;

public class BaseActivity extends AppCompatActivity implements androidx.appcompat.widget.SearchView.OnQueryTextListener {

    public Toolbar toolbar;
    public DrawerLayout drawer;
    public NavigationView navigationView;
    private FrameLayout base_container;
    androidx.appcompat.widget.SearchView searchView;
    public ConstraintLayout baseLayout;
    private MultiWaveHeader multiWaveHeader;
    private AutoTransition transition;
    private boolean isSearchBarVisible;


    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_base);
        } catch (Exception e) {
            e.printStackTrace();
        }

        init();


        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.fade_out)
                .add(R.id.base_container,new Fragment_Dashboard(base_container)).commit();

        base_container.setBackgroundColor(getResources().getColor(R.color.primary_light));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){


                    case  R.id.action_drawer_nmz:{
                        findViewById(R.id.search).setVisibility(View.GONE);


                        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.fade_out)
                                .replace(R.id.base_container,new Fragment_SalahTime_Page()).commit();

                        drawer.closeDrawer(GravityCompat.START);
                        getSupportActionBar().setTitle("Namaz Vakitleri");
                        animateBgColor(base_container,

                                Color.argb(255,248,245,245),
                                Color.argb(255,52,160,164)

                        );
                        return true;


                    }

                    case R.id.action_gunlukvirdlerim:{
                        findViewById(R.id.search).setVisibility(View.VISIBLE);
                        isSearchBarVisible=true;
                        toolbar.getMenu().findItem(R.id.search).setVisible(true);
                        isSearchBarVisible=true;
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.fade_out)
                                .replace(R.id.base_container,new Fragment_DailyPrayers_Page(),"MyFragment").commit();

                        drawer.closeDrawer(GravityCompat.START);
                        getSupportActionBar().setTitle("Günlük Virdlerim");
                        animateBgColor(base_container,

                                Color.argb(255,248,245,245),
                                Color.argb(255,52,160,164)

                        );
                        return true;

                    }

                    case R.id.action_drawer_favoriler:{
                        findViewById(R.id.search).setVisibility(View.VISIBLE);

                        isSearchBarVisible=true;
                        toolbar.getMenu().findItem(R.id.search).setVisible(true);

                        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.fade_out)
                                .replace(R.id.base_container,new Fragment_FavouritePrayers_Page(),"MyFragment").commit();

                        drawer.closeDrawer(GravityCompat.START);
                        getSupportActionBar().setTitle("Favorilerim");
                        animateBgColor(base_container,

                                Color.argb(255,248,245,245),
                                Color.argb(255,52,160,164)

                        );
                        return true;
                    }
                    case R.id.action_drawer_ayetgrupları:{
                        findViewById(R.id.search).setVisibility(View.VISIBLE);

                        toolbar.getMenu().findItem(R.id.search).setVisible(true);

                        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.fade_out)
                                .replace(R.id.base_container,new Fragment_AyahGroups_Page(),"MyFragment").commit();


                        drawer.closeDrawer(GravityCompat.START);
                        getSupportActionBar().setTitle("Ayetler");
                        animateBgColor(base_container,

                                Color.argb(255,248,245,245),
                                Color.argb(255,52,160,164)

                        );
                        return true;
                    }

                    case R.id.action_drawer_salavat:{
                        findViewById(R.id.search).setVisibility(View.VISIBLE);
                        toolbar.getMenu().findItem(R.id.search).setVisible(true);

                        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.fade_out)
                                .replace(R.id.base_container,new Fragment_Salavat_Page(),"MyFragment").commit();

                        drawer.closeDrawer(GravityCompat.START);
                        getSupportActionBar().setTitle("Salavat-ı Şerife");
                        animateBgColor(base_container,

                                Color.argb(255,248,245,245),
                                Color.argb(255,52,160,164)

                        );
                        return true;
                    }

                    case R.id.action_drawer_tesbih:{
                        findViewById(R.id.search).setVisibility(View.VISIBLE);
                        toolbar.getMenu().findItem(R.id.search).setVisible(true);

                        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.fade_out)
                                .replace(R.id.base_container,new Fragment_Tesbih_Page(),"MyFragment").commit();

                        drawer.closeDrawer(GravityCompat.START);
                        getSupportActionBar().setTitle("Tesbihler");
                        animateBgColor(base_container,

                                Color.argb(255,248,245,245),
                                Color.argb(255,52,160,164)

                        );
                        return true;
                    }

                    case R.id.action_drawer_dua:{
                        findViewById(R.id.search).setVisibility(View.VISIBLE);
                        toolbar.getMenu().findItem(R.id.search).setVisible(true);

                        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.fade_out)
                                .replace(R.id.base_container,new Fragment_Dua_Page(),"MyFragment").commit();

                        drawer.closeDrawer(GravityCompat.START);
                        getSupportActionBar().setTitle("Dualar");
                        animateBgColor(base_container,

                                Color.argb(255,248,245,245),
                                Color.argb(255,52,160,164)

                        );
                        return true;
                    }
                    case R.id.action_drawer_esma:{
                        findViewById(R.id.search).setVisibility(View.VISIBLE);

                        toolbar.getMenu().findItem(R.id.search).setVisible(true);
                        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.fade_out)
                                .replace(R.id.base_container,new Fragment_Esma_Page(),"MyFragment").commit();

                        drawer.closeDrawer(GravityCompat.START);
                        getSupportActionBar().setTitle("Esmaül Hüsna");
                        animateBgColor(base_container,

                                Color.argb(255,248,245,245),
                                Color.argb(255,52,160,164)

                        );
                        return true;
                    }

                    case R.id.action_close:{

                        finishAffinity();
                        System.exit(0);
                        return true;
                    }

                    case R.id.action_settings:{
                        findViewById(R.id.search).setVisibility(View.GONE);

                        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.fade_out)
                                .replace(R.id.base_container,new FragmentPreferences(getBaseContext())).commit();

                        drawer.closeDrawer(GravityCompat.START);
                        getSupportActionBar().setTitle("Ayarlar");
                        animateBgColor(base_container,

                                Color.argb(255,248,245,245),
                                Color.argb(255,52,160,164)

                        );
                        return true;
                    }
                }
                return false;
            }
        });




    }

    private void init() throws Exception{

        baseLayout=findViewById(R.id.base_screen_layout);
        toolbar=findViewById(R.id.toolbar);
        drawer=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.drawer_nav);
        base_container=findViewById(R.id.base_container);
        multiWaveHeader=findViewById(R.id.wave_header);
        isSearchBarVisible=false;

        //toolbarı özelleştir
        toolbar.setPadding(15,15,15,15);
        toolbar.setTitle("Virdlerim");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        ActionBarDrawerToggle toggle
                =new ActionBarDrawerToggle(this,drawer,toolbar,0,0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //nav view de ikonların orijinal renkleri ile görülmesi için
        navigationView.setItemIconTintList(null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        String activityName=this.getClass().getSimpleName();

        findViewById(R.id.search).setVisibility(View.GONE);

        if (activityName.equals("Fragment_Dashboard")||activityName.equals("SettingsFragmentParent")){
            getMenuInflater().inflate(R.menu.toolbar_menu_without_search,menu);


        }
        else{

            getMenuInflater().inflate(R.menu.search_menu,menu);
            MenuItem item=menu.findItem(R.id.search);

            findViewById(R.id.search).setVisibility(View.GONE);
            searchView= (androidx.appcompat.widget.SearchView) new CustomSearchView(getBaseContext(),item).getActionView();
            searchView.setOnQueryTextListener(this);

        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_toolbar_gunlukvird:{

                findViewById(R.id.search).setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.fade_out)
                        .replace(R.id.base_container,new Fragment_DailyPrayers_Page()).commit();
                animateBgColor(base_container,

                        Color.argb(255,248,245,245),
                        Color.argb(255,52,160,164)

                );
                break;

            }
            case R.id.action_toolbar_favoriler:{

                findViewById(R.id.search).setVisibility(View.VISIBLE);

                getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.fade_out)
                        .replace(R.id.base_container,new Fragment_FavouritePrayers_Page()).commit();
                animateBgColor(base_container,

                        Color.argb(255,248,245,245),
                        Color.argb(255,52,160,164)

                );
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

        Fragment_VirdBase runningFragment=
                (Fragment_VirdBase) getSupportFragmentManager()
                        .findFragmentById(R.id.base_container);

        if(runningFragment!=null &&runningFragment.isVisible()) {

            if (runningFragment.getAdapter()!=null){
                ((MyRVAdapter) (runningFragment.getAdapter())).getFilter().filter(newText);
            }
        }
        return true;
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
            getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.fade_out)
                    .replace(R.id.base_container,new Fragment_Dashboard(base_container)).commit();
            getSupportActionBar().setTitle("Virdlerim");
        }
        animateBgColor(base_container,
                Color.argb(255,52,160,164),
                Color.argb(255,248,245,245)
        );

        isSearchBarVisible=false;
        findViewById(R.id.search).setVisibility(View.GONE);

        //base_container.setBackgroundColor(getResources().getColor(R.color.primary_light));
    }

    public void animateBgColor(View view,int argb1,int argb2){

        /*final ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(view,
                "backgroundColor",
                new ArgbEvaluator(),
                argb1,
                argb1);
        backgroundColorAnimator.setDuration(4000);
        backgroundColorAnimator.start();*/


        //Let's change background's color from blue to red.
        ColorDrawable[] color = {new ColorDrawable(argb2), new ColorDrawable(argb1)};
        TransitionDrawable trans = new TransitionDrawable(color);
        //This will work also on old devices. The latest API says you have to use setBackground instead.
        base_container.setBackgroundDrawable(trans);
        trans.startTransition(600);

    }

    public boolean isSearchBarVisible() {
        return isSearchBarVisible;
    }

    public void setSearchBarVisible(boolean searchBarVisible) {
        isSearchBarVisible = searchBarVisible;
    }
}


