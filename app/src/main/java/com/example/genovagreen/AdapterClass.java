package com.example.genovagreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewHolder> implements Filterable {
    private List<Oggetto> list;
    private List<Oggetto> listFull;

    @Override
    public Filter getFilter() {
        return filter;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView rifiuto, cassonetto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rifiuto = itemView.findViewById(R.id.rifiutoId);
            cassonetto = itemView.findViewById(R.id.cassonettoId);
        }
    }
    AdapterClass(List<Oggetto> list){
        this.list=list;
        listFull=new ArrayList<>(list);
}

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_holder,viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder( MyViewHolder myViewHolder, int i) {
        Oggetto oggettoAttuale= list.get(i);
            myViewHolder.rifiuto.setText(oggettoAttuale.getRifiuto());
            myViewHolder.cassonetto.setText(oggettoAttuale.getCassonetto());


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        private Filter filter=new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Oggetto> filterList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filterList.addAll(listFull);
                }
                else{
                    String pattrn=constraint.toString().toLowerCase().trim();
                    for(Oggetto ogg:listFull){
                        if(ogg.getRifiuto().toLowerCase().contains(pattrn)){
                            filterList.add(ogg);
                        }
                    }
            }
                FilterResults filterResults=new FilterResults();
                filterResults.values=filterList;
                return filterResults;
        }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
              list.clear();
              list.addAll((List)results.values);
              notifyDataSetChanged();
            }
        };
    }

