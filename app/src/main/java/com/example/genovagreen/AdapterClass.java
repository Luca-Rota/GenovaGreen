package com.example.genovagreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewHolder> {
    ArrayList<Oggetto> list;

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView rifiuto, cassonetto;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            rifiuto=itemView.findViewById(R.id.rifiutoId);
            cassonetto=itemView.findViewById(R.id.cassonettoId);
        }
    }

    public AdapterClass(ArrayList<Oggetto> list){
        this.list=list;
    }
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_holder,viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder( MyViewHolder myViewHolder, int i) {
            myViewHolder.rifiuto.setText(list.get(i).getRifiuto());
            myViewHolder.cassonetto.setText(list.get(i).getCassonetto());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }


    }

