package com.codeforlite.virdlerim.RV_Adapters;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codeforlite.virdlerim.ModelClasses.Vird_Classes.AyetGrubu;
import com.codeforlite.virdlerim.ModelClasses.Vird_Classes.Vird;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyRVAdapter extends RecyclerView.Adapter<MyViewHolder>  implements Filterable, Serializable {


    protected List<Vird> comingList;
    protected List<Vird> virdListAll;
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
            comingList.addAll((Collection<? extends Vird>) results.values);
            notifyDataSetChanged();

        }
    };
    private Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }


}
