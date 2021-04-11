package com.example.genovagreen;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


import java.util.ArrayList;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;
import static java.security.AccessController.getContext;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.myviewholder> {
    ArrayList<Oggetto> list;
    public AdapterClass(ArrayList<Oggetto> list){
        this.list=list;
    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.rifiutotext.setText(list.get(position).getRifiuto());
        holder.cassonettotext.setText(list.get(position).getCassonetto());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView rifiutotext, cassonettotext;
        ImageView cassonettoimg;
        CardView card;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            rifiutotext=itemView.findViewById(R.id.rifiutoId);
            cassonettotext=itemView.findViewById(R.id.cassonettoId);
            card=itemView.findViewById(R.id.card_holder);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("prova","porcaccio il dio");
                }
            });

        }
    }

}

