package com.codeforlite.virdlerim.RV_Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.codeforlite.virdlerim.DB_Interaction;
import com.codeforlite.virdlerim.Popup_Classes.PopupMenu_Card;
import com.codeforlite.virdlerim.Vird_Classes.Esma;
import com.codeforlite.virdlerim.Popup_Classes.Popup_DevamSorusu;
import com.codeforlite.virdlerim.Popup_Classes.Popup_SayiBelirle;
import com.codeforlite.virdlerim.R;
import com.codeforlite.virdlerim.Vird_Classes.Vird;
import com.codeforlite.virdlerim.VirdlerimApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EsmaScreen_RVAdapter extends RecyclerView.Adapter<EsmaScreen_RVAdapter.CardViewObjectsHolder> implements Filterable, Serializable {

    private Context context;
    private List<Vird> esmaList;
    private List<Vird> virdListAll;
    private CardViewObjectsHolder oldHolder;
    private ArrayList<CardViewObjectsHolder> allCards;

    public EsmaScreen_RVAdapter(Context context, ArrayList<Vird> esmaList) {
        this.context = context;
        this.esmaList = esmaList;
        this.virdListAll=new ArrayList<>(esmaList);
        allCards=new ArrayList<>();

    }

    //esmalar arasında filtremele yapmak için:
    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Vird> filteredList=new ArrayList<>();

            if(constraint.toString().isEmpty()){
                filteredList.clear();
                esmaList.clear();
                filteredList.addAll(virdListAll);
            }

            else{
                filteredList.clear();
                esmaList.clear();
                for(Vird vird:virdListAll){
                        if (vird.getImageName().toLowerCase().contains(constraint.toString().toLowerCase())||vird.getTurkishText().toLowerCase().contains(constraint.toString().toLowerCase())){

                            filteredList.add(vird);
                        }
                }
            }

            FilterResults filterResults=new FilterResults();
            filterResults.values=filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            esmaList.clear();
            esmaList.addAll((Collection<? extends Esma>) results.values);
            notifyDataSetChanged();

        }
    };

    @NonNull
    @Override
    public CardViewObjectsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlayout_esma,parent,false);

        return new CardViewObjectsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewObjectsHolder holder, int position) {

        final Vird actualVird= esmaList.get(position);
        Bitmap imageOfVird= null;
        try {
            imageOfVird = BitmapFactory.decodeByteArray(actualVird.getImage_inbyte(),0,actualVird.getImage_inbyte().length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        allCards.add(holder);

        //cardlardaki viewların üstüste binme hatasını düzeltmek için
        holder.setIsRecyclable(false);

        if(actualVird.getTurkishText()!=null){
            holder.turkish_text.setText(actualVird.getTurkishText());
        }

        if(actualVird.getImage_inbyte()!=null) {

            holder.arabic_text_image.setImageDrawable(null);
            holder.arabic_text_image.setImageBitmap(imageOfVird);
            holder.arabic_text_image.getLayoutParams().width=400;
            holder.arabic_text_image.getLayoutParams().height=300;
            holder.arabic_text_image.setPadding(5,5,5,5);
        }

        if(actualVird.getImage_inbyte()==null){
            holder.txt_arabic_text.setText(actualVird.getArabicText());
            holder.txt_arabic_text.setVisibility(View.VISIBLE);
            holder.arabic_text_image.setVisibility(View.GONE);
        }


        if(actualVird.getMealormeaning()!=null){

            holder.txt_anlam.setText(""+actualVird.getMealormeaning());
            holder.txt_anlam.setEllipsize(TextUtils.TruncateAt.END);

        }


        holder.oku_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              int kalansayi=  context.getSharedPreferences("kalansayilar",Context.MODE_PRIVATE).getInt(actualVird.getId(),0);

                if(kalansayi==0) {

                    new Popup_SayiBelirle(context,actualVird,false);
                }

                else{new Popup_DevamSorusu(context,kalansayi,actualVird);}
            }
        });

        //popup menu
        holder.menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PopupMenu_Card(context,holder.menu_button).setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){

                            case R.id.action_sil:{

                                esmaList.remove(actualVird);
                                notifyDataSetChanged();
                                break;
                            }

                            case R.id.action_virdlerime_ekle:{

                                List<Vird> gunlukVirdListesi=new DB_Interaction(context, VirdlerimApplication.getDbHelper()).fetchGunlukVirdlerim();
                                boolean exists=false;

                                for (Vird vird:gunlukVirdListesi){

                                    if (vird.getId().equals(actualVird.getId())){
                                        exists=true;
                                        break;
                                    }
                                }

                                if (exists){

                                    Toast.makeText(context,"Bu vird zaten günlük virdleriniz arasında",Toast.LENGTH_LONG).show();

                                }

                                else {
                                    new Popup_SayiBelirle(context,actualVird,true);
                                    return true;
                                }
                            }

                            case R.id.action_insertfavourites:{

                                List<Vird> favoriler=new DB_Interaction(context,VirdlerimApplication.getDbHelper()).fetchFavourites();
                                boolean exists=false;

                                for (Vird vird:favoriler){

                                    if (vird.getId().equals(actualVird.getId())){
                                        exists=true;
                                        break;
                                    }
                                }

                                if (exists){
                                    Toast.makeText(context,"Bu vird zaten favorileriniz arasında",Toast.LENGTH_LONG).show();
                                }
                                else {

                                    if ( new DB_Interaction(context,VirdlerimApplication.getDbHelper()).insertToFavourites(actualVird)) {
                                        Toast.makeText(context,"Favorilerinize Eklendi",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(context,"Hata! Favorilere eklenemedi!",Toast.LENGTH_LONG).show();
                                    }

                                }

                                return true;
                            }
                        }


                        return false;
                    }
                });
            }
        });

        //yazı veya resim üzerine tıklanıldığında layout anlamın tamamını gösterecek şekilde uzayacak
        final ArrayList<View> items=new ArrayList<>();
        items.add(holder.arabic_text_image);
        items.add(holder.txt_anlam);
        items.add(holder.turkish_text);
        items.add(holder.txt_layout);
        items.add(holder.cardLayout);
        items.add(holder.card_cons_lay);


        for(int i=0;i<items.size();i++){

            items.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(oldHolder==null){

                        oldHolder = holder;
                        holder.isClicked = true;
                        actualVird.setClicked(true);
                        AutoTransition transition = new AutoTransition();
                        transition.setDuration(1000);
                        TransitionManager.beginDelayedTransition(holder.cardLayout, transition);
                        ViewGroup.LayoutParams layoutParams = holder.txt_layout.getLayoutParams();
                        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        holder.txt_layout.setLayoutParams(layoutParams);
                        holder.cardLayout.setBackground(context.getResources().getDrawable(R.drawable.coloredborder_6));

                    }

                    else{

                        if(holder.isClicked) {

                            oldHolder=null;
                            holder.isClicked = false;
                            actualVird.setClicked(false);
                            AutoTransition transition = new AutoTransition();
                            transition.setDuration(100);
                            TransitionManager.beginDelayedTransition(holder.cardLayout, transition);
                            ViewGroup.LayoutParams layoutParams = holder.txt_layout.getLayoutParams();
                            layoutParams.height = (int) (100 * (context.getResources().getDisplayMetrics().density));
                            holder.txt_layout.setLayoutParams(layoutParams);
                            holder.cardLayout.setBackground(context.getResources().getDrawable(R.drawable.coloredborder_4));
                            transition.setDuration(1000);

                        }

                        else{

                            oldHolder.isClicked = false;
                            actualVird.setClicked(false);
                            AutoTransition transition = new AutoTransition();
                            transition.setDuration(1000);
                            TransitionManager.beginDelayedTransition(holder.cardLayout, transition);
                            ;
                            ViewGroup.LayoutParams layoutParams = oldHolder.txt_layout.getLayoutParams();
                            layoutParams.height = (int) (100 * (context.getResources().getDisplayMetrics().density));
                            oldHolder.txt_layout.setLayoutParams(layoutParams);


                            oldHolder.cardLayout.setBackground(context.getResources().getDrawable(R.drawable.coloredborder_7));

                            oldHolder = holder;
                            holder.isClicked = true;
                            actualVird.setClicked(true);
                            transition = new AutoTransition();
                            transition.setDuration(1000);
                            TransitionManager.beginDelayedTransition(holder.cardLayout, transition);
                            layoutParams = holder.txt_layout.getLayoutParams();
                            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                            holder.txt_layout.setLayoutParams(layoutParams);
                            holder.cardLayout.setBackground(context.getResources().getDrawable(R.drawable.coloredborder_3));

                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return esmaList.size();
    }


    //inner class olarak holder clası düzenliyoruz, card üzerindeki view ler burada tanımlanır
    public class CardViewObjectsHolder extends RecyclerView.ViewHolder {

        private boolean isClicked;
        private ImageView arabic_text_image;
        private TextView turkish_text;
        private TextView vird_sayisi;
        private Button virdime_ekle;
        private Button oku_button;
        private CardView cv_vird;
        private ImageView menu_button;
        private TextView txt_anlam;
        private Animation cardEnlargeAnimation;
        private LinearLayout cardLayout;
        private LinearLayout txt_layout;
        private ConstraintLayout card_cons_lay;
        private TextView txt_arabic_text;
        private ConstraintLayout sayiBelirleLAyout;


        public CardViewObjectsHolder(View view){

            super(view);

            isClicked=false;
            arabic_text_image=view.findViewById(R.id.img_arabic_text);
            turkish_text=view.findViewById(R.id.txt_turkish_text);
            txt_anlam=view.findViewById(R.id.txt_anlam);

            oku_button=view.findViewById(R.id.button_ayetler_oku);
            cv_vird=view.findViewById(R.id.folder_cv_vird);
            menu_button=view.findViewById(R.id.img_menubutton_ayetler);

            cardLayout=view.findViewById(R.id.folder_cv_top_linear);
            txt_layout=view.findViewById(R.id.linearLayout_text);
            card_cons_lay=view.findViewById(R.id.card_constraint_layout);
            txt_arabic_text=view.findViewById(R.id.txt_arabic_text);

        }


    }



}
