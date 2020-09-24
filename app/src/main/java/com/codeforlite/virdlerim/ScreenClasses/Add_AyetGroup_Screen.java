package com.codeforlite.virdlerim.ScreenClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.codeforlite.virdlerim.RV_Adapters.Add_AyetGroup_RV_Adapter;
import com.codeforlite.virdlerim.DB_Interaction;
import com.codeforlite.virdlerim.R;
import com.codeforlite.virdlerim.Vird_Classes.Ayet;
import com.codeforlite.virdlerim.Vird_Classes.AyetGrubu;
import com.codeforlite.virdlerim.VirdlerimApplication;

import java.util.ArrayList;
import java.util.List;

public class Add_AyetGroup_Screen extends AppCompatActivity {

    private Spinner spinner_surelist;
    private Spinner spinner_ayetsayıları;
    private Button add_ayet_toList;
    private Button save_list;
    private RecyclerView rv_ayetList_to_add;
    private ArrayAdapter<String> sureNames_arrayAdapter;
    private ArrayAdapter<String> ayetSayilari_arrayAdapter;
    public int suredeki_ayetSayisi;
    public int sureno;
    public int ayetno;
    public AyetGrubu ayetGrubu_forAdding;
    private List<String> from_1_to_ayetSayisi;
    List<Ayet> ayetler;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__ayet_group);

        //get the title from coming intent
        title=getIntent().getStringExtra("title");

        //initiallze views
        rv_ayetList_to_add=findViewById(R.id.rv_ayetlist_to_add);
        spinner_ayetsayıları=findViewById(R.id.spinner_ayetsayıları);
        spinner_surelist=findViewById(R.id.spinner_surelistesi);
        add_ayet_toList=findViewById(R.id.button_addayet_tolist);
        save_list=findViewById(R.id.button_save_list);
        ayetler=new ArrayList<>();
        ayetler.add(new DB_Interaction(getApplicationContext(), VirdlerimApplication.getDbHelper()).fetchAyet(1,1));
        from_1_to_ayetSayisi=new ArrayList<>();

        //fill recycler view with cards
        rv_ayetList_to_add.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        rv_ayetList_to_add.setAdapter(new Add_AyetGroup_RV_Adapter(getApplicationContext(),ayetler));

        //fetch surenames and ayah numbers
        List<String> sureNames=new DB_Interaction(this,VirdlerimApplication.getDbHelper()).getSureNames_fromDB();
        List<Integer> ayetSayilari=new DB_Interaction(this,VirdlerimApplication.getDbHelper()).getAyetSayilari_fromDB();

       //set the spinner for sure names
        sureNames_arrayAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,sureNames);
        sureNames_arrayAdapter.setDropDownViewResource(R.layout.spinner_text_layout);
        spinner_surelist.setAdapter(sureNames_arrayAdapter);

        //when a sure name is selected:
        spinner_surelist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @SuppressLint({"ResourceAsColor", "WrongConstant"})
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //set text styles for the spinner items
                ((TextView)(parent.getChildAt(0))).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                ((TextView)(parent.getChildAt(0))).setTextAppearance(getApplicationContext(),R.style.CustomText);
                ((TextView)(parent.getChildAt(0))).setTextColor(R.color.primary_dark);

                sureno=position+1;

                //find number of the ayahs belong to the surah
                suredeki_ayetSayisi=(ayetSayilari.get(position));

                from_1_to_ayetSayisi.clear();
                //prepare a integer list starting from 1 to the number of ayah's of the selected surah
                for(int i=0;i<suredeki_ayetSayisi;i++){
                    from_1_to_ayetSayisi.add(""+(i+1));
                }

                //set the ayah number spinner
                ayetSayilari_arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,from_1_to_ayetSayisi);
                ayetSayilari_arrayAdapter.setDropDownViewResource(R.layout.spinner_text_layout);
                spinner_ayetsayıları.setAdapter(ayetSayilari_arrayAdapter);

                //when an ayah selected:
                spinner_ayetsayıları.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @SuppressLint({"ResourceAsColor", "WrongConstant"})
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {

                        ((TextView)(parent.getChildAt(0))).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        ((TextView)(parent.getChildAt(0))).setTextAppearance(getApplicationContext(),R.style.CustomText);
                        ((TextView)(parent.getChildAt(0))).setTextColor(R.color.primary_dark);

                        ayetno=position2+1;

                        add_ayet_toList.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                Ayet ayet=new DB_Interaction(getApplicationContext(),VirdlerimApplication.getDbHelper()).fetchAyet(sureno,ayetno);
                                ayet.setAyetNo(ayetno);
                                ayet.setSureAdı(sureNames.get(sureno-1));
                                ayetler.add(ayet);
                                ayetGrubu_forAdding=new AyetGrubu(null,title, (ArrayList<Ayet>) ayetler);
                                rv_ayetList_to_add.getAdapter().notifyDataSetChanged();
                                rv_ayetList_to_add.smoothScrollToPosition(ayetler.size()-1);
                            }
                        });

                        save_list.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent=new Intent(getApplicationContext(), AyetGrubu_Screen.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                if (ayetGrubu_forAdding!=null){

                                    //store on database
                                    new DB_Interaction(getApplicationContext(),VirdlerimApplication.getDbHelper()).insertData(ayetGrubu_forAdding);
                                    //pass to AyetGrubuScreen activity
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
}
