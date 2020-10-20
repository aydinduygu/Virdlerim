package com.codeforlite.virdlerim.ScreenClasses;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Toast;

import com.codeforlite.virdlerim.Folder;
import com.codeforlite.virdlerim.R;
import com.codeforlite.virdlerim.RV_Adapters.FolderScreen_RVAdapter;
import com.codeforlite.virdlerim.Vird_Classes.Vird;
import com.codeforlite.virdlerim.folderColorList_Interface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Folder_Screen extends BaseActivity implements folderColorList_Interface {

    private ArrayList<Folder> folderList;
    private FolderScreen_RVAdapter adapter;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Görüntülenecek klasörleri listeye ekle
        folderList = new ArrayList<>();
        folderList.add(new Folder("AYET GRUPLARI", "Ayetler"));
        folderList.add(new Folder("SALAVAT-I ŞERİFE", "Salavat"));
        folderList.add(new Folder("ESMAÜL HÜSNA", "Esma"));
        folderList.add(new Folder("TESBİHLER", "Tesbih"));
        folderList.add(new Folder("DUALAR", "Dua"));
        folderList.add(new Folder("FAVORİLER", "Vird"));

        adapter = new FolderScreen_RVAdapter(this, folderList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        button_addVird.setVisibility(View.GONE);


    }

}