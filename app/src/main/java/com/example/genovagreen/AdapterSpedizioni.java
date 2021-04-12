package com.example.genovagreen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterSpedizioni extends RecyclerView.Adapter<AdapterSpedizioni.myviewholder> {
    ArrayList<Spedizione> list;
    String key;

    public AdapterSpedizioni(ArrayList<Spedizione> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public AdapterSpedizioni.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder_spedizioni, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSpedizioni.myviewholder holder, final int position) {
        holder.organizzatoretext.setText(list.get(position).getOrganizzatore());
        holder.datatext.setText(list.get(position).getData());
        holder.oratext.setText(list.get(position).getOra());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), Spedizioni5.class);
                intent.putExtra("luogo",list.get(position).getLuogo().trim());
                intent.putExtra("data",list.get(position).getData().trim());
                intent.putExtra("descrizione",list.get(position).getDescrizione().trim());
                intent.putExtra("ora",list.get(position).getOra().trim());
                intent.putExtra("organizzatore",list.get(position).getOrganizzatore().trim());
                intent.putExtra("partecipanti",String.valueOf(list.get(position).getPartecipanti()));
                intent.putExtra("chiave", key);
                v.getContext().startActivity(intent);
            }

        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setKey(String key) {
        this.key=key;
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView organizzatoretext, datatext, oratext;
        CardView card;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            organizzatoretext=itemView.findViewById(R.id.organizzatore);
            datatext=itemView.findViewById(R.id.data);
            oratext=itemView.findViewById(R.id.ora);
            card=itemView.findViewById(R.id.card_holder);
        }
    }

}

