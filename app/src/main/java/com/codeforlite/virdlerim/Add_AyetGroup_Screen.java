package com.codeforlite.virdlerim;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.codeforlite.virdlerim.DB_Classes.DB_Interaction;
import com.codeforlite.virdlerim.ModelClasses.Vird_Classes.Ayet;
import com.codeforlite.virdlerim.ModelClasses.Vird_Classes.AyetGrubu;
import com.codeforlite.virdlerim.RV_Adapters.Add_AyetGroup_RV_Adapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<Ayet> ayetler;
    private EditText editText_title;
    private Ayet ayetToAdd;
    private List<String> sureNames;
    private List<Integer> ayetSayilari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__ayet_group);

        init();

        ayetler.add(new DB_Interaction(getApplicationContext(), VirdlerimApplication.getDbHelper()).fetchAyet(1,1));


        //fill recycler view with cards
        rv_ayetList_to_add.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        rv_ayetList_to_add.setAdapter(new Add_AyetGroup_RV_Adapter(getApplicationContext(),ayetler));

        //fetch surenames and ayah numbers
        sureNames=new DB_Interaction(this,VirdlerimApplication.getDbHelper()).getSureNames_fromDB();
        List<Integer> ayetSayilari=new DB_Interaction(this,VirdlerimApplication.getDbHelper()).getAyetSayilari_fromDB();

       //set the spinner for sure names
        sureNames_arrayAdapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,sureNames);
        spinner_surelist.setAdapter(sureNames_arrayAdapter);

        //when a sure name is selected:
        spinner_surelist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //set text styles for the spinner items
                ((TextView)(parent.getChildAt(0))).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


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
                spinner_ayetsayıları.setAdapter(ayetSayilari_arrayAdapter);

                //when an ayah selected:
                spinner_ayetsayıları.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @SuppressLint({"ResourceAsColor", "WrongConstant"})
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {

                        ((TextView)(parent.getChildAt(0))).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                        ayetno=position2+1;

                        add_ayet_toList.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                new AyahFethcer().execute(""+sureno,sureNames.get(position),""+ayetno);

                            }
                        });

                        save_list.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (editText_title.getText().toString()!=null&&!editText_title.getText().toString().isEmpty()){

                                    if (ayetGrubu_forAdding!=null){

                                        //store on database
                                        new DB_Interaction(getApplicationContext(),VirdlerimApplication.getDbHelper()).insertData(ayetGrubu_forAdding);
                                        //pass to AyetGrubuScreen activity

                                        Intent intent=new Intent();
                                        setResult(102,intent);
                                        finish();
                                    }
                                }

                                else {Toast.makeText(getApplicationContext(), "Başlık Ekleyin!",Toast.LENGTH_SHORT).show();}
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

    private void init() {

        //initiallze views
        rv_ayetList_to_add=findViewById(R.id.rv_ayetlist_to_add);
        spinner_ayetsayıları=findViewById(R.id.spinner_ayetsayıları);
        spinner_surelist=findViewById(R.id.spinner_surelistesi);
        add_ayet_toList=findViewById(R.id.button_addayet_tolist);
        save_list=findViewById(R.id.button_save_list);
        ayetler=new ArrayList<>();
        from_1_to_ayetSayisi=new ArrayList<>();
        editText_title=findViewById(R.id.editText_title);
    }



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class AyahFethcer extends AsyncTask<String,Object,Object> {

        private Context context;

        public Ayet fetchAyah(String sureNumber,String sureName, String ayahNumber) throws IOException,NullPointerException {

            Ayet ayet=new DB_Interaction(context,VirdlerimApplication.getDbHelper())
                    .fetchAyet(Integer.parseInt(sureNumber),Integer.parseInt(ayahNumber));

            String url="https://kuran.diyanet.gov.tr/tefsir/"+sureName.split(" ")[0].toLowerCase()+"-suresi/"+ayet.getAyetId()+"/"+ayahNumber+"-ayet-tefsiri";

            Element body= Jsoup.connect(url).get().body();
            String arabicText=body.getElementsByClass("list-group-item").text();

            ayet.setArabicText(arabicText);
            return ayet;
        }


        @Override
        protected Object doInBackground(String... strings) {

            Ayet ayet=null;
            try {
                return fetchAyah(strings[0],strings[1],strings[2]);
            } catch (IOException e) {
                Toast.makeText(context,"Ayet bilgisi alınamıyor.",Toast.LENGTH_SHORT).show();
            } catch (NullPointerException e){
                Toast.makeText(context,"Ayet bilgisi alınamıyor.",Toast.LENGTH_SHORT).show();

            }
            catch (Exception e){
                Toast.makeText(context,"Ayet bilgisi alınamıyor.",Toast.LENGTH_SHORT).show();

            }
            return ayet;
        }

        @Override
        protected void onPostExecute(Object o) {

            try{
                ayetToAdd=(Ayet)o;
                ayetToAdd.setAyetNo(ayetno);
                ayetToAdd.setSureAdı(sureNames.get(sureno-1));
                ayetler.add(ayetToAdd);
                ayetGrubu_forAdding=new AyetGrubu(null,null, (ArrayList<Ayet>) ayetler);
                Objects.requireNonNull(rv_ayetList_to_add.getAdapter()).notifyDataSetChanged();
                rv_ayetList_to_add.smoothScrollToPosition(ayetler.size()-1);
            }
            catch (Exception e){
                Toast.makeText(context,"Ayet bilgisi alınamıyor.",Toast.LENGTH_SHORT).show();

            }

        }
    }

}
