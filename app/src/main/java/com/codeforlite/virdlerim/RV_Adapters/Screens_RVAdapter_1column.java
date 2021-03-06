package com.codeforlite.virdlerim.RV_Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.os.Build;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import com.codeforlite.virdlerim.Activity_Read;
import com.codeforlite.virdlerim.DB_Classes.DB_Interaction;
import com.codeforlite.virdlerim.ModelClasses.Vird_Classes.AyetGrubu;
import com.codeforlite.virdlerim.ModelClasses.Vird_Classes.Vird;
import com.codeforlite.virdlerim.Popup_Classes.AlertView_DevamSorusu;
import com.codeforlite.virdlerim.Popup_Classes.AlertView_SayiBelirle;
import com.codeforlite.virdlerim.R;
import com.codeforlite.virdlerim.VirdlerimApplication;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class Screens_RVAdapter_1column extends MyRVAdapter implements Serializable{

    public Context context;

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




    @NonNull
    @Override
    public Screens_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.card_vird_1column,parent,false);
        return new Screens_ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        Screens_ViewHolder holder=(Screens_ViewHolder)myViewHolder;
        holder.deleteButton.setVisibility(View.VISIBLE);
        holder.setIsRecyclable(false);

        final Vird  comingVird = comingList.get(position);

        //fav ve g??nl??k vird buttonlar??n??n durumlar??n?? al ona g??re ayarla
        SharedPreferences buttonSP=context.getSharedPreferences("likeButtons",Context.MODE_PRIVATE);
        SharedPreferences.Editor buttonSPEditor=buttonSP.edit();
        boolean isFav=buttonSP.getBoolean("fav"+comingVird.getId(),false);
        if (isFav){holder.likeButton.setLiked(true);}
        else {holder.likeButton.setLiked(false);}
        boolean isGunlukVird=buttonSP.getBoolean("gunluk"+comingVird.getId(),false);
        if (isGunlukVird){holder.gunlukVirdButton.setLiked(true);}
        else {holder.gunlukVirdButton.setLiked(false);}


        if (comingVird instanceof AyetGrubu){

           AyetGrubu ayetGrubu=(AyetGrubu)comingVird;
           comingVird.setMealormeaning(ayetGrubu.getMeal());
        }

        if (activityName.equals("Favoriler_Screen")){
            holder.deleteButton.setVisibility(View.GONE);
        }

        if (activityName.equals("GunlukVirdlerimScreen")){

            holder.deleteButton.setVisibility(View.GONE);

            //set visibility for numbers layout and devider
            holder.numbersLayout.setVisibility(View.VISIBLE);
            holder.numbersLayoutDivider.setVisibility(View.VISIBLE);

            holder.txt_hedefSayi.setText(""+comingVird.getGunlukHedef());

            //e??er g??n de??i??mi??se g??nl??k virdlerin kalan say?? de??erlerini default de??erlerine d??nd??r
            SharedPreferences gunlukVirdKayit=VirdlerimApplication.getAppContext().getSharedPreferences("gunlukvirdler",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=gunlukVirdKayit.edit();
            Calendar calendar=Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            int day=calendar.get(Calendar.DAY_OF_YEAR);
            int savedDay=gunlukVirdKayit.getInt("day",day+1);


            //e??er kay??tl?? g??n haz??r g??n ile uyu??muyorsa kalan vird say??s??n?? default de??ere d??n????t??r
            if (day!=savedDay) {

                editor.putInt("gunlukhedefkalan_" + comingVird.getId(), comingVird.getGunlukHedef());
                editor.commit();

                //save actual day on device
                editor.putInt("day", day);
                editor.commit();
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

        holder.editText_arabic.getLayoutParams().height= ViewGroup.LayoutParams.WRAP_CONTENT;

        holder.imageView.setImageBitmap(imageOfVird);
        holder.imageView.getLayoutParams().height= 700;
        holder.imageView.getLayoutParams().width=MATCH_PARENT;

        //resim varsa k????elere 30 dp  radius vermek i??in:
        float radius = context.getResources().getDimension(R.dimen.default_corner_radius);
        holder.imageView.setShapeAppearanceModel(holder.imageView.getShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED,radius)
                .build());



        //oku butonuna t??klan??ld??????nda yap??lacaklar
        holder.button_oku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!activityName.equals("GunlukVirdlerimScreen")) {
                    int kalansayi=  context.getSharedPreferences("kalansayilar",Context.MODE_PRIVATE).getInt(comingVird.getId(),0);

                    if (kalansayi == 0) {
                        new AlertView_SayiBelirle(context, comingVird, false);
                    } else {
                        new AlertView_DevamSorusu(context, kalansayi, comingVird);
                    }
                }
                else {

                    int sayi=context.getSharedPreferences("gunlukvirdler",Context.MODE_PRIVATE).getInt("gunlukhedefkalan_"+comingVird.getId(),0);
                    comingVird.setTargetNumber(sayi);
                    Intent intent=new Intent(context, Activity_Read.class);
                    intent.putExtra("Vird.class", (Serializable) comingVird);
                    intent.putExtra("activityName",activityName);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

        //silme butonuna t??klan??ld??????nda yap??lacaklar
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              Snackbar snackbar=  Snackbar.make(holder.cardOuterLayout,"Bu virdi silmek istiyor musunuz?", BaseTransientBottomBar.LENGTH_LONG).setAction("Evet", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DB_Interaction db_interaction = new DB_Interaction(context, VirdlerimApplication.getDbHelper());
                        db_interaction.removeData(comingVird);

                        if (activityName.equals("GunlukVirdlerimScreen")) {
                            db_interaction.removeFromGunlukVird(comingVird);
                        } else if (activityName.equals("Favoriler_Screen")) {
                            db_interaction.removeFromFavourites(comingVird);
                        }
                        comingList.remove(comingVird);
                        notifyDataSetChanged();

                        Snackbar snackbar2 = Snackbar.make(v, "Virdiniz silindi.", Snackbar.LENGTH_LONG);
                        snackbar2.setTextColor(context.getResources().getColor(R.color.accent));

                    }
                });

              snackbar.setTextColor(context.getResources().getColor(R.color.accent));
              View view=snackbar.getView();
              view.setPadding(0,10,0,10);
              snackbar.show();



            }

        });

        //g??nl??k virdlere ekleme butonuna t??klan??ld??????nda yap??lacaklar
        holder.gunlukVirdButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

                AlertView_SayiBelirle sayiBelirle = new AlertView_SayiBelirle(context, comingVird, true);

                //e??er g??nl??k hedef girilmeden alert view dismiss olmu??sa liked durumunu geri al
                sayiBelirle.getAlertDialog().setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (!sayiBelirle.isGunlukHedefSet()) {
                            holder.gunlukVirdButton.setLiked(false);
                        }
                    }
                });
            }

            @Override
            public void unLiked(LikeButton likeButton) {

                new DB_Interaction(VirdlerimApplication.getAppContext(),VirdlerimApplication.getDbHelper()).removeFromGunlukVird(comingVird);
                buttonSPEditor.putBoolean("gunluk"+comingVird.getId(),false);
                buttonSPEditor.commit();
                if (activityName.equals("GunlukVirdlerimScreen")){

                    comingList.remove(comingVird);
                    notifyDataSetChanged();
                }
                Toast.makeText(context,"G??nl??k Virdlerinizden ????kart??ld??",Toast.LENGTH_LONG).show();


            }
        });

        //favoriye ekle-????kar butonuna t??klan??ld??????nda yap??lacaklar
        holder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {


                List<Vird> favoriler=new DB_Interaction(context,VirdlerimApplication.getDbHelper()).fetchFavourites();

                    if ( new DB_Interaction(context,VirdlerimApplication.getDbHelper()).insertToFavourites(comingVird)) {
                        Toast.makeText(context,"Favorilerinize Eklendi",Toast.LENGTH_LONG).show();
                        buttonSPEditor.putBoolean("fav"+comingVird.getId(),true);
                        buttonSPEditor.commit();
                    }
                    else {
                        Toast.makeText(context,"Hata! Favorilere eklenemedi!",Toast.LENGTH_LONG).show();
                    }


            }

            @Override
            public void unLiked(LikeButton likeButton) {

                buttonSPEditor.putBoolean("fav"+comingVird.getId(),false);
                buttonSPEditor.commit();
                Toast.makeText(context,"Favorilerinizden ????kart??ld??!",Toast.LENGTH_LONG).show();
                new DB_Interaction(context,VirdlerimApplication.getDbHelper()).removeFromFavourites(comingVird);

                if (activityName.equals("Favoriler_Screen")){
                    comingList.remove(comingVird);
                    notifyDataSetChanged();}

            }
        });


        Animation arrowAlphaAnim=new AlphaAnimation(0.0f,1.0f);
        arrowAlphaAnim.setFillAfter(true);
        arrowAlphaAnim.setDuration(1000);
        arrowAlphaAnim.setRepeatCount(Animation.INFINITE);
        arrowAlphaAnim.setRepeatMode(Animation.REVERSE);
        arrowAlphaAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                holder.arrow1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

           // holder.arrow1.startAnimation(arrowAlphaAnim);

        //t??klan??ld??????nda expand eylemi i??in:
        View.OnClickListener enhanceTextLayouts=new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!holder.isClicked){

                    holder.isClicked=true;
                    holder.editText_meal.setVisibility(View.VISIBLE);
                    TransitionManager.beginDelayedTransition(holder.ayetCard, transition);
                    holder.editText_turkish.setMaxLines(1000);
                    holder.editText_arabic.setMaxLines(1000);

                    holder.editText_meal.getLayoutParams().height=ViewGroup.LayoutParams.WRAP_CONTENT;
                    // holder.editText_arabic.getLayoutParams().height= ViewGroup.LayoutParams.WRAP_CONTENT;
                    holder.editText_turkish.getLayoutParams().height= ViewGroup.LayoutParams.WRAP_CONTENT;
                    //holder.imageView.getLayoutParams().height=WRAP_CONTENT;
                }
                else{

                    holder.editText_arabic.setMaxLines(3);
                    holder.editText_turkish.setMaxLines(3);
                    holder.isClicked=false;
                    holder.editText_meal.getLayoutParams().height=0;
                    holder.editText_meal.setVisibility(View.GONE);
                    holder.imageView.getLayoutParams().height=700;
                }
            }
        };
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

        //title divider visibility durumu ayarla
        if (comingVird.getTitle()==null||comingVird.getTitle().isEmpty()){
            holder.titleDivider.setVisibility(View.GONE);
        }

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

            holder.cardOuterLayout.setBackground(context.getDrawable(R.drawable.coloredborder_red));
        }

        else if(className.equals("Tesbih")){

            holder.cardOuterLayout.setBackground(context.getDrawable(R.drawable.coloredborder_yellow));
        }
        else if(className.equals("Dua")){

            holder.cardOuterLayout.setBackground(context.getDrawable(R.drawable.coloredborder_purple));
        }
        else if(className.equals("Salavat")){

            holder.cardOuterLayout.setBackground(context.getDrawable(R.drawable.coloredborder_blue));
        }
        else if (className.equals("Esma")){

            holder.cardOuterLayout.setBackground(context.getDrawable(R.drawable.coloredborder_green));
        }

    }


    class Screens_ViewHolder extends MyViewHolder{

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
        private LikeButton likeButton;
        private Button deleteButton;
        private LikeButton gunlukVirdButton;
        private View titleDivider;
        private LinearLayout arrow1;
        private View numbersLayoutDivider;





        public Screens_ViewHolder(@NonNull View itemView) {
            super(itemView);

            editText_arabic=itemView.findViewById(R.id.txt_ayetler_arabic);
            editText_title=itemView.findViewById(R.id.txt_ayetler_baslik);
            editText_turkish=itemView.findViewById(R.id.txt_ayetler_turkish);
            editText_meal=itemView.findViewById(R.id.txt_ayetler_meal);
            button_oku=itemView.findViewById(R.id.button_ayetler_oku);

            ayetCard=itemView.findViewById(R.id.card_ayetler);
            isClicked = false;
            popupWindow_sayibelirle = new PopupWindow(LayoutInflater.from(context).inflate(R.layout.alertview_selectnumber, null, false), 600, 700, true);
            cardOuterLayout = itemView.findViewById(R.id.card_1column_linear_layout);
            imageView=itemView.findViewById(R.id.img_card_1column);
            txt_hedefSayi=itemView.findViewById(R.id.txt_gunluk_hedef);
            txt_kalanSayi=itemView.findViewById(R.id.txt_gunluk_kalan);
            numbersLayout=itemView.findViewById(R.id.cv_layout_numbers);
            imageView_isDone=itemView.findViewById(R.id.imageView_isdone);
            likeButton=itemView.findViewById(R.id.likebutton);
            gunlukVirdButton=itemView.findViewById(R.id.gunlukvirdbutton);
            deleteButton=itemView.findViewById(R.id.deletebutton);
            titleDivider=itemView.findViewById(R.id.dividertitle);
            arrow1=itemView.findViewById(R.id.arrow_1);
            numbersLayoutDivider=itemView.findViewById(R.id.dividernumberslayout);
        }
    }
}
