package com.codeforlite.virdlerim.RV_Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.codeforlite.virdlerim.BaseActivity;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_AyahGroups_Page;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_Dua_Page;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_Esma_Page;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_FavouritePrayers_Page;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_Salavat_Page;
import com.codeforlite.virdlerim.Fragments.Pages.Fragment_Tesbih_Page;
import com.codeforlite.virdlerim.ModelClasses.Folder;
import com.codeforlite.virdlerim.R;
import com.codeforlite.virdlerim.folderColorList_Interface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FolderScreen_RVAdapter extends RecyclerView.Adapter<FolderScreen_RVAdapter.VirdScreen_ViewHolder> implements folderColorList_Interface {

    private Context context;
    private List<Folder> folderList;
    private FloatingActionButton button_addFolder2;
    private EditText editText_folderName;
    private LinearLayout layout;
    private List<Integer> backgrounds_with_roundedCorners;


    @RequiresApi(api = Build.VERSION_CODES.N)
    public FolderScreen_RVAdapter(Context context, List<Folder> folderList, ActionBar toolbar) {

        this.context = context;
        this.folderList = folderList;
        fillColorList();
        layout = new LinearLayout(context);

    }

    public LinearLayout getLayout() {
        return layout;
    }

    public void setLayout(LinearLayout layout) {
        this.layout = layout;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void fillColorList() {

        backgrounds_with_roundedCorners = Arrays.stream(BACKGROUNDCOLOR_WITH_ROUNDEDCORNERS).boxed().collect(Collectors.toList());

    }


    @NonNull
    @Override
    public VirdScreen_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_folder, parent, false);
        return new VirdScreen_ViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull VirdScreen_ViewHolder holder, int position) {

        Folder actualFolder = folderList.get(position);

        holder.folder_Text.setText(actualFolder.getFolderName());


        if (backgrounds_with_roundedCorners.size() == 0) {
            fillColorList();
        }
        holder.linearLayout.setBackground(context.getResources().getDrawable(backgrounds_with_roundedCorners.get(backgrounds_with_roundedCorners.size() - 1)));
        //holder.constraintLayout.setBackground(context.getResources().getDrawable(drawables.get(drawables.size()-1)));
        holder.cardView.setBackground(context.getResources().getDrawable(backgrounds_with_roundedCorners.get(backgrounds_with_roundedCorners.size() - 1)));
        backgrounds_with_roundedCorners.remove(backgrounds_with_roundedCorners.size() - 1);


        holder.folder = folderList.get(position);


        switch (holder.folder.getKind()) {

            case "Esma":
                holder.folder_image.setImageDrawable(context.getDrawable(R.drawable.foldericon_allah_text));
                holder.folder_image.getLayoutParams().width = (int) (118 * Resources.getSystem().getDisplayMetrics().density);
                holder.folder_image.getLayoutParams().height = (int) (118 * Resources.getSystem().getDisplayMetrics().density);
                holder.nextFragment = new Fragment_Esma_Page();
                holder.toolbarTitle="Esmaül Hüsna";
                break;
            case "Ayetler":
                holder.folder_image.setImageDrawable(context.getDrawable(R.drawable.foldericon_quran2));
                holder.nextFragment = new Fragment_AyahGroups_Page();
                holder.toolbarTitle="Ayetler";
                break;
            case "Dua":
                holder.folder_image.setImageDrawable(context.getDrawable(R.drawable.foldericon_dua));
                holder.nextFragment = new Fragment_Dua_Page();
                holder.toolbarTitle="Dualar";
                break;
            case "Salavat":
                holder.folder_image.setImageDrawable(context.getDrawable(R.drawable.foldericon_muhammed_sav));
                holder.nextFragment = new Fragment_Salavat_Page();
                holder.toolbarTitle="Salavat-ı Şerife";
                break;

            case "Tesbih":
                holder.folder_image.setImageDrawable(context.getDrawable(R.drawable.foldericon_tesbih));
                holder.nextFragment = new Fragment_Tesbih_Page();
                holder.toolbarTitle="Tesbihler";
                break;

            case "Vird":
                holder.folder_image.setImageDrawable(context.getDrawable(R.drawable.ic_heart_liked));
                holder.nextFragment = new Fragment_FavouritePrayers_Page();
                holder.toolbarTitle="Favorilerim";
                break;

            default:

                Log.e("nullReference", "Folder kind yok!");
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.nextFragment != null) {
                    ((BaseActivity) context)
                            .getSupportFragmentManager()
                            .beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.fade_out)
                            .replace(R.id.base_container, holder.nextFragment)
                            .commit();

                    ((AppCompatActivity)((Activity)context)).getSupportActionBar().setTitle(holder.toolbarTitle);

                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return folderList.size();
    }

    class VirdScreen_ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout linearLayout;
        private CardView cardView;
        private TextView folder_Text;
        private ArrayList<View> onclickItems;
        private Fragment nextFragment;
        private ImageView folder_image;
        private Folder folder;
        private String toolbarTitle;

        public VirdScreen_ViewHolder(@NonNull View itemView) {
            super(itemView);


            linearLayout = itemView.findViewById(R.id.folder_cv_top_linear);
            cardView = itemView.findViewById(R.id.folder_cv_vird);
            folder_Text = itemView.findViewById(R.id.txt_nameof_folder);
            onclickItems = new ArrayList<>();
            onclickItems.add(cardView);
            onclickItems.add(linearLayout);
            folder_image = itemView.findViewById(R.id.folder_image);



        }
    }
}


