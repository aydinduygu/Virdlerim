package com.codeforlite.virdlerim.RV_Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.os.Build;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.codeforlite.virdlerim.DB_Interaction;
import com.codeforlite.virdlerim.Oku;
import com.codeforlite.virdlerim.Popup_Classes.PopupMenu_Card;
import com.codeforlite.virdlerim.Popup_Classes.Popup_Card_GunlukVird;
import com.codeforlite.virdlerim.Vird_Classes.AyetGrubu;
import com.codeforlite.virdlerim.Popup_Classes.Popup_DevamSorusu;
import com.codeforlite.virdlerim.Popup_Classes.Popup_SayiBelirle;
import com.codeforlite.virdlerim.R;
import com.codeforlite.virdlerim.Vird_Classes.Vird;
import com.codeforlite.virdlerim.VirdlerimApplication;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class Screens_RVAdapter_1column extends RecyclerView.Adapter<Screens_RVAdapter_1column.Screens_ViewHolder> implements Filterable,Serializable {

    public Context context;
    private List< Vird> comingList;
    private List virdListAll;
    AutoTransition transition;
    String activityName;


    @RequiresApi(api = Build.VERSION_CODES.N)
    public Screens_RVAdapter_1column(Context context, List comingList, String activityName) {
        this.context = context;
        this.comingList = comingList;
        this.virdListAll=new ArrayList<>(comingList);
        this.activityName=activityName;
        transition=new AutoTransition();
        transition.setDuration(1500);

    }

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
                comingList.clear();
                filteredList.addAll(virdListAll);
            }

            else{
                filteredList.clear();
                comingList .clear();
                for(int i=0;i<comingList.size();i++){

                    String c=constraint.toString().toLowerCase();
                    String meal="";


                    if (comingList.get(i) instanceof AyetGrubu){
                        meal=((AyetGrubu)comingList.get(i)).getMeal();
                    }

                    Vird vird=comingList.get(i);

                    if (  ((vird.getImageName()!=null) && vird.getImageName().toLowerCase().contains(c))||
                          ((vird.getTurkishText()!=null) &&  vird.getTurkishText().toLowerCase().contains(c))||
                          ((vird.getTitle()!=null) && vird.getTitle().toLowerCase().contains(c))||
                          ((vird.getMealormeaning()!=null) && vird.getMealormeaning().toLowerCase().contains(c))||
                            meal.toLowerCase().contains(c)){

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

            comingList.clear();
            comingList.addAll((Collection <? extends Vird>) results.values);
            notifyDataSetChanged();

        }
    };


    @NonNull
    @Override
    public Screens_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.card_vird_1column,parent,false);
        return new Screens_ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Screens_ViewHolder holder, int position) {

        holder.setIsRecyclable(false);

       final Vird  comingVird = comingList.get(position);


       if (comingVird instanceof AyetGrubu){

           AyetGrubu ayetGrubu=(AyetGrubu)comingVird;
           comingVird.setMealormeaning(ayetGrubu.getMeal());
       }

        if (activityName.equals("GunlukVirdlerimScreen")){

            holder.txt_hedefSayi.setText(""+comingVird.getGunlukHedef());

            holder.numbersLayout.setVisibility(View.VISIBLE);

            SharedPreferences gunlukVirdKayit=VirdlerimApplication.getAppContext().getSharedPreferences("gunlukvirdler",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=gunlukVirdKayit.edit();
            Calendar calendar=Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            int day=calendar.get(Calendar.DAY_OF_YEAR);
            int savedDay=gunlukVirdKayit.getInt("day",day+1);

            if (day!=savedDay){

                editor.putInt("gunlukhedefkalan_"+comingVird.getId(),comingVird.getGunlukHedef());
            }

            int gunlukVirdKalan=gunlukVirdKayit.getInt("gunlukhedefkalan_"+comingVird.getId(),0);
            holder.txt_kalanSayi.setText(""+gunlukVirdKalan);


            if (gunlukVirdKalan==0){
                holder.imageView_isDone.setBackground(context.getDrawable(R.drawable.ic_baseline_done_18));
            }
        }



        Bitmap imageOfVird= null;

        if (comingVird.getImage_inbyte()!=null){imageOfVird= BitmapFactory.decodeByteArray(comingVird.getImage_inbyte(),0,comingVird.getImage_inbyte().length);
        }
        else {
            try {
                imageOfVird = DB_Interaction.getImageFromInternalStorage(comingVird.getImageName());
                comingVird.setImage_inbitmap(imageOfVird);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        hideViews_IfDataNotExist(comingVird,holder);
        setFrameColor(comingVird,holder);

        try {
            holder.editText_title.setText(comingVird.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            holder.editText_arabic.setText(comingVird.getArabicText());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            holder.editText_turkish.setText(comingVird.getTurkishText());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (comingVird.getClass().getSimpleName().equals("AyetGrubu")){

            try {
                holder.editText_meal.setText(((AyetGrubu)comingVird).getMeal());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            holder.editText_meal.setText(comingVird.getMealormeaning());
        }

        if (holder.editText_arabic.getText().length()>70){
            holder.editText_arabic.getLayoutParams().height= (int) (130 * (context.getResources().getDisplayMetrics().density));}
        else{ holder.editText_arabic.getLayoutParams().height= ViewGroup.LayoutParams.WRAP_CONTENT;}

        if (holder.editText_turkish.getText().length()>70){
            holder.editText_turkish.getLayoutParams().height= (int) (130 * (context.getResources().getDisplayMetrics().density));}
        else{ holder.editText_turkish.getLayoutParams().height= ViewGroup.LayoutParams.WRAP_CONTENT;}

        holder.imageView.setImageBitmap(imageOfVird);
        holder.imageView.getLayoutParams().height= 700;
        holder.imageView.getLayoutParams().width=MATCH_PARENT;

        float radius = context.getResources().getDimension(R.dimen.default_corner_radius);
        holder.imageView.setShapeAppearanceModel(holder.imageView.getShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED,radius)
                .build());


        View.OnClickListener enhanceTextLayouts=new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!holder.isClicked){
                    holder.isClicked=true;
                    holder.editText_meal.setVisibility(View.VISIBLE);
                    TransitionManager.beginDelayedTransition(holder.ayetCard, transition);

                    holder.editText_meal.getLayoutParams().height=ViewGroup.LayoutParams.WRAP_CONTENT;
                    holder.editText_arabic.getLayoutParams().height= ViewGroup.LayoutParams.WRAP_CONTENT;
                    holder.editText_turkish.getLayoutParams().height= ViewGroup.LayoutParams.WRAP_CONTENT;
                    //holder.imageView.getLayoutParams().height=WRAP_CONTENT;
                }
                else{
                    holder.isClicked=false;
                   // TransitionManager.beginDelayedTransition(holder.ayetCard, transition);
                    if (holder.editText_arabic.getText().length()>70){
                    holder.editText_arabic.getLayoutParams().height= (int) (130 * (context.getResources().getDisplayMetrics().density));}
                    else{ holder.editText_arabic.getLayoutParams().height= ViewGroup.LayoutParams.WRAP_CONTENT;}

                    if (holder.editText_turkish.getText().length()>70){
                        holder.editText_turkish.getLayoutParams().height= (int) (130 * (context.getResources().getDisplayMetrics().density));}
                    else{ holder.editText_turkish.getLayoutParams().height= ViewGroup.LayoutParams.WRAP_CONTENT;}

                    holder.editText_meal.getLayoutParams().height=0;
                    holder.editText_meal.setVisibility(View.GONE);
                    holder.imageView.getLayoutParams().height=700;
                }
            }
        };


        holder.button_oku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!activityName.equals("GunlukVirdlerimScreen")) {
                    int kalansayi=  context.getSharedPreferences("kalansayilar",Context.MODE_PRIVATE).getInt(comingVird.getId(),0);

                    if(kalansayi==0) {

                        new Popup_SayiBelirle(context,comingVird,false);
                    }

                    else{new Popup_DevamSorusu(context,kalansayi,comingVird);}
                }
                else {

                    int sayi=context.getSharedPreferences("gunlukvirdler",Context.MODE_PRIVATE).getInt("gunlukhedefkalan_"+comingVird.getId(),0);
                    comingVird.setTargetNumber(sayi);
                    Intent intent=new Intent(context, Oku.class);
                    intent.putExtra("Vird.class", (Serializable) comingVird);
                    intent.putExtra("activityName",activityName);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

        if (activityName.equals("GunlukVirdlerimScreen")){holder.img_menu.setVisibility(View.VISIBLE);}

        holder.img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((!activityName.equals("GunlukVirdlerimScreen"))&&(!activityName.equals("Favoriler_Screen"))) {
                    new PopupMenu_Card(context,holder.img_menu).setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {


                            switch (item.getItemId()){

                                case R.id.action_sil:{
                                    new DB_Interaction(context, VirdlerimApplication.getDbHelper()).removeData(comingVird);
                                    comingList.remove(comingVird);
                                    notifyDataSetChanged();
                                    return true;

                                }
                                case R.id.action_virdlerime_ekle:{

                                    List<Vird> gunlukVirdListesi=new DB_Interaction(context,VirdlerimApplication.getDbHelper()).fetchGunlukVirdlerim();
                                    boolean exists=false;

                                    for (Vird vird:gunlukVirdListesi){

                                        if (vird.getId().equals(comingVird.getId())){
                                            exists=true;
                                            break;
                                        }
                                    }

                                    if (exists){
                                        Toast.makeText(context,"Bu vird zaten günlük virdleriniz arasında",Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        new Popup_SayiBelirle(context,comingVird,true);
                                    }


                                    return true;

                                }

                                case R.id.action_insertfavourites:{

                                    List<Vird> favoriler=new DB_Interaction(context,VirdlerimApplication.getDbHelper()).fetchFavourites();
                                    boolean exists=false;

                                    for (Vird vird:favoriler){

                                        if (vird.getId().equals(comingVird.getId())){
                                            exists=true;
                                            break;
                                        }
                                    }

                                    if (exists){
                                        Toast.makeText(context,"Bu vird zaten favorileriniz arasında",Toast.LENGTH_LONG).show();
                                    }
                                    else {

                                        if ( new DB_Interaction(context,VirdlerimApplication.getDbHelper()).insertToFavourites(comingVird)) {
                                            Toast.makeText(context,"Favorilerinize Eklendi",Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            Toast.makeText(context,"Hata! Favorilere eklenemedi!",Toast.LENGTH_LONG).show();
                                        }

                                    }

                                    return true;
                                }

                                default:return false;
                            }

                        }
                    });
                }

                else {

                    if (activityName.equals("GunlukVirdlerimScreen")) {
                        new Popup_Card_GunlukVird(context,holder.img_menu).setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                new DB_Interaction(context,VirdlerimApplication.getDbHelper()).removeFromGunlukVird(comingVird);
                                comingList.remove(comingVird);
                                notifyDataSetChanged();
                                return true;
                            }
                        });
                    }
                    else if (activityName.equals("Favoriler_Screen")){

                       Popup_Card_GunlukVird popup= new Popup_Card_GunlukVird(context,holder.img_menu);

                       popup.getMenu().getItem(0).setTitle("Favorilerden Çıkar");

                       popup.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                new DB_Interaction(context,VirdlerimApplication.getDbHelper()).removeFromFavourites(comingVird);
                                comingList.remove(comingVird);
                                notifyDataSetChanged();
                                return true;
                            }
                        });
                    }
                }
            }
        });

        holder.editText_title.setOnClickListener(enhanceTextLayouts);
        holder.editText_arabic.setOnClickListener(enhanceTextLayouts);
        holder.ayetCard.setOnClickListener(enhanceTextLayouts);
        holder.editText_meal.setOnClickListener(enhanceTextLayouts);
        holder.editText_turkish.setOnClickListener(enhanceTextLayouts);
    }

    @Override
    public int getItemCount() {
        return comingList. size();
    }

    private void hideViews_IfDataNotExist (Vird comingVird,Screens_ViewHolder holder){

        if (comingVird.getTitle()==null||comingVird.getTitle().equals("")){
            holder.editText_title.setVisibility(View.GONE);
        } else{holder.editText_title.setVisibility(View.VISIBLE);}

        if (comingVird.getArabicText()==null||comingVird.getArabicText().equals("")){
            holder.editText_arabic.setVisibility(View.GONE);
        } else {holder.editText_arabic.setVisibility(View.VISIBLE);}

        if(comingVird.getTurkishText()==null||comingVird.getTurkishText().equals("")){
            holder.editText_turkish.setVisibility(View.GONE);
        } else {holder.editText_turkish.setVisibility(View.VISIBLE);}

        if (comingVird.getClass().getSimpleName().equals("AyetGrubu")){
            if ((((AyetGrubu)comingVird).getMeal()==null)||((AyetGrubu)comingVird).getMeal().equals("")){
            holder.editText_meal.setVisibility(View.GONE);
            }
            //else {holder.editText_meal.setVisibility(View.VISIBLE);}
        }

        else{
             if(((comingVird).getMealormeaning()==null)||(comingVird).getMealormeaning().equals("")){
            holder.editText_meal.setVisibility(View.GONE);
            } //else{ holder.editText_meal.setVisibility(View.VISIBLE);}
        }

        if (comingVird.getImage_inbyte()==null&&comingVird.getImage_inbitmap()==null){

            holder.imageView.setVisibility(View.GONE);
        }else{

            holder.imageView.setVisibility(View.VISIBLE);}


    }

    private void setFrameColor(Vird comingVird,Screens_ViewHolder holder){

        String className=comingVird.getClass().getSimpleName();
        if (className.equals("AyetGrubu")){

            holder.cardOuterLayout.setBackground(context.getDrawable(R.drawable.coloredborder_3));
        }

        else if(className.equals("Tesbih")){

            holder.cardOuterLayout.setBackground(context.getDrawable(R.drawable.coloredborder_1));
        }
        else if(className.equals("Dua")){

            holder.cardOuterLayout.setBackground(context.getDrawable(R.drawable.coloredborder_5));
        }
        else if(className.equals("Salavat")){

            holder.cardOuterLayout.setBackground(context.getDrawable(R.drawable.coloredborder_2));
        }
        else if (className.equals("Esma")){

            holder.cardOuterLayout.setBackground(context.getDrawable(R.drawable.coloredborder_4_thick));
        }

    }


    class Screens_ViewHolder extends RecyclerView.ViewHolder{

        private TextView editText_title;
        private TextView editText_arabic;
        private TextView editText_turkish;
        private TextView editText_meal;
        private Button button_oku;
        private Button img_menu;
        private Button imageView_isDone;
        private CardView ayetCard;
        private boolean isClicked;
        private PopupWindow popupWindow_sayibelirle;
        private LinearLayout cardOuterLayout;
        private ShapeableImageView imageView;
        private LinearLayout numbersLayout;
        private TextView txt_hedefSayi;
        private TextView txt_kalanSayi;




        public Screens_ViewHolder(@NonNull View itemView) {
            super(itemView);

            editText_arabic=itemView.findViewById(R.id.txt_ayetler_arabic);
            editText_title=itemView.findViewById(R.id.txt_ayetler_baslik);
            editText_turkish=itemView.findViewById(R.id.txt_ayetler_turkish);
            editText_meal=itemView.findViewById(R.id.txt_ayetler_meal);
            button_oku=itemView.findViewById(R.id.button_ayetler_oku);
            img_menu=itemView.findViewById(R.id.img_menubutton_ayetler);
            ayetCard=itemView.findViewById(R.id.card_ayetler);
            isClicked=false;
            popupWindow_sayibelirle=new PopupWindow(LayoutInflater.from(context).inflate(R.layout.popup_sayi_belirle,null,false),600,700,true);
            cardOuterLayout=itemView.findViewById(R.id.card_1column_linear_layout);
            imageView=itemView.findViewById(R.id.img_card_1column);
            txt_hedefSayi=itemView.findViewById(R.id.txt_gunluk_hedef);
            txt_kalanSayi=itemView.findViewById(R.id.txt_gunluk_kalan);
            numbersLayout=itemView.findViewById(R.id.cv_layout_numbers);
            imageView_isDone=itemView.findViewById(R.id.imageView_isdone);
        }
    }
}
