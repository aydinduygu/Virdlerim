package com.codeforlite.virdlerim.RV_Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.codeforlite.virdlerim.ScreenClasses.AyetGrubu_Screen;
import com.codeforlite.virdlerim.ScreenClasses.Dua_Screen;
import com.codeforlite.virdlerim.ScreenClasses.Esma_Screen;
import com.codeforlite.virdlerim.Folder;
import com.codeforlite.virdlerim.R;
import com.codeforlite.virdlerim.ScreenClasses.Favoriler_Screen;
import com.codeforlite.virdlerim.ScreenClasses.Salavat_Screen;
import com.codeforlite.virdlerim.ScreenClasses.Tesbih_Screen;
import com.codeforlite.virdlerim.Vird_Classes.Salavat;
import com.codeforlite.virdlerim.Vird_Classes.Vird;
import com.codeforlite.virdlerim.folderColorList_Interface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FolderScreen_RVAdapter extends RecyclerView.Adapter<FolderScreen_RVAdapter.VirdScreen_ViewHolder> implements folderColorList_Interface {

    private Context context;
    private List<Folder> folderList;
    private List<Vird> virdList;
    private PopupWindow addFolderPopup;
    private FloatingActionButton button_addFolder2;
    private TextInputLayout textInputLayout_addFolder;
    private EditText editText_folderName;
    private LinearLayout layout;



    public PopupWindow getAddFolderPopup() {
        return addFolderPopup;
    }

    public void setAddFolderPopup(PopupWindow addFolderPopup) {
        this.addFolderPopup = addFolderPopup;
    }

    public FloatingActionButton getButton_addFolder2() {
        return button_addFolder2;
    }

    public void setButton_addFolder2(FloatingActionButton button_addFolder2) {
        this.button_addFolder2 = button_addFolder2;
    }

    public EditText getEditText_folderName() {
        return editText_folderName;
    }

    public void setEditText_folderName(EditText editText_folderName) {
        this.editText_folderName = editText_folderName;
    }

    public LinearLayout getLayout() {
        return layout;
    }

    public void setLayout(LinearLayout layout) {
        this.layout = layout;
    }

    public List<Folder> getFolderList() {
        return folderList;
    }

    public void setFolderList(List<Folder> folderList) {
        this.folderList = folderList;
    }

    List<Integer> backgrounds_with_roundedCorners;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public FolderScreen_RVAdapter(Context context, List<Folder> folderList) {
        this.context = context;
        this.folderList = folderList;
        fillColorList();
        layout=new LinearLayout(context);
        addFolderPopup=new PopupWindow(LayoutInflater.from(context).inflate(R.layout.addfolder_popup_layout,null,false), 910,300,true);
        addFolderPopup.setAnimationStyle(R.anim.slide_down);
        button_addFolder2=addFolderPopup.getContentView().findViewById(R.id.button_addFolder2);
        editText_folderName=addFolderPopup.getContentView().findViewById(R.id.editText_title);
        
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public FolderScreen_RVAdapter(Context context, List<Folder> folderList, List<Vird> virdList) {
        this.context = context;
        this.folderList = folderList;
        this.virdList = virdList;
        fillColorList();
        layout=new LinearLayout(context);
        addFolderPopup=new PopupWindow(LayoutInflater.from(context).inflate(R.layout.addfolder_popup_layout,null,false), 300,300,true);
        button_addFolder2=addFolderPopup.getContentView().findViewById(R.id.button_addFolder2);
        editText_folderName=addFolderPopup.getContentView().findViewById(R.id.editText_title);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void fillColorList(){

        backgrounds_with_roundedCorners= Arrays.stream(BACKGROUNDCOLOR_WITH_ROUNDEDCORNERS).boxed().collect(Collectors.toList());

    }


    @NonNull
    @Override
    public VirdScreen_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_folder,parent,false);
        return new VirdScreen_ViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull VirdScreen_ViewHolder holder, int position) {

        Folder actualFolder=folderList.get(position);

        holder.folder_Text.setText(actualFolder.getFolderName());


        if(backgrounds_with_roundedCorners.size()==0){fillColorList();}
        holder.linearLayout.setBackground(context.getResources().getDrawable(backgrounds_with_roundedCorners.get(backgrounds_with_roundedCorners.size()-1)));
        //holder.constraintLayout.setBackground(context.getResources().getDrawable(drawables.get(drawables.size()-1)));
        holder.cardView.setBackground(context.getResources().getDrawable(backgrounds_with_roundedCorners.get(backgrounds_with_roundedCorners.size()-1)));
        backgrounds_with_roundedCorners.remove(backgrounds_with_roundedCorners.size()-1);


        if(actualFolder.getKind().equals("Esma")){

            holder.intent_to_nextScreen=new Intent(context, Esma_Screen.class);

        }

        else if(actualFolder.getKind().equals("Ayetler")){
            holder.folder_image.setImageDrawable(context.getDrawable(R.drawable.quran3));
            holder.intent_to_nextScreen=new Intent(context, AyetGrubu_Screen.class);}
        else if(actualFolder.getKind().equals("Dua")){
            holder.folder_image.setImageDrawable(context.getDrawable(R.drawable.dua));
            holder.intent_to_nextScreen=new Intent(context, Dua_Screen.class);}
        else if(actualFolder.getKind().equals("Salavat")){
            holder.folder_image.setImageDrawable(context.getDrawable(R.drawable.muhammed_sav2));
            holder.intent_to_nextScreen=new Intent(context, Salavat_Screen.class);}
        else if(actualFolder.getKind().equals("Vird")){
            holder.folder_image.setImageDrawable(context.getDrawable(R.drawable.ic_heart_liked));
            holder.intent_to_nextScreen=new Intent(context, Favoriler_Screen.class);}
        else if(actualFolder.getKind().equals("Tesbih")){
            holder.folder_image.setImageDrawable(context.getDrawable(R.drawable.tesbih_yeni));
            holder.intent_to_nextScreen=new Intent(context, Tesbih_Screen.class);}
        else{

            Log.e("nullReference","Folder kind yok!");

        }

        for(View view:holder.onclickItems){


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (holder.intent_to_nextScreen!=null) {
                        context.startActivity(holder.intent_to_nextScreen);
                    }
                    
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return folderList.size();
    }

    class VirdScreen_ViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout linearLayout;
        private CardView cardView;
        private TextView folder_Text;
        private ArrayList<View> onclickItems;
        private ConstraintLayout constraintLayout;
        public Intent intent_to_nextScreen;
        private ImageView folder_image;


        public VirdScreen_ViewHolder(@NonNull View itemView) {
            super(itemView);


            linearLayout=itemView.findViewById(R.id.folder_cv_top_linear);
            cardView=itemView.findViewById(R.id.folder_cv_vird);
            folder_Text=itemView.findViewById(R.id.txt_nameof_folder);
            constraintLayout=itemView.findViewById(R.id.folder_cv_constraint_layout);
            onclickItems=new ArrayList<>();
            onclickItems.add(cardView);
            onclickItems.add(linearLayout);
            folder_image=itemView.findViewById(R.id.folder_image);



        }
    }
}


