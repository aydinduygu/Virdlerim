package com.codeforlite.virdlerim.RV_Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codeforlite.virdlerim.ModelClasses.Vird_Classes.Ayet;
import com.codeforlite.virdlerim.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Add_AyetGroup_RV_Adapter extends RecyclerView.Adapter<Add_AyetGroup_RV_Adapter.Ayet_Card_Holder> {

    private Context context;
    private List<Ayet> ayetListesi;

    public List<Ayet> getAyetListesi() {
        return ayetListesi;
    }

    public Add_AyetGroup_RV_Adapter(Context context, List<Ayet> ayetListesi) {
        this.context = context;
        this.ayetListesi=ayetListesi;
    }

    @NonNull
    @Override
    public Ayet_Card_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ayet_toadd,null,false);

        return new Ayet_Card_Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Ayet_Card_Holder holder, int position) {

        Ayet ayet=ayetListesi.get(position);
        holder.txt_ayetMetni.setText(""+ayet.getArabicText());
        holder.txt_sureAdı.setText(ayet.getSureAdı());
        holder.txt_ayetNo.setText(ayet.getAyetNo()+". Ayet");
        holder.setIsRecyclable(false);
        holder.button_removefromList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ayetListesi.remove(ayetListesi.get(position));
                notifyDataSetChanged();
            }
        });

    }



    @Override
    public int getItemCount() {
        return ayetListesi.size();
    }

    class Ayet_Card_Holder extends RecyclerView.ViewHolder{

        private TextView txt_sureAdı;
        private TextView txt_ayetNo;
        private TextView txt_ayetMetni;
        private FloatingActionButton button_removefromList;

        public Ayet_Card_Holder(@NonNull View itemView) {
            super(itemView);

            txt_sureAdı=itemView.findViewById(R.id.txt_card_add_sureadı);
            txt_ayetNo=itemView.findViewById(R.id.txt_card_add_ayetno);
            txt_ayetMetni=itemView.findViewById(R.id.txt_card_add_ayettext);
            button_removefromList=itemView.findViewById(R.id.button_card_removefromlist);

        }
    }
}
